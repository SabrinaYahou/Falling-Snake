package Model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import javax.imageio.ImageIO;

import Utils.Constants;
import View.Images;


public class Shooter {
	
	private int defaultX = Constants.paddingX+Constants.BORDERSIZE+12*Constants.DISTSIZE;
	private int defaultY = 5*Constants.paddingY+Constants.BORDERSIZE+Constants.HEIGHT*Constants.DISTSIZE;
	private int x, y;
	private ArrayList<Projectile> projectiles ;
	private Image image;
	private int speed = 25;
	private int paddingX = 25;
	private Game game;

	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	public Shooter (Game game) throws IOException {
		this.x=defaultX;
		this.y=defaultY;
		this.game = game;
		projectiles = new ArrayList<>();
		this.image = Images.getImageShooter();
	}

	public Image getImage() {
		return this.image;
	}

	// Methode shoot() qui crée une nouvelle projectile et qui l'ajoute dans la liste des projectiles
	public void shoot() {
		Projectile p = new Projectile(x + speed, y - 50, game);
		projectiles.add(p);
	}

	// Methode removeProjectile qui supprime une projectile donnée dans la liste des projectiles
	public void removeProjectile(Projectile p) {
		projectiles.remove(p);
	}

	// Methode moveRight qui deplace le shooter vers la droite en augmentant la coordonnée x
	public void moveRight() {
		if (x == paddingX + (Constants.WIDTH - 1) * speed) {
			x = paddingX + (Constants.WIDTH - 1) * speed;
		} else
			this.x = this.x + this.speed;
	}

	// Methode moveRight qui deplace le shooter vers la gauche en decrémentant la coordonnée x
	public void moveLeft() {
		if (x == paddingX) {
			x = paddingX;
		} else
			this.x = this.x - this.speed;
	}

	
	
}