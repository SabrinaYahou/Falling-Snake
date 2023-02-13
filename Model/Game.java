package Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import Utils.Constants;



public class Game {


	protected Player player;
	protected Snake snake;
	protected Cell[][] cells;
	protected Thread thread;
	protected ArrayList<Obstacles> obstacles;
	protected Shooter shooter;


	protected static int speed = 100;
	private boolean gameIsStarted;
	private boolean gameIsPaused;
	private boolean gameOver;
	private boolean playerIsTheWinner;

    /* le constructeur Game() initialise Player, snake et cells (Plateu). au debut toutes les cases sont vide ensuite on parcours les cases du snake afin de mettre à jour les cases
     correspondante (avec les memes coordonnées) sur le plateau avec l'element Snake. Pour les obstacles, on génère des coordonnées aléatoirement puis on change l'element de case
      correspondante dans le plateau
    */
	public Game() {
		player = new Player();
		snake = new Snake();
		cells = new Cell[Constants.WIDTH][Constants.HEIGHT];
		obstacles = new ArrayList<Obstacles>();

		for (int i = 0; i < Constants.WIDTH; i++)
			for (int j = 0; j < Constants.HEIGHT; j++)
				cells[i][j] = new Cell(Constants.paddingX + Constants.BORDERSIZE + i * Constants.DISTSIZE, Constants.paddingY + Constants.BORDERSIZE + j * Constants.DISTSIZE, Element.EMPTY);

		for (int idx = 0; idx < snake.length(); idx++) {
			int i = snake.getX(idx), j = snake.getY(idx);
			cells[i][j].setElement(snake.getType());
		}
		Random r = new Random();
		for (int i = 0; i < 35; i++) {
			int element = r.nextInt(4);
			if (element == 0) {
				Obstacles o = new Obstacles(Element.STRAWBERRY);
				o.creatThings(this);
				obstacles.add(o);
			}
			if (element == 1) {
				Obstacles o = new Obstacles(Element.BLUBERRY);
				o.creatThings(this);
				obstacles.add(o);
			}
			if (element == 2) {
				Obstacles o = new Obstacles(Element.GOLD);
				o.creatThings(this);
				obstacles.add(o);
			}
			if (element == 3) {
				Obstacles o = new Obstacles(Element.WOOD);
				o.creatThings(this);
				obstacles.add(o);
			}
		}
		try {
			shooter = new Shooter(this);
		} catch (IOException e) {
			e.printStackTrace();
		}

		gameOver = false;
		gameIsStarted = false;
		gameIsPaused = true;
		playerIsTheWinner = false;


	}
    /* Methode updateCells() qui met à jour les elements de chaque case, elle met déja toutes
     les cases à vide (Empty) puis change les elements des cases avec l'element snake, obstacles ou projectile
     */
	public void updateCells() {

		for (int i = 0; i < Constants.WIDTH; i++)
			for (int j = 0; j < Constants.HEIGHT; j++)

				cells[i][j].setElement(Element.EMPTY);

		for (int i = 0; i < snake.length(); i++) {
			int x = snake.getX(i), y = snake.getY(i);
			if (x < 0 || x >= Constants.WIDTH || y < 0 || y >= Constants.HEIGHT) continue;
			cells[x][y].setElement(snake.getType());
		}
		for (int i = 0; i < obstacles.size(); i++) {
			Obstacles o = obstacles.get(i);
			o.updateCell(this);
		}
		if (shooter.getProjectiles().size() > 0) {

			for (int i = 0; i < shooter.getProjectiles().size(); i++) {

				shooter.getProjectiles().get(i).update();
			}
		} else return;
	}

	public Snake getSnake() {
		return snake;
	}

	public Cell[][] getCells() {
		return cells;
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public ArrayList<Obstacles> getObstacles() {
		return obstacles;
	}

	public Shooter getShooter() {
		return shooter;
	}

	public boolean isGameIsStarted() {
		return gameIsStarted;
	}

	public boolean isGameIsPaused() {
		return gameIsPaused;
	}

	public void setGameIsPaused(boolean gameIsPaused) {
		this.gameIsPaused = gameIsPaused;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public boolean isPlayerIsTheWinner() {
		return playerIsTheWinner;
	}

	public void setPlayerIsTheWinner(boolean playerIsTheWinner) {
		this.playerIsTheWinner = playerIsTheWinner;
	}

	public int getSpeed() {
		return speed;
	}
}
