package Model;

import java.util.ArrayList;
import java.util.Random;

import Utils.Constants;


public class Snake {
    
	protected static final int lengthDefault = 6,posDefaultX = 6, posDefaultY = 0;
	
	private final Direction dirDefault  = Direction.RIGHT;
	
	private ArrayList<Integer> X, Y;
	private Direction direction;
	private boolean immortal;
	private boolean isOpen;
	protected static int timesEatBluberry, countTimeImmortal, defaultTimeBluberry = 50;

	
	public Snake() {
		direction = dirDefault;
		X = new ArrayList<>();
		Y = new ArrayList<>();
		
		for(int i = 0; i < lengthDefault; i ++) {
			X.add(posDefaultX-i);
			Y.add(posDefaultY);
		}
		 isOpen = true;

	}


	 public int length() {return X.size();}

	 protected int getX(int i) {return X.get(i);}                                  
	 protected int getY(int i) {return Y.get(i);}

	 
	 protected Element getType() {
	         if (immortal==true) {
	        	 return Element.SUPERSNAKE;
	         }
	         else return Element.SNAKE;
	 }

	 // Methode Move() qui deplace le snake (en mettant à jour les coordonnées de ses cases) selon la direction (en incrémentant ou en décrementant la coordonnée x ou Y )
	public void move() {
		if (length() == 0) return;
		for (int i = length() - 1; i > 0; i--) {
			X.set(i, X.get(i - 1));
			Y.set(i, Y.get(i - 1));
		}
		int x = X.get(0);
		int y = Y.get(0);
		if (direction == Direction.RIGHT && x == Constants.WIDTH - 1) {
			X.set(0, x + Direction.DOWN.getPosX());
			Y.set(0, y + Direction.DOWN.getPosY());
			direction = Direction.LEFT;

		} else if (direction == Direction.LEFT && x == 0) {
			X.set(0, x + Direction.DOWN.getPosX());
			Y.set(0, y + Direction.DOWN.getPosY());
			direction = Direction.RIGHT;

		} else {
			X.set(0, x + direction.getPosX());
			Y.set(0, y + direction.getPosY());
		}
	}

