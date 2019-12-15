/**
 * File: ChainedTest.java
 * Created By: amaykadre
 * Created On: 2019-12-12
 * Type: ChainedTest
 */
package analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hash.ChainedHashing;


/**
 * @author amaykadre
 *
 */
public class ChainedTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
    	
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
