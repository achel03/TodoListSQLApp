package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		TodoUtil.BuffReader(l);
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
				l.sortByName();
				System.out.println("제목순으로 정렬하였습니다..");
				isList = true;
				break;

			case "ls_name_desc":
				l.sortByName();
				l.reverseList();
				System.out.println("제목역순으로 정렬하였습니다.");
				isList = true;
				break;
				
			case "ls_date":
				l.sortByDate();
				isList = true;
				break;
				
			case "ls_date_desc":
				l.sortByDateDesc();
				isList = true;
				break;
				
			case "ls_cate":
				l.listCate();
				isList = true;
				break;
				
			case "exit":
				quit = true;
				TodoUtil.fileWriter(l);
				break;

			default:
				if(choice.contains("find_cate")) {
					String keyword = choice.replace("find_cate", " ").trim();
					l.find_cate(keyword);
				}else if(choice.contains("find")) {
					String keyword = choice.replace("find", " ").trim();
					l.find(keyword);
				}
				break;
//					else {
//					System.out.println("위의 항목 중 원하는 명령어 하나를 입력해주세요!~");
//					break;
//				}
				
			}
			
			if(isList) l.listAll();
		} while (!quit);
	sc.close();
	}
}