	/* Methode checkEatStrawberry qui vérifie si le snake a mangé la fraise (si les coordonnées de la tete du snake sont les meme que celle de l'obstacle fraise )
	 si c'est le cas alors la taille du snake augmente et met à jour le statut( available )de l'obstacle */
	 public void checkEatStrawberry(Game g,Obstacles c) {
		
		 int x = X.get(0);
		 int y = Y.get(0);
		 
		 if (x != c.getX() || y != c.getY() || c.getElement() != Element.STRAWBERRY ) return;
		 this.grow(g, 1);
		 c.setAvailable(false);	 
	 }
    /* Methode grow() qui  augmente la taille du snake en ajoutant une case à sa queue en prenant en compte son emplacement sur le plateau au moment de l'ajout
    on distingue 4 cas différent : snake direction droite au milieu du plateau / snake direction gauche au milieu du plateau / snake direction droite à l'extrémité du plateau
     / snake direction gauche à l'extrémité du plateau */
	public void grow(Game g, int i) {
		if (length() == 1) {
			int x = X.get(0);
			int y = Y.get(0);
			if (direction == Direction.RIGHT) {
				if (x == 0) {
					X.add(x);
					Y.add(y - 1);
				} else {
					X.add(x - 1);
					Y.add(y);
				}
			} else {
				if (x == Constants.WIDTH - 1) {
					X.add(x);
					Y.add(y - 1);
				} else {
					X.add(x + 1);
					Y.add(y);
				}
			}
		} else {
			for (int j = 0; j < i; j++) {
				int x = X.get(X.size() - 1);
				int x1 = X.get(X.size() - 2);
				int y = Y.get(Y.size() - 1);
				if (direction == Direction.RIGHT) {
					if (x == x1) {
						X.add(x + 1);
						Y.add(y);
					} else if (x != x1 && x == 0) {
						X.add(x);
						Y.add(y - 1);
					} else {
						X.add(x - 1);
						Y.add(y);
					}
				} else {
					if (x == x1) {
						X.add(x - 1);
						Y.add(y);
					} else if (x == Constants.WIDTH - 1 && x != x1) {
						X.add(x);
						Y.add(y - 1);
					} else {
						X.add(x + 1);
						Y.add(y);
					}
				}
			}
		}
	}	
	 // methode checkEndImmortal() qui verifie la fin de l'immortalité du snake (SperSnake)
	 public void checkEndImmortal() {
		 if (timesEatBluberry == 0)return ;
		 if (++countTimeImmortal == defaultTimeBluberry) {
			 immortal = false;
			 timesEatBluberry--;
			 countTimeImmortal = 0;
		 }
	 }
	 /* methode checkEatBluberry () qui verifie si le snake a mangé une myrtille (les coordonnées de la tete du snake et les coordonnées de l'obstacle myrtille sont les memes)
	  pour le rendre immortel pendant quelque temps */
	 public void checkEatBluberry(Obstacles o) {
		 int x = X.get(0);
		 int y = Y.get(0);
		 if (x != o.getX() || y != o.getY() || o.getElement()!= Element.BLUBERRY) return;
		 o.setAvailable(false);
		 immortal = true;
		 timesEatBluberry++;
	 }
	/* methode checkEatWood () qui verifie si le snake a rencontré un obstacle bois (les coordonnées de la tete du snake et les coordonnées de l'obstacle bois sont les memes)
     pour descendre la ligne et changer de direction */
	 public void checkEatWood(Obstacles o) {
		
		 int x = X.get(0);
		 int y = Y.get(0);
		 if (x != o.getX() || y != o.getY() || o.getElement()!= Element.WOOD) {return;}
		 
		 if (direction == Direction.RIGHT) {
			 X.set(0, x + Direction.DOWN.getPosX());
			 Y.set(0, y + Direction.DOWN.getPosY());
			 direction = Direction.LEFT;
			 
		 }else if (direction == Direction.LEFT) {
			 X.set(0, x + Direction.DOWN.getPosX());
			 Y.set(0, y + Direction.DOWN.getPosY());
			 direction = Direction.RIGHT;}
		 
	 }
	/* methode checkEatGold () qui verifie si le snake a mangé un obstacle pièce d'or (les coordonnées de la tete du snake et les coordonnées de l'obstacle or sont les memes)
     pour changer aléatoirement les emplacements des obstacles du plateau */
	 public void checkEatGold(Obstacles o, Game game){
	
		 int x = X.get(0);
		 int y = Y.get(0);
		 if (x != o.getX() || y != o.getY() || o.getElement()!= Element.GOLD) {return;}
		 o.setAvailable(false);
		 Random r = new Random();
		 for(int i = 0; i < game.obstacles.size(); i ++) {
			 int newElement = r.nextInt(4);
			 if (newElement == 0) {
				 game.obstacles.get(i).setElement(Element.BLUBERRY);
			 }
			 if (newElement == 1) {
				 game.obstacles.get(i).setElement(Element.STRAWBERRY);
			 }
			 if (newElement == 2) {
				 game.obstacles.get(i).setElement(Element.WOOD);
			 }
			 if (newElement == 3) {
				 game.obstacles.get(i).setElement(Element.GOLD);
			 }
			 
		 }
	 }
	 
	 
	 
	 public boolean isAlive() {
	        if (length() == 0) return false;
	        else return true;
	 }
	 public boolean SnakeIsOut() {
		 if(Y.get(0) > Constants.HEIGHT - 1) return true;
		 else return false;
	 }
	 protected void remove(int pos) {
		 X.remove(pos);
		 Y.remove(pos);
	 }
	 
	 @Override
	    public String toString() {
	    	String s = "";
	    	for (int i = 0; i< X.size();i++) {
	    		s= s+ "(" + X.get(i)+";"+Y.get(i)+")";
	    	}
	    	return s;
	    }
	 
	
}
