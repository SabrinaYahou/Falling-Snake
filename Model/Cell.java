package Model;

import java.awt.*;
import View.Images;

public class Cell {
	private int posX;
	private int posY;
	private Element element;
	private Image image;
	
	
	
	public Cell(int posX,int posY, Element element){
		this.posX =posX;
		this.posY = posY;
		this.element = element;
		this.image = Images.getImageOfType(element);
	}
	
	public int getPosX(){
		return posX;
	}

	public void setPosX(int posX){
		this.posX = posX;
	}

	public int getPosY(){
		return posY;
	}

	public void setPosY(int posY){
		this.posY = posY;
	}

	public Element getElement(){
		return element;
	}

	public void setElement(Element element){
		this.element = element;
		this.image = Images.getImageOfType(element);
	}

	public Image getImage() {return this.image;}

	public void setImage(Image image) {
		this.image = image;
	}

	@Override
    public String toString() {
 
            	switch (element) {
            	case STRAWBERRY: {
            		return "F";
            	}
            	case BLUBERRY : {
            		 return "B";
            	}
            	
            	case GOLD :{
            		 return "G";
            	}
            	case EMPTY : {
            		return ".";       
                }
            	case SNAKE:{
            		return "s";
                }
            	case WOOD:{
            		return "W";
            	}
				case SUPERSNAKE:{
            		return "S";	            	
            	}
                default:
                  return " ";
                	
                }

    }
	  
}