/**
 * 
 */
package stacks_queues;

/**
 * @author conanz
 *
 * Class for a data structure queue using doubly linked list.
 * 
 * FUNCTIONS:
 *  Public
 *  1. enqueue(Generic data) adds node to the before the back of the linked list
 *  2. dequeue() removes node at the front of the linked list
 *  3. printListQueue() prints values of linked list
 *  4. reset() Make linked list null again to reset size
 *  
 */
public class ListQueue<Generic>
{
    /**INNER CLASS LIST NODE**/
    /*
     * Is an inner class to avoid unnecessary parameters, have clean code, 
     * and completely hide the linked list nodes from the end user
     */
    private class ListNode
    {
        /**Node Class Member Variables**/
        /*Value*/
        Generic data;//data nodes hold **TYPE SPECIFIED WHEN LINKED LIST IS CREATED**
        
        /*Pointers*/
        ListNode next;//pointer to next node in linked list
        ListNode prev;//pointer to previous node in linked list
        
        /**NODE CONSTRUCTOR**/
        public ListNode(Generic data, ListNode next, ListNode prev)
        {
            //Assign parameters to class member variables
            this.data = data;
            
            this.next = next;
            this.prev = prev;
        }
    }
    
    /**Class Member Variables**/
    //references for queues
    ListNode front, back;
    
    /**CONSTRUCTOR**/
    ListQueue()
    {
        //initiate class member variables as references
        front = new ListNode(null,back,null);
        back = new ListNode(null,null,front);
    }
    
    /**FUNCTION TO INSERT DATA INTO LIST BEFORE CURRENT**/
    public void enqueue(Generic data)
    {
        //create new node with next and previous
        ListNode n = new ListNode(data, back, back.prev);
        
        //set references to inserted node
        back.prev.next=n;
        back.prev= n;
    }
    
    /**FUNCTION TO REMOVE FIRST NODE OF QUEUE**/
    public Generic dequeue()
    {
        //temporary holder to return data
        Generic temp = front.next.data;
        
        //skip over and let garbage collection handle the rest
        front.next = front.next.next;
        front.next.prev = front;
        
        return temp;
    }
    
    /**FUNCTION TO RESET LIST STACK SIZE**/
    public void reset()
    {
        //reset reference nodes
        front = new ListNode(null,back,null);
        back = new ListNode(null,null,front);
    }
    
    /**FUNCTION TO PRINT LINKED LIST**/ 
    public void printListQueue()
    {
        //Create test node to scroll through list with that starts on the next node after reference node front
        ListNode test = front.next;
        
        //Counter for specifying node in print
        int current = 1;
        
        //Print data until you reach the back
        while(test != back) 
        {
            System.out.println("Node " + current + ": " + test.data);//print
            
            //Scroll to next node (since != back, you aren't at the end) and increment counter
            test = test.next;
            current++;
        }
    }
}
