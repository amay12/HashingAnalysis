package data_structures;

public class LinkedObject 
{
    public int key;
    public int value;
    public LinkedObject next;
 

    public LinkedObject(int key, int value) 
    {
        this.key = key;
        this.value = value;
        this.next = null;
    }
}
