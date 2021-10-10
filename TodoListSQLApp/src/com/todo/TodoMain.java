package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		l.importData("todolist.txt");
		boolean isList = false;
		boolean quit = false;
//		TodoUtil.BuffReader(l);
		do {
			Menu.prompt();
			isList = false;
			String choice = sc.nextLine();
			switch (choice) {
			
			case "help":
				Menu.displaymenu();
				break;
				
			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name_asc":
				TodoUtil.listAll(l,"title",1);
				System.out.println("제목순으로 정렬하였습니다..");
				isList = true;
				break;

			case "ls_name_desc":
				TodoUtil.listAll(l,"title",0);
				System.out.println("제목역순으로 정렬하였습니다.");
				isList = true;
				break;
				
			case "ls_date":
				TodoUtil.listAll(l,"due_date",1);
				isList = true;
				break;
				
			case "ls_date_desc":
				TodoUtil.listAll(l,"due_date",0);
				isList = true;
				break;
				
			case "ls_cate":
				TodoUtil.listCateAll(l);
				isList = true;
				break;
				
			case "find":
				String keyword = sc.nextLine().trim();
				TodoUtil.findList(l,keyword);
				break;
			
			case "find_cate":
				String keyword2 = sc.nextLine().trim();
				TodoUtil.findCateList(l,keyword2);
				break;
				
			case "exit":
				quit = true;
				TodoUtil.fileWriter(l);
				break;

			default:
				
			}
			
		} while (!quit);
	sc.close();
	}
}
