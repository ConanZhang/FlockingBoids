/**
 * 
 */
package stacks_queues;

/**
 * @author conanz
 *
 * Class for a data structure stack using arrays.
 * 
 * FUNCTIONS:
 *  Public
 *  1. push(Generic input) adds data to the front of the array
 *  2. pop() removes data at the end of the array
 *  3. printArrayStack() prints values of array only
 *  4. reset() make array default size so double size will need to be called the same amount of times again
 *  
 *  Private
 *  1. doubleSize() call in push(Generic input) to increase size of array when necessary
 *  2. halfSize() call in pop() to decrease size of array when necessary
 */
public class ArrayStack<Generic>
{
    /**Class Member Variables**/
    private Generic [] array;//create array of generics
    private int top;//index to look at top of stack
    
    /**CONSTRUCTOR**/
    @SuppressWarnings("unchecked")//
    public ArrayStack()
    {
       //initiate top
        top = 0;
        
        //create an array that holds objects and type cast it
        array = (Generic []) new Object[10];
    }
    
    /**FUNCTION TO ADD DATA**/
    public void push(Generic input)
    {
        //check if input size is or will be larger than default size
        if(top>=array.length)
        {
            doubleSize();
        }
        
        array[top++]=input;
    }
    
    /**FUNCTION TO REMOVE DATA**/
    public Generic pop()
    {
        //check if top is at ONE-FOURTH current array length
        if(top <= array.length/4)
        {
            halfSize();
        }
        
        //reduce top and let garbage collection do the rest
        return(array[--top]);
    }
    
    /**FUNCTION TO SEE TOP DATA**/
    public Generic top()
    {
        return(array[top]);
    }
    
    /**FUNCTION TO CHECK IF EMPTY**/
    public boolean isEmpty()
    {
        if(top == 0)
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
        top = 0;
    }
    
    /**FUNCTION TO DECREASE SIZE WHEN NECESSARY**/
    @SuppressWarnings("unchecked")
    private void halfSize()
    {
        //create new array that is half the size
        Generic[] halfArray;
        halfArray = (Generic []) new Object[array.length/2];
        
        //copy over data
        for(int i=0; i< top;i++)
        {
            halfArray[i] = array[i];
        }
        
        //set reference
        array=halfArray;
    }
    
    /**FUNCTION TO INCREASE SIZE WHEN NECESSARY**/
    @SuppressWarnings("unchecked")
    private void doubleSize()
    {
        //create new array that is double the size
        Generic[] doubleArray;
        doubleArray = (Generic []) new Object[array.length*2];
        
        //copy over data
        for(int i=0; i<array.length;i++)
        {
            doubleArray[i] = array[i];
        }
        
        //set reference
        array=doubleArray;
    }
    
    /**FUNCTION TO PRINT ARRAY**/
    public void printArrayStack()
    {
        //scroll through beginning to top reference
        for(int i = 0; i < top; i++)
        {
            System.out.println(array[i]);
        }
    }
}
