package cs2420;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;
import javax.swing.JApplet;
import javax.swing.SwingUtilities;
import kd_tree.KD_Tree;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;


/**
 * @author conanz
 * 
 * Crowd class to launch an applet that simulates flocking behaviour by boids.
 * 
 * Boids demonstrate simple flocking behavior through the combination of three simple behaviors: 
 * (1) avoiding other boids
 * (2) moving toward the center of nearby boids 
 * (3) matching the velocity of nearby boids
 * 
 * The KD Tree is implemented on lines 73-103.
 */

public class Crowd extends JApplet implements GLEventListener, MouseListener 
{
    /**Class Member Variables**/
    GLU glu;
    int winWidth=600, winHeight=800;
    
    FPSAnimator animator;
    Thread updateThread;
    
    ArrayList<Boid> boids;
    double sqrRad, fweight, cweight, vweight, rweight;
    int size;

    boolean increaseSize, decreaseSize;
    
    /**CLASS CONSTRUCTOR**/
    public Crowd() {
        //create list to hold boids
        boids = new ArrayList<Boid>();
        
        //parameters to mess with simulation
        size = 1;
        sqrRad = 10000; // square of the interaction radius
        
        fweight = 0.1; // avoidance weight
        cweight = 0.00001; // centering weight
        vweight = 0.00001; // velocity matching weight
        rweight = 0.000001; // randomization weight
        
        increaseSize = false;
        decreaseSize = false;
        
        //create random boids
        for (int i=0; i<1000; i++) {
            //randomize position and velocities
            double posx = Math.random()*winWidth;
            double posy = Math.random()*winHeight;
            
            double velx = Math.random()-0.5;
            double vely = Math.random()-0.5;
            
            boids.add(new Boid(posx, posy, velx, vely));
        }
    }
    
    /**UPDATE METHOD FOR ENTIRE SIMULATION**/
    public synchronized void update() {
        //create kd tree from array list
        KD_Tree tree = new KD_Tree(boids);
        
        // loop over all the boids
        ListIterator<Boid> bi = boids.listIterator(); 
        
        while (bi.hasNext()) {
            Boid i = bi.next(); 
            
            // initialize forces and centers to zero
            double frcx=0;
            double frcy=0;
            double centerx = 0;
            double centery = 0;
            
            //number of neighbors
            int n = 0;
            
            //average velocity of neighbors
            double velx = 0;
            double vely = 0;
            
            //find the neighbors of the current boid
            LinkedList<Boid> neighbors = tree.neighbors(i, sqrRad);
            
            // iterate over boids that are neighbors to calculate average velocity
            ListIterator<Boid> bj = neighbors.listIterator();
            
            // we're averaging... keep track of how many nearby boids there are
            n = neighbors.size();
            
            while (bj.hasNext()) {
                Boid j = bj.next();
                
                // boids don't affect themselves
                if (i==j) continue;//goes to next iteration in while
                
                // compute the distance between the boids
                double r = sqr(i.posx - j.posx) + sqr(i.posy - j.posy);//SET DISTANCE BOID LOOKS
                    
                    // careful about dividing by zero
                    if (r > 0.1) {
                        // compute a force to push boids apart (avoidance)
                        frcx += (i.posx - j.posx)/sqr(r);
                        frcy += (i.posy - j.posy)/sqr(r);
                    }
                    
                    // compute the center of nearby boids
                    centerx += j.posx;
                    centery += j.posy;
                    
                    // compute the average velocity of nearby boids
                    velx += j.velx;
                    vely += j.vely;
            }
            
            // if there were nearby boids
            if (n > 0) {
                // compute the difference between our position and the average of nearby boids
                centerx = (centerx/n) - i.posx;//gives vector towards center
                centery = (centery/n) - i.posy;
                
                // compute the difference between our velocity and the average of nearby boids
                velx = (velx/n) - i.velx;
                vely = (vely/n) - i.vely;
                
                //compute a weighted force on this boid
                //uses collision avoidance force and make it a little bit random
                i.frcx = fweight*frcx + cweight*centerx + vweight*velx + rweight*Math.random();
                i.frcy = fweight*frcy + cweight*centery + vweight*vely + rweight*Math.random(); 
            }
        }
        
        // iterate over the boids updating their positions/velocities
        bi = boids.listIterator(); 
        
        while (bi.hasNext()) {
            bi.next().update();
        }
        
        if(increaseSize)
        {
            size+=5;
        }
        if(decreaseSize)
        {
            size-=5;
        }
    }
    
    /**DISPLAY METHOD**/
    public synchronized void display (GLAutoDrawable gld)
    {
        final GL2 gl = gld.getGL().getGL2();
        
        // clearing the window
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
        // setting up the camera 
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0.0, winWidth, 0.0, winHeight);
        
        // initializing world transformations
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glLoadIdentity();
        
