package com.todo.dao;

import java.util.*;

import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByDateDesc;
import com.todo.service.TodoSortByName;

public class TodoList {
	private List<TodoItem> list;

	public TodoList() {
		this.list = new ArrayList<TodoItem>();
	}

	public void addItem(TodoItem t) {
		list.add(t);
	}

	public void deleteItem(int check) {
		list.remove(check);
	}

	void editItem(TodoItem t, TodoItem updated) {
		int index = list.indexOf(t);
		list.remove(index);
		list.add(updated);
	}

	public ArrayList<TodoItem> getList() {
		return new ArrayList<TodoItem>(list);
	}

	public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}
	
	public void sortByDateDesc() {
		Collections.sort(list, new TodoSortByDateDesc());

	}

	public void listAll() {
		System.out.println("\n"
				+ "inside list_All method\n");
		for (TodoItem myitem : list) {
			System.out.println(myitem.getTitle() + myitem.getDesc());
		}
	}
	public void listCate() {
		HashSet<String> li = new HashSet<String>();
		for (TodoItem myitem : list) {
			li.add((String)(myitem.getCategory()));
		}
		System.out.println(li);
	}
	public void find(String keyword){ // equals 사용해서 같은 값을 가지면 반환해
		
		for (TodoItem item : list) {
//			if (keyword.equals(item.getTitle())||keyword.equals(item.getDesc())) {
//				System.out.println("|"+". [ " + item.getCategory()+" ] "+ item.getTitle() + " | " + item.getDesc()+" Time: "+item.getCurrent_date()+" - "+item.getDue_date());
//			}
			if (item.getTitle().contains(keyword)||item.getDesc().contains(keyword)) {
				System.out.println("|"+". [ " + item.getCategory()+" ] "+ item.getTitle() + " | " + item.getDesc()+" Time: "+item.getCurrent_date()+" - "+item.getDue_date());
			}
		}
	}
	public void find_cate(String keyword) {
		for (TodoItem item : list) {
			if(item.getCategory().contains(keyword)) {
				System.out.println("|"+". [ " + item.getCategory()+" ] "+ item.getTitle() + " | " + item.getDesc()+" Time: "+item.getCurrent_date()+" - "+item.getDue_date());
			}
		}
	}
	public void listOne(int index) {
		System.out.println((index+1)+". [ "+list.get(index).getCategory()+" ] "+ list.get(index).getTitle() + " | " + list.get(index).getDesc()+" Time: "+list.get(index).getCurrent_date()+" - "+list.get(index).getDue_date());
		
	}
	
	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}

	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}

	public Boolean isDuplicate(String title) {
		for (TodoItem item : list) {
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}

	public void addItem(TodoItem t, int check) {
		list.add(check, t);
	}

}
