


import javax.swing.SwingUtilities;

import View.FrameGame;

public class App {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FrameGame();
            }
        });
    }
    
}