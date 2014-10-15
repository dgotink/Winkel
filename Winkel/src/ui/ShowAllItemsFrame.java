package ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import domain.Shop;

public class ShowAllItemsFrame extends JFrame 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 100;
	
	private Shop shop;
	
	public ShowAllItemsFrame(String title, Shop shop) 
	{
		super(title);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(true);
		
		this.shop = shop;
		
		JPanel flowPanel = new JPanel();
		JPanel resultPanel = new JPanel();
		
		flowPanel.setLayout(new BorderLayout());
		resultPanel.setLayout(new BorderLayout());
		
		flowPanel.add(resultPanel, BorderLayout.CENTER);
		
		JTextArea result = new JTextArea();
		result.setEditable(false);
		result.setText(this.shop.toString());
		resultPanel.add(result);
		
		this.add(flowPanel);
	}
}
