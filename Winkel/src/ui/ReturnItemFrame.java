package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.DbException;
import db.ShopDatabaseHandler;
import db.TXTReaderAndWriter;
import domain.Shop;

public class ReturnItemFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 120;
	
	private Shop shop;
	private ShopDatabaseHandler handler;
	private String file = "Winkel.txt";
	private JLabel result;
	private JCheckBox isDamaged;
	
	public ReturnItemFrame(String title, Shop shop) 
	{
		super(title);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
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
		JPanel inputPanel = new JPanel();
		JPanel submitPanel = new JPanel();
		JPanel resultPanel = new JPanel();
		
		flowPanel.setLayout(new BorderLayout());
		inputPanel.setLayout(new GridLayout(2,2));
		submitPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		resultPanel.setLayout(new BorderLayout());
		
		flowPanel.add(resultPanel, BorderLayout.SOUTH);
		flowPanel.add(inputPanel, BorderLayout.NORTH);
		flowPanel.add(submitPanel, BorderLayout.CENTER);
		
		
		JLabel id = new JLabel("Enter the id: ");
		inputPanel.add(id);
		
		JTextField itemID = new JTextField();
		inputPanel.add(itemID);
		
		isDamaged = new JCheckBox("damaged");
		inputPanel.add(isDamaged);
		
		JButton submitButton = new JButton("Submit");
		submitPanel.add(submitButton);
		
		result = new JLabel("The item has been succesfully returned.");
		result.setVisible(false);
		resultPanel.add(result);
		
		this.add(flowPanel);
		
		ActionListener listener = new returnItemListener(itemID);
		submitButton.addActionListener(listener);
	}
	
	class returnItemListener implements ActionListener
	{
		private JTextField itemID;
		
		public returnItemListener(JTextField itemID)
		{
			this.itemID = itemID;
		}
		
		public void actionPerformed(ActionEvent event)
		{
			returnItem(itemID.getText());
		}
	}
	
	private void returnItem(String id) 
	{
		try{
			if(isDamaged.isSelected())
			{
				this.shop.returnItem(id);
				this.shop.declareItemDamaged(id);
			}
			else
			{
				this.shop.returnItem(id);
			}
			result.setVisible(true);
			handler.setShop(this.shop);
			handler.writeItem();
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
}
