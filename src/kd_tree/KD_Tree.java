/**
 * 
 */
package kd_tree;

import java.util.*;
import cs2420.Crowd.Boid;

/**
 * @author conanz
 *
 * KD Tree class that creates a tree based on x and y coordinates. The root will begin by x coordinate, then the children will be by y coordinates, then x coordinate, ext...
 * 
 * It takes in an ArrayList then implements:
 * 
 *      1.buildTreeX
 *      2.buildTreeY
 *      
 *      To build the tree.
 * 
 * An outer class can then call on:
 * 
 *      1. neighbors
 * 
 *      Which will return the neighbors of the current Boid in a radius the user specifies.
 * 
 * Miscellaneous:
 * 
 * Debugging Tools:
 *      
 *      1. iterativeLevelOrderTraversal
 *      2. preOrder Traversal
 * 
 *      To see the structure of the tree
 * 
 * Comparators:
 * 
 *      1. XComparator
 *      2. YComparator
 * 
 *      For sorting the array list passed into the KD Tree
 * 
 * Inner Class:
 * 
 *      1. TreeNode
 *         
 *      To hold boids   
 * 
 */
public class KD_Tree
{
    /**Class Member Variables**/
    //arrays to sort points by
    Boid[] BoidsSortedByX;
    Boid[] BoidsSortedByY;
    
    
    TreeNode root;
    
    /**KD TREE CONSTRUCTOR**/
    public KD_Tree(ArrayList<Boid> points){
        
        //initiate class member variables
        BoidsSortedByX = new Boid[points.size()];
        BoidsSortedByY = new Boid[points.size()];
        
        //copy points into x and y arrays
        for(int i = 0; i < points.size(); i++)
        {
            BoidsSortedByX[i] = points.get(i);
            BoidsSortedByY[i] = points.get(i);
        }
        
        //sort arrays
        Arrays.sort(BoidsSortedByX, new XComparator());
        Arrays.sort(BoidsSortedByY, new YComparator());
        
        root = buildTreeX(BoidsSortedByX, BoidsSortedByY, 0, points.size() );
    }
    
    /**FUNCTION TO CREATE TREE BASED ON X COORDINATES**/
    TreeNode buildTreeX(Boid[] BoidsSortedByX1, Boid[] BoidsSortedByY1, int start, int end){
        /**BASE CASE**/
        if(start >= end){
            return null;
        }
        
        //find median
        int median = start+ (end-start)/2;
        
        //root of tree sorted by X
        TreeNode n = new TreeNode(BoidsSortedByX1[median], null, null,0);

        //rework BoidsSortedByY because the Y median will be different than the X median
        Boid[] temp = new Boid[end-start];
        
        //start at beginning of temp
        int j = 0;
        
        //fill in temp with values less than the x median
        for(int i = start; i < end; i++){
            if(BoidsSortedByY1[i].posx <= BoidsSortedByX1[median].posx){
                //check to see if the median of the y values is the same as the x values
                if (BoidsSortedByY1[i] == BoidsSortedByX1[median]) {
                    continue;
                }
                temp[j++] = BoidsSortedByY1[i];
            }
        }
        
        //fill in temp with values greater than x median
        for(int k = start; k < end; k++){
            if(BoidsSortedByY1[k].posx > BoidsSortedByX1[median].posx) {
                temp[j++] = BoidsSortedByY1[k];
            }
        }
        
        //copy temp into array sorted by y coordinate
        for (int l=start; l<end; l++) {
            //values less than median
            if (l < median) {
                BoidsSortedByY1[l] = temp[l-start];
            } 
            //equal to median
            else if (l == median) {
                BoidsSortedByY1[l] = BoidsSortedByX1[median];
            }
            //greater than median
            else if (l > median) {
                BoidsSortedByY1[l] = temp[l-start-1];
            }
        }
        
        //recursive call to switch nodes to hold y coordinates
        n.left = buildTreeY(BoidsSortedByX1, BoidsSortedByY1, start, median);
        n.right = buildTreeY(BoidsSortedByX1, BoidsSortedByY1, median+1, end);
        
        return n;
    }


    /**FUNCTION TO CREATE TREE BASED ON Y COORDINATES**/
    TreeNode buildTreeY(Boid[] BoidsSortedByX1, Boid[] BoidsSortedByY1, int start, int end){
        /**BASE CASE**/
        if(start >= end){
            return null;
        }
        
        //find median
        int median = start+ (end-start)/2;
        
        TreeNode n = new TreeNode(BoidsSortedByY1[median], null, null,1);

        //rework BoidsSortedByX because the X median will be different than the Y median
        Boid[]temp = new Boid[end-start];
        
        //start at beginning of temp
        int j = 0;
        
        //fill in temp with values less than the y median
        for(int i = start; i < end; i++){
            if(BoidsSortedByX1[i].posy <= BoidsSortedByY1[median].posy){
                //check to see if the median of the y values is the same as the x values
                if (BoidsSortedByX1[i] == BoidsSortedByY1[median]) {
                    continue;
                }
                temp[j++] = BoidsSortedByX1[i];
            }
        }
        
        //fill in temp with values greater than y median
        for(int k = start; k < end; k++){
            if(BoidsSortedByX1[k].posy > BoidsSortedByY1[median].posy) {
                temp[j++] = BoidsSortedByX1[k];
            }
        }
        
      //copy temp into array sorted by x coordinate
        for (int l=start; l<end; l++) {
            //values less than median
            if (l < median) {
                BoidsSortedByX1[l] = temp[l-start];
            } 
            //equal to median
            else if (l == median) {
                BoidsSortedByX1[l] = BoidsSortedByY1[median];
            }
            //greater than median
            else if (l > median) {
                BoidsSortedByX1[l] = temp[l-start-1];
            }
        }
        
        //recursive call to switch nodes to hold x coordinates
        n.left = buildTreeX(BoidsSortedByX1, BoidsSortedByY1 , start, median);
        n.right = buildTreeX(BoidsSortedByX1, BoidsSortedByY1, median+1, end);
        
        return n;
    }
    
