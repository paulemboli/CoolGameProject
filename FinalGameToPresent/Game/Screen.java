import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Screen extends JPanel{
	
	public Screen (){
		repaint();
	}
	
	public void paint (Graphics g){
		g.setColor(Color.GREEN);
		g.drawRect(100, 100, 50, 50);
		g.fillRect(200, 200, 50, 50);
	}

}