        // draw the objects
        ListIterator<Boid> bi = boids.listIterator();
        while (bi.hasNext()) {
            Boid b = bi.next();
            b.draw(gld);
        }
    }

    public void displayChanged (GLAutoDrawable arg0, boolean arg1, boolean arg2) {}

    /**OPEN GL INITIATE**/
    public void init (GLAutoDrawable gld)
    {
        glu = new GLU();
    }
    
    /**APPLET INITIATE**/
    public void init() {
        setLayout(new FlowLayout());
        
        //create a gl drawing canvas
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);        
        GLCanvas canvas = new GLCanvas(caps);
        canvas.setPreferredSize(new Dimension(winWidth, winHeight));

        //add gl event listener
        canvas.addGLEventListener(this);
        canvas.addMouseListener(this);
        
        //canvas properties
        add(canvas);
        setSize(winWidth, winHeight);
        
        //add the canvas to the frame
        animator = new FPSAnimator(canvas,30);
        
        //define new class without naming it to run thread
        updateThread = new Thread(new Runnable() {
                                            		public void run() {
                                            		    while(true) {
                                            		        update();
                                            		        //multithread to get rid of lag
                                            		        try{
                                                                Thread.sleep(10);
                                                            }
                                                            catch (InterruptedException exception){
                                                                exception.printStackTrace();
                                                            }
                                            		    }
                                            		}
                                        	     });
    }

    /**SETS UP VIEWPORT: CALLED WHEN WINDOW IS FIRST INITIALIZED**/
    public void reshape (GLAutoDrawable gld, int x, int y, int width, int height)
    {
        GL gl = gld.getGL();
        
        winWidth = width;
        winHeight = height;
        
        // sets the mapping from camera space to the window
        gl.glViewport(0,0, width, height);
    }

    /**START ANIMATOR**/
    public void start() {
        animator.start();
        updateThread.start();
    } 

    /**STOP ANIMATOR**/
    public void stop() {
        animator.stop();
        updateThread.interrupt();
    }

    /**INNER CLASS BOID**/
    public class Boid {
        /**Class Member Variables**/
        //positions, velocities, and forces using f = ma
        public double posx,posy;

        double velx, vely, frcx, frcy;
        
        long lastTime;
        
        //color
        public double red, green, blue;
        
        /**BOID CONSTRUCTOR**/
        Boid(double posx, double posy, double velx, double vely) {
            lastTime = System.currentTimeMillis();
            
            //initiate variables
            this.posx = posx;
            this.posy = posy;
            this.velx = velx;
            this.vely = vely;
            
            double random = Math.random();
            //color
            red = 1.0f*random;
            green = 1.0f*random;
            blue = 1.0f*random;
        }
        
        /**UPDATE METHOD**/
        public void update() {
            //calculate time since last update
            long dt = System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();
            
            // limit forces because simulation may become unstable (forces approach infinite without limit)
            if (frcx > 1) frcx = 0.1;
            if (frcx < -1) frcx = -0.1;
            if (frcy > 1) frcy = 0.1;
            if (frcy < -1) frcy = -0.1;
            
            //integrate forces and velocities
            velx = velx + frcx*dt;
            vely = vely + frcy*dt;
            
            posx = posx + velx*dt/50.0;
            posy = posy + vely*dt/50.0;
            
            //check if we've left the window and move back in if necessary
            if (posx < size) {
                posx = size;
                velx = -velx;
            }
            if (posx > winWidth-size) {
                posx = winWidth-size;
                velx = -velx;
            }                
            if (posy < size) {
                posy = size;
                vely = -vely;
            } 
            if (posy > winHeight-size) {
                posy = winHeight-size;
                vely = -vely;
            }
        }
        
        /**DRAW FOR SINGLE BOID**/
        public void draw(GLAutoDrawable gld) {
            //create gl to use
            final GL2 gl = gld.getGL().getGL2();
            
            Math.atan2(vely, velx);
            
            //Create triangle and it's color
            gl.glColor3f((float)red, (float)green, (float)blue);
            gl.glBegin(GL.GL_TRIANGLE_FAN);
            
            //coordinates
            gl.glVertex3f((float)(posx-size), (float)(posy-size), 0);//bottom left
            gl.glVertex3f((float)(posx+size), (float)(posy-size), 0);//bottom right
            gl.glVertex3f((float)(posx), (float)(posy+size), 0);//top center
            
            
            gl.glEnd();
            
        }
    }
    
    /**MOUSE PRESSED EVENT LISTENER**/
    public synchronized void mousePressed (MouseEvent e)
    {
        //right click
        if(SwingUtilities.isRightMouseButton(e)){
            increaseSize = true;
        }
        else if(SwingUtilities.isMiddleMouseButton(e))
        {
            double velx = Math.random()-0.5;
            double vely = Math.random()-0.5;
            
            boids.add(new Boid(e.getX(), winHeight-e.getY(), velx, vely));
        }
        else
        {
            decreaseSize = true;
        }
    }
    
    /**MOUSE RELEASED EVENT LISTENER**/
    public void mouseReleased (MouseEvent e) 
    {
      //right click
        if(SwingUtilities.isRightMouseButton(e)){
            increaseSize = false;
        }
        else if( SwingUtilities.isLeftMouseButton(e) )
        {
            decreaseSize = false;
        }
    }
    
    /**METHOD TO SQUARE NUMBERS**/
    private double sqr(double x) {return x*x;}
    
    /**UNUSED INTEFACE FUNCTIONS DUE TO IMPLEMENTATIONS: GLEventListener, MouseListener**/
    //mouse events
    public void mouseClicked (MouseEvent e) {}
    public void mouseEntered (MouseEvent e) {}
    public void mouseExited (MouseEvent e){}
    public void dispose (GLAutoDrawable arg0){}
}