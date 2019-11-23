/* This is a test enviroment for the Wallet class */

public class WalletTest
{
    public static void main(String args[])
    {
        testConstruction();

        testAddCoin();

        testClearWallet();

        testContainsCoin();

        testContainsAmmount();

        testPay();

        testPayMinimum();

        testPayExactMaximum();
    }

    private static void testConstruction()
    {
        Wallet wallet = new Wallet();

        System.out.println("Testing construction...");

        errorCheck(wallet.getWalletSize() != 0);
        errorCheck(wallet.getWalletTotal() != 0.0);
    }

    private static void testAddCoin()
    {
        Wallet wallet = new Wallet();
        Coin coin1 = new Coin(0.05),
             coin2 = new Coin(0.05),
             coin3 = new Coin(0.5),
             coin4 = new Coin(1.0),
             coin5 = new Coin(5.0);

        System.out.println("Testing addCoin...");

        // Simple coin addition
        wallet.addCoin(coin1);

        errorCheck(wallet.getWalletSize() != 1);
        errorCheck(wallet.getWalletTotal() != 0.05);

        // Add same coin
        wallet.addCoin(coin1);

        errorCheck(wallet.getWalletSize() != 1);
        errorCheck(wallet.getWalletTotal() != 0.05);

        // Add coin of same value
        wallet.addCoin(coin2);

        errorCheck(wallet.getWalletSize() != 2);
        errorCheck(wallet.getWalletTotal() != 0.1);

        // Add all other 3 coins
        coin2 = new Coin(10);
        wallet.addCoin(coin2);
        wallet.addCoin(coin3);
        wallet.addCoin(coin4);
        wallet.addCoin(coin5);

        errorCheck(wallet.getWalletSize() != 6);
        errorCheck(wallet.getWalletTotal() != 16.6);
    }

    private static void testClearWallet()
    {
        Wallet wallet = new Wallet();
        Coin coin1 = new Coin(0.05),
             coin2 = new Coin(0.05),
             coin3 = new Coin(0.5),
             coin4 = new Coin(1.0),
             coin5 = new Coin(5.0);

        System.out.println("Testing walletClear...");

        wallet.addCoin(coin1);
        wallet.addCoin(coin2);
        wallet.addCoin(coin3);
        wallet.addCoin(coin4);
        wallet.addCoin(coin5);

        wallet.emptyWallet();

        errorCheck(wallet.getWalletSize() != 0);
        errorCheck(wallet.getWalletTotal() != 0.0);
    }

    private static void testContainsCoin()
    {
        Wallet wallet = new Wallet();
        Coin coin1;
        
        System.out.println("Testing containsCoin...");

        coin1 = new Coin(1.0);
        
        errorCheck(wallet.containsCoin(1.0));
        
        wallet.addCoin(coin1);
        
        errorCheck(!wallet.containsCoin(1.0));
        errorCheck(wallet.containsCoin(5.0));
    }

    private static void testContainsAmmount()
    {
        Wallet wallet = new Wallet();
        Coin coin1 = new Coin(1.0),
             coin2 = new Coin(5.0);

        System.out.println("Testing containsAmmount...");

        wallet.emptyWallet();

        // Checking empty wallet
        errorCheck(!wallet.containsAmmount(0));
        errorCheck(wallet.containsCoin(0.05));

        wallet.addCoin(coin1);
        wallet.addCoin(coin2);

        // Below actual amount
        errorCheck(!wallet.containsAmmount(0.5));
        // Actual amount
        errorCheck(!wallet.containsAmmount(6.0));
        // Above actual amount
        errorCheck(wallet.containsAmmount(15.0));
    }

    private static void testPay()
    {
        Wallet wallet = new Wallet();
        Coin coin1 = new Coin(5.0), 
             coin2 = new Coin(1.0),
             coin3 = new Coin(1.0);

        System.out.println("Testing pay...");

        wallet.addCoin(coin1);

        // The 5 coin should be payed with
        errorCheck(wallet.pay(2.0) != 5.0);
        errorCheck(wallet.getWalletSize() != 0);

        wallet.addCoin(coin1);
        wallet.addCoin(coin2);
        wallet.addCoin(coin3);

        // The 5 coin should be payed with
        errorCheck(wallet.pay(2.0) != 5.0);
        errorCheck(wallet.getWalletSize() != 2);

        wallet.addCoin(coin1);

        // The 2 1's coins should be payed with
        errorCheck(wallet.pay(2.0) != 2.0);
        errorCheck(wallet.getWalletSize() != 1);

        // Paying more than possible
        errorCheck(wallet.pay(10.0) != 0.0);
        errorCheck(wallet.getWalletSize() != 1);
    }

    private static void testPayMinimum()
    {
        Wallet wallet = new Wallet();
        Coin coin1 = new Coin(5.0),
             coin2 = new Coin(1.0),
             coin3 = new Coin(1.0);

        System.out.println("Testing payMinimum...");

        wallet.addCoin(coin1);
        wallet.addCoin(coin2);
        wallet.addCoin(coin3);

        // Should be payed with 2 1's coins
        errorCheck(wallet.payMinimum(2.0) != 2.0);
        errorCheck(wallet.getWalletSize() != 1);
    }

    private static void testPayExactMaximum()
    {
        Wallet wallet = new Wallet();
        Coin coin1 = new Coin(10.0),
             coin2 = new Coin(1.0),
             coin3 = new Coin(1.0),
             coin4 = new Coin(1.0),
             coin5 = new Coin(1.0),
             coin6 = new Coin(1.0),
             coin7 = new Coin(0.5),
             coin8 = new Coin(0.5);

        System.out.println("Testing payExactMaximum...");

        wallet.addCoin(coin1);
        wallet.addCoin(coin2);
        wallet.addCoin(coin3);

        errorCheck(wallet.payExactMaximum(5.0) != 0.0);
        errorCheck(wallet.getWalletSize() != 3);

        // Should be payed with 10 coin
        errorCheck(wallet.payExactMaximum(10.0) != 10.0);
        errorCheck(wallet.getWalletSize() != 2);

        coin1 = new Coin(5.0);
        wallet.addCoin(coin1);
        wallet.addCoin(coin4);
        wallet.addCoin(coin5);
        wallet.addCoin(coin6);
        wallet.addCoin(coin7);
        wallet.addCoin(coin8);
        
        // Wallet should be <1 1 5 1 1 1 0.5 0.5>
        errorCheck(wallet.getWalletSize() != 8);

        // Should be payed with <1 1 1 1 0.5 0.5>
        errorCheck(wallet.payExactMaximum(5.0) != 5.0);
        errorCheck(wallet.getWalletSize() != 2);       
    }

    private static void errorCheck(Boolean failed)
    {
        if (failed) {
            System.out.println("Error!");
        }
    }
}