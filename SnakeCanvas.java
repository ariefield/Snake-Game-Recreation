//Arie
//Purpose: Gameboard for the game
import java.util.Random;
import javax.swing.*;

import java.awt.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;


public class SnakeCanvas extends Canvas implements ActionListener
{
   //Static variables
   private static int score = 0;
   private static int level = 1;
   
   //Final variables
   private final int GRID_X = 150;
   private final int GRID_Y = 50;
   private final int GRID_WIDTH = 625;
   private final int GRID_HEIGHT = 625;
   private final int BOX_WH = 625/25; //Width and height of grid boxes
   private final int LEVEL_X = 10;
   private final int LEVEL_Y = 60;
   private final int SCORE_X = 10;
   private final int SCORE_Y = 160;
   private final int NEXT_LEVEL_X = 10;
   private final int NEXT_LEVEL_Y = 260;
   
   //Other variables
   private SnakeBody snake;
   private Food food;
   private int[] snakeX;
   private int[] snakeY;
   private int nextLevel;
   private boolean run;
   private boolean pause;
   private boolean moved;
   private boolean eaten;
   private boolean finishedLevel;
   private Timer timer;
   
   public SnakeCanvas()
   {
      addKeyListener(new Key()); //Allows keys to be used to control the snake
      setFocusable(true);
      startGame(); //Starts the game
   }
   
   public void startGame()
   {
      //Setting snake starting properties
      snake = new SnakeBody();
      snake.setX(0,450);
      snake.setY(0,100);
      snake.setX(1,450);
      snake.setY(1,75);
      snake.setX(2,450);
      snake.setY(2,50);
      snakeX = snake.getX();
      snakeY = snake.getY();
      snake.setDown(true); //Start the snake moving right
      
      //Creating food
      food = new Food();
      spawnFood();
      
      //Setting other variables
      run = true;
      pause = false;
      eaten = false;
      finishedLevel = false;
      nextLevel = 50 + 50*level; //100 points to next level, and 50 more points for each level after that 
      
      timer = new Timer(75-(10*level),this); //Timer gets faster as the levels increase
      timer.setInitialDelay(600); //Sets some delay to allow the user to react
      timer.start();
      repaint(); //Repaints for when the user is starting a new level
   }
   
   //Moves the snake  
   public void move()
   {
      //Moves each joint to replace the joint in front of it
      for(int i=(snake.getSize()-1);i>0;i--)
      {
         snake.setX(i, snakeX[i-1]);
         snake.setY(i, snakeY[i-1]);
      } 
      //Moves the head of the snake
      if (snake.getUp() == true)
      {
         snake.setY(0,snakeY[0] - 25);
      }
      else if (snake.getDown() == true)
      {
         snake.setY(0,snakeY[0] + 25);
      }
      else if (snake.getLeft() == true)
      {
         snake.setX(0,snakeX[0] - 25);
      }
      else if (snake.getRight() == true)
      {
         snake.setX(0,snakeX[0] + 25);
      } 
      moved = true;
   }
   
