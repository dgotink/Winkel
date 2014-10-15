package ui;

import javax.swing.JOptionPane;

import db.ShopDatabaseHandler;
import db.TXTReaderAndWriter;
import domain.Shop;

public class ShopViewer 
{
	
	
	private static Shop mijnWinkel;
	private static String file = "Winkel.txt";
	private static ShopDatabaseHandler handler;
	
	public static void main(String[] args) 
	{
		start();
		MainFrame frame = new MainFrame("Shop", mijnWinkel);
		frame.setVisible(true);
		
	}
	
	public static void start()
	{
		try {
			handler = new TXTReaderAndWriter(file, mijnWinkel);
			mijnWinkel  = handler.readItem();
			if(mijnWinkel == null)
			{
				mijnWinkel = new Shop();
				handler.setShop(mijnWinkel);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Something went really wrong.", 
					 "Error", JOptionPane.ERROR_MESSAGE); 
		}
	}
}
