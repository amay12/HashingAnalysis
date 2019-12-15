package hash;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import data_structures.LinkedObject;
 

 
public class ChainedHashing
{
    private static int TABLE_SIZE;
    private int size;
    private LinkedObject[] hashTable;
 
    public ChainedHashing(int table_size)
    {
        size = 0;
        TABLE_SIZE = table_size;
        hashTable = new LinkedObject[TABLE_SIZE];
        for (int i = 0; i < TABLE_SIZE; i++){
        	hashTable[i] = null;
        }	
    }
    
    public int get(int key) throws CustomTableException{
    	int hashKey = calculateHash(key);
    	// when hash table returns null for given key
    	if(hashTable[hashKey] == null) {
    		throw new CustomTableException("Invalid Key: The key "+key+" is not found!");
    	}
    	else{
            LinkedObject foundLinkedList = hashTable[hashKey];
            while (foundLinkedList != null && foundLinkedList.key != key) {
            	foundLinkedList = foundLinkedList.next;
            }
            if (foundLinkedList == null)
            	throw new CustomTableException("Invalid Key: The key "+key+" is not found!");
            else
                return foundLinkedList.value;
        }
    	
    }
    public int calculateHash(int key) {
    	return key%TABLE_SIZE;
    }
    class CustomTableException extends RuntimeException{
    	  public CustomTableException(String message){
    	    super(message);
    	  }
    }
    
    public int size()
    {
        return size;
    }
    
    public void clearTable()
    {
        for (int i = 0; i < TABLE_SIZE; i++)
            hashTable[i] = null;
    }
    
    public void insert(int key, int value) {
    	int hashKey = calculateHash(key);
    	if(hashTable[hashKey] == null) {
    		LinkedObject element = new LinkedObject(key,value);
    		hashTable[hashKey] = element;
    	}
    	else {
    		LinkedObject foundLinkedList = hashTable[hashKey];
            while (foundLinkedList.next != null && foundLinkedList.key != key) {
            	foundLinkedList = foundLinkedList.next;
            }
            if (foundLinkedList.key == value) {
            	foundLinkedList.value = value;
            }	
            else {
            	LinkedObject element = new LinkedObject(key,value);
            	foundLinkedList.next = element;
            } 
            size++;
    	}
    }

    public void printHashTable()
    {
        for (int i = 0; i < TABLE_SIZE; i++)
        {
            System.out.print("\nBucket "+ (i + 1) +" : ");
            LinkedObject entry = hashTable[i];
            while (entry != null)
            {
                System.out.print(entry.key + "-"+entry.value +" ");
                entry = entry.next;
            }            
        }
    }
}
 
class ChainedHashing1
{
    public static void main(String[] args)
    {
    	
    	int keySize = 0; // Element size
    	int N = 1000; // table size
    	
    	for(int a = 0; a<100; a++) {
    		
    		Random r = new Random();
        	List<Integer> keys = new ArrayList<>();
        	List<Integer> values = new ArrayList<>();
        	
        	
        	for(int i = 0; i< keySize; i++) {
        		keys.add(r.nextInt(keySize));
        		values.add(r.nextInt(keySize));
        	}
        	
        	ChainedHashing ht = new ChainedHashing(N);
        	long startTimeInsert = System.nanoTime()/1000;
        	for(int i = 0; i< keySize; i++) {
        		ht.insert(keys.get(i), values.get(i));
        	}
        	
        	long endTimeInsert = System.nanoTime()/1000;
        	
        	long totalTimeToInsert = endTimeInsert - startTimeInsert;
        	
        	double loadFactor = (double)keySize/N;
        	System.out.print("Load Factor: "+loadFactor);
        	System.out.println(" TotalTimeTaken: "+totalTimeToInsert);
        	
        	keySize+= 100;
    	}
    	
    	
    	
    }
}