   //Checks collision of snake with other objects
   public void checkCollision()
   {
      //Left and right boundaries 
      if(snakeX[0]<150 || snakeX[0]>=775)
      {
         run = false;
      }
      //Upper and Lower boundaries
      if(snakeY[0]<50 || snakeY[0]>=675)
      {
         run = false;
      }
      //Collision with itself
      for(int i=1;i<snake.getSize();i++)
      {
         if(i >= 4 && snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i])
         {
            run = false;
            break;
         }
      }
      //Door collision
      if(snakeX[0] == 450 && snakeY[0] == 650 && finishedLevel == true|| snakeX[0] == 475 && snakeY[0] == 650 && finishedLevel == true)
      {
         level++;
         timer.stop();
         startGame();      
      }
      //Collision with food
      if(snakeX[0] == food.getX() && snakeY[0] == food.getY())
      {
         score += 10;
         nextLevel -= 10;
         snake.grow();
         eaten = true;
         if(nextLevel != 0)
            spawnFood();
         //If the level is complete, stops spawning food
         else
         {
            food.setX(0);
            food.setY(0);
            finishedLevel = true;
         }
      }
   }
   //Genearates food at a random tile
   public void spawnFood()
   {
      Random rand = new Random();
      int randX;
      int randY;
      boolean repeat = true;
      
      while(repeat)
      {
         repeat = false;
         //Sets fruit x/y coordinates to random values within grid
         randX = GRID_X + (rand.nextInt(25)*BOX_WH);
         randY = GRID_Y + (rand.nextInt(25)*BOX_WH);
         food.setX(randX);
         food.setY(randY);
         
         for(int i = 0;i<snake.getSize();i++)
         {
            //If random spot is on top of snake
            if(snakeX[i] == randX && snakeY[i] == randY)
            {
               repeat = true;
               break;
            }
         }
      }    
   }
   //Waits for next tick of timer, and then calls the core methods of the game
   public void actionPerformed(ActionEvent e)
   {
      if(run == true && pause == false)
      {
         move();
         checkCollision();
      }
      repaint();
   }
   
   //Paints images to screen
   public void paint(Graphics g)
   {
      //Black background
      g.setColor(Color.black);
      g.fillRect(0,0,800,750);
      
      //While game is running, prints food, the snake, the grid, and text
      if(run == true)
      {
         if(finishedLevel == false)
            drawFood(g);
         if(finishedLevel == true)
            drawDoor(g);
            
         drawSnake(g);
         drawGrid(g);
         drawText(g);
         
         if(pause == true)
         {
            g.setColor(Color.white);
            g.drawString("PAUSED", 450, 35);
         }
      }
      
      else if(run == false)
      {
         drawGameOver(g);
      }
   
   }
   //Draws the grid in which the snake can move
   public void drawGrid(Graphics g)
   {
      g.setColor(Color.gray);
      //Grid outline
      g.drawRect(GRID_X, GRID_Y, GRID_WIDTH, GRID_HEIGHT);
      //Vertical lines
      for(int i = GRID_X; i<(GRID_X+GRID_WIDTH);i+=(BOX_WH))
      {
         g.drawLine(i, GRID_Y, i, (GRID_Y+GRID_HEIGHT));
      }
      //Horizontal lines
      for(int i = GRID_Y; i<(GRID_Y+GRID_HEIGHT); i+=(BOX_WH))
      {
         g.drawLine(GRID_X, i, (GRID_X+GRID_WIDTH), i);
      }
   }
   //Draws door to next level
   public void drawDoor(Graphics g)
   {
      g.setColor(Color.white);
      g.drawLine(450, 675, 500, 675);
      g.fillRect(450, 650, 50, 25);
   }
   //Draws text of level, score, and points to next level
   public void drawText(Graphics g)
   {      
      g.setColor(Color.white);
      g.drawString("Level: "+level,LEVEL_X, LEVEL_Y);
      g.drawString("Score: "+score,SCORE_X, SCORE_Y);
      g.drawString("Points To Next Level: "+nextLevel,NEXT_LEVEL_X, NEXT_LEVEL_Y);
      if(finishedLevel)
         g.drawString("Proceed to next level", 420, 700);
         
      //Creates black text to partially solve a problem with the GUI fickering
      g.setColor(Color.black);
      g.drawString("Easter Egg", 0, 0);
   }
   //Draws snake
   public void drawSnake(Graphics g)
   {
      //Paints if the snake has just grown
      if(eaten == true)
      {
         for(int i = 0;i<snake.getSize()-1;i++)
         {
            g.setColor(Color.green);
            //Changes colour for head of snake
            if(i == 0)
            {
               g.setColor(new Color(204,255,204));
            }
            g.fillRect(snakeX[i], snakeY[i], BOX_WH, BOX_WH);
         }
      }
      //Paints if the snake has not just grown
      else
      {
         for(int i = 0;i<snake.getSize();i++)
         {
            g.setColor(Color.green);
            if(i == 0)
            {
               g.setColor(new Color(204,255,204));
            }
            g.fillRect(snakeX[i], snakeY[i], BOX_WH, BOX_WH);
         }
      }
      eaten = false; //Resets the "eaten" boolean
   }
   //Draws food
   public void drawFood(Graphics g)
   {
      g.setColor(Color.red);
      g.fillRect(food.getX(),food.getY(), BOX_WH, BOX_WH);
   }
   
   //Draws the game over screen
   public void drawGameOver(Graphics g)
   {
      g.setColor(Color.white);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
      g.drawString("Game Over",300,100);
      g.drawString("Level: " + level,330,200);
      g.drawString("Score: " + score,315,300);
      g.drawString("Press spacebar to start a new game",120,450);
      g.drawString("Press esc to exit",280,550);
   
      timer.stop();
   }
      
   //Responds to key inputs
   private class Key extends KeyAdapter
   {
      public void keyPressed(KeyEvent ke)
      {
         int key = ke.getKeyCode();
         
         //Changes the direction of the snake based on the key pressed
         if(moved == true)
         {
            if(key == KeyEvent.VK_UP && snake.getDown() == false && pause == false && run == true)
            {
               snake.setDirections(true, false, false, false); //up, down, left, right
               moved = false;
            }
            
            if(key == KeyEvent.VK_DOWN && snake.getUp() == false && pause == false && run == true)
            {
               snake.setDirections(false, true, false, false); //up, down, left, right
               moved = false;
            }
            
            if(key == KeyEvent.VK_LEFT && snake.getRight() == false && pause == false && run == true)
            {
               snake.setDirections(false, false, true, false); //up, down, left, right
               moved = false;
            }
            
            if(key == KeyEvent.VK_RIGHT && snake.getLeft() == false && pause == false && run == true)
            {
               snake.setDirections(false, false, false, true); //up, down, left, right
               moved = false;
            }
            //Pauses game
            if(key == KeyEvent.VK_SPACE && pause == false && run == true)
            {
               pause = true;
            }
            //Unpauses game
            else if(key == KeyEvent.VK_SPACE && pause == true && run == true)
            {
               pause = false;
            }
         	//Starts a new game
            if (key == KeyEvent.VK_SPACE && run == false && pause == false)
            {
               level = 1;
               score = 0;
               startGame();
            }
         	//Exits game
            if (key == KeyEvent.VK_ESCAPE && run == false && pause == false)
            {
               System.exit(0);
            }
         }
      }
   }
}