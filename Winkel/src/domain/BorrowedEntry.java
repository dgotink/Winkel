package domain;

import java.util.Date;

public class BorrowedEntry {
	
	private Customer customer;
	private Date date;
	
	public BorrowedEntry(Customer customer, Date date){
		setCustomer(customer);
		setDate(date);
	}

	private void setCustomer(Customer customer) throws IllegalArgumentException{
		if(customer != null) this.customer = customer;
		else throw new IllegalArgumentException("Customer is null.");
		
	}
	
	private void setDate(Date date) throws IllegalArgumentException{
		if(date != null) this.date = date;
		else throw new IllegalArgumentException("Date is null.");
		
	}
	
	public Customer getCustomer(){
		return customer;
	}
	
	public Date getDate(){
		return date;
	}

}
