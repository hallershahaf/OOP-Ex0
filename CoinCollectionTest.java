/* Testing enviroment for the CoinCollection class */

public class CoinCollectionTest
{
    public static void main(String args[])
    {
        testConstruction();

        testAddCoin();

        testEmptyCollection(); 
    }

    private static void testConstruction()
    {
        CoinCollection collection = new CoinCollection();

        System.out.println("Testing construction...");

        errorCheck(collection.getCollectionSize() != 0);
        errorCheck(collection.getCollectionTotal() != 0);
    }

    private static void testAddCoin()
    {
        CoinCollection collection = new CoinCollection();
        Coin coin1, coin2, coin3, coin4, coin5, coin6;

        System.out.println("Testing addCoin...");

        coin1 = new Coin(0.05);
        coin2 = new Coin(0.05);
        coin3 = new Coin(0.5);
        coin4 = new Coin(1);
        coin5 = new Coin(5);
        coin6 = new Coin(10);

        collection.addCoin(coin1);

        errorCheck(collection.getCollectionSize() != 1);
        errorCheck(collection.getCollectionTotal() != 0.05);

        collection.addCoin(coin1);

        errorCheck(collection.getCollectionSize() != 1);
        errorCheck(collection.getCollectionTotal() != 0.05);

        collection.addCoin(coin2);

        errorCheck(collection.getCollectionSize() != 1);
        errorCheck(collection.getCollectionTotal() != 0.05);

        collection.addCoin(coin3);
        collection.addCoin(coin4);
        collection.addCoin(coin5);
        collection.addCoin(coin6);

        errorCheck(collection.getCollectionSize() != 5);
        errorCheck(collection.getCollectionTotal() != 16.55);
    }

    private static void testEmptyCollection()
    {
        CoinCollection collection = new CoinCollection();
        Coin coin1, coin2, coin3;

        System.out.println("Testing emptyCollection...");

        coin1 = new Coin(1);
        coin2 = new Coin(5);
        coin3 = new Coin(10);

        collection.addCoin(coin1);
        collection.addCoin(coin2);
        collection.addCoin(coin3);

        errorCheck(collection.getCollectionSize() != 3);
        errorCheck(collection.getCollectionTotal() != 16);

        collection.emptyCollection();

        errorCheck(collection.getCollectionSize() != 0);
        errorCheck(collection.getCollectionTotal() != 0);
    }

    private static void errorCheck(Boolean result)
    {
        if (result)
        {
            System.out.println("Error!");
        }
    }
}