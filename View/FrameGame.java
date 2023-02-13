package View;

import java.awt.Dimension;

import javax.swing.JFrame;



public class FrameGame extends JFrame {
	
    
	private static final long serialVersionUID = 1L;
	
	private static int WidthFrame = 1000;

	private static int HeightFrame = 650;
    
    public static int getWidthFrame() {
		return WidthFrame;
	}


	public static int getHeightFrame() {
		return HeightFrame;
	}


	public FrameGame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WidthFrame, HeightFrame));
        setResizable(false);
        setTitle("Falling snake");
        
        Images.loadImages();
        add(new GamePanel());
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
