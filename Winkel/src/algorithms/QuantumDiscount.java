package algorithms;

import domain.IDiscount;

public class QuantumDiscount implements IDiscount{
	
	public QuantumDiscount(){
		
	}

	@Override
	public double getDiscount(double price, int days) {
		return price * (25/100.0);
	}

}
