package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domain.Shop;

public class ShowItemFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 100;
	
	private Shop shop;
	private JLabel result;
	
	public ShowItemFrame(String title, Shop shop) 
	{
		super(title);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		
		this.shop = shop;
		
		JPanel flowPanel = new JPanel();
		JPanel inputPanel = new JPanel();
		JPanel submitPanel = new JPanel();
		JPanel resultPanel = new JPanel();
		
		flowPanel.setLayout(new BorderLayout());
		inputPanel.setLayout(new GridLayout(1,2));
		submitPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		resultPanel.setLayout(new BorderLayout());
		
		flowPanel.add(resultPanel, BorderLayout.SOUTH);
		flowPanel.add(inputPanel, BorderLayout.NORTH);
		flowPanel.add(submitPanel, BorderLayout.CENTER);
		
		
		JLabel id = new JLabel("Enter the id: ");
		inputPanel.add(id);
		
		JTextField itemID = new JTextField();
		inputPanel.add(itemID);
		
		JButton submitButton = new JButton("Submit");
		submitPanel.add(submitButton);
		
		result = new JLabel();
		result.setVisible(false);
		resultPanel.add(result);
		
		this.add(flowPanel);
		
		ActionListener listener = new showItemListener(itemID);
		submitButton.addActionListener(listener);
	}
	
	class showItemListener implements ActionListener
	{
		private JTextField itemID;
		
		public showItemListener(JTextField itemID)
		{
			this.itemID = itemID;
		}
		
		public void actionPerformed(ActionEvent event)
		{
			showItem(itemID.getText());
		}
	}
	
	private void showItem(String id) 
	{
		try{
			result.setText(this.shop.getItemString(id));
			result.setVisible(true);
		}catch(IllegalArgumentException e){
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					 "Error", JOptionPane.ERROR_MESSAGE); 
		}
	}
	
	public Shop getShop()
	{
		return this.shop;
	}
}
