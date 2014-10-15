package db;

import java.io.IOException;

import algorithms.XMLReadAndWriteAlgorithm;
import domain.Shop;

public class XMLReaderAndWriter extends ShopDatabaseHandler
{
	
	public XMLReaderAndWriter(String filename, Shop shop) throws DbException, IOException 
	{
		super(filename, shop);
		setReadAndWriteAlgorithm(new XMLReadAndWriteAlgorithm());
	}
}
