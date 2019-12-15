/**
 * File: CuckooHashing.java
 * Created By: amaykadre
 * Created On: 2019-11-02
 * Type: CuckooHashing
 */
package hash;

import data_structures.LinkedObject;

/**
 * @author amaykadre
 *
 */
public class CuckooHashing{

	LinkedObject[] h1;
	LinkedObject[] h2;
	
	private int TABLE_SIZE;
	private int ACTUAL_SIZE = 0;
	
	/**
	 * @return the tABLE_SIZE
	 */
	public int getTABLE_SIZE() {
		return TABLE_SIZE;
	}
	/**
	 * @return the aCTUAL_SIZE
	 */
	public int getACTUAL_SIZE() {
		return ACTUAL_SIZE;
	}


	public CuckooHashing() {
		this(1024);
	}
	public CuckooHashing(int table_size) {
		super();
		TABLE_SIZE = table_size;
		this.h1 = new LinkedObject[TABLE_SIZE];
		this.h2 = new LinkedObject[TABLE_SIZE];
		initializeTables();
	}
	public void initializeTables() {
		for(int i = 0; i<TABLE_SIZE; i++) {
			h1[i] = null;
			h2[i] = null;
		}
	}
	
	public int calculateHash1(int key) {
    	return key%TABLE_SIZE;
    }
	
    public int calculateHash2(int key) {
    	return (key*1019)%TABLE_SIZE;
    }
    
    public int get(int key) throws KeyNotFoundException{
    	int hashValue1 = calculateHash1(key);
    	int hashValue2 = calculateHash2(key);
    	if(h1[hashValue1] != null && h1[hashValue1].key == key) {
    		return h1[hashValue1].value;
    	}
    	else if(h2[hashValue2] != null && h2[hashValue2].key == key) {
    		return h2[hashValue2].value;
    	}
    	else {
    		throw new KeyNotFoundException(key);
    	}
    	
    }
    
    class KeyNotFoundException extends RuntimeException{
    	  public KeyNotFoundException(int key){
    	    super("ALERT! Key: "+key+" not found.");
    	  }
    }
    public void insert(int key, int value) {
    	insert(key, value, false);
    }
    public void insert(int key, int value, boolean insertWhileResize) {
    	int hashValue1 = calculateHash1(key);
    	int hashValue2 = calculateHash2(key);
    	
    	if(h1[hashValue1] == null) {
    		h1[hashValue1] = new LinkedObject(key, value);
    		if(!insertWhileResize) ACTUAL_SIZE++;
    	}
    	else if(h1[hashValue1] != null && h1[hashValue1].key == key){
    		h1[hashValue1].value = value; // updating with new value
    	}
    	else if(h2[hashValue2] != null && h2[hashValue2].key == key){
    		h2[hashValue2].value = value; // updating with new value
    	}
    	// its a new key, cell value is not empty: collision occurred
    	else if(h1[hashValue1]!= null) {
    		//System.out.println("For key:"+key+" value already occupied by key:"+h1[hashValue1].key);
    		boolean isCycleDetected = cycleDetection(0, key, key, value, 1);
    		if(!isCycleDetected) {
    			if(!insertWhileResize) ACTUAL_SIZE++;
    		}
    	}
    }

    public boolean cycleDetection(int count, int startingKey, int key, int value, int tableNumber) {
    	boolean isTableResized = false;
    	if(startingKey == key) {
    		count++;
    	}
    	if(count == 3) {
    		// resize and rehash the table
    		isTableResized = resize();
    		//System.out.println("Table resized, inserting key:"+key+" value "+value);
    		insert(key, value, true);
    		return isTableResized;
    		
    	}
    	
    	
    	LinkedObject oldElement = null;
    	if(tableNumber == 1) {
    		
    		int hashValue1 = calculateHash1(key);
    		//System.out.println("For table:"+tableNumber+" Hash of key:"+key+" = "+hashValue1);
    		oldElement = h1[hashValue1];
    		h1[hashValue1] = new LinkedObject(key, value);
    		
    	}
    	else if(tableNumber == 2){
    		int hashValue2 = calculateHash2(key);
    		//System.out.println("For table:"+tableNumber+" Hash of key:"+key+" = "+hashValue2);
    		oldElement = h2[hashValue2];
    		h2[hashValue2] = new LinkedObject(key, value);
    	}
    	else {
    		System.out.println("INVALID TABLE");
    		return false;
    	}
    	
    	int newTableNumber = tableNumber == 1?2:1;
    	if(oldElement != null) {
    		cycleDetection(count, startingKey, oldElement.key, oldElement.value, newTableNumber);
    	}
    	
    	return isTableResized;
    	
    }
    
    public boolean resize() {
    	LinkedObject[] h1temp = new LinkedObject[TABLE_SIZE];
        LinkedObject[] h2temp = new LinkedObject[TABLE_SIZE];

        //System.out.println(TABLE_SIZE);
        for(int i=0; i<TABLE_SIZE; i++)
        {
        	//System.out.println(i);
        	h1temp[i] = h1[i];
        }
        
        for(int i=0; i<TABLE_SIZE; i++)
        {
        	h2temp[i] = h2[i];
        }
        int oldsize = TABLE_SIZE;
        
        //Resizing the table by 2
        TABLE_SIZE*=2;
        
        
        //hashtable = new LinkedObject[2][HASHTABLE];
        
        h1 = new LinkedObject[TABLE_SIZE];
        h2 = new LinkedObject[TABLE_SIZE];
        
        for(int i=0; i<TABLE_SIZE; i++)
        {
        	h1[i] = null;
        }
        
        for(int i=0; i<TABLE_SIZE; i++)
        {
        	h2[i] = null;
        }
        
        for(int i=0; i<oldsize; i++)
        {
        	if(h1temp[i]!= null)
        		insert(h1temp[i].key, h1temp[i].value, true);
        	//h1[i] = h1temp[i];
        }
        
        for(int i=0; i<oldsize; i++)
        {
        	if(h2temp[i]!= null)
        		insert(h2temp[i].key, h2temp[i].value, true);
        }
        
        h1temp = null;
        h2temp = null;
        
        return true;
    }
    
    public void remove(int key) throws KeyNotFoundException{
    	int hashValue1 = calculateHash1(key);
    	int hashValue2 = calculateHash2(key);
    	
    	if(h1[hashValue1] != null && h1[hashValue1].key == key) {
    		h1[hashValue1] = null;
    		ACTUAL_SIZE--;
    	}
    	else if(h2[hashValue2] != null && h2[hashValue2].key == key){
    		h2[hashValue2] = null;
    		ACTUAL_SIZE--;
    	}
    	else {
    		throw new KeyNotFoundException(key);
    	}
    }
    
    public void printTable()
    {
    	for(int j=0; j<TABLE_SIZE; j++)
        {
            System.out.print("HashTable: 1 - " + "Index: " + j + " ");
            if(h1[j]!=null)
            {
                System.out.println("Key: " + h1[j].key + "  " + "Value: " + h1[j].value);
            }
            else
            {
                System.out.println("Key: NULL  Value: NULL");
            }
        }
        System.out.println();
        for(int j=0; j<TABLE_SIZE; j++)
        {
            System.out.print("HashTable: 2 - " + "Index: " + j + " ");
            if(h2[j]!=null)
            {
                System.out.println("Key: " + h2[j].key + "  " + "Value: " + h2[j].value);
            }
            else
            {
                System.out.println("Key: NULL  Value: NULL");
            }
        }
    }
	

}
