package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.DbException;
import db.ShopDatabaseHandler;
import db.TXTReaderAndWriter;
import domain.Item;
import domain.ItemType;
import domain.Shop;

public class AddItemFrame extends JFrame 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 100;
	
	private Shop shop;
	private ShopDatabaseHandler handler;
	private String file = "Winkel.txt";
	
	public AddItemFrame(String title, Shop shop)
	{
		super(title);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
				
		this.shop = shop;
		try {
			handler = new TXTReaderAndWriter(file, this.shop);
		} catch (DbException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					 "Error", JOptionPane.ERROR_MESSAGE); 
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					 "Error", JOptionPane.ERROR_MESSAGE); 
		}
		
		JPanel flowPanel = new JPanel();
		JPanel headerPanel = new JPanel();
		JPanel inputPanel = new JPanel();
		JPanel submitPanel = new JPanel();
		flowPanel.setLayout(new BorderLayout());
		headerPanel.setLayout(new GridLayout(1,4));
		inputPanel.setLayout(new GridLayout(1,4));
		submitPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		flowPanel.add(headerPanel, BorderLayout.NORTH);
		flowPanel.add(submitPanel, BorderLayout.SOUTH);
		flowPanel.add(inputPanel, BorderLayout.CENTER);
		
		JLabel titleLabel = new JLabel("Title: ");
		JLabel idLabel = new JLabel("id: ");
		JLabel priceLabel = new JLabel("Price: ");
		JLabel typeLabel = new JLabel("Type: ");
		headerPanel.add(titleLabel);
		headerPanel.add(idLabel);
		headerPanel.add(priceLabel);
		headerPanel.add(typeLabel);
		

		JTextField itemTitle = new JTextField();
		JTextField idTitle = new JTextField();
		JTextField priceTitle = new JTextField();
		JTextField typeTitle = new JTextField();
		inputPanel.add(itemTitle);
		inputPanel.add(idTitle);
		inputPanel.add(priceTitle);
		inputPanel.add(typeTitle);
		
		JButton submitButton = new JButton("Add item");
		submitPanel.add(submitButton);
		
		this.add(flowPanel);
		
		
		ActionListener listener = new addItemListener(itemTitle, idTitle, priceTitle, typeTitle);
		submitButton.addActionListener(listener);
		
		
		
	}
	
	class addItemListener implements ActionListener
	{
		private JTextField itemTitle;
		private JTextField idTitle;
		private JTextField priceTitle;
		private JTextField typeTitle; 
		
		public addItemListener(JTextField itemTitle, JTextField idTitle,
										JTextField priceTitle, JTextField typeTitle)
		{
			this.itemTitle = itemTitle;
			this.idTitle = idTitle;
			this.priceTitle = priceTitle;
			this.typeTitle = typeTitle;
		}
		public void actionPerformed(ActionEvent event)
		{
			String titleString = itemTitle.getText();
			String idString = idTitle.getText();
			String priceString = priceTitle.getText();
			String typeString = typeTitle.getText(); 
			
			addItem(titleString, idString, priceString, typeString);
			
			close();
		}
	}
	
	private void addItem(String title, String id, String price, String type) 
	{
		try{
			Item item = new Item(ItemType.valueOf(type.toUpperCase()), title);
			item.setPrice(Double.parseDouble(price));
			this.shop.addItem(id, item);
			handler.setShop(this.shop);
			handler.writeItem();
		}catch(NullPointerException e){
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					 "Error", JOptionPane.ERROR_MESSAGE); 
		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					 "Error", JOptionPane.ERROR_MESSAGE); 
		}catch(IllegalArgumentException e){
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					 "Error", JOptionPane.ERROR_MESSAGE); 
		}catch(DbException e){
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					 "Error", JOptionPane.ERROR_MESSAGE); 
		}
	}
	
	public Shop getShop()
	{
		return this.shop;
	}
	
	public void close()
	{
		this.dispose();
	}
}
