/* BallGameProject.java in Java
   Matthew DuPrey
   4/12/2023
*/

//import the classes that I am going to need
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.image.*;
import javafx.scene.canvas.*;
import javafx.event.*;
import java.io.*;
import java.text.*;


public class BallGameProject extends Application
{  
   //these are used to hold the amount of Balls and the amount of Moves left
   //these are set to their initial values
   private int numberOfBalls = 15;
   private int numberOfMoves = 2;
   
   //these are used when adding things to the gridPanes
   private int i = 0;
   private int j = 0;
   
   //these are used when you are showing which button was pressed
   private int x = 0;
   private int y = 0;
   
   //this keepse track of what direction the button pressed was
   private String direction = new String("");
   
   //this makes the label at the top of the screen
   private Label gameLabel = new Label("Balls Left: " + numberOfBalls + "\t" + "Possible Moves: " + numberOfMoves);
   
   //this creates the graphics context
   private GraphicsContext gc;
   
   //this makes the 2d array to hold the gamePanes 
   private GamePane[][] gamePanes = new GamePane[4][4];
   
   //this makes the 2d array to hold the boolean of whether or not certain things are visible
   private Boolean[][] ballChecker = new Boolean[4][4];
   private Boolean[][] moveBallDownVisibility = new Boolean[4][4];
   private Boolean[][] moveBallLeftVisibility = new Boolean[4][4];
   private Boolean[][] moveBallRightVisibility = new Boolean[4][4];
   private Boolean[][] moveBallUpVisibility = new Boolean[4][4];
       
   //creates my flowpane
   private FlowPane root = new FlowPane();
   
      
   public void start(Stage stage)
   {
      
      //this creates the main gridPane
      GridPane gp = new GridPane();
      
      //this is used to make the grid panes and the gamepanes and to add vlues to the ball checker
      for(i = 0; i < 4; i++)
      {
         for(j = 0; j < 4; j++)
         {  
            if(i == 0 && j == 2)
            {
               ballChecker[i][j] = false;
            }
            else
            {
               ballChecker[i][j] = true;
            }
            
            //creates the gamepane objet
            GamePane gamePane = new GamePane();
            gamePanes[i][j] = gamePane;
            
            //adds each of the gamepanes to the gridpane
            gp.add(gamePanes[i][j],i,j);
         }
      }
      
      //this creates the border pane
      BorderPane borderPane = new BorderPane();
      borderPane.setPrefSize(600,600);
      
      //this sets the top part of the borderpane to the label
      borderPane.setTop(gameLabel);
      //sets the allignment of the gameLable to the center
      borderPane.setAlignment(gameLabel,Pos.CENTER);
      
      //adds spacing between each of the options
      gp.setHgap(10);
      gp.setVgap(10);
      
      //adds the gridPane to the center
      borderPane.setCenter(gp); 
      
      //makes the game centered
      borderPane.setPadding(new Insets(0,30,0,30)); 
      
      //adds the borderPane to the root
      root.getChildren().add(borderPane);
      
      //runs the move checker to see what buttons should be visible
      moveChecker();
      
      for(i = 0; i < 4; i++)
      {
         for(j = 0; j < 4; j++)
         {  
            //draws everything initially
            gamePanes[i][j].draw();
         }
      }
            
      Scene scene = new Scene(root, 600, 600); //other scene code here
      stage.setScene(scene);
      stage.setTitle("Ball Game");
      stage.show();
   }

   public class GamePane extends GridPane
   {
      //these are each of my five booleans that I use
      boolean ballVisibility = true;
      boolean moveUpButtonVisibility = false;
      boolean moveDownButtonVisibility = false;
      boolean moveLeftButtonVisibility = false;
      boolean moveRightButtonVisibility = false;
      
      //this creates the internal gridpane
      GridPane gamePane = new GridPane();  
      
      //establishes the canvas
      Canvas ballHolder = new Canvas(80,80);
      
      //establishes the graphics context
      GraphicsContext gc = ballHolder.getGraphicsContext2D();
      
      //this creates each of the buttons
      Button moveBallDown = new Button();
      Button moveBallUp = new Button();
      Button moveBallLeft = new Button();
      Button moveBallRight = new Button();
         
