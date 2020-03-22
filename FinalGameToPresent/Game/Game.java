import java.applet.Applet;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.awt.*;

//import java.io.*;

import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

public class Game extends Applet implements KeyListener, Runnable, MouseListener, MouseMotionListener
{

//Goals: Create a barrier; if either player crosses the barrier, they lose.
			// The players have a certain set distance from each other; they push off each other
			// Make it easy for two people to play 
			// Make sure to have the attacks working.

    Image    off_screen;
    Graphics off_g;
    
   // Array used the animation selection; Changing the position changes the "i"
   String[] action = {"idle", "WalkingL","Walking", "Jump", "Crouch", "idleL", "JumpL", "Fall", "LFall", 
		   				"Jab", "JabL", "Kick", "KickL", "Hit", "HitL", "Defeat", "DefeatL", "Victory", "Victory2"};
   
   int floor = 500;
   int ceiling = floor-150;
   
   // calling the sprite g- is the generic name. 
   // Sprite (x-position, y-position, StringName, StringAction, amount of frames, How long frames are on the screen) 
   Sprite s1 = new Sprite(510, floor,"Ryu_", action, 3, 20);
   Sprite s2 = new Sprite(740, floor,"Ken_", action, 3, 20);
   
   Sound playthis = new Sound("Overhaul.wav");
   
  // background images
   imageLayer startBG = new imageLayer("startbg.jpg", 0, 0, 100);
   imageLayer b0 = new imageLayer("4.png", 0, 0, 100);
   ImageBasic Ryu = new ImageBasic("Ryu.png", 100, 50, 100);
   
   ImageBasic Ken = new ImageBasic ("K0.png", 1200, 50, 100);

   Rect r = new Rect(50, 50, 100, 100);

   Rect r1 = new Rect(350, 350, 300, 200);
   
   Stages one = new Stages("4.png", 50, 50, 100, 100, 100);
   Stages two = new Stages("bg2.png", 180, 50, 100, 100, 100);
   
   boolean stageSelected;
   
   boolean StageOne   = false;
   boolean StageTwo   = false;
   boolean StageThree = false;
   boolean StageFour  = false;
   boolean StageFive  = false;
   

   Thread t;// the process you're going to run 

   // Boolean expressions for Ryu (white) activate by key inputs
   boolean lt_Pressed = false;
   boolean rt_Pressed = false;
   boolean up_Pressed = false;
   boolean dn_Pressed = false;
   boolean j_Pressed  = false;
   boolean k_Pressed  = false;
   
   // Boolean expressions for Ken (red) activate by key inputs
   boolean a_Pressed = false;
   boolean d_Pressed = false;
   boolean w_Pressed = false;
   boolean s_Pressed = false;
   boolean j2_Pressed =false;
   boolean k2_Pressed =false;
   
   boolean space_Pressed;
   
   int attackReset = 40;
   int attackRange = 30;

   int mx;
   int my;
   
   boolean roundEnd;
   
   boolean startScreen = true;
   
   boolean r_Pressed = false;
   
   boolean battleMusic = false;
   boolean menuMusic   = true;
   
   public void init()
   { 
	   //playing screen size
      off_screen = this.createImage(1400, 700);
      off_g      = off_screen.getGraphics();

      s1.jabCycle = attackReset;
      s2.jabCycle = attackReset;
      s1.kickCycle = attackReset;
      s2.kickCycle = attackReset;
      requestFocus();
      addKeyListener(this);

      addMouseListener(this);
      addMouseMotionListener(this);

      t = new Thread(this);

      t.start();
   }

