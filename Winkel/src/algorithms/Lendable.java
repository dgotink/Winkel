package algorithms;

import domain.IState;
import domain.Item;

public class Lendable implements IState 
{
	private Item item;
	
	public Lendable(Item item)
	{
		this.item = item;
	}

	@Override
	public void borrow() 
	{
		this.item.setState(item.getBorrowedState());
	}

	@Override
	public void returnMe() throws IllegalArgumentException 
	{
		throw new IllegalArgumentException("You can't return an item that hasn't been borrowed.");
	}

	@Override
	public void repair() throws IllegalArgumentException 
	{
		throw new IllegalArgumentException("You cant't repair an item that isn't damaged.");
	}
	
	@Override
	public String toString()
	{
		return "available";
	}

}
