package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByDateDesc;
import com.todo.service.TodoSortByName;

public class TodoList {
	private Connection conn;

	public TodoList() {
		this.conn = DbConnect.getConnection();
	}
	public TodoItem getItem(int index) {
		Statement stmt;
		TodoItem t = null;
		String sql = "SELECT id, title, memo, category, current_date, due_date";
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.getRow()<1)
				return null;
			rs.next();
			int id = rs.getInt("id");
			String category = rs.getString("category");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}
	public int addItem(TodoItem t) {
		String sql = "insert into list (title, memo, category, current_date, due_date"+" values(?,?,?,?,?);";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			count = pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	public int updateItem(TodoItem t) {
		String sql = "update list set title=?, memo=?, category=?, current_date=?, due_date=?"+" where id=?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setInt(6, t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	public int deleteItem(int index) {
		String sql = "delete from list where id=?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return count;
		
	}
	public ArrayList<TodoItem> getList(){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list";
			ResultSet rs;
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String des = rs.getString("des");
				String due = rs.getString("due");
				String cur = rs.getString("cur");
				TodoItem t = new TodoItem(category,title,des,due);
				t.setId(id);
				t.setCurrent_date(cur);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public int getCount() {
		Statement stmt;
		int count = 0;
		try {
			stmt = conn.createStatement();
		String sql = "SELECT count(id) FROM list;";
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		count = rs.getInt("count(id)");
		stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	public void importData(String filename) {
		try {
			BufferedReader r = new BufferedReader(new FileReader(filename));
			String oneline;
			String sql = "insert into list (title, memo, category, current_date, due_date"+" values(?,?,?,?,?);";
			int records = 0;
			while((oneline=r.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(oneline,"##"); 
				String cat = st.nextToken();
				String tit = st.nextToken();
				String des = st.nextToken();
				String cur_d = st.nextToken();
				String due = st.nextToken();
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, tit);
					pstmt.setString(2, des);
					pstmt.setString(3, cat);
					pstmt.setString(4, cur_d);
					pstmt.setString(5, due);
					int count = pstmt.executeUpdate();
					if(count>0)records++;
					pstmt.close();	
			}
			System.out.println(records+"개의 항목들을 가져왔습니다 :)");
			r.close();
		}catch(FileNotFoundException e) {
			System.out.println("todolist.txt 파일이 없습니다.");
		}catch (IOException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	public boolean isDuplicate(String title) {
		// TODO Auto-generated method stub
		return false;
	}
	public ArrayList<TodoItem> getList(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%"+keyword+"%";
		String sql = "SELECT * FROM list WHERE title like ? or memo like ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<String> getCategories() {
		ArrayList<String> list = new ArrayList<String>();
		Statement pstmt;
		String sql = "SELECT DISTINCT category FROM list";
		try {
			pstmt = conn.createStatement();
			ResultSet rs = pstmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<String> getListCategories(String keyword2) {
		ArrayList<String> list = new ArrayList<String>();
		PreparedStatement pstmt;
		String sql = "SELECT * FROM list WHERE category = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword2);
			ResultSet rs = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement pstmt;
		try {
			pstmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY title DESC"+orderby;
			if(ordering==0) {
				sql+="desc";
			}
			ResultSet rs = pstmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
