import java.awt.*;

public class Tank
{
  double x;
  double y;
  int A;

  static final int radius = 60;


  static final int[] bodyx = {35, 35, -35, -35};
  static final int[] bodyy = {-25, 25, 25, -25};

  static final int[] turretx = {5, 5, -5, -5};
  static final int[] turrety = {-12, 12, 12, -12};

  static final int[] gunx    = {30, 30, 5, 5};
  static final int[] guny    = {-3, 3, 3, -3};

  static final int[] leftTredx  = {27, 27, -27, -27};
  static final int[] leftTredy  = {25, 32, 32, 25};

  static final int[] rightTredx  = {27, 27, -27, -27};
  static final int[] rightTredy  = {-25, -32, -32, -25};

  public Tank(int x, int y, int A)
  {
     this.x = x;
     this.y = y;
     this.A = A;
  }
  public void draw(Graphics g)
  {
     int[] xp = new int[4];
     int[] yp = new int[4];
/*
     double radians = A * Math.PI / 180;

     double sinA = Math.sin(radians);
     double cosA = Math.cos(radians);
*/

     double sinA = Lookup.sin[A];
     double cosA = Lookup.cos[A];

     for(int i = 0; i < 4; i++)
     {
        xp[i] = (int)(bodyx[i]*cosA - bodyy[i]*sinA + x);
        yp[i] = (int)(bodyx[i]*sinA + bodyy[i]*cosA + y);
     }

     g.drawPolygon(xp, yp, 4);

     for(int i = 0; i < 4; i++)
     {
        xp[i] = (int)(turretx[i]*cosA - turrety[i]*sinA + x);
        yp[i] = (int)(turretx[i]*sinA + turrety[i]*cosA + y);
     }

     g.drawPolygon(xp, yp, 4);

     for(int i = 0; i < 4; i++)
     {
        xp[i] = (int)(gunx[i]*cosA - guny[i]*sinA + x);
        yp[i] = (int)(gunx[i]*sinA + guny[i]*cosA + y);
     }

     g.drawPolygon(xp, yp, 4);

     for(int i = 0; i < 4; i++)
     {
        xp[i] = (int)(leftTredx[i]*cosA - leftTredy[i]*sinA + x);
        yp[i] = (int)(leftTredx[i]*sinA + leftTredy[i]*cosA + y);
     }

     g.drawPolygon(xp, yp, 4);

     for(int i = 0; i < 4; i++)
     {
        xp[i] = (int)(rightTredx[i]*cosA - rightTredy[i]*sinA + x);
        yp[i] = (int)(rightTredx[i]*sinA + rightTredy[i]*cosA + y);
     }

     g.drawPolygon(xp, yp, 4);



  }

  public boolean contains(int mx, int my)
  {
     double dist2 = (mx-x)*(mx-x) + (my-y)*(my-y);

     return dist2  < radius * radius;
  }

  public void moveForwardBy(int distance)
  {
     x += distance * Math.cos(A*Math.PI/180);
     y += distance * Math.sin(A*Math.PI/180);
  }

  public void moveBy(int dx, int dy)
  {
      x += dx;
      y += dy;
  }


  public void rotateLeftBy(int dA)
  {
     A -= dA;

     if(A < 0)     A += 360;
  }

  public void rotateRightBy(int dA)
  {
     A += dA;

     if(A >= 360)  A-= 360;
  }




}