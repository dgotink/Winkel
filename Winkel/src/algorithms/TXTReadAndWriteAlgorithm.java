package algorithms;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JOptionPane;

import db.DbException;
import db.IReaderAndWriter;
import domain.Customer;
import domain.Item;
import domain.ItemType;
import domain.Shop;

public class TXTReadAndWriteAlgorithm implements IReaderAndWriter 
{
	private PrintWriter printer;
	private Scanner scanner;
	
	public TXTReadAndWriteAlgorithm() throws DbException, IOException 
	{
	}
	
	@Override
	public void writeItem(File file, Shop shop)
	{
		try{
			printer = new PrintWriter(file); 
			for(Item item: shop.getValueList())
			{
				String line = item.toTXT() + shop.getKey(item) + ";";
				printer.println(line);
			}
			if (printer !=null)
				printer.close();
		}
		catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}
	
	@Override
	public Shop readItem(File file, Shop shop){
		try {
			scanner = new Scanner(file);
			Scanner lijn = null;
			if(shop == null)
				shop = new Shop();
					
			while(scanner.hasNext()){
				lijn = new Scanner(scanner.nextLine());
				lijn.useDelimiter(";");
				
				String stringType = lijn.next();
				String title = lijn.next();
				String priceString = lijn.next();
				String state = lijn.next();
				String id = lijn.next();
				
				ItemType type = ItemType.valueOf(stringType.toUpperCase());
				double price = Double.parseDouble(priceString);
				
				Item newItem = new Item(type, title);
				newItem.setPrice(price);
				
				if(state.equals("borrowed"))
					newItem.borrow();
				if(state.equals("damaged"))
					newItem.setState(new Damaged(newItem));
				//else item is available
				shop.addItem(id, newItem);
			}
			if (scanner!=null){
				scanner.close();
			}
			if (lijn!=null){
				lijn.close();
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return shop;
	}

	@Override
	public void writeCustomer(File file, Shop shop) throws DbException {
		try{
			printer = new PrintWriter(file); 
			for(Customer customer: shop.getCustomerValueList())
			{
				String line = customer.toTXT() + shop.getKey(customer) + ";";
				printer.println(line);
			}
			if (printer !=null)
				printer.close();
		}
		catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}

	@Override
	public Shop readCustomer(File file, Shop shop) throws DbException {
		try {
			scanner = new Scanner(file);
			Scanner lijn = null;
			if(shop == null)
				shop = new Shop();
					
			while(scanner.hasNext()){
				lijn = new Scanner(scanner.nextLine());
				lijn.useDelimiter(";");
				
				String name = lijn.next();
				String email = lijn.next();
			
				shop.addCustomer(name, email);
				
			}
			if (scanner!=null){
				scanner.close();
			}
			if (lijn!=null){
				lijn.close();
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return shop;
	}
}
