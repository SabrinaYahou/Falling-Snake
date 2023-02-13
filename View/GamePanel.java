package View;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.JPanel;

import Model.*;
import Utils.Constants;


public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {

	private Game game;
	
	
	 public GamePanel() {
	        setPreferredSize(new Dimension(FrameGame.getWidthFrame(), FrameGame.getHeightFrame()));
	        setFocusable(true);
	        newGamePanel();
	    }
    
   public void newGamePanel() {
        game = new Game();
        game.setThread(new Thread(this));  
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        game.getThread().start();
    }
	
    /* Methode run() qui fait tourner le jeu : si le jeu est sur pause on afffiche l'affichage adapté et on attend
     sinon on verifie si le snake ou le joueur a gangé pour arreter le jeu et faire l'affichage adapté, si le jeu est toujours
      en cours on fait avancer le snake en verifiant si il a rencontré des obstacles et changer son état ou l'état du plateau selon ça */
	@Override
	public void run() {
		while (true) {
			if (!game.isGameIsPaused()) {
				if (!game.getSnake().isAlive())
					game.setPlayerIsTheWinner(true);
				else if (game.getSnake().SnakeIsOut())
					game.setGameOver(true);
				else {
					game.getSnake().move();
					game.getSnake().checkEndImmortal();
					for (int i = 0; i < game.getObstacles().size(); i++) {
						Obstacles o = game.getObstacles().get(i);
						game.getSnake().checkEatBluberry(o);
						game.getSnake().checkEatWood(o);
						game.getSnake().checkEatGold(o, game);
						game.getSnake().checkEatStrawberry(game, o);
					}
					if (Obstacles.addFood(game.getObstacles().size())) {
						Random r = new Random();
						int element = r.nextInt(3);
						if (element == 0) {
							Obstacles o = new Obstacles(Element.STRAWBERRY);
							o.creatThings(game);
							game.getObstacles().add(o);
						}
						if (element == 1) {
							Obstacles o = new Obstacles(Element.BLUBERRY);
							o.creatThings(game);
							game.getObstacles().add(o);
						}
						if (element == 2) {
							Obstacles o = new Obstacles(Element.GOLD);
							o.creatThings(game);
							game.getObstacles().add(o);
						}
					}
				}
				game.updateCells();
			}
			repaint();
			if (game.isGameOver() || game.isPlayerIsTheWinner()) {
				endGame();
				return;
			}
			try {
				Thread.sleep(10000 / game.getSpeed());
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}

	}
	 
	
	 private void endGame() {
	        removeKeyListener(this);
	        addMouseListener(this);
	        addMouseMotionListener(this);
	 }
	 
	 
	 @Override
	    public void paint(Graphics g) {
	    	 if (!game.isGameIsStarted()) {
	             g.drawImage(Images.background, 0, 0, null);     
	             g.drawImage(Images.border, Constants.paddingX, Constants.paddingY, Constants.BORDERSIZE, Constants.HEIGHTGAME-Constants.BORDERSIZE, null);
	             g.drawImage(Images.border, Constants.paddingX+Constants.BORDERSIZE, Constants.paddingY, Constants.WIDTHGAME-Constants.BORDERSIZE, Constants.BORDERSIZE, null);
	             g.drawImage(Images.border, Constants.paddingX+Constants.WIDTHGAME-Constants.BORDERSIZE, Constants.paddingY+Constants.BORDERSIZE, Constants.BORDERSIZE, Constants.HEIGHTGAME-Constants.BORDERSIZE, null);
	             g.drawImage(Images.border, Constants.paddingX, Constants.paddingY+Constants.HEIGHTGAME-Constants.BORDERSIZE, Constants.WIDTHGAME-Constants.BORDERSIZE, Constants.BORDERSIZE, null);
	    	 }
	    	 
	        if (game.isGameOver()) {
	            int x = Constants.paddingX+Constants.BORDERSIZE, y = Constants.paddingY+Constants.BORDERSIZE;
	            int w = Constants.WIDTHGAME-2*Constants.BORDERSIZE, h = Constants.HEIGHTGAME-2*Constants.BORDERSIZE;
	            g.drawImage(Images.gameover, x, y, w, h, null);
	            g.setColor(new Color(88,6,8));
	            g.setFont(new Font("Bookman Old Style", Font.BOLD, 60));
	            String score = String.format("%06d", Player.getScore());
	            g.drawString(score, x+200, y+250);
	            return ;
	        }
	        if(game.isPlayerIsTheWinner()) {
	        	int x = Constants.paddingX+Constants.BORDERSIZE, y = Constants.paddingY+Constants.BORDERSIZE;
	            int w = Constants.WIDTHGAME-2*Constants.BORDERSIZE, h = Constants.HEIGHTGAME-2*Constants.BORDERSIZE;
	            g.drawImage(Images.winner, x, y, w, h, null);
	            g.setColor(new Color(88,6,8));
	            g.setFont(new Font("Bookman Old Style", Font.BOLD, 60));
	            String score = String.format("%06d", Player.getScore());
	            g.drawString(score, x+200, y+250);
	            return ;
	        }
	        paintPlaygroud(g);
	        paintInformation(g);
	        showPressSpace(g);
	    }
		
		
		
	    private long TimeShowPause = (long) System.currentTimeMillis();
	    private boolean showPause = true;
	    private void showPressSpace(Graphics g) {
	        if (game.isGameIsPaused() && showPause) 
	            g.drawImage(Images.pressSpace, 90, 300, 550, 120, null);
	        long t = (long) System.currentTimeMillis();
	        if (t-TimeShowPause > 400) {
	            TimeShowPause = t; 
	            showPause = !showPause;
	        }
	    }
	    
	    private void paintInformation(Graphics g) {
	        int x = Constants.paddingX+Constants.WIDTHGAME+20, y = Constants.paddingY*7, w = 240, h = 380;
	        g.clearRect(x, y, w, h);
	        g.drawImage(Images.info, x, y, w, h, null);
	        g.setColor(new Color(88, 6, 8));
	        g.setFont(new Font("Bookman Old Style", Font.BOLD, 40));
	        String score = String.format("%06d", Player.getScore());
	        g.drawString(score, x+45, y+115);
	        String length = String.format("%03d", game.getSnake().length());
	        g.drawString(length, x+80, y+220);
	    }
	    
		private void paintPlaygroud(Graphics g) {
			int x = Constants.paddingX + Constants.BORDERSIZE, y = Constants.paddingY + Constants.BORDERSIZE;
			int w = Constants.WIDTHGAME - 2 * Constants.BORDERSIZE, h = Constants.HEIGHTGAME - 2 * Constants.BORDERSIZE;
			g.drawImage(Images.backgroundCells, x, y, w, h, null);

			for (int i = 0; i < Constants.WIDTH; i++)
				for (int j = 0; j < Constants.HEIGHT; j++)
					if (game.getCells()[i][j].getElement() != Element.EMPTY) {
						g.drawImage(game.getCells()[i][j].getImage(), game.getCells()[i][j].getPosX(),
								game.getCells()[i][j].getPosY(), Constants.CELLSIZE, Constants.CELLSIZE, null);
					}
			for (int i = 0; i < game.getShooter().getProjectiles().size(); i++) {
				g.drawImage(game.getShooter().getProjectiles().get(i).getImage(),
						game.getShooter().getProjectiles().get(i).getX(),
						game.getShooter().getProjectiles().get(i).getY(), Constants.CELLSIZE, Constants.CELLSIZE, null);
			}
			g.drawImage(game.getShooter().getImage(),
					game.getShooter().getX() ,
					game.getShooter().getY() , 3 * Constants.CELLSIZE,
					3 * Constants.CELLSIZE, null);
		}
		
		
		
		
	    @Override
	    public void keyPressed(KeyEvent e) {
	        e.consume();
	        int key = e.getKeyCode();
	        if (key == KeyEvent.VK_SPACE) {
	            game.setGameIsPaused(!game.isGameIsPaused());
	            return ;
	        }
	        if (game.isGameIsPaused()) return ;
	        if (key == KeyEvent.VK_LEFT) {
	        	game.getShooter().moveLeft();
	        }
	        if (key == KeyEvent.VK_RIGHT) {
	        	game.getShooter().moveRight();
	        }
	    }
	    
	    @Override
	    public void mouseClicked(MouseEvent e) {
	        if (game.isGameOver() == true || game.isPlayerIsTheWinner() == true) {
	        int x = e.getX(), y = e.getY();
	        boolean yflag = 460 <= y && y <= 510;
	        boolean exit  = 180 <= x && x <= 330;
	        boolean again  = 400 <= x && x <= 580;
	        if (!yflag) ;
	        if (exit) System.exit(0);
	        if (again) newGamePanel();}
	        else game.getShooter().shoot();
	    

	    }
	    @Override
	    public void mouseDragged(MouseEvent e) {
	        int x = e.getX(), y = e.getY();
	        boolean yflag = 460 <= y && y <= 510;
	        boolean again = 150 <= x && x <= 330;
	        boolean exit  = 430 <= x && x <= 580;
	        if (yflag && (again || exit)) setCursor(new Cursor(Cursor.HAND_CURSOR));
	        else setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    }
	    @Override
	    public void mouseMoved(MouseEvent e) {
	        int x = e.getX(), y = e.getY();
	        boolean yflag = 460 <= y && y <= 510;
	        boolean again = 150 <= x && x <= 330;
	        boolean exit  = 430 <= x && x <= 580;
	        if (yflag && (again || exit)) setCursor(new Cursor(Cursor.HAND_CURSOR));
	        else setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    }
	    
	    @Override
	    public void keyReleased(KeyEvent e) {
	        e.consume();
	    }
	    @Override
	    public void keyTyped(KeyEvent e) {
	        e.consume();
	    }
	    @Override
	    public void mousePressed(MouseEvent e) {
	        e.consume();
	    }
	    @Override
	    public void mouseReleased(MouseEvent e) {
	        e.consume();
	    }
	    @Override
	    public void mouseEntered(MouseEvent e) {
	      e.consume();
	    }
	    @Override
	    public void mouseExited(MouseEvent e) {
	        e.consume();
	    }

}