   public void run()
   {
	   
	   playthis.loop();
	   
	   while(startScreen) {
		 //  musicLoop();   
		   if (r_Pressed) {
			   startScreen = false;
			   battleMusic = true;
			   menuMusic = false;
		   }
	 
		   repaint();
		   try
		   {
			   t.sleep(15);
		   }
		   catch(Exception x){}
		  
	   }
	   
      while(!startScreen)
      {  
    	  playthis.stop();
    	  if(battleMusic) {
    		  if(StageOne)
    		  music("fightMusic.wav");
    		  if(StageTwo)
    			  music("fightMusic2.wav");
    	  }
    	  battleMusic = false;
    	  
    	  endCheck();
    		  
    	  if(!roundEnd) {
    		  
         if(lt_Pressed && (!s1.attack && !s1.hurt)){   
        	 s1.moveLeftBy(2);  
         }
         
         if(rt_Pressed && (!s1.attack && !s1.hurt)){   
        	 s1.moveRightBy(2); 
         }
         
         jumpAbility(s1);
         // if jumping and you have not touched the ceiling yet
         if(s1.y >ceiling && s1.canJump && up_Pressed && !s1.airStop && !s1.attack)
   		 
   		  //double checks you have not touched the ceiling. allows you to move up by 6
        	 jumpProccess(s1);
         
        ///////////////////////////////////////////////////////////////////////////////////
   	  	// keeps s1 still if nothing is pressed and he is on the ground
         if(s1.y >= floor && !up_Pressed && !s1.airStop && !lt_Pressed && !rt_Pressed)
        	 s1.moving = false;
         //////////////////////////////////////////////////////////////////////////////////
         
         if(j_Pressed && s1.jabCheck && !s1.attack && s1.y >= floor) { 
        	 s1.jabCheck = false;
        	 s1.jab = true;
        	 s1.attack = true;
         }
         
         if(s1.resetCheck)
        	 s1.jabCheck = true;
         
         if (s1.jab) 
        	 jab(s1,s2);
         
         if(k_Pressed && s1.kickCheck && !s1.attack && s1.y >= floor) {
        	 s1.kickCheck = false;
        	 s1.kick = true;
        	 s1.attack = true;
         }
         
        if(s1.resetCheck)
        	s1.kickCheck = true;
        	 
         if(s1.kick)
             kick(s1, s2);

         if(s2.hurt)
         s2.hurt();
         
         // if s1 is supposed to fall, falls down by 5
         if((s1.y < floor && !s1.canJump) || (s1.y < floor && s1.airStop)) {
        	
       	  s1.gravity(5);
         
       	  // falling poses
       	  if(s1.facingRight)
       		  s1.pose = 7;
       	  else
       		  s1.pose = 8;
    	  }  
         
         /////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
         						// S2 part. should be the same as S1
         /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         
     if(a_Pressed && (!s2.attack && !s2.hurt)){   
 
    	 s2.moveLeftBy(2);  
    	 
     }
     
     if(d_Pressed && (!s2.attack && !s2.hurt)){   
    	 s2.moveRightBy(2); 
     }
     
     		jumpAbility(s2);
     
	  if(s2.y > ceiling && s2.canJump && w_Pressed && !s2.airStop)
		 
		  jumpProccess(s2);
     
     if(s2.y >= floor && !w_Pressed && !s2.airStop && !a_Pressed && !d_Pressed)
    	 s2.moving = false;
     
     if(j2_Pressed && s2.jabCheck && !s2.attack &&  s2.y >= floor) { 
    	 s2.jabCheck = false;
    	 s2.jab = true;
    	 s2.attack = true;
     }
     
     if(s2.resetCheck)
    	 s2.jabCheck = true;
     
     if (s2.jab) 
    	 jab(s2,s1);
     
     if(k2_Pressed && s2.kickCheck && !s2.attack && s2.y >= floor) {
    	 s2.kickCheck = false;
    	 s2.kick = true;
    	 s2.attack = true;
     }
     
    if(s2.resetCheck)
    	s2.kickCheck = true;
    	 
     if(s2.kick)
         kick(s2, s1);

     if(s1.hurt)
    	 s1.hurt();
     
     if((s2.y < floor && !s2.canJump) || (s2.y < floor && s2.airStop)) {
    	
   	  s2.gravity(5);
     
   	  if(s2.facingRight)
   		  s2.pose = 7;
   	  else
   		  s2.pose = 8;
   		  
	  }
     
     //!! resets both s1 and s2 to idle state if doing nothing 
     //Update: Now makes sure that both are always facing each other if they are not moving
     //This will need to be updated later
     if(s2.y >= floor && !w_Pressed && !d_Pressed && !a_Pressed && !s_Pressed && !s2.jab && !s2.kick && !s2.hurt) {
    
    	 //checks if the beginning of s2 is past s1
    	 // if it is, it faces left, else if faces right
    	 if(s2.x > s1.x)
    		 s2.facingRight = false;
    
    	 else
    		 s2.facingRight = true;
    	
    	 s2.Idle();
     }
     
     //same thing as the code above
     if(s1.y >= floor && !up_Pressed && !rt_Pressed && !lt_Pressed && !dn_Pressed && !s1.hurt && !s1.jab && !s1.kick ) {
    	 if(s1.x > s2.x)
        	 s1.facingRight = false;
         
    	 else
        	 s1.facingRight = true;
     
     s1.Idle();
     }
     
    	  }
    	  
    if (s1.roundOneLost && !s1.resetGame)
    	s2.pose = 17;
    if (s1.roundTwoLost)
    	s2.pose = 18;
    
    if (s2.roundOneLost && !s2.resetGame)
    	s1.pose = 17;
    if (s2.roundTwoLost)
    	s1.pose = 18;
    
         repaint();

         try
         {
             t.sleep(15);
         }
         catch(Exception x){}
      }
      
      

   }
   
