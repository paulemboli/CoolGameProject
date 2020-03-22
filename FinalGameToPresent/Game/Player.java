import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;


public class Player extends Sprite {
	
	public Player(int x, int y, String file, String[] action, int count, int duration) {
		super(x, y, file, action, count, duration);

	}
	
	
	
	
	
	public void draw(Graphics g) {
		  g.setColor(Color.blue);
	     
		 
	   	  g.drawRect((int)x, (int)y,w,h);
	}

			
			
	

}
