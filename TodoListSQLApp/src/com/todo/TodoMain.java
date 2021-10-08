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
