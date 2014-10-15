package ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import db.DbException;
import db.ShopDatabaseHandler;
import db.TXTReaderAndWriter;
import db.XMLReaderAndWriter;
import domain.Shop;

public class MainFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int FRAME_WIDTH = 250;
	private static final int FRAME_HEIGHT = 400;
	
	private Shop shop;
	private ShopDatabaseHandler handler;
	
	public MainFrame(String title, Shop shop) 
	{
		//Customize main frame
		super(title);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		this.shop = shop;
		
		//Make buttons
		JButton addItemButton = new JButton("Add item");
		JButton removeItemButton = new JButton("Remove item");
		JButton borrowItemButton = new JButton("Borrow item");
		JButton returnItemButton = new JButton("Return item");
		JButton repairItemButton = new JButton("Repair item");
		JButton showItemButton = new JButton("Show item");
		JButton showPriceButton = new JButton("Show price");
		JButton showAllItemsButton = new JButton("Show all items");
		JButton writeToTextButton = new JButton("Save to text file");
		JButton writeToXMLButton = new JButton("Save to XML file");
		
		//Make main window
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		
		//Set window in the center of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		this.setResizable(false);
		
		//Add menu options to main window
		panel.add(addItemButton);
		panel.add(removeItemButton);
		panel.add(borrowItemButton);
		panel.add(returnItemButton);
		panel.add(repairItemButton);
		panel.add(showItemButton);
		panel.add(showPriceButton);
		panel.add(showAllItemsButton);
		panel.add(writeToTextButton);
		panel.add(writeToXMLButton);
		this.add(panel);
		
		//Actions to perform if a button is pressed
		ActionListener addItemlistener = new addItemListener();
		addItemButton.addActionListener(addItemlistener);
		
		ActionListener removeItemlistener = new removeItemListener();
		removeItemButton.addActionListener(removeItemlistener);
		
		ActionListener borrowListener = new borrowItemListener();
		borrowItemButton.addActionListener(borrowListener);
		
		ActionListener returnItemListener = new returnItemListener();
		returnItemButton.addActionListener(returnItemListener);
		
		ActionListener repairItemListener = new repairItemListener();
		repairItemButton.addActionListener(repairItemListener);
		
		ActionListener showItemListener = new showItemListener();
		showItemButton.addActionListener(showItemListener);
		
		ActionListener showPriceListener = new showPriceListener();
		showPriceButton.addActionListener(showPriceListener);
		
		ActionListener showAllItemsListener = new showAllItemsListener();
		showAllItemsButton.addActionListener(showAllItemsListener);
		
		ActionListener writeToTextListener = new writeToTextListener();
		writeToTextButton.addActionListener(writeToTextListener);
		
		ActionListener writeToXMLListener = new writeToXMLListener();
		writeToXMLButton.addActionListener(writeToXMLListener);
	}
	
	class addItemListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			addItem();
		}
	}
	
	class removeItemListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			removeItem();
		}
	}
	
	class borrowItemListener implements ActionListener
	{
	
		public void actionPerformed(ActionEvent event)
		{
			borrowItem();
		}
	}
	
	class returnItemListener implements ActionListener
	{
	
		public void actionPerformed(ActionEvent event)
		{
			returnItem();
		}
	}
	
	class repairItemListener implements ActionListener
	{
	
		public void actionPerformed(ActionEvent event)
		{
			repairItem();
		}
	}
	
	class showItemListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			showItem();
		}
	}
	
	class showPriceListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			showPrice();
		}
	}
	
	class showAllItemsListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			showAllItems();
		}
	}
	
	class writeToTextListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			writeToText();
		}
	}
	
	class writeToXMLListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			writeToXML();
		}
	}
	
	private void addItem()
	{
		AddItemFrame addFrame = new AddItemFrame("Add Item", this.shop);
		addFrame.setVisible(true);
	}
	
	public void removeItem()
	{
		RemoveItemFrame removeFrame = new RemoveItemFrame("Remove Item", this.shop);
		removeFrame.setVisible(true);
	}
	
	private void borrowItem()
	{
		BorrowItemFrame borrowFrame = new BorrowItemFrame("Borrow Item", shop);
		borrowFrame.setVisible(true);
	}
	
	private void returnItem()
	{
		ReturnItemFrame returnFrame = new ReturnItemFrame("Return Item", shop);
		returnFrame.setVisible(true);
	}
	
	private void repairItem()
	{
		RepairItemFrame repairFrame = new RepairItemFrame("Repair Item", shop);
		repairFrame.setVisible(true);
	}
	
	private void showItem()
	{
		ShowItemFrame showFrame = new ShowItemFrame("Show Item", shop);
		showFrame.setVisible(true);
	}
	
	private void showPrice()
	{
		ShowPriceFrame showPriceFrame = new ShowPriceFrame("Show Item", shop);
		showPriceFrame.setVisible(true);
	}
	
	private void showAllItems()
	{
		ShowAllItemsFrame showAllFrame = new ShowAllItemsFrame("Show All Items", shop);
		showAllFrame.setVisible(true);
	}
	
	private void writeToText()
	{
		JFileChooser dialog = new JFileChooser();
		int result = dialog.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION)
		{
			try {
				System.out.println(dialog.getSelectedFile().getAbsolutePath());
				handler = new TXTReaderAndWriter(dialog.getSelectedFile().getAbsolutePath(), this.shop);
				handler.writeItem();
			} catch (DbException | IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), 
						 "Error", JOptionPane.ERROR_MESSAGE); 
			}
		}
	}
	
	private void writeToXML()
	{
		JFileChooser dialog = new JFileChooser();
		int result = dialog.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION)
		{
			try {
				System.out.println(dialog.getSelectedFile().getAbsolutePath());
				
				handler = new XMLReaderAndWriter(dialog.getSelectedFile().getAbsolutePath(), this.shop);
				handler.writeItem();
			} catch (DbException | IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), 
						 "Error", JOptionPane.ERROR_MESSAGE); 
			}
		}
	}
}