      public GamePane()
      {  
         super();
         
         //this adds the booleans to the 2d arrays
         moveBallUpVisibility[i][j] = moveUpButtonVisibility;
         moveBallRightVisibility[i][j] = moveRightButtonVisibility;
         moveBallLeftVisibility[i][j] = moveLeftButtonVisibility;
         moveBallDownVisibility[i][j] = moveDownButtonVisibility;
         
         //this adds the listener to the buttons
         moveBallDown.setOnAction(new ButtonHandler());
         moveBallUp.setOnAction(new ButtonHandler()); 
         moveBallLeft.setOnAction(new ButtonHandler());
         moveBallRight.setOnAction(new ButtonHandler());
         
         //sets the size of all the buttons
         moveBallDown.setPrefSize(80,20);
         moveBallUp.setPrefSize(80,20);
         moveBallLeft.setPrefSize(20,80);
         moveBallRight.setPrefSize(20,80); 
         
         //runs if the ball is supposed to be visible
         if(ballChecker[i][j] == true)
         {
            //sets the fill color and then draws the ball
            gc.setFill(new Color(1,0,0,1));
            gc.fillOval(0, 0, 80, 80);
         }        
         
         //adds everything to the internal gridpane
         add(moveBallDown, 1, 0);
         add(moveBallLeft, 2, 1);
         add(moveBallUp, 1, 2);
         add(moveBallRight, 0, 1);      
         add(ballHolder,1,1);
         
      }
      
      public void draw()
      {
         //this runs if the ball is visible
         if(ballChecker[i][j] == true)
         {
            gc.setFill(new Color(1,0,0,1));
            gc.fillOval(0, 0, 80, 80);
         }
         //this runs if it is not
         else
         {
            gc.clearRect(0, 0, 80, 80);
         }
               
         //this runs if the move down button is visible
         if(moveBallDownVisibility[i][j] == true)
         {
            moveBallDown.setVisible(true);
         }
         //this runs if it is not
         else
         {
            moveBallDown.setVisible(false);
         }
               
         //this runs if the move up button is visible
         if(moveBallUpVisibility[i][j] == true)
         {
            moveBallUp.setVisible(true);
         }
         //this runs if it is not
         else
         {
            moveBallUp.setVisible(false);
         }
               
         //this runs if the move left button is visible
         if(moveBallLeftVisibility[i][j] == true)
         {
            moveBallLeft.setVisible(true);
         }
         //this runs if it is not
         else
         {
            moveBallLeft.setVisible(false);
         }
               
         //this runs if the move right button is visible
         if(moveBallRightVisibility[i][j] == true)
         {
            moveBallRight.setVisible(true);
         }
         //this runs if it is not
         else
         {
            moveBallRight.setVisible(false);
         }
         
         //this changes the label to say you win when one ball is left
         if(numberOfBalls == 1)
         {
            gameLabel.setText("You Win!!!!!!!!");
         }
         
         //this changes the game label to say you lose if there are no moves left
         if(numberOfMoves == 0 && numberOfBalls != 1)
         {
             gameLabel.setText("You Lose");
         }
         
         //This updates the label for normal gameplay
         if(numberOfBalls > 1 && numberOfMoves > 0)
         {
            gameLabel.setText("Balls Left: " + numberOfBalls + "\t" + "Possible Moves: " + numberOfMoves);
         }  
      }  
         
      //This is used to get which of the buttons the user actually clicked
      public Button getSelectedButton(String direction) 
      {
         if(direction == "down") 
         {
            //if it is down it returns the moveBallDown button
            return moveBallDown;
         } 
         else if(direction == "up") 
         { 
            //if it is up it returns the moveBallUp button
            return moveBallUp;
         } 
         else if(direction == "right") 
         { 
            //if it is right it returns the moveBallUp button
            return moveBallRight;
         } 
         else if(direction == "left") 
         { 
            //if it is left it returns the moveBallUp button
            return moveBallLeft;
         }
         
         return null;
      }
   }
   
   public class ButtonHandler implements EventHandler<ActionEvent>  
   {
      public void handle(ActionEvent e) 
      {         
         for(y = 0; y < 4; y++) 
         {
            for(x = 0; x < 4; x++) 
            {
               if(e.getSource() == gamePanes[x][y].getSelectedButton("down")) 
               {
                  //this sends the stuff inside to the click method after finding which button was clicked   
                  click("down",x,y); 
               } 
               else if(e.getSource() == gamePanes[x][y].getSelectedButton("left")) 
               { 
                  //this sends the stuff inside to the click method after finding which button was clicked
                  click("left",x,y); 
               } 
               else if(e.getSource() == gamePanes[x][y].getSelectedButton("right")) 
               {
                  //this sends the stuff inside to the click method after finding which button was clicked
                  click("right",x,y); 
               } 
               else if(e.getSource() == gamePanes[x][y].getSelectedButton("up")) 
               { 
                  //this sends the stuff inside to the click method after finding which button was clicked
                  click("up",x,y); 
               }
            }
         }      
      }
   }
   
