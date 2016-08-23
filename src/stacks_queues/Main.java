/**
 * 
 */
package stacks_queues;

/**
 * @author conanz
 *
 * Main class to implement data structures and test their run times.
 * 
 * Data Structures:
 *  1. Array Stack
 *  2. List Stack
 *  3. Array Queue
 *  4. List Queue
 *  
 * Run Time:
 *  1. Alter inputSize, removeAmount, and iterationAmount
 *  2. Run to print the run average time in NANOSECONDS.
 *  3. Comment out and uncomment the bodies of code which are labeled the specific data structure you wish to use.
 */
public class Main
{
    /**MAIN CLASS TO INITIATE THE UNIVERSE**/
    public static void main(String args[])
    {
        //Initiate test variables
        int inputSize = 1000000;
        int removeAmount = 10000;
        int iterationAmount = 100;
        
        //Initiate test classes
//        ArrayStack<Integer> testArrayStack = new ArrayStack<Integer>();
//        ListStack<Integer> testListStack = new ListStack<Integer>();
//        ArrayQueue<Integer> testArrayQueue = new ArrayQueue<Integer>();
        ListQueue<Integer> testListQueue = new ListQueue<Integer>();
        
//        System.out.println("/----------Array Stack----------/");
//        /**START TIME**/
//        long start = System.nanoTime();
//        for(int i = 0; i < iterationAmount; i++)
//        {
//            //fill array stack
//            for(int j = 0; j < inputSize; j++)
//            {
//                testArrayStack.push(j);
//            }
//            //remove
//            for(int k = 0; k < removeAmount; k++)
//            {
//                testArrayStack.pop();
//            }
//            //reset array size
//            testArrayStack.reset();
//        }
//        /**END TIME**/
//        long end = (System.nanoTime() - start)/iterationAmount;
//        
//        //Print time
//        System.out.println("Nanoseconds: " + end);
////        testArrayStack.printArrayStack();
        
        
        
//        System.out.println("/----------List Stack----------/");
//        /**START TIME**/
//        long start = System.nanoTime();
//        for(int i = 0; i < iterationAmount; i++)
//        {
//            //fill array stack
//            for(int j = 0; j < inputSize; j++)
//            {
//                testListStack.push(j);
//            }
//            //remove
//            for(int k = 0; k < removeAmount; k++)
//            {
//                testListStack.pop();
//            }
//            
//            //reset list size
//          testListStack.reset();
//        }
//        /**END TIME**/
//        long end = (System.nanoTime() - start)/iterationAmount;
//        
//        //Print time
//        System.out.println("Nanoseconds: " + end);
////        testListStack.printListStack();
        
        
        
//        System.out.println("/----------Array Queue----------/");
//        /**START TIME**/
//        long start = System.nanoTime();
//        for(int i = 0; i < iterationAmount; i++)
//        {
//            //fill array stack
//            for(int j = 0; j < inputSize; j++)
//            {
//                testArrayQueue.enqueue(j);
//            }
//            //remove
//            for(int k = 0; k < removeAmount; k++)
//            {
//                testArrayQueue.dequeue();
//            }
//            //reset array size
//            testArrayQueue.reset();
//            
//        }
//        /**END TIME**/
//        long end = (System.nanoTime() - start)/iterationAmount;
//        
//        //Print time
//        System.out.println("Nanoseconds: " + end);    
////        testArrayQueue.printArrayQueue();
        
        
        
        System.out.println("/----------List Queue----------/");
        /**START TIME**/
        long start = System.nanoTime();
        for(int i = 0; i < iterationAmount; i++)
        {
            //fill array stack
            for(int j = 0; j < inputSize; j++)
            {
                testListQueue.enqueue(j);
            }
            //remove
            for(int k = 0; k < removeAmount; k++)
            {
                testListQueue.dequeue();
            }
            testListQueue.reset();
        }
        /**END TIME**/
        long end = (System.nanoTime() - start)/iterationAmount;
        
        //Print time
        System.out.println("Nanoseconds: " + end);
//        testListQueue.printListQueue();
    }
}
