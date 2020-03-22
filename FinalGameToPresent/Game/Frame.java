import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JPanel {

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    g2d.setColor(new Color(212, 212, 212));
    g2d.drawRect(100, 150, 100, 60);


    //g2d.setColor(new Color(31, 21, 1));
    //g2d.fillRect(250, 195, 90, 60);

  }

  public static void main(String[] args) {
    Frame rects = new Frame();
    JFrame frame = new JFrame("Rectangles");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(rects);
    frame.setSize(360, 300);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}




//import java.awt.GridLayout;

//import javax.swing.JFrame;

//public class Frame extends JFrame {
	//Screen s;
	
	//public Frame() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setSize(900,800);
		//setResizable(false);
		//setTitle("Gr;aphics");
		
		//init();
		
		
	//}
	//public void init(){
		//setLocationRelativeTo(null);
		
		//setLayout(new GridLayout(1,1,0,0));
		//s= new Screen();
		
		//add(s);
		
		//setVisible(true);
		
		
	//}
	//public static void main(String[] args){
	//	new Frame();
	//}

//}
