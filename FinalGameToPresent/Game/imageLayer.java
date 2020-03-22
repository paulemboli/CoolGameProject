import java.awt.*;

public class imageLayer {
	 Image image;
	   int x;
	   int y;
	   int z;

	   public imageLayer(String file, int x, int y, int z)
	   {
	      image = Toolkit.getDefaultToolkit().getImage(file).getScaledInstance(1400, 750, Image.SCALE_DEFAULT);
	      this.x = x;
	      this.y = y;
	      this.z = z;
	   }
	   public void moveLeftBy(int dx)
	   {
	      x -= dx;
	   }
	   public void moveRightBy(int dx)
	   {
	      x += dx;
	   }

	   public void draw(Graphics g)
	   {
	      for(int i = 0; i < 10; i++)
	         g.drawImage(image, x, y, null);
	   }
	   
	   public void changeStage (String file) {
		   image = Toolkit.getDefaultToolkit().getImage(file).getScaledInstance(1400, 750, Image.SCALE_DEFAULT);
	   }
	}

