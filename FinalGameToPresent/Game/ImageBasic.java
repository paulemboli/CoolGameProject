import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
public class ImageBasic {

		 Image image;
		   int x;
		   int y;
		   int z;

		   public ImageBasic(String file, int x, int y, int z)
		   {
		      image = Toolkit.getDefaultToolkit().getImage(file);
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
		


}
