//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: (HashTable)
// Description: (implementing a hashtable to store books)
// Course: (001 FALL 2019)
//
// Author: (Rosalie CAI)
// Email: (rcai25@wisc.edu)
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

// TODO: comment and complete your HashTableADT implementation
//
// TODO: implement all required methods
// DO ADD REQUIRED PUBLIC METHODS TO IMPLEMENT interfaces
//
// DO NOT ADD ADDITIONAL PUBLIC MEMBERS TO YOUR CLASS
// (no public or package methods that are not in implemented interfaces)
//
// TODO: describe the collision resolution scheme you have chosen
// identify your scheme as open addressing or bucket
//
// if open addressing: describe probe sequence
// if buckets: describe data structure for each bucket
//
// TODO: explain your hashing algorithm here
// NOTE: you are not required to design your own algorithm for hashing,
// since you do not know the type for K,
// you must use the hashCode provided by the <K key> object

/**
 * HashTable implementation that uses:
 * 
 * @param <K> unique comparable identifier for each <K,V> pair, may not be null
 * @param <V> associated value with a key, value may be null
 */
public class BookHashTable implements HashTableADT<String, Book> {

  /** The initial capacity that is used if none is specifed user */
  static final int DEFAULT_CAPACITY = 101;

  /** The load factor that is used if none is specified by user */
  static final double DEFAULT_LOAD_FACTOR_THRESHOLD = 0.75;

  private ValuePair[] hashtable;// an array that stores books
  private int initialCapacity;// initial capacity
  private double loadFactorThreshold;// initial load factor threshold
  private int HTsize;// # of items stored in the array