   public void update(Graphics g)
   {
      off_g.clearRect(0, 0, 1400, 700);

      paint(off_g);

      g.drawImage(off_screen, 0, 0, null);
   }


   public void paint(Graphics g)
   {
	   
	   
	   //main background
	   if(!startScreen) {
		   
	     b0.draw(g);
	     Ryu.draw(g);
	     Ken.draw(g);
	     personalSpace(); 
	     personalSpace2();
	     s1.draw(g);
	     s2.draw(g);

	   //  g.drawString("-------", mx, my);
      
        // HEALTH indicator
        g.setColor(Color.blue);
          String health1 = new String ("Player 1:   "+ s1.health);
          if(s1.health <= 0)  
        	health1 = "Player1 lost"; 
          
          g.drawString(health1, 100, 150);
          
          String health2 = new String ("Player 2:    "+ s2.health);
          if(s2.health <= 0)  
        	health2 = "Player2 lost"; 
          
          g.drawString(health2, 1200, 150);
          
          // S1:
          // Rect = health outline
          // Ovals = Round win outline
          g.drawRect(100,150,100,10);
          g.drawOval(105, 165, 10, 10);
          g.drawOval(120, 165, 10, 10);
          
          // S2:
          // Rect = health outline
          // Ovals = Round win outline
          g.drawRect(1200,150,100,10);
          g.drawOval(1205, 165, 10, 10);
          g.drawOval(1220, 165, 10, 10);
         
          //Changes Color of everthing afterwards. Used for emphasize health and round wins
          g.setColor(Color.red);
          
          // S1 health: Will decrease as player takes damage because width is equal to health
          g.fillRect(100,150,s1.health,10);

          // S2 health: Will decrease as player takes damage because width is equal to health
          g.fillRect(1200,150,s2.health,10);  
          
          //RoundLost method checks if player lost round one or two, then resets health
          roundLost(s1, s2);
          
          roundLost(s2, s1);
          
          // this color is for the round wins
          g.setColor(Color.yellow);
          
          // Checks if whoever los and gives point to opposite player (player who won)
          if(s2.roundOneLost) {
        	  g.fillOval(105, 165, 10, 10);
          }
          if(s2.roundTwoLost) {
        	  g.fillOval(120, 165, 10, 10);
          }
          if(s1.roundOneLost) {
        	  g.fillOval(1205, 165, 10, 10);
          }
          if(s1.roundTwoLost) {
        	  g.fillOval(1220, 165, 10, 10);
          }  
	   }
	   
	   else
		   startPaint(g);
   }

   public void startPaint(Graphics g) {
	   
	   startBG.draw(g);
	   
	   if(StageOne)
		   g.fillRect(one.x-5, one.y-5, one.w+10, one.h+10);
	   one.draw(g);
	  
	   if(StageTwo)
		   g.fillRect(two.x-5, two.y-5, two.w+10, two.h+10);	   
	   two.draw(g);
	   
	   g.setColor(Color.yellow);
	   //g.drawString("-------", mx, my);
	   s1.drawbrief(g);
	   s2.drawbrief(g);
	   g.setFont(new Font("sanserif", Font.BOLD, 16));
	   
	   g.drawString("Select the Stage. Press R To Start The Game", 400, 768 / 2);
	   
   }
   
   public void personalSpace() {
	   //Checks if you are on the floor. It is the only time the collision will work
	   //We want them to be able to pass each other in the air
	   if(s1.y >= floor && s2.y >= floor) {
		   
	   if (s1.x < s2.x && s1.x+40 >s2.x && s1.pose == 2 )
		   s2.moveRightBy(2);   
	   
	   if (s1.x > s2.x && s1.x <s2.x+40 && s1.pose == 1) 
			s2.moveLeftBy(2);
	   
	   }   

   }
   
   public void personalSpace2() {
	   //Checks if you are on the floor. It is the only time the collision will work
	   //We want them to be able to pass each other in the air
	   if(s1.y >= floor && s2.y >= floor) {
		   
   if (s2.x < s1.x && s2.x+40 >s1.x && s2.pose == 2 ) 
	   s1.moveBy((int)(s2.x+40 - s1.x)*(1), 0);
   
   if (s2.x > s1.x && s2.x <s1.x+40 && s2.pose == 1 ) 

   	s1.moveBy((int)(s1.x +40 - s2.x)*(-1), 0);
	   }
   }
   
