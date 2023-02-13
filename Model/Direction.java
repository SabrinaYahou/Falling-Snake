package Model;

public enum Direction {
    
	
	LEFT(-1,0),RIGHT(1,0), DOWN(0,1), UP(0,-1);
	
	
    private int x;
    private int y;
    
    
	Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	
	public int getPosX() {
		return x;
	}
	
	public int getPosY() {
		return y;
	}
}