  /**
   * REQUIRED default no-arg constructor Uses default capacity and sets load factor threshold for
   * the newly created hash table.
   */
  public BookHashTable() {
    this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR_THRESHOLD);
  }

  /**
   * Creates an empty hash table with the specified capacity and load factor.
   * 
   * @param initialCapacity     number of elements table should hold at start.
   * @param loadFactorThreshold the ratio of items/capacity that causes table to resize and rehash
   */
  public BookHashTable(int initialCapacity, double loadFactorThreshold) {
    // TODO: comment and complete a constructor that accepts initial capacity
    // and load factor threshold and initializes all fields

    // while(initialCapacity<0||loadFactorThreshold<0||loadFactorThreshold>1) {
    // throw new IllegalArgumentException();
    // }//must be valid input
    //
    hashtable = new ValuePair[initialCapacity];
    this.loadFactorThreshold = loadFactorThreshold;

  }


  /**
   * Add the key,value pair to the data structure and increase the number of keys. If key is null,
   * throw IllegalNullKeyException; If key is already in data structure, throw
   * DuplicateKeyException();
   * 
   * @param key   key of the data structure
   * @param value value associated
   * @throws IllegalNullKeyException If key is null throw IllegalNullKeyException
   * @throws DuplicateKeyException   If key is already in data structure, throw
   *                                 DuplicateKeyException
   */
  @Override
  public void insert(String key, Book value) throws IllegalNullKeyException, DuplicateKeyException {
    // if key argument is null, throw IllegalNullKeyException
    if (key == null) {
      throw new IllegalNullKeyException();
    }

    // expand size if reach threshold
    if ((double) HTsize / hashtable.length >= loadFactorThreshold) {
      resize(hashtable);
    }

    // get initial index
    int hashIndex = Math.abs(key.hashCode()) % (hashtable.length);


    // find the right place
    while (hashtable[hashIndex] != null) {
      if (hashtable[hashIndex].getKey().equals(key)) {
        throw new DuplicateKeyException();
      } //
      hashIndex = (hashIndex + 1) % hashtable.length;// linear probing, move to next
    }

    hashtable[hashIndex] = new ValuePair(key, value);// insert value
    HTsize++;// update size


  }


  /**
   * resize the current hash table, rehash all non-null items to their new position in the expanded
   * array
   * 
   * @param hashtable the current table that needs to be resized
   * @throws IllegalNullKeyException if key is null, throw IllegalNullKeyException
   * @throws DuplicateKeyException   if key already present, throw DuplicateKeyException
   */
  private void resize(ValuePair[] ht) throws IllegalNullKeyException, DuplicateKeyException {
    // expand the array
    hashtable = new ValuePair[2 * ht.length + 1];

    // rehash items
    for (int i = 0; i < ht.length; i++) {
      // if book is not null, add it to the expanded array
      if (ht[i] != null) {
        insert(ht[i].getKey(), ht[i].getValue());
        HTsize--;// decrease the size because insert increases the size
      }
    }

  }



  /**
   * If key is found, remove the key,value pair from the data structure decrease number of keys.
   * return true If key is null, throw IllegalNullKeyException If key is not found, return false
   * 
   * @param key key of the data structure
   * @return if key is found
   * @throws IllegalNullKeyException If key is null, throw IllegalNullKeyException
   */
  @Override
  public boolean remove(String key) throws IllegalNullKeyException {
    // if key argument is null, throw IllegalNullKeyException
    if (key == null) {
      throw new IllegalNullKeyException();
    }

    // get initial index
    int hashIndex = Math.abs(key.hashCode()) % (hashtable.length);

    // find the right place
    for (int i = 0; i < hashtable.length; i++) {
      if (hashtable[hashIndex] != null && hashtable[hashIndex].getKey().equals(key)) {
        hashtable[hashIndex] = null;// clear the element
        HTsize--;// decrease size

        return true;
      }
      hashIndex = (hashIndex + 1) % hashtable.length;// linear probing, move to next
    }

    return false;
  }

  /**
   * Returns the value associated with the specified key Does not remove key or decrease number of
   * keys
   * 
   * If key is null, throw IllegalNullKeyException If key is not found, throw
   * KeyNotFoundException().
   * 
   * @param key key of the data structure
   * @return the value associated
   * @throws IllegalNullKeyException If key is null, throw IllegalNullKeyException
   * @throws KeyNotFoundException    If key is not found, throw KeyNotFoundException()
   */
  @Override
  public Book get(String key) throws IllegalNullKeyException, KeyNotFoundException {
    // if key argument is null, throw IllegalNullKeyException
    if (key == null) {
      throw new IllegalNullKeyException();
    }

    // initial index
    int hashIndex = Math.abs(key.hashCode()) % (hashtable.length);

    // find the right place
    for (int i = 0; i < hashtable.length; i++) {
      if (hashtable[hashIndex] != null && hashtable[hashIndex].getKey().equals(key)) {
        return hashtable[hashIndex].getValue();// key found, return value
      }
      hashIndex = (hashIndex + 1) % hashtable.length;// linear probing, move to next
    }

    throw new KeyNotFoundException();
  }

  /**
   * Returns the number of key,value pairs in the data structure
   * 
   * @return the number of key,value pairs in the data structure
   */
  @Override
  public int numKeys() {
    return HTsize;
  }

  /**
   * Returns the load factor for this hash table that determines when to increase the capacity of
   * this hash table
   * 
   * @return the load factor for this hash table
   */
  @Override
  public double getLoadFactorThreshold() {
    return loadFactorThreshold;
  }

  /**
   * Capacity is the size of the hash table array This method returns the current capacity.
   * 
   * The initial capacity must be a positive integer, 1 or greater and is specified in the
   * constructor.
   * 
   * REQUIRED: When the load factor is reached, the capacity must increase to: 2 * capacity + 1
   * 
   * Once increased, the capacity never decreases
   * 
   * @return the current capacity
   */
  @Override
  public int getCapacity() {
    return hashtable.length;
  }

  /**
   * Returns the collision resolution scheme used for this hash table. Implement this ADT with one
   * of the following collision resolution strategies and implement this method to return an integer
   * to indicate which strategy.
   * 
   * 1 OPEN ADDRESSING: linear probe
   * 
   * 2 OPEN ADDRESSING: quadratic probe
   * 
   * 3 OPEN ADDRESSING: double hashing
   * 
   * 4 CHAINED BUCKET: array list of array lists
   * 
   * 5 CHAINED BUCKET: array list of linked lists
   * 
   * 6 CHAINED BUCKET: array list of binary search trees
   * 
   * 7 CHAINED BUCKET: linked list of array lists
   * 
   * 8 CHAINED BUCKET: linked list of linked lists
   * 
   * 9 CHAINED BUCKET: linked list of of binary search trees
   * 
   * @return the collision resolution scheme used for this hash table
   */
  @Override
  public int getCollisionResolutionScheme() {
    return 1;
  }

  /**
   * an inner class that stores key and associated value
   * 
   * @author rosaliecarrow
   *
   */
  class ValuePair {
    String key;// key of this value pair
    Book value;// value of this value pair

    /**
     * constructor of value pair class
     * 
     * @param key   key to set to
     * @param value value to set to
     */
    public ValuePair(String key, Book value) {
      this.key = key;
      this.value = value;
    }

    /**
     * @return the key
     */
    public String getKey() {
      return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
      this.key = key;
    }

    /**
     * @return the value
     */
    public Book getValue() {
      return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Book value) {
      this.value = value;
    }


  }
}
