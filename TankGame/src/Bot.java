import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Bot {
	private int health=2;
	public int getHealth() {
		return health;
	}
//sound
	Sound clips;
	// variables for rotating the turret
	private double angleRad;
	private double turretRotateRatio; 
	// responsible for covering the original rotate angle
	public AffineTransform trans;
	//bullet
	private ArrayList<Bullet> bullet;
	//game materials
	private GameScreen game;
	private JFrame window;	
	
	// vector info
	private int Vectorx;
	private int Vectory;
	private double Velocity;
	private int x;
	private int y;
	private Point center;//to rotate tank
	private Point fireXY;
	//timer
	Time timer;
	Time Repath;
	public final int tankReloadTime=2;
	public final int repathAgain=10;
	//textures
	Texture tank;
	Texture turret;
	//AI features
	private double visionRange;
	private Mapsolver botFindway;
	private int arraymap[][];
	// Movement
	private Point endDestination;
	private Point nextDestination;
	private boolean movingLeft;
	private int leftItr=0;
	private boolean movingRight;
	private int RightItr=0;
	private boolean movingUp;
	private int UpItr=0;
	private boolean movingDown;
	private int DownItr=0;
	
	public int getX() {
		return tank.x;
	}
	public int getY() {
		return tank.y;
	}
	public int getW() {
		return tank.w;
	}
	public int getH() {
		return tank.h;
	}
	
	
	public Bot(int x , int y ,double Velocity , GameScreen game ,JFrame window,Tank player) {
		Repath = new Time();
		clips= new Sound();
		timer = new Time();
		visionRange=400.0;
	angleRad=0.0;
	turretRotateRatio=0.0;
	bullet=new ArrayList<Bullet>();
		this.window=window;
		this.game=game;
	Vectorx=0;
	Vectory=0;
	Velocity=5.0;	
		tank = new Texture("/Textures/tankb2.png",x,y,48,48);
		turret = new Texture("/Textures/turret2.png",tank.x+12,tank.y+15,24,48);
		center=new Point(tank.x+tank.w/2,tank.y+tank.h/2);
	this.x=x;
	this.y=y;
	
	arraymap = new int [][] {
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,1,1,1,1,1,1,1,1,1,1,1,1,0,1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,0},
		{0,1,1,1,1,1,1,1,1,1,1,1,0,1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,1,0},
		{0,1,1,0,0,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,0,0,1,1,0},
		{0,1,1,0,0,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,0,0,1,1,0},
		{0,1,1,0,0,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,0,0,1,1,0},
		{0,1,1,0,0,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,0,0,1,1,0},
		{0,1,1,0,0,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,0,0,1,1,0},
		{0,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,0},
		{0,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,0},	
		{0,1,1,0,0,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,0,0,1,1,0},
		{0,1,1,0,0,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,0,0,1,1,0},
		{0,1,1,0,0,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,0,0,1,1,0},
		{0,1,1,0,0,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,0,0,1,1,0},
		{0,1,1,0,0,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,0,0,1,1,0},
		{0,1,1,1,1,1,1,1,1,1,1,1,0,1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,1,0},
		{0,1,1,1,1,1,1,1,1,1,1,1,1,0,1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
			
	};
	movingLeft=true;

	botFindway = new Mapsolver(arraymap,player.getX()/48,player.getY()/48,tank.x/48,tank.y/48); 
	 arraymap[tank.y/48][tank.x/48]=1;
	
	}
	public void calAngle(Tank player){
		int dy= player.getCenter().y  - center.y;
		int dx = player.getCenter().x - center.x;
	angleRad=Math.atan2(dy, dx);
	}

	public void rotateTurret(Graphics2D g2D) {
		if(  Math.abs( turretRotateRatio - angleRad)  >= 0.01) {

			if ( turretRotateRatio > angleRad && angleRad <= 0) {
				
				if( turretRotateRatio + -angleRad > 6 ) {
					turretRotateRatio = angleRad;
				}
				else
				turretRotateRatio += -3.14/100;
			}
			else if ( turretRotateRatio < angleRad && angleRad <= 0)
				turretRotateRatio -= -3.14/100;
			else if ( turretRotateRatio > angleRad && angleRad >= 0 ) {
				
				turretRotateRatio -= 3.14/100;
			}
				else if ( turretRotateRatio < angleRad && angleRad >= 0 )
				{
					if( -turretRotateRatio + angleRad > 6 )
						turretRotateRatio = angleRad;
					else
					turretRotateRatio += 3.14/100;
				}
			}
			g2D.rotate(turretRotateRatio+Math.toRadians(270),center.x,center.y);
	}
	public void turretStayStill(Graphics2D g2D) {
		g2D.rotate(turretRotateRatio+Math.toRadians(270),center.x,center.y);
	}
	
	public boolean checkDistance(Tank player) {
		
		double distance = Math.sqrt(  Math.pow(center.x - player.getCenter().x, 2) + Math.pow(center.y - player.getCenter().y, 2) );
		if( distance <= visionRange)
			return true;
		else
			return false;
		
	}
	public boolean checkVision(Tank player , ArrayList<Obstacles> obs) {
		int flag=0;
		double dy=Math.sin(angleRad)*turret.h;
		double dx=Math.cos(angleRad)*turret.h;
		Bullet b= new Bullet(center,window,angleRad);
		for(int i = 0 ; i < 100  ; ++i) {
			b.move();

			if(b.CheckVision(obs,player,this) == 3 ) {
		    return false;
			}
	    if(b.CheckVision(obs,player,this) == 1 ) {

	    return true;
		}
		}
		return false;
	
	}
	public void Fire() {
		
		double dy=Math.sin(turretRotateRatio)*turret.h;
		double dx=Math.cos(turretRotateRatio)*turret.h;	
		fireXY=new Point(  (int)(dx+center.x) , (int)(dy+center.y) ); 
		Bullet b= new Bullet(fireXY,window,turretRotateRatio);
		bullet.add(b);
	}
	public void paint(Graphics2D g2D,ArrayList<Obstacles> obs,Tank player,Texture mermi) throws InterruptedException {



		move(player);
		trans = g2D.getTransform();
		Rotate(g2D);
		tank.render(g2D, tank.x, tank.y, tank.w, tank.h);
		g2D.setTransform(trans);
		trans = g2D.getTransform();
		calAngle(player);
		if(checkDistance(player) && checkVision(player,obs)) { // if player its in range and in sight then rotate turret;
			
		if(Math.abs( turretRotateRatio - angleRad)  <= 0.1) {
			if(timer.getTime() >= tankReloadTime){
				clips.TANKFIRE.stop();
				clips.TANKFIRE.play();
				Thread.sleep(10);
				Fire();
		    timer.StartTime();	
		}
		}
		
		rotateTurret(g2D);
		turret.render(g2D, turret.x, turret.y, turret.w, turret.h);
		}
		else {
		
			turretStayStill( g2D);
		turret.render(g2D, turret.x, turret.y, turret.w, turret.h);
		}
		g2D.setTransform(trans);
		
		
		for(int i =0 ; i < bullet.size() ; ++i)
			if(!bullet.isEmpty()) {
				trans = g2D.getTransform();
				bullet.get(i).paint(g2D,mermi,angleRad);
				g2D.setTransform(trans);
			}

			for(int i =0 ; i < bullet.size() ; ++i) {
				if(bullet.get(i).CheckCollision(obs,player,this) || bullet.get(i).bulletOutOfWindow() ) {
					bullet.get(i).play();
					bullet.remove(i);
					i=0;
				}
			}
	}

public void repath(Tank player) {
	
	arraymap = new int [][] {
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,1,1,1,1,1,1,1,1,1,1,1,1,0,1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,0},
		{0,1,1,1,1,1,1,1,1,1,1,1,0,1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,1,0},
		{0,1,1,0,0,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,0,0,1,1,0},
		{0,1,1,0,0,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,0,0,1,1,0},
		{0,1,1,0,0,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,0,0,1,1,0},
		{0,1,1,0,0,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,0,0,1,1,0},
		{0,1,1,0,0,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,0,0,1,1,0},
		{0,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,0},
		{0,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,0},	
		{0,1,1,0,0,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,0,0,1,1,0},
		{0,1,1,0,0,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,0,0,1,1,0},
		{0,1,1,0,0,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,0,0,1,1,0},
		{0,1,1,0,0,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,0,0,1,1,0},
		{0,1,1,0,0,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,0,0,1,1,0},
		{0,1,1,1,1,1,1,1,1,1,1,1,0,1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,1,0},
		{0,1,1,1,1,1,1,1,1,1,1,1,1,0,1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
			
	};
	botFindway.traverse(arraymap, tank.y/48, tank.x/48, player.getY()/48, player.getX()/48);
}
	
	public void NextPath(Tank player) {
		
		
		  if(isLeftPossible()) {
				movingLeft = true;
				movingRight=false;
				movingDown=false;
				movingUp=false;
		
			}
		  else if(isRightPossible()) {
				
				movingLeft = false;
				movingRight=true;
				movingDown=false;
				movingUp=false;
			
			}
		 else if(isUpPossible()) {
			
				movingLeft = false;
				movingRight=false;
				movingDown=false;
				movingUp=true;
				
			}
		 else if(isDownPossible()) {
				
				movingLeft = false;
				movingRight=false;
				movingDown=true;
				movingUp=false;	
			}
			else if ( (tank.x/48 == player.getX() && tank.y/48 == player.getY()) ){
				arraymap[tank.y/48][tank.x/48]=1;
				System.out.println("reached destination");			
				repath(player);
				NextPath(player);
			}
			else {
				arraymap[tank.y/48][tank.x/48]=1;
				repath(player);
				NextPath(player);
			}
			
		}
		
		
		
	
	public boolean isLeftPossible() {
		
		Point tankLocation = new Point(tank.x/48-1,tank.y/48);
		if(arraymap[tankLocation.y][tankLocation.x] == 3 ) {
			arraymap[tank.y/48][tank.x/48]=1;
			return true;
		}
		return false;
	}
	public boolean isRightPossible() {
		
		Point tankLocation = new Point( (tank.x/48) + 1,tank.y/48);
		if(arraymap[tankLocation.y][tankLocation.x] == 3 ) {
			arraymap[tank.y/48][tank.x/48]=1;
			return true;
		}
		return false;
	}
	public boolean isUpPossible() {
		
		Point tankLocation = new Point(tank.x/48,tank.y/48-1);
		if(arraymap[tankLocation.y][tankLocation.x]==3 ) {
			arraymap[tank.y/48][tank.x/48]=1;
			return true;
		}
		return false;
	}
public boolean isDownPossible() {
		
		Point tankLocation = new Point(tank.x/48,tank.y/48+1);
		if(arraymap[tankLocation.y][tankLocation.x] ==3) {
			arraymap[tank.y/48][tank.x/48]=1;

			return true;
		}
		return false;
	}
public void Rotate(Graphics2D g2D) {
	if(movingUp) {
		
		g2D.rotate(Math.toRadians(180),center.x,center.y);
	
	}
	else if(movingDown) {
		
		
	}
	else if(movingLeft) {
		
		g2D.rotate(Math.toRadians(90),center.x,center.y);

	}
	else if(movingRight) {
		
		g2D.rotate(Math.toRadians(270),center.x,center.y);

	}
}
public void move(Tank player) {
	
	if(movingLeft && leftItr < 48 ) {
		Vectorx=-1;
		Vectory=0;
	++leftItr;

	}
	else if(movingRight && RightItr < 48) {
		Vectorx=1;
		Vectory=0;
	++RightItr;
	
	}
	else if(movingUp && UpItr < 48) {
		Vectorx=0;
		Vectory=-1;
		++UpItr;
		
	}
	else if(movingDown && DownItr < 48 ) {
		Vectorx=0;
		Vectory=1;
		++DownItr;
		
	}
	else {
		Vectorx=0;
		Vectory=0;
	}
	tank.x += Vectorx;
	tank.y += Vectory;
	turret.x += Vectorx;
	turret.y += Vectory;
	center.x += Vectorx;
	center.y += Vectory;
	
	if(leftItr == 48) {
		NextPath(player);
		leftItr = 0;
	}
	if(RightItr == 48) {
		NextPath(player);
		RightItr = 0;
	}
	if(UpItr == 48) {
		NextPath(player);
		UpItr = 0;
	}
	if(DownItr == 48) {
		NextPath(player);
		DownItr = 0;
	}
}
public void gotDamage() {
	--health;
}
	
	
	
	
	
}
