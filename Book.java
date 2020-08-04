//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: (HashTable)
// Description: (implementing a hashtable to store books)
// Course: (001 FALL 2019)
//
// Author: (Rosalie CAI)
// Email: (rcai25@wisc.edu)
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
import java.util.Objects;

/**
 * The book objects to be stored in the hash table
 * 
 * @author rosaliecarrow
 *
 */
public class Book {
  // properties of a book
  private String isbn13;
  private String authors;
  private String original_publication_year;
  private String title;
  private String language_code;
  private String average_rating;
  private String cover_type;
  private String pages;

  /**
   * constructor for book class
   * 
   * @param isbn13                    isbn13 of the book
   * @param authors                   authors of the book
   * @param original_publication_year original_publication_year of the book
   * @param title                     title of the book
   * @param language_code             language_code of the book
   * @param average_rating            average_rating of the book
   * @param cover_type                cover_type of the book
   * @param pages                     pages of the book
   */
  public Book(String isbn13, String authors, String original_publication_year, String title,
      String language_code, String average_rating, String cover_type, String pages) {
    // set fields of the book class
    this.isbn13 = isbn13;
    this.title = title;
    this.authors = authors;
    this.original_publication_year = original_publication_year;
    this.language_code = language_code;
    this.average_rating = average_rating;
    this.cover_type = cover_type;
    this.pages = pages;
  }

  /**
   * return the unique key of this book
   * 
   * @return the unique key of this book
   */
  public String getKey() {
    return this.isbn13;
  }

  /**
   * set the unique key of this book
   * 
   * @param isbn13 the unique key of this book
   */
  public void setKey(String isbn13) {
    this.isbn13 = isbn13;
  }

  /**
   * return string representation of the info of this book
   * 
   * @return string representation of the info of this book
   */
  @Override
  public String toString() {
    return "ISBN13: " + this.isbn13 + "; Book: " + this.title + ", Author: " + this.authors
        + ", Original Publication Year: " + this.original_publication_year + ", Language: "
        + this.language_code + ", Average Rating: " + this.average_rating + ", Cover Type: "
        + this.cover_type + ", Pages: " + this.pages;
  }

}

