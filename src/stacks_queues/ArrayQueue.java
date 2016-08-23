/**
 * 
 */
package stacks_queues;

/**
 * @author conanz
 *
 * Class for a data structure queue using array.
 * 
 * FUNCTIONS:
 *  Public
 *  1. enqueue(Generic data) adds data to the back of the array
 *  2. dequeue() removes data at the front of the array
 *  3. printArrayQueue() prints values of array only
 *  4. reset() make array default size so double size will need to be called the same amount of times again
 *  
 *  Private
 *  1. doubleSize() call in enqueue(Generic input) to increase size of array when necessary
 *  2. halfSize() call in dequeue() to decrease size of array when necessary
 */
public class ArrayQueue<Generic>
{
    /**Class Member Variables**/
    //starting references
    private int front;
    private int back;
    private int size;
    
    //create array of generics
    private Generic [] array;
    
    /**CONSTRUCTOR**/
    @SuppressWarnings("unchecked")
    public ArrayQueue()
    {
        //initiate class member variables
        front = 0;
        back = 0;
        size = 0;
        
        //create an array that holds objects and type cast it
        array = (Generic []) new Object[10];
    }
    
    /**FUNCTION TO PUT DATA INTO QUEUE**/
    public void enqueue(Generic data)
    {        
        //check if necessary to increase size
        if(size>=array.length)
        {
            doubleSize();
        }
        
        //loop back to front
        if(back == array.length)
        {
            back = 0;
        }
        
        array[back++] = data;
        size++;
    }
    
    /**FUNCTION TO REMOVE FIRST ELEMENT OF QUEUE**/
    public Generic dequeue()
    {   
        //loop back to front
        if(front==array.length)
        {
            front=0;
        }
        
        //check if top is at ONE-FOURTH current array length
        if(size <= array.length/4)
        {
            halfSize();
        }
        
        //increase front and let garbage collection do the rest
        size--;
        
        return array[front++];
    }
    
    /**FUNCTION TO DETERMINE IF QUEUE IS EMPTY**/
    public boolean isEmpty()
    {
        if(size == 0 && front == back)
        {
            return true;
        }
        return false;
    }
    /**FUNCTION TO RESET ARRAY STACK SIZE**/
    @SuppressWarnings("unchecked")
    public void reset()
    {
        //create new array that is default the size
        Generic[] defaultArray;
        defaultArray = (Generic []) new Object[10];
        
        //set reference
        array = defaultArray;
        front = 0;
        back = 0;
        size = 0;
    }
    
    /**FUNCTION TO DECREASE SIZE WHEN NECESSARY**/
    @SuppressWarnings("unchecked")
    private void halfSize()
    {
      //create new array that is half the size
        Generic[] halfArray;
        halfArray = (Generic []) new Object[array.length/2];
        
        //copy over data
        for(int i=0;i<size;i++)
        {
            halfArray[i] = array[front++];
            if(front == array.length)
            {
                front = 0;
            }
        }
        
        //set reference
        array=halfArray;
        front=0;
        back=size;
    }
    
    /**FUNCTION TO DOUBLE SIZE**/
    @SuppressWarnings("unchecked")
    private void doubleSize()
    {
        //create new array that is double the size
        Generic[] doubleArray;
        doubleArray = (Generic []) new Object[size*2];
        
        //copy over data
        for(int i=0;i<size;i++)
        {
            if(front == array.length)
            {
                front = 0;
            }
            doubleArray[i] = array[front++];
        }
        
        //set new references
        array=doubleArray;
        front=0;
        back=size;
    }
    
    /**FUNCTION TO PRINT ARRAY**/
    public void printArrayQueue()
    {
        //scroll from front to back
        for(int i = front; i < back; i++)
        {
            System.out.println(array[i]);
        }
    }
}
