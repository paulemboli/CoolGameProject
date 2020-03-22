import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Stages {
	Image image;
	int x;
	int y;
	int z;
	
	int w;
	int h;

	public Stages(String file, int x, int y, int z, int w, int h)
	{
		image = Toolkit.getDefaultToolkit().getImage(file).getScaledInstance(w, h, Image.SCALE_DEFAULT);
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		this.h = h;
	}
	
	public void draw(Graphics g)
	{
		for(int i = 0; i < 10; i++)
			g.drawImage(image, x, y, null);
	}	
	
	public boolean contains(int mx, int my)
	{
		return (mx > x) && (mx < x+w) && (my > y) && (my < y+h);
	}
}



