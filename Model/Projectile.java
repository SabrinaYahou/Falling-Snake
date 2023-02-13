package Model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Utils.Constants;

public class Projectile {
    
	private Shooter shooter;
	private int x, y , speed;
	private Image image;
	private Game game;
	
	public Projectile(int x , int y , Game game) {
		this.x = x;
		this.y = y;
		this.speed = Constants.CELLSIZE;
		try {
			this.image = ImageIO.read(new File("images/projectile.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.game = game;
		this.shooter = game.shooter;
    }
		
		public Image getImage() {
			return this.image;
		}

	/* fonction update() qui fait déplacer la projectile sur le plateau, on commence par vérifier si la coordonnée Y de la projectile est bien supérieur à padddingY ce qui veut
	dire qu'elle est dans le plateau et donc peut toujours avancé en diminuant la coordonnée Y et ensuite vérifier les collisions avec le snake ou les obstacles. si la projectile
	est en dehors du plateau elle sera supprimé
	 */
	public void update() {
		if (y > Constants.paddingY) {
			y = y - speed;
			for (int j = 0; j < game.obstacles.size(); j++) {
				checkCollisionObstacles(game.obstacles.get(j));
			}
			for (int j = 0; j < game.snake.length(); j++) {
				checkCollisionSnake(game.snake.getX(j), game.snake.getY(j));
			}
		} else {
			shooter.removeProjectile(this);
		}
	}
    
	// Fonction checkCollisionObsatcles() qui vérifie si la projectile touche un obstacle en comparant leurs coordonnées,dans ce cas là l'obstacle est supprimé ainsi que la projectile

	public void checkCollisionObstacles(Obstacles o) {
		if (o.getX()*Constants.DISTSIZE+Constants.paddingX+Constants.BORDERSIZE == this.x && o.getY()*Constants.DISTSIZE+Constants.paddingY
				+ Constants.BORDERSIZE== this.y) {
	
			game.obstacles.remove(o);
			game.shooter.getProjectiles().remove(this);
		}
	}
	//Fonction checkCollisionObsatcles() qui vérifie si la projectile touche le snake en comparant leurs coordonnées,dans ce cas là, la taille du snake sera diminué et la projectile est supprimée
	public void checkCollisionSnake(int x, int y) {
		if (this.x == x*Constants.DISTSIZE+Constants.paddingX+Constants.BORDERSIZE && this.y == y*Constants.DISTSIZE+Constants.paddingY
				+ Constants.BORDERSIZE && game.snake.getType() == Element.SNAKE) {
			Player.setScore(Player.getScore() + 1);
			game.snake.remove(game.snake.length()-1);
			game.shooter.getProjectiles().remove(this); 
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