    /**DRIVER FUNCTION NEIGHBORS**/
    public LinkedList<Boid> neighbors(Boid point, double squareRadius){
        return neighbors(point, squareRadius, root);
    }
    
    /**FUNCTION TO RETURN NEIGHBORS OF GIVEN POINT BY SEARCHING THROUGH KD TREE**/
    /**Avoid square root with SQUARE RADIUS whenever possible. It's really expensive.**/
    public LinkedList<Boid> neighbors(Boid point, double squareRadius, TreeNode n){
        LinkedList<Boid> neighbors = new LinkedList<Boid>();
        
        //fallen off list
        if(n==null)return neighbors;
        
        //distance formula without square root
        double x = (point.posx - n.point.posx)*(point.posx - n.point.posx);
        double y = (point.posy - n.point.posy)*(point.posy - n.point.posy);
        double a =  x + y;
        
        //case 1 where distance is less than the radius given
        if( a < squareRadius){
            //you are not your own neighbor
            if(n.point != point) neighbors.add(n.point);
            
            //descend to both children because previous point is in radius
            neighbors.addAll(neighbors(point, squareRadius, n.left));
            neighbors.addAll(neighbors(point, squareRadius, n.right));
            
            return neighbors;
        }
        
        //case 2 where nodes aren't in radius
        if(n.type == 0 && x<squareRadius || (n.type == 1 && y < squareRadius)) {
            
            //left or right
            if( (n.type == 0 && point.posx <n.point.posx) || (n.type == 1 & point.posy < n.point.posy )) {
                //left
                neighbors.addAll(neighbors(point, squareRadius, n.left));
            }
            else {
                //right
                neighbors.addAll(neighbors(point, squareRadius, n.right));
            }
            return neighbors;
        }
        
        return neighbors;
    }
    
    /**DEBUGGING TOOLS**/
    /**ITERATIVE FUNCTION TO TRAVERSE BREADTH FIRST**/
    public void iterativeLevelOrder()
    {
        System.out.println("Iterative Level-Order");
        
        //create array queue
        stacks_queues.ArrayQueue<TreeNode> arrayQueue = new stacks_queues.ArrayQueue<TreeNode>();
        
        //start at root
        TreeNode n = root;
        arrayQueue.enqueue(n);
        
        while(!arrayQueue.isEmpty())
        {
            //remove and print out data
            n = arrayQueue.dequeue();
            System.out.println("X:" + n.point.posx + " " + "Y:" + n.point.posy);
            
            if(n != null)
            {
                //traverse left sub tree
                if(n.left!=null)
                {
                    arrayQueue.enqueue(n.left);
                }
                //traverse right sub tree
                if(n.right!=null)
                {
                    arrayQueue.enqueue(n.right);
                }
            }
        }
        System.out.println();

    }
    
    /**DRIVER METHOD FOR PREORDER TRAVERSAL**/
    public void preOrder()
    {
        System.out.println("Pre-Order");
        preOrder(root);
        System.out.println();
    }
    
    /**FUNCTION TO TRAVERSE BY ROOT, LEFT, RIGHT**/
    private void preOrder(TreeNode n)
    {
        if(n != null)
        {
            System.out.print("X:" + n.point.posx + " " + "Y:" + n.point.posy);
            preOrder(n.left);
            preOrder(n.right);
        }
    }
    
    /**KD TREE NODE THAT TAKES IN A POINT**/
    private class TreeNode{
        /**Class Member Variables**/
        private Boid point;
        private TreeNode left;
        private TreeNode right;
        private int type;

        /**CONSTRUCTOR**/
        public TreeNode(Boid pointParameter, TreeNode leftParameter, TreeNode rightParameter, int typeParameter){
            point = pointParameter;
            left = leftParameter;
            right = rightParameter;
            type = typeParameter;
        }
        
    }
    
    /**COMPARATOR TO COMPARE X VALUES**/
    public class XComparator implements Comparator<Boid>{
        public int compare(Boid p1, Boid p2) {
            if(p1.posx < p2.posx) return -1;//p1's x value is less
            if(p2.posx < p1.posx) return 1;//p2's x value is less
            return 0;//same x values
        }
    }
    
    /**COMPARATOR TO COMPARE Y VALUES**/
    public class YComparator implements Comparator<Boid>{
        public int compare(Boid p1, Boid p2) {
            if(p1.posy < p2.posy) return -1;//p1's y value is less
            if(p2.posy < p1.posy) return 1;//p2's y value is less
            return 0;//same y values
        }
    }
}
