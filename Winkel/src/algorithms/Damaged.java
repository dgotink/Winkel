package algorithms;

import domain.IState;
import domain.Item;

public class Damaged implements IState 
{
	private Item item;
	
	public Damaged(Item item)
	{
		this.item = item;
	}
	
	@Override
	public void borrow() throws IllegalArgumentException
	{
		throw new IllegalArgumentException("You can't borrow a damaged item");
	}

	@Override
	public void returnMe() throws IllegalArgumentException
	{
		throw new IllegalArgumentException("You can't return a damaged and already returned item");
	}

	@Override
	public void repair()
	{
		this.item.setState(item.getLendableState());
	}
	
	@Override
	public String toString()
	{
		return "damaged";
	}
}
