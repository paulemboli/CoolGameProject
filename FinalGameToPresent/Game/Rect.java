import java.awt.*;

public class Rect
{
   double x;
   double y;

   int w;
   int h;

   boolean held = false;

   public Rect(double x, double y, int w, int h)
   {
      this.x = x;
      this.y = y;
      this.w = w;
      this.h = h;
   }

   public void grab()
   {
      held = true;
   }

   public void drop()
   {
      held = false;
   }

   public boolean overlaps(Rect r)
   {
      return (x      < r.x + r.w )   &&
             (x + w  > r.x       )   &&
             (y      < r.y + r.h )   &&
             (y + h  > r.y       );
   }

   public boolean contains(int mx, int my)
   {
      return (mx > x) && (mx < x+w) && (my > y) && (my < y+h);
   }


   public void draw(Graphics g)
   {
      g.drawRect((int)x, (int)y, w, h);
   }

   public void moveBy(int dx, int dy)
   {
      x += dx;
      y += dy;
   }
}