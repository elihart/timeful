package com.example.timeful;

import java.util.Date;

public class TodoItem {
	private String title;
	private Date dueDate;
	
	public TodoItem(String title, Date dueDate) {
		this.title = title;
		this.dueDate = dueDate;
	}
	
	public String getTitle() {
		return title;
	}

	/**
	 * 
	 * @return Can  be null.
	 */
	public Date getDueDate() {
		return dueDate;
	}
	

}
