//*************************************************************************************
//  Book.java
//
//  AUTHOR: DUSTIN KABAN
//  DATE: APRIL 14th, 2021
//
//  This is a book class that allows us to create a hashtable of books with multiple pieces of information.
//  Used by DynamicHashTable.java
//*************************************************************************************

public class Book
{
    String name;
    String iSBN;

    public Book(String bookName, String isbnNumber)
    {
        name = bookName;
        iSBN = isbnNumber;
    }

    public String getName()
    {
        return name;
    }

    public String getISBN()
    {
        return iSBN;
    }
}
