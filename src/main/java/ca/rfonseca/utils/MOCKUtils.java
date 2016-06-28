package ca.rfonseca.utils;

import java.util.ArrayList;
import java.util.List;

import ca.rfonseca.model.Person;

public class MOCKUtils {

	
	public static List<Person> getPersonList(){
		
		List<Person> personList = new ArrayList<Person>();
		
		Person person1 = new Person("PersonName1");
		personList.add(person1);
		
		Person person2 = new Person("PersonName2");
		personList.add(person2);
		
		Person person3 = new Person("PersonName3");
		personList.add(person3);
		
		Person person4 = new Person("PersonName4");
		personList.add(person4);
		
		Person person5 = new Person("PersonName5");
		personList.add(person5);
		
		
		
		
		
		return personList;
		
		
	}
	
	
	
	
	
	
}
