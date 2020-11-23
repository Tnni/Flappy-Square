package flappy;

import java.awt.Graphics;

import javax.swing.JPanel;

public class Graphic extends JPanel{

	private static final long serialVersionUID = 1L;

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponents(g);
		
		FlappySquare.flappySquare.repaint(g);
	}
	

}
