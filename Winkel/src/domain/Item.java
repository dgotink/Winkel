package domain;

import algorithms.Borrowed;
import algorithms.Damaged;
import algorithms.Lendable;


public class Item
{
	private String title;
	private ItemType type;
	private Validator validator = new Validator();
	private IState borrowedState;
	private IState damagedState;
	private IState lendableState;
	private IState state;
	
	public Item(ItemType type, String title) throws IllegalArgumentException
	{
		this.setType(type);
		this.setTitle(title);
		this.borrowedState = new Borrowed(this);
		this.damagedState = new Damaged(this);
		this.lendableState = new Lendable(this);
		this.state = lendableState;
	}
	
	private void setTitle(String title) throws IllegalArgumentException
	{
		if(!validator.isValidString(title))
			throw new IllegalArgumentException("Invalid title");
		this.title = title;
	}
	
	private void setType(ItemType type) throws IllegalArgumentException	
	{
		if(!validator.isValidType(type))
			throw new IllegalArgumentException("Invalid type");
		this.type = type;
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public ItemType getType(){
		return this.type;
	}
	
	public double getPrice(){
		return this.type.getPrice();
	}
		
	public void borrow()
	{
		this.state.borrow();
	}
	
	public void returnMe()
	{
		this.state.returnMe();
	}
	
	public void repair()
	{
		this.state.repair();
	}
	
	public void setState(IState state)
	{
		this.state = state;
	}
	
	public IState getBorrowedState()
	{
		return this.borrowedState;
	}
	
	public IState getLendableState()
	{
		return this.lendableState;
	}
	
	public IState getDamagedState()
	{
		return this.damagedState;
	}
	
	public IState getState()
	{
		return this.state;
	}
	@Override
	public boolean equals(Object o) {
		if(o != null){
			if(o instanceof Item){
				Item temp = (Item) o;
				return (temp.getTitle().equals(this.title) && temp.getType().equals(this.type));
			}
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		return this.type.getType() + " with title " + this.getTitle() + ", " + "price: " 
				+ this.type.getPrice() + ", " + this.getState().toString() + ", ";
	}
	
	public String toTXT()
	{
		return this.type.getType() + ";"  + this.getTitle() + ";" + this.type.getPrice() 
				+ ";" + this.getState().toString() + ";";
	}
	
	public void setPrice(double price) throws IllegalArgumentException
	{
		if(!validator.isValidPrice(price))
			throw new IllegalArgumentException("Invalid price");
		this.type.setPrice(price);
	}
}
