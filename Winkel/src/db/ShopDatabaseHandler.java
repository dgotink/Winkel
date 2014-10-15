package db;

import java.io.File;
import java.io.IOException;

import domain.Shop;

public abstract class ShopDatabaseHandler
{

	private File file;
	private Shop shop;
	private IReaderAndWriter readerAndWriter;
	
	public ShopDatabaseHandler(String filename, Shop shop) throws DbException, IOException
	{
		this.setFile(filename);
		this.setShop(shop);
	}

	public File getFile() 
	{
		return this.file;
	}

	private void setFile(String filename) throws DbException, IOException
	{
		try
		{
			this.file = new File(filename);
			this.file.createNewFile();
		}
		catch(NullPointerException e)
		{
			throw new DbException("file not valid");
		}		
	}
	
	public Shop getShop() 
	{
		return this.shop;
	}

	public void setShop(Shop shop)
	{
		this.shop = shop;
	}
	
	public void setReadAndWriteAlgorithm(IReaderAndWriter readerAndWriter)
	{
		this.readerAndWriter = readerAndWriter;
	}
	
	public void writeItem() throws DbException
	{
		this.readerAndWriter.writeItem(file, shop);
	}
	
	public Shop readItem() throws DbException
	{
		return this.readerAndWriter.readItem(file, shop);
	}
	
	public void writeCustomer() throws DbException
	{
		this.readerAndWriter.writeCustomer(file, shop);
	}
	
	public Shop readCustomer() throws DbException
	{
		return this.readerAndWriter.readCustomer(file, shop);
	}
	
}
