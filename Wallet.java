/**
 * A wallet can contain a number of coins. There could be several coins of the same value, 
 * but the same coin cannot appear in the wallet twice
 */
public class Wallet {
	
    /**
     * @effects Creates a new empty wallet
     */
    public Wallet() {
    	//
    }


    /**
     * @modifies this
     * @effects Adds a coin to the wallet
     * @return true if the coin was successfully added to the wallet;
     * 		   false otherwise
     */
    public boolean addCoin(Coin coin) {
    	//
    }

    /**
	 * @requires sum > 0
     * @modifies this
     * @effects tries to match at least the sum "sum" with coins in the wallet. 
	 *			If transaction is possible, removes the paid coins from the wallet; else; changes nothing
     * @return the amount actually paid, 0 if amount could not be obtained
     */
    public double pay(double sum) {
    	//
    }


    /**
     * @return the current total value of coins in the wallet
     */
    public double getWalletTotal() {
    	//
    }


    /**
     * @return the number of coins in the wallet
     */
    public int getWalletSize() {
    	//
    }


    /**
     * @modifies this
     * @effects Empties the the wallet. After this method is called,
	 * 			both getWalletSize() and getWalletTotal() will return 0
	 * 			if called
     */
    public void emptyWallet() {
    	//
    }


    /**
     * @return true if this wallet contains a coin with value "value"
     *  	   false otherwise
     */
    public boolean containsCoin(double value) {
    	//
    }
	
	
	/**
     * @return true if this wallet contains an ammount of money with value "value"
     *  	   false otherwise
     */
    public boolean containsAmmount(double value) {
    	//
    }

	
	
}
