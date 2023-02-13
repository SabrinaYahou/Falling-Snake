package View;

import Model.Element;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Images {
    protected static Image background, snakeLogo, gameover, winner;
    protected static Image backgroundCells, border, info, pressSpace;
    protected static Image empty, snake, superSnake, strawberry, bluberry, gold, wood, shooter;
   
    
    protected static void loadImages() {
        try {
            background = ImageIO.read(new File("images/background.jpg"));
          
            backgroundCells = ImageIO.read(new File("images/bgCells.jpg"));
            border = ImageIO.read(new File("images/border.jpg"));
            info = ImageIO.read(new File("images/infoBG.jpg"));
            pressSpace = ImageIO.read(new File("images/press.png"));
            gameover = ImageIO.read(new File("images/gameOverBG.jpg"));
            winner = ImageIO.read(new File("images/winner.jpg"));
            empty = ImageIO.read(new File("images/empty.png"));
            snake = ImageIO.read(new File("images/snake.png"));
            superSnake = ImageIO.read(new File("images/superSnake.png"));
            gold = ImageIO.read(new File("images/gold.png"));
            strawberry = ImageIO.read(new File("images/strawberry.png"));
            bluberry = ImageIO.read(new File("images/bluberry.png"));
            wood = ImageIO.read(new File("images/wood.png"));
            shooter = ImageIO.read(new File("images/shooter.png"));
            
        } 
        catch (IOException ex) {
            System.err.println("Error reading images - class Images.java");
        }
    }
    
    public static Image getImageOfType(Element element) {
        switch (element) {
            case EMPTY: return empty;
            case SNAKE: return snake;
            case SUPERSNAKE: return superSnake;
            case GOLD: return gold;
            case WOOD: return wood;
            case STRAWBERRY: return strawberry;
            case BLUBERRY: return bluberry;
            default: {
            	 return empty;
                }
            }
        }


        public static Image getImageShooter(){
            return shooter;
        }
   }
