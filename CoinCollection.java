/**
 * A coin collection contains coins. Coins can only be of value 
 * 0.05, 0.5, 1, 5, 10
 * Each coin VALUE can only appear in the collection once 
 * (e.g only one coin with value 5)
 */
public class CoinCollection 
{

    // A wallet that will be our collection
    private Wallet _collection;

    /**
     * @effects Creates a new coin collection
     */
    public CoinCollection() 
    {
        _collection = new Wallet();
    }
    
    /**
     * @modifies this
     * @effects Adds a coin to the collection
     * @return true if the coin was successfully added to the collection;
     * 		   false otherwise
     */
    public boolean addCoin(Coin coin) 
    {
        Boolean doesContainCoin = _collection.containsCoin(coin.getValue());

        if (doesContainCoin) {
            return false;
        } 

        _collection.addCoin(coin);
        return true;
    }
    
    /**
     * @return the current value of the collection
     */
    public double getCollectionTotal() 
    {
        return _collection.getWalletTotal();
    }
    
    /**
     * @return the number of coins in the collection
     */
    public int getCollectionSize() 
    {
        return _collection.getWalletSize();    	
    }
    
    /**
     * @modifies this
     * @effects Empties the collection
     */
    public void emptyCollection() 
    {
        _collection.emptyWallet();
    }
}
