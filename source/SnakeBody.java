//Arie
//Purpose: Class for snake body
public class SnakeBody
{
   private final int MAX_SIZE = 625; //Grid is 25x25, so the max size is 625
   private int size;
   private int[] x;
   private int[] y;
   private boolean up;
   private boolean down;
   private boolean left;
   private boolean right;
   
   //Constructor
   public SnakeBody()
   {
      up = false;
      down = false;
      left = false;
      right = false;  
      x = new int[MAX_SIZE];
      y = new int[MAX_SIZE];
      size = 3; //Snake starts as 3 squares
   }
   
   //Sets directions of snake
   public void setDirections (boolean up, boolean down, boolean left, boolean right)
   {
      this.up = up;
      this.down = down;
      this.left = left;
      this.right = right;
   }
   
   //Returns the up status
   public boolean getUp ()
   {
      return up;
   }
   
   //Returns the down status
   public boolean getDown ()
   {
      return down;
   }
   
	//Returns the left status
   public boolean getLeft ()
   {
      return left;
   }
   
	//Returns the right status
   public boolean getRight ()
   {
      return right;
   }
   
	//Sets a new up status
   public void setUp (boolean newUp)
   {
      up = newUp;
   }
   
	//Sets a new down status
   public void setDown (boolean newDown)
   {
      down = newDown;
   }
   
   //Sets a new left status
   public void setLeft (boolean newLeft)
   {
      left = newLeft;
   }
   
	//Sets a new right status
   public void setRight (boolean newRight)
   {
      right = newRight;
   }
   
	//Sets a new X coordinate
   public void setX (int element, int newX)
   {
      x[element] = newX;
   }
   
	//Sets a new Y coordinate
   public void setY (int element, int newY)
   {
      y[element] = newY;
   }
   
	//Returns the snake x coordinate array
   public int[] getX ()
   {
      return x;
   }
   
	//Returns the snake y coordinate array
   public int[] getY ()
   {
      return y;
   }
   
	//Returns the snake's current body size
   public int getSize ()
   {
      return size;
   }
   
	//Increases the snake's size by one
   public void grow ()
   {
      size++;
   }


}