import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics;

public class Sprite extends Rect
{
   double x;
   double y;

   Animation[] animation;

   boolean moving = false;
   boolean facingRight = true;
   
// call the string array action 
   static final int IDLE   = 0;
   
   static final int LEFT   = 1;
   
   static final int RIGHT  = 2;
   
   static final int JUMP   = 3;
   
   static final int DOWN   = 4;
   
   static final int IDLEL  = 5;
   
   static final int JUMPL  = 6;
   
   static final int PUNCH  = 9;
   
   static final int KICK   = 11;
   
   static final int HIT    = 13;
   
   int pose = HIT;
   int lastPose;
   int savePose;
   
   // confirms player has a canJump
   boolean canJump;
   //confirms player can touch ceiling
   boolean touchedCeiling;
   
   boolean airStop;
   
   int jabCycle = 0;
   int kickCycle = 0;
   int hurtCycle = 0;
   int hurtReset = 30;
   int defeatCycle = 0;
   int defeatReset = 15;
   
   int health = 100;
   //int attackReset = 24;
   
   boolean jab = false;
   boolean kick = false;
   boolean attack = false;
   
   boolean jabCheck = true;
   boolean kickCheck = true;
   boolean hurt = false;
   boolean resetCheck = false;
   
   boolean roundOneLost = false;
   boolean roundTwoLost = false;
   
   boolean defeat = false;
   boolean winner = false;
   
   boolean resetGame = false;
   
   int timer = 60;

   public Sprite(int x, int y, String file, String[] action, int count, int duration)
   {
	   super(x,y,40,80);
	   this.x = x;
	   this.y = y;

	   animation = new Animation[action.length];

	   for(int i = 0; i < action.length; i++)

		   animation[i] = new Animation(file + action[i]+"_", count, duration);
      
	   hurtCycle = hurtReset;
   	}

   	public void moveBy(int dx, int dy)
   	{
   		x += dx;
   		y += dy;
   	}
   
   	public void moveUpBy(int dy)
   	{
	   
   		y -= dy;

   		moving = true;
      
     // System.out.println(pose);
      
   		if(facingRight)
   			pose = JUMP;
   		else
   			pose = JUMPL;
      
   		lastPose = pose;
     
   	}
   	public void moveDownBy(int dy)
   	{
   		y += dy;

   		moving = true;

   		pose = DOWN;
   	}
   	
   	public void moveLeftBy(int dx)
   	{   
   		x -= dx;

   		moving = true;
      
   		if (facingRight)
   			pose = RIGHT;
   		else
   			pose = LEFT;
      
   		lastPose = pose;
   	}
  
   	public void moveRightBy(int dx)
   	{
   		x += dx;

   		moving = true;
    //  facingRight = true;
    //  pose = RIGHT;
      
   		if (facingRight)
   			pose = RIGHT;
   		else
   			pose = LEFT;
      
   		lastPose = pose;
   	}

   	public void gravity(int dy){
		
   		y+= dy;	
   	}
   
   	public void Jab()
   	{  

   		if(facingRight)
   			pose = PUNCH;
   		else
   			pose = PUNCH+1;

   		lastPose = pose;
      
   	}
   
   	public void Kick()
   	{  
   		if(facingRight)
   			pose = KICK;
   		else
   			pose = KICK+1;
      
   		lastPose = pose;
     
   	}
   
   	public void Idle()
   	{  
	   
	  
   		if(facingRight)
   			pose = IDLE;
   		else
   			pose = IDLEL;

     
   	} 
   
   	public void hurt() {
	   
   		if(hurtCycle == hurtReset)
   			health = health - 10;
	   
   		if(hurtCycle > 0) {
   			if(facingRight) {
   				pose = HIT;
   				x = x-3;
   			}
	   
   			else {
   				pose = HIT+1;
   				x = x+3;
   				lastPose = pose;
   			}
	   
   			hurtCycle--;
	   		}
	   
	   		if(hurtCycle <= 0) {
	   			hurtCycle = hurtReset;
	   			hurt = false;
	   			
		   		if(health == 0)
		   			defeat = true;
	   		}
	   		

   	}	
   	
