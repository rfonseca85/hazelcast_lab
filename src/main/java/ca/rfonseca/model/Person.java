package ca.rfonseca.model;

import java.io.Serializable;

public class Person implements Serializable {


	private static final long serialVersionUID = 4216054642703802479L;

	private String name;
	
	public Person(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
