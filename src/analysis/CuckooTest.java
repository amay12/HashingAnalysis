/**
 * File: CuckooTest.java
 * Created By: amaykadre
 * Created On: 2019-11-02
 * Type: CuckooTest
 */
package analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hash.CuckooHashing;

/**
 * @author amaykadre
 *
 */
public class CuckooTest {


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int table_size = 1000;
		int keySize = 2;
		CuckooHashing cuckoo = new CuckooHashing(table_size);
		for(int a = 0; a< 999; a++) {
			for(int rerun = 0; rerun< 2; rerun++) {
				Random r = new Random();
		    	List<Integer> keys = new ArrayList<>();
		    	List<Integer> values = new ArrayList<>();
		    	
		    	for(int i = 0; i< keySize; i++) {
		    		keys.add(r.nextInt(keySize));
		    		values.add(r.nextInt(keySize));
		    	}
		    	long startTimeInsert = System.nanoTime()/1000;
		    	for(int i = 0; i< keySize; i++) {
		    		cuckoo.insert(keys.get(i), values.get(i));
		    	}
		    	long endTimeInsert = System.nanoTime()/1000;
		    	long totalTimeToInsert = endTimeInsert - startTimeInsert;
		    	
		    	double loadFactor = (double)cuckoo.getACTUAL_SIZE()/(2*cuckoo.getTABLE_SIZE());
		    	System.out.print("Load Factor: "+loadFactor);
		    	System.out.println(" TotalTimeTaken: "+totalTimeToInsert);
				
			}
			keySize+=2;
		}
		

	}

}
