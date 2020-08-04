//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: (HashTable)
// Description: (implementing a hashtable to store books)
// Course: (001 FALL 2019)
//
// Author: (Rosalie CAI)
// Email: (rcai25@wisc.edu)
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
/**
 * Filename: TestHashTableDeb.java Project: p3 Authors: Debra Deppeler (deppeler@cs.wisc.edu)
 * 
 * Semester: Fall 2018 Course: CS400
 * 
 * Due Date: before 10pm on 10/29 Version: 1.0
 * 
 * Credits: None so far
 * 
 * Bugs: TODO: add any known bugs, or unsolved problems here
 */

import org.junit.After;
import java.io.FileNotFoundException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Random;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test HashTable class implementation to ensure that required functionality works for all cases.
 */
public class BookHashTableTest {

  // Default name of books data file
  public static final String BOOKS = "books.csv";

  // Empty hash tables that can be used by tests
  static BookHashTable bookObject;
  static ArrayList<Book> bookTable;

  static final int INIT_CAPACITY = 2;
  static final double LOAD_FACTOR_THRESHOLD = 0.49;

  static Random RNG = new Random(0); // seeded to make results repeatable (deterministic)

  /** Create a large array of keys and matching values for use in any test */
  @BeforeAll
  public static void beforeClass() throws Exception {
    bookTable = BookParser.parse(BOOKS);
  }

  /** Initialize empty hash table to be used in each test */
  @BeforeEach
  public void setUp() throws Exception {
    // TODO: change HashTable for final solution
    bookObject = new BookHashTable(INIT_CAPACITY, LOAD_FACTOR_THRESHOLD);
  }

  /** Not much to do, just make sure that variables are reset */
  @AfterEach
  public void tearDown() throws Exception {
    bookObject = null;
  }

  /**
   * Insert many books helper method
   * 
   * @param bookTable the book table to insert in
   * @throws IllegalNullKeyException if key is null
   * @throws DuplicateKeyException   if key is already in the table
   */
  private void insertMany(ArrayList<Book> bookTable)
      throws IllegalNullKeyException, DuplicateKeyException {
    for (int i = 0; i < bookTable.size(); i++) {
      bookObject.insert(bookTable.get(i).getKey(), bookTable.get(i));

    }
  }

  /**
   * IMPLEMENTED AS EXAMPLE FOR YOU Tests that a HashTable is empty upon initialization
   */
  @Test
  public void test000_collision_scheme() {
    if (bookObject == null)
      fail("Gg");
    int scheme = bookObject.getCollisionResolutionScheme();
    if (scheme < 1 || scheme > 9)
      fail("collision resolution must be indicated with 1-9");
  }


  /**
   * IMPLEMENTED AS EXAMPLE FOR YOU Tests that a HashTable is empty upon initialization
   */
  @Test
  public void test000_IsEmpty() {
    // "size with 0 entries:"
    assertEquals(0, bookObject.numKeys());
  }

  /**
   * IMPLEMENTED AS EXAMPLE FOR YOU Tests that a HashTable is not empty after adding one (key,book)
   * pair
   * 
   * @throws DuplicateKeyException   if key is already present
   * @throws IllegalNullKeyException if key is null
   */
  @Test
  public void test001_IsNotEmpty() throws IllegalNullKeyException, DuplicateKeyException {
    bookObject.insert(bookTable.get(0).getKey(), bookTable.get(0));
    String expected = "" + 1;
    // "size with one entry:"
    assertEquals(expected, "" + bookObject.numKeys());
  }



  /**
   * insert bunch to see if no exception is thrown
   */
  @Test
  public void test003_InsertBunch() {
    try {
      insertMany(bookTable);
    } catch (Exception e) {
      fail("Should not thrown any exception");// unexpected
    }
  }


  // --------------------------testing insert-------------------------
  // ---------------------------------------------------------------------
  /**
   * test if null exception is thrown when key to be inserted is null
   */
  @Test
  public void test004_Insert_null_exception() {
    try {
      bookObject.insert(null, bookTable.get(0));// insert null key
      fail("Should throw null key exception");
    } catch (IllegalNullKeyException e) {
      // expected
    } catch (Exception e) {
      fail("Should throw null key exception");// unexpected
    }

    if (bookObject.numKeys() != 0)
      fail("Should not increase number of keys");// check if size is correct
  }

  /**
   * test if dupicate exception is thrown for inserting duplicate key
   */
  @Test
  public void test005_Insert_Duplicate_exception() {
    try {
      bookObject.insert(bookTable.get(0).getKey(), bookTable.get(0));
      bookObject.insert(bookTable.get(0).getKey(), bookTable.get(0));// dupicate

      fail("Should throw duplicated key exception");
    } catch (DuplicateKeyException e) {
      // expected
    } catch (Exception e) {
      fail("Should throw null key exception");// unexpected
    }

    if (bookObject.numKeys() != 1)
      fail("Should not increase number of keys");// check # of keys, should not change
  }

