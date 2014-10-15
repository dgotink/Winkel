package domain;
/**
 * Checks is the given input is what it should be.
 * @author Liana Lacatus
 *
 */
public class Validator
{
	public Validator()
	{
	}
	
	/**
	 * Checks if the given input is not empty
	 * @param string 
	 * @return false if the input is empty
	 */
	public boolean isValidString(String string)
	{
		if(string == null || string.equals("") || string.equals(" "))
			return false;
		return true;
	}
	
	public boolean isValidPrice(double price){
		if(price >= 0) return true;
		return false;
	}
	
	public boolean isValidType(ItemType type)
	{
		if(type == null) return false;
		ItemType[] validTypes = ItemType.values();
		for(ItemType validType: validTypes)
		{
			if(validType.getType().equals(type.getType()))
				return true;
		}
		return false;
	}
}
