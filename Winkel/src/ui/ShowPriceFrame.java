package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import algorithms.NoDiscount;
import algorithms.QuantumDiscount;
import algorithms.RegularCustomerDiscount;
import domain.Shop;

public class ShowPriceFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int FRAME_WIDTH = 610;
	private static int FRAME_HEIGHT = 140;
	
	private Shop shop;
	private JLabel result;
	private JCheckBox regularDiscount;
	private JCheckBox quantumDiscount;
	private JPanel inputPanel;
	private JTextField itemID;
	private JTextField daysField;
	private int rowsInputPanel;
	private Map<JTextField, JTextField> items = new HashMap<JTextField, JTextField>(); 
	private boolean isQuantum = false;
	
	
	public ShowPriceFrame(String title, Shop shop) 
	{
		super(title);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		
		this.shop = shop;
		
		JPanel flowPanel = new JPanel();
		inputPanel = new JPanel();
		JPanel submitPanel = new JPanel();
		JPanel resultPanel = new JPanel();
		JPanel discountPanel = new JPanel();
		
		flowPanel.setLayout(new BorderLayout());
		
		rowsInputPanel = 1;
		
		inputPanel.setLayout(new GridLayout(rowsInputPanel,4));
		discountPanel.setLayout(new GridLayout(2, 1));
		submitPanel.setLayout(new BoxLayout(submitPanel, BoxLayout.X_AXIS));
		resultPanel.setLayout(new BorderLayout());
		
		
		flowPanel.add(inputPanel, BorderLayout.NORTH);
		flowPanel.add(discountPanel, BorderLayout.CENTER);
		flowPanel.add(submitPanel, BorderLayout.SOUTH);
		
		
		
		JLabel id = new JLabel("Enter the id: ");
		inputPanel.add(id);
		
		itemID = new JTextField();
		inputPanel.add(itemID);
		
		JLabel days = new JLabel("    Number of days: ");
		inputPanel.add(days);
		
		daysField = new JTextField();
		inputPanel.add(daysField);
		
		//Makes a panel with the addButton and adds the panel 
		//to the input panel
		JButton addButton = new JButton("+");
		JPanel addButtonPanel = new JPanel(new FlowLayout());
		
		addButtonPanel.add(addButton);
		inputPanel.add(addButtonPanel);
		
		regularDiscount = new JCheckBox("Regular discount");
		discountPanel.add(regularDiscount);
		
		quantumDiscount = new JCheckBox("Quantum discount");
		discountPanel.add(quantumDiscount);
		
		JButton submitButton = new JButton("Submit");
		submitPanel.add(submitButton);
		submitPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		
		result = new JLabel();
		result.setVisible(false);
		submitPanel.add(result);
		
		this.add(flowPanel);
		updateFrameLocation();
		
		
		ActionListener listener = new showPriceListener();
		submitButton.addActionListener(listener);
		
		ActionListener addListener = new addItemListener();
		addButton.addActionListener(addListener);
	}

	private void updateFrameLocation()
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	}
	
	class showPriceListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			items.put(itemID, daysField);
			showPrice();
		}
	}
	
	class addItemListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
				try 
				{
					addItemPanel();
				} 
				catch (IllegalArgumentException e)
				{
					JOptionPane.showMessageDialog(null, e.getMessage(), 
							 "Error", JOptionPane.ERROR_MESSAGE); 
				}
		}
	}
	
	private void showPrice() 
	{
		result.setText(computePrice() + " euro");
		result.repaint();
		result.setVisible(true);
	}

	/**
	 * Computes normal price without discount
	 */
	private double computePrice() 
	{
		double totalPrice = 0;
		applyDiscount();
		if(isQuantum)
		{
			for(Entry<JTextField, JTextField> field : items.entrySet())
			{
				int days = Integer.parseInt(field.getValue().getText());
				totalPrice += this.shop.getRentalPrice(field.getKey().getText(), days)
											+ this.shop.getFine(days);

			}
			totalPrice -= this.shop.getDiscountPrice(totalPrice, 0); 
			return totalPrice;
		}
		else
		{
			for(Entry<JTextField, JTextField> field : items.entrySet())
			{
									
				totalPrice += this.shop.getTotalPrice(field.getKey().getText(), 
															//Get days
											Integer.parseInt(field.getValue().getText()));
			}
		}
		return totalPrice;
		
	}
	
	private void applyDiscount()
	{
		isQuantum= false;
		try
		{
			if(!regularDiscount.isSelected() && !quantumDiscount.isSelected())
				this.shop.setDiscountStrategy(new NoDiscount());
			if(regularDiscount.isSelected())
				this.shop.setDiscountStrategy(new RegularCustomerDiscount());
			if(quantumDiscount.isSelected())
			{
				this.shop.setDiscountStrategy(new QuantumDiscount());
				isQuantum = true;
			}
			if(quantumDiscount.isSelected() && regularDiscount.isSelected())
				throw new IllegalArgumentException("You are not allowed to select 2 discounts at the same time.");
		}
		catch(IllegalArgumentException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					 "Error", JOptionPane.ERROR_MESSAGE); 
		}
	}
	
	private void addItemPanel() throws IllegalArgumentException
	{
		if(itemID.getText().equals("") || itemID.getText().equals(""))
			throw new IllegalArgumentException("You must fill the requiered fields first");
		items.put(itemID, daysField);
		ShowPriceFrame.FRAME_HEIGHT += 50;
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		rowsInputPanel++;
		
		inputPanel.setLayout(new GridLayout(rowsInputPanel,4));
		
		JLabel id = new JLabel("Enter the id: ");
		inputPanel.add(id);
		
		itemID = new JTextField();
		inputPanel.add(itemID);
		
		JLabel days = new JLabel("    Number of days: ");
		inputPanel.add(days);
		
		
		daysField = new JTextField();
		inputPanel.add(daysField);
		
		JLabel empty = new JLabel();
		inputPanel.add(empty);
		updateFrameLocation();
	}
	
	public Shop getShop()
	{
		return this.shop;
	}
}