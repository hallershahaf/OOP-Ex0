import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

/**
 * A wallet can contain a number of coins. There could be several coins of the 
 * same value, but the same coin cannot appear in the wallet twice
 */
public class Wallet 
{
    // An array to contain the coins
    private  ArrayList<Coin> _wallet;

    /**
     * @effects Creates a new empty wallet
     */
    public Wallet()
    {
        _wallet = new ArrayList<Coin>();
    }


    /**
     * @modifies this
     * @effects Adds a coin to the wallet
     * @return true if the coin was successfully added to the wallet;
     * 		   false otherwise
     */
    public boolean addCoin(Coin coin) 
    {
        Boolean doesContainCoin = _wallet.contains(coin);

        if (!doesContainCoin) {
            _wallet.add(coin);
            return true;
        }

        return false;
    }

    /**
	 * @requires sum > 0
     * @modifies this
     * @effects tries to match at least the sum "sum" with coins in the wallet. 
     *			If transaction is possible, removes the paid coins from the 
     *          wallet; else; changes nothing
     * @return the amount actually paid, 0 if amount could not be obtained
     */
    public double pay(double sum) 
    {
        double currentSum = 0.0;
        Iterator<Coin> iterator = (_wallet).iterator();
        ArrayList<Coin> coinsToRemove = new ArrayList<Coin>();

        while ((currentSum < sum) && iterator.hasNext())
        {
            Coin currentCoin = iterator.next();
            currentSum += currentCoin.getValue();
            coinsToRemove.add(currentCoin);
        }

        if (currentSum >= sum) {
            _wallet.removeAll(coinsToRemove);
            return currentSum;
        } 
        return 0.0;
    }

    /**
	 * @requires sum > 0
     * @modifies this
     * @effects tries to match at least the sum "sum" with the minimum number
     *          of coins available from the wallet.If transaction is possible, 
     *          removes the paid coins from the wallet; else; changes nothing
     * @return the amount actually paid, 0 if amount could not be obtained
     */ 
    public double payMinimum(double sum) 
    {
        // In order to get minimum number of coins, we should just pick coins 
        // in ascending order (easily proven).
        _wallet.sort(new SortByValue());
        return this.pay(sum);
    }

    /**
	 * @requires sum > 0
     * @modifies this
     * @effects tries to match the exact sum "sum" with the maximum number of 
     *          coins available from the wallet. If transaction is possible,
     *          removes the paid coins from the wallet; else; changes nothing
     * @return the amount actually paid, 0 if amount could not be obtained
     */
    public double payExactMaximum(double sum)
    {
        // This can be solved using the knapsack algorithm. 
        // Since we want the max amount of coins, not their value : 
        // The weight is the value of the coins 
        // The value of coins is always 1.

        int startIndex = 0;        
        double solutionSum = 0.0;
        Iterator<Coin> iterator;

        // Find solution is it exists
        ArrayList<Coin> bestSolution = 
                            recursiveKnapsack(_wallet, startIndex, sum * 20);
        iterator = bestSolution.iterator();

        // Check solution correctness
        while (iterator.hasNext()) {
            solutionSum += iterator.next().getValue();
        }

        // Return result
        if (solutionSum == sum) {
            _wallet.removeAll(bestSolution);
            return sum;
        }
        return 0.0;
    }


    private ArrayList<Coin> recursiveKnapsack(ArrayList<Coin> array, 
                                              int index, double weight)
    {
        // Value is always 1
        int currentCoinWeight,
            weightWithCoin = 0,
            weightWithoutCoin = 0;

        ArrayList<Coin> listWithoutCoin;
        ArrayList<Coin> listWithCoin;

        Iterator<Coin> iterWithoutCoin;
        Iterator<Coin> iterWithCoin;

        // If sum is zero, or wallet is empty, return empty coin list
        if (weight == 0 || (index >= array.size())) {
            return new ArrayList<Coin>();
        }

        // The current coin's weight is it's value
        currentCoinWeight = (int)(array.get(index).getValue() * 20);

        // Recursive stage
        listWithoutCoin = recursiveKnapsack(array, index + 1, 
                                            weight);
        listWithCoin    = recursiveKnapsack(array, index + 1, 
                                            weight - currentCoinWeight);

        listWithCoin.add(array.get(index));
        
        iterWithoutCoin = listWithoutCoin.iterator();
        iterWithCoin = listWithCoin.iterator();

        // Calculate weight of both lists
        while (iterWithCoin.hasNext() || iterWithoutCoin.hasNext()) {
            if (iterWithCoin.hasNext()) {
                weightWithCoin += 
                            (int)(iterWithCoin.next().getValue() * 20);
            }
            if (iterWithoutCoin.hasNext()) {
                weightWithoutCoin += 
                            (int)(iterWithoutCoin.next().getValue() * 20);
            }
        }

        // If neither solution has the exact amount, return an empty list
        if ((weightWithCoin != weight) && (weightWithoutCoin != weight)) {
            return new ArrayList<Coin>();
        }

        // If both solutions have the exact amount
        if ((weightWithCoin == weight) && (weightWithoutCoin == weight)) {
            // Checking which solution has more coins
            if (listWithoutCoin.size() > listWithCoin.size()) {
                 return listWithoutCoin;
            }
                 return listWithCoin;
        }

        // If only one solution has the exact amount
       return (weightWithCoin == weight) ? listWithCoin : listWithoutCoin;
    }

    /**
     * @return the current total value of coins in the wallet
     */
    public double getWalletTotal() 
    {
        double walletSum = 0.0;
        Iterator<Coin> iterator = (_wallet).iterator();

        while(iterator.hasNext()) {
            walletSum += iterator.next().getValue();
        }

        return walletSum;
    }


    /**
     * @return the number of coins in the wallet
     */
    public int getWalletSize() 
    {
    	return _wallet.size();
    }


    /**
     * @modifies this
     * @effects Empties the the wallet. After this method is called,
	 * 			both getWalletSize() and getWalletTotal() will return 0
	 * 			if called
     */
    public void emptyWallet() 
    {
    	_wallet.clear();
    }


    /**
     * @return true if this wallet contains a coin with value "value"
     *  	   false otherwise
     */
    public boolean containsCoin(double value) 
    {
        Iterator<Coin> iterator = _wallet.iterator();

        while (iterator.hasNext()) {
            if (iterator.next().getValue() == value) {
                return true;
            }
        }

        return false;    	
    }
	
	
	/**
     * @return true if this wallet contains an ammount of money with value
     *         "value"; false otherwise
     */
    public boolean containsAmmount(double value) 
    {
        double walletSum = 0;
        Iterator<Coin> iterator = _wallet.iterator();

        while (walletSum < value && iterator.hasNext()) {
            walletSum += iterator.next().getValue();
        }

        if (walletSum >= value) {
            return true;
        }        
        return false;
    }

    /**
     * A private class to define how to order coins in the wallet.
     * Coins with smaller values should be ordered first in the wallet.
     */
    private class SortByValue implements Comparator<Coin>
    {
        /**
         * The function that defines ordering and comparing.
         */
        public int compare(Coin coin1, Coin coin2)
        { 
            double coin1Value = coin1.getValue();
            double coin2Value = coin2.getValue();
            return (coin1Value < coin2Value) ? -1 : 
                                        ((coin1Value == coin2Value) ? 0 : 1);
        } 
    }
}
