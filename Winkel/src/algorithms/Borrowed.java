package algorithms;

import domain.IState;
import domain.Item;

public class Borrowed implements IState 
{
	private Item item;
	
	public Borrowed(Item item) 
	{
		this.item = item;
	}

	@Override
	public void borrow() throws IllegalArgumentException
	{
		throw new IllegalArgumentException("You can't borrow an item that already has been borrowed.");
	}

	@Override
	public void returnMe() 
	{
		if(item.getState() != item.getDamagedState())
		{
			this.item.setState(item.getLendableState());
		}
		else
		{
			this.item.setState(item.getDamagedState());
		}
	}

	@Override
	public void repair() throws IllegalArgumentException
	{
		throw new IllegalArgumentException("You can't repair an item that is not returned.");
	}
	
	@Override
	public String toString()
	{
		return "borrowed";
	}

}