   	public void Defeated() {
   		if(facingRight)
   			pose = 15;
   		else
   			pose = 16;
   	}
   
   	public void reset() {
   		//for (int i =0; i > animation.length; i++)
   		animation[lastPose].resetImage();
   	}

   	
   	
   	public void draw(Graphics g)
   	{
   		
   		if(lastPose != pose)
   			animation[lastPose].current = 0;
   		
   		if(pose>= 9 && pose <= 12)
   			if(lastPose != pose)
   			animation[lastPose].current = 0;
	   
   		if(pose >= 15)
   			g.drawImage(animation[pose].onceImage(), (int)x, (int)y+40, null);
	   
	   
   		else
   			g.drawImage(animation[pose].nextImage(), (int)x, (int)y, null);

         

         
   		//System.out.println(animation[pose].current);
        
//        		 Displays range of attacks
//         if ((pose == 9 || pose == 10) && facingRight) {
//        	 g.setColor(Color.red);
//        	 g.drawRect((int)x, (int)y,w+20,h);
//         }
//         
//         if ((pose == 11 || pose == 12) && facingRight) {
//        	 g.setColor(Color.red);
//        	 g.drawRect((int)x, (int)y,w+20,h);
//         }
//         
//         if ((pose == 9 || pose == 10) && !facingRight) {
//        	 g.setColor(Color.red);
//        	 g.drawRect((int)x-20, (int)y,w+20,h);
//         }
//         
//         if ((pose == 11 || pose == 12) && !facingRight) {
//        	 g.setColor(Color.red);
//        	 g.drawRect((int)x-20, (int)y,w+20,h);
//         }
//         
//        	 g.setColor(Color.blue);
//        	 g.drawRect((int)x, (int)y,w,h);
//         
   	}
   	
   	
   	// This is to generate the animations immediately to avoid flickering for any of them
   	// It is in a timed loop in order to remove the animation after 1 second (timer = 60; 60 Frames a second)
   	// If left in without a timer, it would change the animation speed and affect the game overall.
   	public void drawbrief(Graphics g) {

   	   while(timer > 0) {
   	 		g.drawImage(animation[0].onceImage(), -1000, -1000, null);
   	 		g.drawImage(animation[1].onceImage(), -1000, -1000, null);
   	 		g.drawImage(animation[2].onceImage(), -1000, -1000, null);
   	 		g.drawImage(animation[3].onceImage(), -1000, -1000, null);
   	 		g.drawImage(animation[4].onceImage(), -1000, -1000, null);
   	 		g.drawImage(animation[5].onceImage(), -1000, -1000, null);
   	 		g.drawImage(animation[6].onceImage(), -1000, -1000, null);
   	 		g.drawImage(animation[7].onceImage(), -1000, -1000, null);
   	 		g.drawImage(animation[8].onceImage(), -1000, -1000, null);
   	 		g.drawImage(animation[9].onceImage(), -1000, -1000, null);
   	 		g.drawImage(animation[10].onceImage(), -1000, -1000, null);
   	 		g.drawImage(animation[11].onceImage(), -1000, -1000, null);
   	 		g.drawImage(animation[12].onceImage(), -1000, -1000, null);
   	 		g.drawImage(animation[13].onceImage(), -1000, -1000, null);
   	 		g.drawImage(animation[14].onceImage(), -1000, -1000, null);
   	 		
   	 		// This specifically is left out because we actually WANT it to flicker
   	 		// The reason we want the effect because without it, the animations run too fast
//   	 		g.drawImage(animation[15].onceImage(), -1000, -1000, null);
//   	 		g.drawImage(animation[16].onceImage(), -1000, -1000, null);
//   	 		g.drawImage(animation[17].onceImage(), -1000, -1000, null);
//   	 		g.drawImage(animation[18].onceImage(), -1000, -1000, null);
   	 		
   	 	      
   	 	      timer--;
   	 	   }	
   	}
   	
}