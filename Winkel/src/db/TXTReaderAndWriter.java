package db;

import java.io.IOException;

import algorithms.TXTReadAndWriteAlgorithm;
import domain.Shop;

public class TXTReaderAndWriter extends ShopDatabaseHandler
{

	public TXTReaderAndWriter(String filename, Shop shop) throws DbException, IOException 
	{
		super(filename, shop);
		setReadAndWriteAlgorithm(new TXTReadAndWriteAlgorithm());
	}
}
