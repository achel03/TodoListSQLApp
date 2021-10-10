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

import com.todo.service.DbConnect;
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
			e.printStackTrace();
		}
		return t;
	}
	public int addItem(TodoItem t) {
		String sql = "insert into list (title, memo, category, current_date, due_date)"+" values (?,?,?,?,?);";
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
		String sql = "update list set title=?, memo=?, category=?, current_date=?, due_date=?"+" where id = ?;";
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
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date= rs.getString("current_date");
				TodoItem t = new TodoItem(title,description,category,due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
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
			String sql = "insert into list (title, memo, category, current_date, due_date)"+" values (?,?,?,?,?);";
			int records = 0;
			while((oneline=r.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(oneline,"##"); 
				String category = st.nextToken();
				String title = st.nextToken();
				String description = st.nextToken();
				String current_date = st.nextToken();
				String due_date = st.nextToken();
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, description);
				pstmt.setString(3, category);
				pstmt.setString(4, current_date);
				pstmt.setString(5, due_date);
				int count = pstmt.executeUpdate();
				if(count>0)records++;
				pstmt.close();	
			}
			System.out.println(records+"개의 항목들을 가져왔습니다 :)");
			r.close();
		}catch(Exception e) {
			e.printStackTrace();
		} 
	}
//	public Boolean isDuplicate(String title) {
//		for (TodoItem item : list) {
//			if (title.equals(item.getTitle())) return true;
//		}
//		return false;
//	}
	public ArrayList<TodoItem> getList(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%"+keyword+"%";
		try {
			String sql = "SELECT * FROM list WHERE title like ? or memo like ?";
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
		Statement stmt;
		String sql = "SELECT DISTINCT category FROM list";
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
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
			String sql = "SELECT * FROM list ORDER BY "+orderby;
			if(ordering==0) {
				sql+=" desc";
			}
			ResultSet rs = pstmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
