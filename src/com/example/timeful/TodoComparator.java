package com.example.timeful;

import java.util.Comparator;

public class TodoComparator implements Comparator<TodoItem> {

	@Override
	public int compare(TodoItem t1, TodoItem t2) {
		if (t1.getDueDate() != null && t2.getDueDate() != null) {
			return t1.getDueDate().compareTo(t2.getDueDate());
		}

		if (t1.getDueDate() != null) {
			return -1;
		}

		if (t2.getDueDate() != null) {
			return 1;
		}

		return t1.getTitle().compareTo(t2.getTitle());
	}

}
