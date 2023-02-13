package Model;

import Utils.Constants;

public class Obstacles {
    private int x, y;
    private Element element;
    private boolean available;
    
    
    public Obstacles(Element element) {
           this.x = -1;
           this.y = -1;
           this.available = false;
           this.element = element;
           
    }
    
    private int randomX() {return random(0, Constants.WIDTH-1);}
    private int randomY() {return random(0, Constants.HEIGHT-1);}
    
    
    protected static int random(int L, int R) {
        return (int) Math.round(L + Math.random() * (R-L));
    }

    // Methode creatThings() génère aléatoirement des coordonnées et place l'element de l'obstacle dans la case concerné dans le plateau de jeu
    public void creatThings(Game game) {
        while (true) {
            x = randomX(); y = randomY();
            Cell c = game.cells[x][y];
            if (c.getElement() == Element.EMPTY) break;
        }
        available = true;
        game.cells[x][y].setElement(element);
    }
    
    protected void setAvailable(boolean bool) {available = bool;}
    protected int getX() {return x;}
    protected int getY() {return y;}
    
    
    public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}


	protected void updateCell(Game game) {
        if (!available) return ;
        if (x < 0 || x >= Constants.WIDTH || y < 0 || y >= Constants.HEIGHT) return ;
        game.cells[x][y].setElement(element);
    }

    // Methode addFood qui crée des nouveaux obstacles (foods) selon le nombre d'obstacles restant sur le plateau
    public static boolean addFood(int foodSize) {
        if (foodSize <= 2) return true;
        if (foodSize <= 5) return random(0, foodSize*100) <= 100;
        if (foodSize <= 9) return random(0, foodSize*300) <= 100;
        if (foodSize <= 15) return random(0, foodSize*500) <= 100;
        return false;
    }
    
}
