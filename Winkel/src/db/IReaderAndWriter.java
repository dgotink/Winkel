package db;

import java.io.File;

import domain.Shop;

/**
 * Interface for different read and write algorithms.
 * All read and write algorithms must implement these methods.
 * @author Liana & Dries
 *
 */
public interface IReaderAndWriter
{
	public void writeItem(File file, Shop shop) throws DbException;
	public Shop readItem(File file, Shop shop) throws DbException;
	public void writeCustomer(File file, Shop shop) throws DbException;
	public Shop readCustomer(File file, Shop shop) throws DbException;
}
