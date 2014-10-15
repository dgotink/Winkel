package domain;

import java.util.LinkedList;
import java.util.List;

public class Customer {
	private String name;
	private String email;
	private boolean registered;
	private List<String> messages;
	
	public Customer(String name, String email){
		setName(name);
		setEmail(email);
		setRegistered(false);
		messages = new LinkedList<String>();
	}
	
	public void setName(String name) throws IllegalArgumentException{
		if(name != null) this.name = name;
		else throw new IllegalArgumentException("String is null");
	}
	
	public void setEmail(String email) throws IllegalArgumentException{
		if(email != null){
			if(email.contains("@")) this.email = email;
			else throw new IllegalArgumentException("No '@', invalid email-adress");
		}
		else throw new IllegalArgumentException("String is null");
	}
	
	public void setRegistered(boolean value){
		this.registered = value;
	}
	
	public String getName(){
		return name;
	}
	
	public String getEmail(){
		return email;
	}
	
	public boolean getRegistered(){
		return registered;
	}
	
	public List<String> getMessages(){
		return messages;
	}
	
	public void addMessage(String message)throws IllegalArgumentException{
		if(message == null) throw new IllegalArgumentException("Message is null.");
		else {
			messages.add(message);
			if(messages.size() > 3) messages.remove(0);
		}
	}
	
	public String toString(){
		return name + " " + email;
	}
	
	public String toTXT(){
		return name + ";" + email;
	}
}
