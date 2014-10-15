package algorithms;

import domain.IDiscount;

public class RegularCustomerDiscount implements IDiscount {
	
	public RegularCustomerDiscount(){
		
	}

	@Override
	public double getDiscount(double price, int days) {
		return days;
	}
	
}
