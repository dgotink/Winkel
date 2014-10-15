package algorithms;

import domain.IDiscount;

public class NoDiscount implements IDiscount{
	
	public NoDiscount(){
		
	}

	@Override
	public double getDiscount(double price, int days) {
		return 0;
	}

}
