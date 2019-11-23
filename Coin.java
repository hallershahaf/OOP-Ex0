/**
	A Coin can have a value of 0.05, 0.5, 1, 5, 10
 */
public class Coin 
{
	private double _value;
	
    /**
     * @requires value in {0.05, 0.5, 1, 5, 10}
     * @modifies this
     * @effects Creates and initializes new Coin with the value, value
     * 
     */
    public Coin(double value) 
    {
        _value = value;
    }


    /**
     * @return the value of the Coin
     */
    public double getValue() 
    {
    	return _value;
    }
}