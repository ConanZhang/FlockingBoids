/**
 * 
 */
package stacks_queues;

/**
 * @author conanz
 *
 * Class for a data structure stack using singly linked list.
 * Contains private inner class ListNode for use.
 * 
 * FUNCTIONS:
 *  Public
 *  1. push(Generic data) creates new node at the top of the linked list
 *  2. pop() skips over top as list is circular
 *  3. printListStack() prints values of linked list
 *  4. reset() Make linked list null again to reset size
 *  
 */
public class ListStack<Generic>
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
        private Generic data;//data nodes hold **TYPE SPECIFIED WHEN LINKED LIST IS CREATED**
        
        /*Pointers*/
        private ListNode next;//pointer to next node in linked list
        
        /**LIST NODE CONSTRUCTOR**/
        public ListNode(Generic data, ListNode next)
        {
            //Assign parameters to class member variables
            this.data = data;
            
            this.next = next;
        }
    }
    
    /**Class Member Variables**/
    ListNode top;
    
    /**CONSTRUCTOR**/
    public ListStack()
    {
        //initiate reference
        top = null;
    }
    
    /**FUNCTION TO ADD DATA**/
    public void push(Generic data)
    {
        //create new node and advance top reference
        top = new ListNode(data,top);
    }
    
    /**FUNCTION TO REMOVE DATA**/
    public Generic pop()
    {
        //store data for returning
        Generic temp = top.data;
        //skip over data and leave the rest to garbage collection
        top = top.next;//LIST IS CIRCULAR!!!
        return temp;
    }
    
    /**FUNCTION TO RESET LIST STACK SIZE**/
    public void reset()
    {
        top = null;
    }
    
    /**FUNCTION TO PRINT LINKED LIST**/ 
    public void printListStack()
    {
        //Create test node to scroll through list with that starts on the top node
        ListNode test = top;
        
        //Counter for specifying node in print
        int current = 1;
        
        //Print data until you reach null
        while(test != null) 
        {
            System.out.println("Node " + current + ": " + test.data);//print
            
            //Scroll to next node (since != null, you aren't at the end) and increment counter
            test = test.next;
            current++;
        }
    }
}
