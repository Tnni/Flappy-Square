package flappy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class FlappySquare  implements ActionListener, MouseListener{

	public final int width = 800, height = 800; 
	
	public static FlappySquare flappySquare;
	public Graphic graphic;
	public Rectangle player;
	public int ticks, motion, score;
	public ArrayList<Rectangle> columns;
	public Random ran;
	public Dimension dim;
	
	Boolean gameOver, started;
	
	public FlappySquare() {
		
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		JFrame jframe = new JFrame();
		graphic = new Graphic();
		ran = new Random();
		Timer timer = new Timer(20, this);
		gameOver = false;
		started = false;
		score = 0;
		jframe.add(graphic);
		jframe.setSize(width, height);
		jframe.addMouseListener(this);
		jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, dim.height / 2 - jframe.getHeight() / 2);
		jframe.setResizable(false);
		jframe.setVisible(true);
		jframe.setTitle("~~ Flappy Square ~~");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // termination on close
		
		player = new Rectangle(width / 2 - 10, height / 2 - 10, 20, 20);
		columns = new ArrayList<Rectangle>();
		
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
		timer.start();
	}
	
	public void repaint(Graphics g) {
		// BG
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		
		//Ground
		g.setColor(Color.red);
		g.fillRect(0, height - 120, height, 120);
		g.setColor(Color.orange);
		g.fillRect(0, height - 120, height, 20);
		
		//Player
		g.setColor(Color.white);
		g.fillRect(player.x, player.y, player.width, player.height);
		
		for (Rectangle column : columns) {
			columnCreation(g, column);
		}
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial", 1, 100));
		
		if (!started) {
			g.drawString("Click to start", 75, height / 2 - 50);
		}
		
		if (gameOver) {
			g.drawString("Square Died", 90, height / 2 - 50);
		}
		
		if (!gameOver && started) {
			g.drawString(String.valueOf(score), width / 2 - 25, 100);
		}
		
	}
	
	public void columnCreation(Graphics g, Rectangle c) {
		
		g.setColor(Color.pink.darker());
		g.fillRect(c.x, c.y, c.width, c.height);
	}
	
	public void jump() {
		
		if (gameOver) {
			player = new Rectangle(width / 2 - 10, height / 2 - 10, 20, 20);
			columns.clear();
			motion = 0;
			score = 0;
			
			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);
			
			gameOver = false;
		}
		
		if (!started) {
			started = true;
		} else if (!gameOver) {
			if (motion > 0) {
				motion = 0;
			}
			
			motion -= 10;
		}
		
	}
	
	public void addColumn(boolean begin) {
		
		int gap = 300;
		int w = 100;
		int h = 50 + ran.nextInt(300);
		
		if (begin) {
			columns.add(new Rectangle(width + height + columns.size() * 300, height -  h - 110, w , h)); //110 in for above the lava
			columns.add(new Rectangle(width + height + (columns.size() - 1) * 300, 0, w, height - h - gap));			
		} else {
			columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, height -  h - 110, w , h)); 
			columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, w, height - h - gap));			
		}

		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		int speed = 10;
		ticks ++;
		
		if (started) {
			for (int i = 0; i < columns.size(); i++) {
				Rectangle column = columns.get(i);
				column.x -= speed;
			}
			
			if (ticks % 2 == 0 && motion < 12) {
				motion += 2;
			}
			
			for (int i = 0; i < columns.size(); i++) {
				Rectangle column = columns.get(i);
				if (column.x + column.width < 0) {
					columns.remove(column);
					if (column.y == 0) {
						addColumn(false);
					}
				}
			}
			
			player.y += motion;
			
			for (Rectangle column : columns) {
				
				if (column.y == 0 && player.x + player.width / 2 > column.x + column.width / 2 - 10 && player.x + player.width / 2 < column.x + column.width / 2 + 10  ) {
					score ++;
				}
				
				if (column.intersects(player)) {
					gameOver = true;
					
					if (player.x <= column.x) {
						player.x = column.x - player.width;
					} else if (column.y != 0) {
						player.y = column.y - player.height;
					} else if (player.y < column.height) {
						player.y = column.height;
					}
					
				}
			}
			
			if (player.y > height - 120 || player.y < 0) {
				gameOver = true;
			}
			
			if (player.y + motion >= height - 120) {
				player.y = height - 110 - player.height;
			}

		}
		graphic.repaint();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		jump();
	}
	
	public static void main(String[] args) {
		
		flappySquare = new FlappySquare();
		
	}

//============================================================================================================
	
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	
}
