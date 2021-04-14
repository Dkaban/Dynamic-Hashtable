//*************************************************************************************
//  DynamicHashTable.java
//
//  AUTHOR: DUSTIN KABAN
//  ID: T00663749
//  DATE: APRIL 13th, 2021
//  COURSE INFO: COMP 2231 ASSIGNMENT 5, QUESTION 3
//
//  This is an implementation of a hash table, that allows for dynamic resizing
//  and uses the extraction method with division using the last three digits of the ISBN mod the array length
//  as the hash address.  Collisions are resolved using Linear Probing.
//*************************************************************************************

public class DynamicHashTable
{
    //Create an array of books, this will act as our hashtable
    Book[] bookArray;
    //The loadFactor will help determine when resizing is required
    float loadFactor;
    //This helps keep track of the number of books for the loadFactor calculation
    int numberOfBooks = 0;

    //Instantiate the default hash table
    public DynamicHashTable(int initialCapacity, float loadFactorCapacity)
    {
        loadFactor = loadFactorCapacity;
        bookArray = new Book[initialCapacity];
        //Populate with empty books (ISBN = 0) so we can compare empty elements
        populateArrayWithEmptyBooks(bookArray);
    }

    public void put(Book book)
    {
        //Verify that it is actually a book
        if(!book.getISBN().equals("0") && book.getISBN().length() == 10)
        {
            //Get the hash address to place the book
            int hashAddress = findOpenHashAddress(book,bookArray);
            if(hashAddress != -1)
            {
                //Place the book and update the number of books, then check if resizing required
                bookArray[findOpenHashAddress(book,bookArray)] = book;
                numberOfBooks++;
                validateLoadFactor();
            }
        }
    }

    public void remove(Book book)
    {
        int hashAddress = locateBookInHashTable(book);
        if(hashAddress != -1)
        {
            bookArray[hashAddress] = new Book("","0");
            numberOfBooks--;
        }
    }

    //Very similar to the remove functionality, but we don't decrement numberOfBooks
    public int find(Book book)
    {
        //Return the hashAddress.  It will return -1 if it is not found
        return locateBookInHashTable(book);
    }

    private int locateBookInHashTable(Book book)
    {
        //Get the last 3 digits from the books ISBN
        String tempISBN = book.getISBN();
        int digitsFromISBN = Integer.parseInt(tempISBN.substring(tempISBN.length() - 3));
        int hashAddress = digitsFromISBN % bookArray.length;

        //If the ISBN's match, we found the element so just remove it.
        if(book.getISBN().equals(bookArray[hashAddress].getISBN()))
        {
            bookArray[hashAddress] = new Book("","0");
        }
        else
        {
            //Else, we need to do linear probing to find the element
            hashAddress = linearProbeToFindIndex(digitsFromISBN,bookArray);
            if(hashAddress != -1)
            {
                bookArray[hashAddress] = new Book("","0");
            }
        }
        return hashAddress;
    }

    private int findOpenHashAddress(Book book, Book[] array)
    {
        //Get the last 3 digits from the books ISBN
        String tempISBN = book.getISBN();
        int digitsFromISBN = Integer.parseInt(tempISBN.substring(tempISBN.length() - 3));
        int hashAddress = digitsFromISBN % array.length;

        if (!array[hashAddress].getISBN().equals("0"))
        {
            //We do a linear probe to find a new index
            hashAddress = linearProbeToFindIndex(digitsFromISBN, array);
        }
        return hashAddress;
    }

    private int linearProbeToFindIndex(int digitsFromISBN, Book[] array)
    {
        int hashAddress = 0;
        //We need to use division to find a new spot for it
        for(int i=0;i<array.length;i++)
        {
            hashAddress = (digitsFromISBN + i) % array.length;

            if(array[hashAddress].getISBN().equals("0"))
            {
                //Found empty index
                return hashAddress;
            }
        }
        return -1;
    }

    private void populateArrayWithEmptyBooks(Book[] array)
    {
        //Loop through the array and fill it with empty books
        for(int i=0;i<array.length;i++)
        {
            array[i] = new Book("","0");
        }
    }

    private void validateLoadFactor()
    {
        //If the load factor is exceeded, we need to expand the size of the array
        if(((float)numberOfBooks / bookArray.length) > loadFactor)
        {
            growHashtable();
        }
    }

    private void growHashtable()
    {
        //Create a blank array of the new size, populate with empty books then shift old elements over
        //Then copy that array over to the working bookArray
        Book[] tempBookArray = new Book[bookArray.length * 2];
        populateArrayWithEmptyBooks(tempBookArray);
        moveOldToNew(tempBookArray);
        bookArray = tempBookArray;
    }

    private void moveOldToNew(Book[] cleanArray)
    {
        //Loop through the bookArray, validate if it's a book, then rehash in the cleanArray
        for(int i=0;i<bookArray.length;i++)
        {
            Book book = bookArray[i];

            //Check if the element is actually a book
            if(!book.getISBN().equals("0"))
            {
                //Find an open hash address
                int hashAddress = findOpenHashAddress(book, cleanArray);
                if(hashAddress != -1)
                {
                    //Assign the book to the hashAddress in the array
                    cleanArray[hashAddress] = book;
                }
            }
        }
    }

    //Display the hash table to the user in a readable format.
    public void displayHashtable()
    {
        System.out.println("------------------ HASH TABLE ------------------");
        for(int i=0;i<bookArray.length;i++)
        {
            Book book = bookArray[i];
            if(book.getISBN().equals("0"))
            {
                System.out.println("Book[" + i + "] is BLANK");
            }
            else
            {
                System.out.println("Book[" + i + "], Name: " + book.getName() + ", ISBN: " + book.getISBN());
            }
        }
        System.out.println("-------------------------------------------------");
    }
}
