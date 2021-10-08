package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc, category,due;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "=============== Create item Section ==============\n");
		System.out.print(">>> 할 일의 카테고리를 입력해주세요 : ");
		category = sc.next();
		sc.nextLine();
		
		System.out.print(">>> 할 일의 이름을 입력해주세요 : ");
		title = sc.next();
		sc.nextLine(); // 개행 문자 제거
		if (list.isDuplicate(title)) {
			System.out.printf("ERROR: 이미 존재하는 항목의 이름입니다!!!");
			return;
		}
		
		System.out.print(">>> 할 일의 내용을 입력해주세요 : ");
		desc = sc.nextLine();
		
		System.out.print(">>> 할 일의 마감일자를 입력해주세요 : ");
		due = sc.nextLine();
		
		TodoItem t = new TodoItem(category, title, desc, due);
		if(list.addItem(t)>0)
			System.out.println("항목이 추가되었습니다 :)");
	}
	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "=============== Delete Item Section ==============\n"
				+ ">>> 삭제할 할 일의 번호를 입력해주세요 : \n"
				+ "\n");
		int check = sc.nextInt();
		System.out.print("위 항목을 삭제하시겠습니까?(y/n)");
		char rg = sc.next().charAt(0);
		if(rg=='y') {
			if(l.deleteItem(check)>0)
				System.out.println("삭제되었습니다 :)");
		}
		
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "=============== Edit Item Section ==============\n"
				+ ">>> 수정하고 싶은 할 일의 번호를 입력해주세요 : \n"
				+ "\n");
		int check = sc.nextInt();

		sc.nextLine(); // 개행 문자 제거
		System.out.print(">>> 할 일의 카테고리를 입력해주세요 : ");
		String new_category = sc.next();

		System.out.print(">>> 새로운 할 일의 이름을 입력해주세요 : ");
		String new_title = sc.next().trim();

		sc.nextLine();
		System.out.print(">>> 새로운 할 일의 내용을 입력해주세요 : ");
		String new_description = sc.nextLine().trim();
		l.deleteItem(check-1);
		
		System.out.print(">>> 새로운 할 일의 마감일자를 입력해주세요 : ");
		String new_due = sc.nextLine();
		
		TodoItem t = new TodoItem(new_category, new_title, new_description, new_due);
		t.setId(check);
		if(l.updateItem(t)>0)
			System.out.println("항목이 수정되었습니다 :)");

	}

	public static void listAll(TodoList l) {
		System.out.println();
		System.out.println(".=========================TODOLIST====================================.");
		System.out.println("|                  	전체목록, 총 "+l.getCount()+"개			              |");
        System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");
        System.out.println("|                                                                     |");
		for (TodoItem item : l.getList()) {
			System.out.println("|"+" [ " + item.toString()+" ] ");
		}
		System.out.println("|                                                                     |");
		System.out.println("|.....................................................................|");
        System.out.println(".=====================================================================.");
        System.out.println();
        System.out.println();
        System.out.println();
	}
	
	public static void fileWriter(TodoList l) {
		try {
			Writer w = new FileWriter("todolist.txt");
			for (TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			System.out.println("항목들이 저장되었습니다 :)");
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void BuffReader(TodoList list) {
		try {
			BufferedReader r = new BufferedReader(new FileReader("todolist.txt"));
			String oneline;
			int count = 0;
			while((oneline=r.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(oneline,"##"); 
				String cat = st.nextToken();
				String tit = st.nextToken();
				String des = st.nextToken();
				String cur_d = st.nextToken();
				String due = st.nextToken();
				TodoItem t = new TodoItem(cat, tit, des, due);
				t.setCurrent_date(cur_d);
				list.addItem(t);
				count++;
			}
			System.out.println(count+"개의 항목들을 가져왔습니다 :)");
			r.close();
		}catch(FileNotFoundException e) {
			System.out.println("todolist.txt 파일이 없습니다.");
		}catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static boolean isExistCategory(List<String> clist, String cate) {
		for(String c: clist) if (c.equals(cate)) return true;
		return false;
	}
	public static void findList(TodoList l, String keyword) {
		int count = 0;
		for(TodoItem item: l.getList(keyword))
		{
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.\n",count);
		
	}
	public static void listCateAll(TodoList l) {
		int count = 0;
		for(String item : l.getCategories()) {
			System.out.print(item+" ");
			count++;			
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.\n",count);
		
	}
	public static void findCateList(TodoList l, String keyword2) {
		int count = 0;
		for(String item : l.getListCategories(keyword2)) {
			System.out.print(item.toString());
			count++;			
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.\n",count);
		
	}
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.println("|                  	전체목록, 총 "+l.getCount()+"개			              |");
		for(TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.print(item.toString());
		}
	}
}
