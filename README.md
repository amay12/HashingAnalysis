# Hashing Analysis
> Comparative Time analysis of Cuckoo Hashing and Chaining

## Table of contents
* [General Information](#general-information)
* [Design Choices](#design-choices)
* [Comparative Analysis](#comparative-analysis)
* [References](#references)
* [Contact](#contact)

## General Information
### Chained Hashing
In Chained Hashing we maintain a linked list for every element in the hash table. A hash value is generated for every key in the key-value pair of the table. To insert and element to the table we and check if the hash table is already filled for the given index. If it is not filled, we create a new Linked List and insert the element to the Linked List head. If the given index is already occupied by another element, we place the element to the head of the linked list. We continue on doing this for every element. The table size remains constant and just the sizes of Linked List increases.

#### Amortized time for Chained Hashing:
The amortized time for all cases of a chained hashing algorithm is O(1). This is the result of calculating over a sequence of operations.

### Cuckoo Hashing
In Cuckoo Hashing we maintain two tables, each of same size h. Each table has its own hash function (h1 and h2) such that every inserted element x will be either in position h1(x) in the first table or h2(x) in the second. Look-ups take worst case O(1) time because for every element we need to look at just two places: h1(x) and h2(x). Deletions, similar to lookups, take worst case O(1) time as well.

#### Insertion:
To insert an element x, we start by inserting it into the first table. If h1(x) is empty, we place x there. Otherwise we place x there and evict the element already present there to its corresponding value at table 2. This process is repeated, where in we bounce between tables until all the elements stabilize.
The insertion fails when we encounter an infinite cycle while stabilizing elements during insertion. If failure occurs, we need to rehash the tables and then try inserting all values back to the tables.

#### Deletion:
To delete an element x from the hash table, we need to compute its hash values for both tables and check if x is present in h1(x) or h2(x). If present we clear its location. If not present we report back that the element is not found. Worst cast time: O(1).

#### Search:
To search / perform lookup for an element x, we need to compute its hash values for both tables and check if x is present in h1(x) or h2(x). Worst cast time: O(1).

#### Amortized time for Cuckoo Hashing:
The amortized time for all cases of cuckoo hashing algorithm is O(1).

## Design Choices:

### Chained Hashing
* Hash function: Modulo of the Hash table size was chosen as the hash function.
* Increase the Alpha value from 0.1 to 9.9 in increments of 0.1. 
* Plot log(1 + Alpha) on the X-axis and log(Insertion time in nanoseconds) on Y- axis.
* Random data set of (key, value) pairs is generated using a random generator.

### Cuckoo Hashing
* Hash function: For the first table, the hash function is modulo function of the table size. For the second table, hash function is the (number of keys) * (a large prime number) modulo table size.
* Increase load factor / alpha from 0.01 to 0.99. Multiple runs per alpha.
* Plot log(alpha) on the X-axis and log(Insertion time in nanoseconds) on Y-axis.
* Actual clock time is used as a measure for evaluating alpha.
* Random data set of (key, value) pairs is generated using a random generator.

## Comparative Analysis:
The following plot demonstrates the relation between log(1+alpha) with log(insertion time in ns) for chained hashing: <br/><br/>
<img width="849" alt="Chaining graph" src="https://user-images.githubusercontent.com/31099049/70860628-cd0a6080-1ed8-11ea-9434-06b9f2de66e6.png">
<br/>

The following plot demonstrates the relation between log(alpha) with log(insertion time in ns) for cuckoo hashing: <br/><br/>
<img width="983" alt="Cuckoo Chart" src="https://user-images.githubusercontent.com/31099049/70860492-2b364400-1ed7-11ea-9385-c5852de45f4d.png">
<br/>

For an increase in the load factor, the time taken to insert elements must also increase. Plotting the values on a log log graph, a straight line until a threshold is expected, after which the insertion time should spike due to increase in collisions.
<br/><br/>
For load factor less than 0.5, The sudden spikes in time of a given load factor are due increase in collisions for a given random input which results in an infinite loop during insertion and thus rebuilding the hashing structure. During each resizing operation (calling the resize() function), a new table of length twice the current table length is created. After a new table is created, all existing elements plus the element whose insertion caused an infinite loop are inserted into the rebuilt tables. The expected cost of rehashing a Cuckoo Hashing structure with n elements takes expected time O(n).
The logarithmic plot of load factor vs time is nearly constant until the load factor becomes greater than 0.5. After this value, the probability of collision increases which leads to more frequent rehashing of the tables.
<br/><br/>
Slope of the graph is nearly zero until alpha becomes 0.5 or log(alpha) > -0.3. Hence we can conclude that the insertion time is nearly constant till alpha = 0.5. Although for values greater than 0.5, where slope >0, insertion takes constant amortized time.
Hence, Cuckoo Hashing insertion operation, including any rehashing, is an amortized constant-time operation.
<br/><br/>
For Chaining, the plot between log(1+alpha) and log(insertion time in ns) shows that as the alpha increases, the average time taken to insert elements also increase. This is because an increase in the load factor increases the probability of collision. When collisions occur, new elements are needed to be inserted into the head of linked lists of their cells. To search these elements, the worst case time will be when all elements are present in one linked list and the element to be searched is at the end of a linked list. However, insertion and search are amortized constant-time operation. Average insertion and search time is constant. However, some keys take more time than others, as visible in the plot. Insertion and search depends on the load factor alpha.

## References
1. Stanford University CS166 Lecture Notes: https://web.stanford.edu/class/cs166/lectures/13/Small13.pdf
2. University of California Irvine CS-261P Data Structures class notes.

## Contact
Created by [@AmayKadre](http://linkedin.com/in/amaykadre/) feel free to contact!
