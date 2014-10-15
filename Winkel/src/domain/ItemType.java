package domain;

public enum ItemType {
	MOVIE("Movie",5.00),
	GAME("Game",4.00),
	CD("CD",3.00);
	
	private double price;
	private String type;
	
	ItemType(String type, double price){
		this.price = price;
		this.type = type;
	}
	
	public String getType(){
		return type;
	}
	
	public void setPrice(double price){
		this.price = price;
	}
	
	public double getPrice(){
		return price;
	}
}