   public boolean endCheck() {
	   if(s1.defeat || s2.defeat)
		   
		   roundEnd = true;
	   return roundEnd;
		   
   }
   
   public void jumpAbility (Sprite player) {
	// if player has not touched the ceiling can jump. Resets jump if already jumped.
	   if(player.y >=floor){
		  player.y = floor;
 		  player.canJump = true;
 		  player.touchedCeiling = false;

 		  player.airStop = false;
 	  }
 	  
 	  // if player touched the ceiling can't jump. automatically falls down.
 	  if(player.y <= ceiling) {
 		  player.canJump = false;
 		  
 		  player.touchedCeiling = true; 		  
 	 }
   }
   
   public void jumpProccess(Sprite player) {
	   //player moves up if that player has not reached the characters jump limit

 	       	 player.moveUpBy(6);
 	       	 
 	   	  		//Changes to jumping poses
 	         if(player.moving == false){
 	        	 
 	        	 if(player.facingRight)
 	        		 player.pose = 0;
 	        	
 	        	 else
 	        		 player.pose = 5;	 
 	         }

   }


   public void roundLost(Sprite player, Sprite opponent) {
	  // System.out.println(space_Pressed);
	   // Only triggers if player heath is at 0
	   if (player.defeat) {
		  
		   player.Defeated();
		   player.roundOneLost = true;
		   if(player.resetGame)
			   player.roundTwoLost = true;
		   if  (space_Pressed) {
			   
			   // Specifically resets for RoundOne For Round 2
			   if(player.roundOneLost && !player.roundTwoLost) {
			   player.health = 100;  
			   opponent.health = 100;
			   player.defeat = false;
			   roundEnd = false;
			   resetLocation();
			   player.resetGame = true;
			   space_Pressed = false;
			   
			   }
		   }
	   }
   }
   
   public void resetLocation() {
	   s1.x = 510;
	   s1.y = floor;
	   s2.x = 740;
	   s2.y = floor;
	   
	   ceiling = floor-150;
	   
   }
   
   public void jab(Sprite player, Sprite opponent) {
	   
	   //JabCycle is amount of time the animation should be taking place. Needs to be matched with the player duration of animation
	   if(player.jabCycle >0) {
	    	//this is the animation
	         player.Jab();
	         
	         // this is a is largely unnecessary but interesting. The damage given is normally at the beginning of the jabCycle
	         //This makes it so that the jabCycle has a set duration before damage is dealt
	         if(player.jabCycle< attackRange)

	         // checks if opponent is on the ground.
	         if(opponent.y >= floor)
	 	        //basic check to see if player's jab reaches opponent's body on either side.
	 	        //if so, the boolean hurt is called.
	 	        // *** May want to change so that it is not checking so many things at once.
	         if(((player.x+50 >= opponent.x) && (player.x< opponent.x) && (player.facingRight))||((player.x-50 <= opponent.x) && (player.x > opponent.x) && (!player.facingRight))) {
	            opponent.hurt = true;
	            
	            // ***The hurt method is no longer called in the attack 
	            //    Because we want the damage duration separate from the attack duration
	        // if(opponent.hurt)
	        // opponent.hurt();
	         }
	         
	         // depletes the jabCycle about every frame. 
	         player.jabCycle--;
	    }
	    
	   //checks if the jabCycle is complete.
	   //if so, resets the everything necessary to reset.
	    if (player.jabCycle <= 0){
	    	player.jab = false;
	    	player.attack = false;
	    	player.jabCycle = attackReset;
	    	player.resetCheck = false;
	    	//player.reset();
	    }

   }

   public void kick(Sprite player, Sprite opponent) {
	   // Everything is exactly the same as the jab cycle
	   if(player.kickCycle >0) {
	    	
	         player.Kick();
	     
	         if(player.kickCycle < attackRange)
	        	 if(opponent.y >= floor)
	         if(((player.x+60 >= opponent.x) && (player.x< opponent.x) && (player.facingRight)) || ((player.x-60 <= opponent.x) && (player.x > opponent.x) && (!player.facingRight))) {
		            opponent.hurt = true;
	         
	         //if(opponent.hurt)
	         //opponent.hurt();
	      
	         }
	         
	         player.kickCycle--;
	    }
	    
	    if (player.kickCycle <= 0){
	    	player.kick = false;
	    	player.attack = false;
	    	player.kickCycle = attackReset;
	    	player.resetCheck = false;
	    }

   }
   