  /**
   * IMPLEMENTED AS EXAMPLE FOR YOU Test if the hash table will be resized after adding two
   * (key,book) pairs given the load factor is 0.49 and initial capacity to be 2.
   */

  @Test
  public void test002_Resize() throws IllegalNullKeyException, DuplicateKeyException {
    bookObject.insert(bookTable.get(0).getKey(), bookTable.get(0));
    int cap1 = bookObject.getCapacity();
    bookObject.insert(bookTable.get(1).getKey(), bookTable.get(1));
    int cap2 = bookObject.getCapacity();

    // "size with one entry:"
    assertTrue(cap2 > cap1 & cap1 == 2);
  }

  // --------------------------testing remove-------------------------
  // ---------------------------------------------------------------------
  /**
   * test if null exception is thrown when key to be removed is null
   */
  @Test
  public void test006_Remove_null_exception() {

    try {
      bookObject.remove(null);// null key
      fail("Should throw null key exception");
    } catch (IllegalNullKeyException e) {
      // expected
    } catch (Exception e) {
      fail("Should throw null key exception");// unexpected
    }

    if (bookObject.numKeys() != 0)
      fail("Should not increase number of keys");// check # of keys, should not change

  }

  /**
   * test if remove inserted keys successfully with no exception
   */
  @Test
  public void test009_Remove_successfully() {

    try {
      bookObject.insert(bookTable.get(0).getKey(), bookTable.get(0));
      bookObject.insert(bookTable.get(1).getKey(), bookTable.get(1));
      bookObject.remove(bookTable.get(0).getKey());
      bookObject.insert(bookTable.get(2).getKey(), bookTable.get(2));
      bookObject.insert(bookTable.get(0).getKey(), bookTable.get(0));// can be added back
    } catch (IllegalNullKeyException e) {
      fail("Should not throw exception");// unexpected
    } catch (Exception e) {
      fail("Should throw null key exception");// unexpected
    }

    if (bookObject.numKeys() != 3)
      fail("Should not increase number of keys");// check # of keys, should not change

  }

  /**
   * test if remove non-existence key returns false
   */
  @Test
  public void test011_Remove_non_existent() {

    try {
      bookObject.insert(bookTable.get(0).getKey(), bookTable.get(0));
      bookObject.insert(bookTable.get(1).getKey(), bookTable.get(1));
      bookObject.insert(bookTable.get(2).getKey(), bookTable.get(2));
      if (bookObject.remove(bookTable.get(3).getKey()) != false) {
        fail("remove an non-existent key");// unexpected, should return false for remove
      }
    } catch (Exception e) {
      fail("Should throw null key exception");// unexpected
    }

    if (bookObject.numKeys() != 3)
      fail("Should not increase number of keys");// check # of keys, should not change

  }

  // --------------------------testing get-------------------------
  // ---------------------------------------------------------------------
  /**
   * test if null exception is thrown when key is null
   */
  @Test
  public void test007_Get_null_exception() {

    try {
      bookObject.get(null);// null key
      fail("Should throw null key exception");
    } catch (IllegalNullKeyException e) {
      // expected
    } catch (Exception e) {
      fail("Should throw null key exception");// unexpected
    }

    if (bookObject.numKeys() != 0)
      fail("Should not increase number of keys");// check # of keys, should not change

  }

  /**
   * test if key not found exception is thrown when key in search is not in the hashtable
   */
  @Test
  public void test008_Get_Not_Found_exception() {

    try {
      bookObject.insert(bookTable.get(0).getKey(), bookTable.get(0));
      bookObject.insert(bookTable.get(1).getKey(), bookTable.get(1));
      bookObject.get(bookTable.get(2).getKey());// not-found key
      fail("Should throw null key exception");
    } catch (KeyNotFoundException e) {
      // expected
    } catch (Exception e) {
      fail("Should throw null key exception");// unexpected
    }

    if (bookObject.numKeys() != 2)
      fail("Should not increase number of keys");// check # of keys, should not change

  }

  /**
   * test if value is get correcly
   */
  @Test
  public void test010_Get_right_value() {

    try {
      bookObject.insert(bookTable.get(0).getKey(), bookTable.get(0));
      bookObject.insert(bookTable.get(1).getKey(), bookTable.get(1));
      bookObject.insert(bookTable.get(2).getKey(), bookTable.get(2));
      bookObject.insert(bookTable.get(3).getKey(), bookTable.get(3));
      bookObject.insert(bookTable.get(4).getKey(), bookTable.get(4));
      bookObject.insert(bookTable.get(5).getKey(), bookTable.get(5));

      // check get
      for (int i = 0; i < 6; i++) {
        if (!bookObject.get(bookTable.get(i).getKey()).getKey().equals(bookTable.get(i).getKey())) {
          fail("Get incorrect value at " + i);
        }
      }
    } catch (Exception e) {
      fail("Should not throw exception");// unexpected
    }

    if (bookObject.numKeys() != 6)
      fail("Incorrect number of keys");// check # of keys, should not change

  }

}
