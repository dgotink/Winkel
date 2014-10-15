package domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import algorithms.Damaged;
import algorithms.NoDiscount;

public class Shop
{
	private Map<String, Item> items;
	private Map<String, Customer> customers;
	private Validator validator = new Validator();
	private IDiscount discountstrategy;
	
	public Shop()
	{
		this.items = new HashMap<String, Item>();
		this.customers = new HashMap<String, Customer>();
		discountstrategy = new NoDiscount();
	}
	
	public void setDiscountStrategy(IDiscount type)
	{
		this.discountstrategy = type;
	}
	
	public void addItem(ItemType type, String id, String titel) throws IllegalArgumentException
	{
		if(type == null || !validator.isValidString(id) || !validator.isValidString(titel))
			throw new IllegalArgumentException("Invalid item");
		items.put(id , new Item(type, titel));
		
	}
	
	public void addItem(String id, Item item) throws IllegalArgumentException
	{
		if(item == null)
			throw new IllegalArgumentException("Invalid item");
		items.put(id, item);	
	}
	
	public void addCustomer(String name, String email) throws IllegalArgumentException{
		if(!validator.isValidString(name) || !validator.isValidString(email))
			throw new IllegalArgumentException("Invalid item");
		Integer count = customers.size() +1;
		customers.put(count.toString(), new Customer(name, email));
	}
			
	public Item getItem(String id) throws IllegalArgumentException
	{
		if(!items.containsKey(id)) throw new IllegalArgumentException("Invalid id");
		return	items.get(id);
	}
		
	public String getKey(Item item)
	{
		for(Entry<String, Item> entry: items.entrySet())
		{
			if (item.equals(entry.getValue())) 
			{
	            return entry.getKey();
	        }
		}
		return null;
	}
	
	public String getKey(Customer customer)
	{
		for(Entry<String, Customer> entry: customers.entrySet())
		{
			if (customer.equals(entry.getValue())) 
			{
	            return entry.getKey();
	        }
		}
		return null;
	}
	
	public void returnItem(String id)
	{
		this.getItem(id).returnMe();
	}
	
	public void removeItem(String id) 
	{
		Item toRemove = this.getItem(id);
		if(toRemove.getLendableState() == toRemove.getState() || 
				toRemove.getDamagedState() == toRemove.getState())
		{
			this.items.remove(id);
		}
		else
		{
			throw new IllegalArgumentException("You can't remove an item that isn't available in the"
					+ "shop at the moment");
		}
	}
	
	public void borrowItem(String itemID, String customerID)
	{
		this.getItem(itemID).borrow();
		
	}
	
	public void borrowItem(String itemID, String customerName, String customerEmail)
	{
		this.getItem(itemID).borrow();
		addCustomer(customerName, customerEmail);
	}
	
	public void declareItemDamaged(String id)
	{
		this.getItem(id).setState(new Damaged(this.getItem(id)));
	}
	
	public void declareItemRepaired(String id)
	{
		this.getItem(id).repair();
	}
	
	public double getRentalPrice(String id, int days)
	{
		return this.getItem(id).getPrice() * days;
	}
	
	public double getFine(int days)
	{
		if(days >= 5) return 5*(days - 4);
		return 0;
	}
	
	public double getDiscount(String id, int days)
	{
		double price = this.getItem(id).getPrice();
		return discountstrategy.getDiscount(price, days);
	}
	
	public double getDiscountPrice(double price, int days)
	{
		return discountstrategy.getDiscount(price, days);
	}
	
	public double getTotalPrice(String id, int days)
	{
		return getRentalPrice(id, days) + getFine(days) - getDiscount(id, days);
	}
	
	public Map<String,Item> getItemList()
	{
		return this.items;
	}
	
	public Collection<Item> getValueList(){
		return this.items.values();
	}
	
	public Collection<Customer> getCustomerValueList(){
		return this.customers.values();
	}
	
	public String getItemString(String id) throws IllegalArgumentException
	{
		if(!items.containsKey(id)) throw new IllegalArgumentException("Invalid id");
		return items.get(id).toString() + "ID: " + id;
	}
	
	public String toString()
	{
		String uit = "";
		if(items.size() == 0)
			return "Er zijn geen items in de winkel";
		for(Entry<String, Item> i: items.entrySet())
		{
			uit += i.getValue().toString() + " ID: " + i.getKey() + "\n";
		}
		return uit;
	}
}