   public void moveChecker()
   {
      //resets the amount for the Number of Balls and the NumberOfMoves variables
      numberOfBalls = 0;
      numberOfMoves = 0;
         
      //this loop runs through so that it checks all the parts of the Arrays
      for(int l = 0; l < 4; l++) 
      {
         for(int m = 0; m < 4; m++) 
         {    
            //Checks to see if the boolean at the location is true and if it is adds one to the number of Balls
            if(ballChecker[l][m] == true)
            {
               numberOfBalls++; 
            }
                 
            //Checks if there is a ball 2 spaces each ball
            if(m + 2 < 4) 
            {
               //this setts the move down button to be true and increases the number of moves
               if(ballChecker[l][m + 1]== true && ballChecker[l][m + 2] == false && ballChecker[l][m] == true) 
               {
                  moveBallDownVisibility[l][m] = true;
                  numberOfMoves++;
               } 
               //sets the move down button to be false
               else 
               {
                  moveBallDownVisibility[l][m] = false;
               }
            }
         
            //Checks if there is a ball 2 spaces each ball
            if(l - 2 > -1) 
            {
               //this setts the move left button to be true and increases the number of moves
               if(ballChecker[l-1][m] == true && ballChecker[l - 2][m] == false && ballChecker[l][m] == true) 
               {
                  moveBallLeftVisibility[l][m] = true;
                  numberOfMoves++;
               } 
               //sets the move left button to be false
               else 
               {
                  moveBallLeftVisibility[l][m] = false;
               }
            }
            
            //Checks if there is a ball 2 spaces each ball
            if(m-2 > -1) 
            {
               //this setts the move up button to be true and increases the number of moves
               if(ballChecker[l][m - 1] == true && ballChecker[l][m - 2] == false && ballChecker[l][m] == true) 
               {
                  moveBallUpVisibility[l][m] = true;
                  numberOfMoves++;
               }
               //sets the move up button to be false 
               else 
               {
                  moveBallUpVisibility[l][m] = false;
               }
            }
                 
            //Checks if there is a ball 2 spaces each ball
            if(l + 2 < 4) 
            {
               //this setts the move right button to be true and increases the number of moves
               if(ballChecker[l + 1][m] == true && ballChecker[l + 2][m] == false && ballChecker[l][m] == true) 
               {
                  moveBallRightVisibility[l][m] = true;
                  numberOfMoves++;
               }
               //sets the move right button to be false 
               else 
               {
                  moveBallRightVisibility[l][m] = false;
               }
            }
         }
      }
   }
   
   public void click(String direction, int x, int y)
   {
      //this is if the clicked button was to move the ball up
      if(direction.equals("up"))
      {
         ballChecker[x][y] = false; 
         ballChecker[x][y - 1] = false;             
         ballChecker[x][y - 2] = true; 
      }
      
      //this is if the clicked button was to move the ball down
      if(direction.equals("down"))
      {
         ballChecker[x][y] = false; 
         ballChecker[x][y + 1] = false;             
         ballChecker[x][y + 2] = true; 
      }
      
      //this is if the clicked button was to move the ball left
      if(direction.equals("left"))
      {
         ballChecker[x][y] = false; 
         ballChecker[x - 1][y] = false;             
         ballChecker[x - 2][y] = true;
      }
      
      //this is if the clicked button was to move the ball right
      if(direction.equals("right"))
      {
         ballChecker[x][y] = false; 
         ballChecker[x + 1][y] = false;             
         ballChecker[x + 2][y] = true;
      }
      
      //this runs the move checker
      moveChecker();
      
      //this runs through and draws for each of the gamepanes
      for(i = 0; i < 4; i++)
      {
         for(j = 0; j < 4; j++)
         {
            gamePanes[i][j].draw();
         }
      }
   }
   
   public static void main(String[] args)
   {
      launch(args);
   }
}
