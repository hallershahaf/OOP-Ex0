
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Collections;

/**
 * A wallet can contain a number of coins. There could be several coins of the same value, 
 * but the same coin cannot appear in the wallet twice
 */
public class Wallet 
{
    private  ArrayList<Coin> wallet;

    /**
     * @effects Creates a new empty wallet
     */
    public Wallet()
    {
        this.wallet = new ArrayList<Coin>();
    }


    /**
     * @modifies this
     * @effects Adds a coin to the wallet
     * @return true if the coin was successfully added to the wallet;
     * 		   false otherwise
     */
    public boolean addCoin(Coin coin) 
    {
        if (!this.wallet.contains(coin))
        {
            this.wallet.add(coin);
            return true;
        }
        return false;
    }

    /**
	 * @requires sum > 0
     * @modifies this
     * @effects tries to match at least the sum "sum" with coins in the wallet. 
	 *			If transaction is possible, removes the paid coins from the wallet; else; changes nothing
     * @return the amount actually paid, 0 if amount could not be obtained
     */
    public double pay(double sum) 
    {
        double current_sum = 0;
        Iterator<Coin> iterator = (this.wallet).iterator();
        ArrayList<Coin> coins_to_remove = new ArrayList<Coin>();

        while (current_sum < sum && iterator.hasNext())
        {
            Coin current_coin = iterator.next();
            current_sum += current_coin.getValue();
            coins_to_remove.add(current_coin);
        }

        if (current_sum >= sum)
        {
            this.wallet.removeAll(coins_to_remove);
            return current_sum;
        } 
        return 0;
    }

    /**
	 * @requires sum > 0
     * @modifies this
     * @effects tries to match at least the sum "sum" with the minimum number of coins available from the wallet.
     * If transaction is possible, removes the paid coins from the wallet; else; changes nothing
     * @return the amount actually paid, 0 if amount could not be obtained
     */ 
    public double payMinimum(double sum) 
    {
        // In order to get minimum number of coins, we should just pick coins in descending order (easily proven).
        this.wallet.sort(new SortByValue());
        return this.pay(sum);
    }

    /**
	 * @requires sum > 0
     * @modifies this
     * @effects tries to match the exact sum "sum" with the maximum number of coins available from the wallet.
     * If transaction is possible, removes the paid coins from the wallet; else; changes nothing
     * @return the amount actually paid, 0 if amount could not be obtained
     */
    public double payExactMaximum(double sum)
    {
        /**
         * This can be solved using the knapsack algorithm. 
         * Since we want the max amount of coins, not their value : 
         * The weight is the value of the coins (multiplied by 20 to use integers).
         * The value of coins is always 1.
         */

        double solution_sum = 0;
        Iterator<Coin> iterator;

        ArrayList<Coin> best_solution = recursiveKnapsack(this.wallet, 0, sum * 20);
        iterator = best_solution.iterator();

        while (iterator.hasNext())
        {
            solution_sum += iterator.next().getValue();
        }

        if (solution_sum == sum)
        {
            this.wallet.removeAll(best_solution);
            return sum;
        }
        return 0;
    }


    private ArrayList<Coin> recursiveKnapsack(ArrayList<Coin> array, int index, double weight)
    {
        // Value is always 1
        int current_coin_weight,
            weight_with_coin = 0,
            weight_without_coin = 0;

        ArrayList<Coin> list_without_coin;
        ArrayList<Coin> list_with_coin;

        Iterator<Coin> iter_without_coin;
        Iterator<Coin> iter_with_coin;

        // If sum is zero, or wallet is empty, return empty coin list
        if (weight == 0 || (index >= array.size()))
        {
            return new ArrayList<Coin>();
        }

        // The current coin's weight is it's value
        current_coin_weight = (int)(array.get(index).getValue() * 20);

        // Recursive stage
        list_without_coin = recursiveKnapsack(array, index + 1, weight);
        list_with_coin = recursiveKnapsack(array, index + 1, weight - current_coin_weight);

        list_with_coin.add(array.get(index));
        
        iter_without_coin = list_without_coin.iterator();
        iter_with_coin = list_with_coin.iterator();

        // Calculate weight of both lists
        while (iter_with_coin.hasNext() || iter_without_coin.hasNext())
        {
            if (iter_with_coin.hasNext())
            {
                weight_with_coin += (int)(iter_with_coin.next().getValue() * 20);
            }
            if (iter_without_coin.hasNext())
            {
                weight_without_coin += (int)(iter_without_coin.next().getValue() * 20);
            }
        }

        // If neither solution has the exact amount, return an empty list
        if ((weight_with_coin != weight) && (weight_without_coin != weight))
        {
            return new ArrayList<Coin>();
        }

        // If both solutions have the exact amount
        if ((weight_with_coin == weight) && (weight_without_coin == weight))
        {
            // Checking which solution has more coins
            if (list_without_coin.size() > list_with_coin.size())
            {
                 return list_without_coin;
            } else
            {
                 return list_with_coin;
            }
        }

        // If only one solution has the exact amount
       return (weight_with_coin == weight) ? list_with_coin : list_without_coin;
    }

    /**
     * @return the current total value of coins in the wallet
     */
    public double getWalletTotal() {
        double wallet_sum = 0;
        Iterator<Coin> iterator = (this.wallet).iterator();

        while(iterator.hasNext())
        {
            wallet_sum += iterator.next().getValue();
        }
        return wallet_sum;
    }


    /**
     * @return the number of coins in the wallet
     */
    public int getWalletSize() {
    	return this.wallet.size();
    }


    /**
     * @modifies this
     * @effects Empties the the wallet. After this method is called,
	 * 			both getWalletSize() and getWalletTotal() will return 0
	 * 			if called
     */
    public void emptyWallet() {
    	this.wallet.clear();
    }


    /**
     * @return true if this wallet contains a coin with value "value"
     *  	   false otherwise
     */
    public boolean containsCoin(double value) {
        Iterator<Coin> iterator = (this.wallet).iterator();
        while (iterator.hasNext())
        {
            if (iterator.next().getValue() == value)
            {
                return true;
            }
        }
        return false;    	
    }
	
	
	/**
     * @return true if this wallet contains an ammount of money with value "value"
     *  	   false otherwise
     */
    public boolean containsAmmount(double value) {
        double wallet_sum = 0;
        Iterator<Coin> iterator = (this.wallet).iterator();

        while (wallet_sum < value && iterator.hasNext())
        {
            wallet_sum += iterator.next().getValue();
        }

        if (wallet_sum >= value)
        {
            return true;
        }
        return false;
    }

    private class SortByValue implements Comparator<Coin>
    {
        public int compare(Coin a, Coin b) 
        { 
            double a_val = a.getValue();
            double b_val = b.getValue();
            return (a_val < b_val) ? -1 : ((a_val == b_val) ? 0 : 1);
        } 
    }
}
