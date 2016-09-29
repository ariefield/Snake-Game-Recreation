//Arie
//Purpose: Main class of game
/*
-Game is played by using arrow keys to change the direction of the snake
-Game can be paused by pressing spacebar
-Advance through levels by entering the white door that spawns
-The snake moves faster for each level completed
*/
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class SnakeGame extends JFrame
{
   
   public SnakeGame()
   {
      //Sets up frame
      setTitle("Snake");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setSize(800, 775);
      this.setVisible(true);
      this.setResizable(false);    
   }
   
   public static void main(String[]args)
   {
      //Creates the frame, and main canvas
      SnakeGame frame = new SnakeGame();
      SnakeCanvas gameCanvas = new SnakeCanvas();
      frame.add(gameCanvas);
   }
}