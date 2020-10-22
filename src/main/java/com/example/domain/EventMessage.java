package com.example.domain;

import java.io.Serializable;
public class EventMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	String description;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public EventMessage(Long id, String description) {
		this.id = id;
		this.description = description;
	}
	public EventMessage() {
	}
}
