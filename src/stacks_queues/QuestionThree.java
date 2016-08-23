/**
 * 
 */
package stacks_queues;

import java.util.Queue;
import java.util.Stack;

/**
 * @author conanz
 *
 * Class to organize thoughts and ideas for question three and translate it into pseudo/real code.
 */
public class QuestionThree<Generic>
{
    /**
     *   Suppose you have a stack S containing n elements and a queue Q that is initially empty. 
     *   Describe how you can use Q to scan S to see if it contains a certain element x, with the 
     *   additional constraint that your algorithm must return the elements back to S in their 
     *   original order. You may not use an array or linked list—only S and Q and a constant 
     *   number of reference variables.
     */
    
//    /**FUNCTION TO FIND ELEMENT X IN STACK S**/
//    public boolean findX(Stack<Generic> S, Generic x)
//    {
//        Queue<Generic> Q = new Queue<Generic>();
//        
//        /**Copy Elements from S to Q**/
//        while( S.isEmpty() != true )
//        {
//            //copy stack's top to queue's back
//            Q.enqueue(S.top()); S.
//            
//            //remove top
//            S.pop();
//        }
//        
//        /**Scan Q for Element X**/
//        while(Q.front() != x)
//        {   
//            //copy element back to stack if not s
//            S.push( Q.front() );
//            
//            //remove queue front
//            Q.dequeue();
//        }
//        
//        /**Break Out of Q Scan**/
//        if(Q.front() == x)
//        {
//            return true;
//        }
//        /**Copy Rest of Elements From Q to S**/
//        while(Q.isEmpty() != true)
//        {
//            S.push( Q.front() );
//            Q.dequeue();
//        }
//        
//        /**REDO COPYING AND RETURNING FOR ELEMENTS IN STACK TO BE IN SAME ORDER**/
//        /*
//         * Copying from stack to queue reverses the elements. Just redo the process to return them in the same order.
//         */
//        while( S.isEmpty() != true )
//        {
//            //copy stack's top to queue's back
//            Q.enqueue(S.top()); S.
//            
//            //remove top
//            S.pop();
//        }
//        while(Q.isEmpty() != true)
//        {
//            S.push( Q.front() );
//            Q.dequeue();
//        }
//        
//        //didn't find element X in stack S
//        return false;
//    }
}