   public void mouseMoved(MouseEvent e)
   {

   }

   public void mouseDragged(MouseEvent e)
   {
      int mx = e.getX();
      int my = e.getY();

      int dx = mx - this.mx;
      int dy = my - this.my;

      if (r.held)  r.moveBy(dx, dy);

      this.mx = mx;
      this.my = my;

   }
   
   public void mousePressed(MouseEvent e)
   {
	   
	   
	   
      mx = e.getX();
      my = e.getY();
      
      if(startScreen) {

      if(one.contains(mx, my)) {
    	  StageOne = true;
    	  b0.changeStage("4.png");
    	  floor = 500;
    	  resetLocation();
    	  System.out.println("Stage Selected");

    	  System.out.println("                    ");
      }
      else {
    	  StageOne = false;
    	  System.out.println("Not Selected");    

    	  System.out.println("                    "); 
      }
      
      if(two.contains(mx, my)){
    	  StageTwo = true;
    	  b0.changeStage("bg2.png");
    	  floor = 550;
    	  resetLocation();
    	  System.out.println("Stage Selected");

    	  System.out.println("                    ");
      }
      else {
    	  StageTwo = false;
    	  System.out.println("Not Selected");    

    	  System.out.println("                    "); 
      }
      
	   }
   }

   public void mouseReleased(MouseEvent e)
   {
      r.drop();
   }

   public void mouseClicked(MouseEvent e)
   {
	   
   }

   public void mouseEntered(MouseEvent e)
   {

   }

   public void mouseExited(MouseEvent e)
   {

   }

   public void keyPressed(KeyEvent e)
   {
      int code = e.getKeyCode();

      if(code == e.VK_LEFT)     lt_Pressed = true;
      if(code == e.VK_RIGHT)    rt_Pressed = true;
      if(code == e.VK_UP)       up_Pressed = true;
      if(code == e.VK_DOWN)     dn_Pressed = true;
      if(code == e.VK_J)   		j_Pressed = true;
      if(code == e.VK_K)   		k_Pressed = true;
      
      if(code == e.VK_A)     	a_Pressed = true;
      if(code == e.VK_D)  	    d_Pressed = true;
      if(code == e.VK_W)        w_Pressed = true;
      if(code == e.VK_S)     	s_Pressed = true;
      if(code == e.VK_1)   		j2_Pressed = true;
      if(code == e.VK_2)   		k2_Pressed = true;
      
      if(code == e.VK_SPACE)   	space_Pressed = true;
      
      if(code == e.VK_R)   	r_Pressed = true;
   }

   public void keyReleased(KeyEvent e)
   {
      int code = e.getKeyCode();

      if(code == e.VK_LEFT)     lt_Pressed = false;
      if(code == e.VK_RIGHT)    rt_Pressed = false;
      if(code == e.VK_UP) {     up_Pressed = false; s1.airStop = true;   }
      
      if(code == e.VK_DOWN)     dn_Pressed = false;
      if(code == e.VK_J) {   	j_Pressed = false; s1.resetCheck = true; }
      if(code == e.VK_K) {  	k_Pressed = false; s1.resetCheck = true; }

      if(code == e.VK_A)     	a_Pressed = false;
      if(code == e.VK_D)   	    d_Pressed = false;
      if(code == e.VK_W) {      w_Pressed = false; s2.airStop = true;    }
      
      if(code == e.VK_S)    	s_Pressed = false;
      if(code == e.VK_1) {   	j2_Pressed = false; s2.resetCheck = true; } 
      if(code == e.VK_2) {  	k2_Pressed = false; s2.resetCheck = true; }
      
      if(code == e.VK_SPACE)   	space_Pressed = false;
   
      if(code == e.VK_R)   	r_Pressed = false;
   }

   public void keyTyped(KeyEvent e) {   }

   public void music(String filepath) {

	   	InputStream music;
	   
	   try {
		  music = new FileInputStream(new File(filepath));
		  AudioStream audios = new AudioStream(music);
		  AudioPlayer.player.start(audios);
		  }
	   
	   catch(Exception e) {}
   }
   
	public static void musicLoop(){
		AudioPlayer MGP = AudioPlayer.player;
		FileInputStream BGM;
		AudioStream T;
		AudioData MD;
		ContinuousAudioDataStream loop = null;
		try{
			BGM = new FileInputStream("Overhaul.wav");
			T = new AudioStream(BGM);
			MD = (T).getData();
			loop = new ContinuousAudioDataStream(MD);
		}catch(Exception error){
			System.out.print("file not found");
		}
		
		MGP.start(loop);
	}
}