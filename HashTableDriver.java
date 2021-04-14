//*************************************************************************************
//  HashTableDriver.java
//
//  AUTHOR: DUSTIN KABAN
//  ID: T00663749
//  DATE: APRIL 14th, 2021
//  COURSE INFO: COMP 2231 ASSIGNMENT 5, QUESTION 3
//
//  This class demonstrates the functionality of the DynamicHashTable.java class
//*************************************************************************************

import java.util.concurrent.ThreadLocalRandom;

public class HashTableDriver
{
    public static void main(String[] args)
    {
        //Initialize the DynamicHashTable
        DynamicHashTable dynamicHT = new DynamicHashTable(11, 0.7f);

        //Create a list of randomly generated books
        Book[] testBookArray = generateBookArray(8);

        //Put the elements into the hash table
        putInHashTable(testBookArray,dynamicHT);
        dynamicHT.displayHashtable();

        //Remove an element
        dynamicHT.remove(testBookArray[5]);
        dynamicHT.displayHashtable();

        //Find an element
        System.out.println("Book 2 is located at Index: " + dynamicHT.find(testBookArray[2]));
    }

    //Generate a random 10 digit ISBN
    private static long randomISBN()
    {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return random.nextLong(1_000_000_000L, 10_000_000_000L);
    }

    private static Book[] generateBookArray(int numBooks)
    {
        Book[] bookArray = new Book[numBooks];
        for(int i=0;i<numBooks;i++)
        {
            bookArray[i] = new Book("Book"+i,String.valueOf(randomISBN()));
        }
        return bookArray;
    }

    private static void putInHashTable(Book[] array, DynamicHashTable dht)
    {
        for(int i=0;i<array.length;i++)
        {
            dht.put(array[i]);
        }
    }
}
