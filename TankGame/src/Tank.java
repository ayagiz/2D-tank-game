import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Tank {
	private int health=1;
	public int getHealth() {
		return health;
	}
private double angleRad;

private double turretRotateRatio;
private ArrayList<Bullet> bullet;
private GameScreen game;
private JFrame window;	
public AffineTransform trans;
private int Vectorx;
private int Vectory;
private double Velocity;

private boolean Moving_Upward;
private boolean Moving_Downward;
private boolean Moving_Right;
private boolean Moving_Left;

private boolean upwardPressed;
private boolean downwardPressed;
private boolean rightPressed;
private boolean leftPressed;

private boolean lookingLeft;
private boolean lookingRight;
private boolean lookingUp;
private boolean lookingDown;

public boolean FireTheTank;
public boolean TankMove;
Sound clips;
private Point center;//to rotate tank
private Point fireXY;
public final int tankReloadTime=2;
Texture tank;
Texture turret;

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
public Point getCenter() {
	return center;
}
public Tank(int x , int y , double Velocity , GameScreen game ,JFrame window) {
	FireTheTank=false;
	angleRad=0.0;
	turretRotateRatio=0.0;
	clips= new Sound();
	
	bullet=new ArrayList<Bullet>();
	upwardPressed=false;
	downwardPressed=false;
	rightPressed=false;
	leftPressed=false;
	
	this.window=window;
	this.game=game;

	Vectorx=0;
	Vectory=0;
	Velocity=(float) 5.0;

Moving_Upward=false;
Moving_Downward=true;
Moving_Left=false;
Moving_Right=false;

lookingLeft=false;
lookingRight=false;
lookingDown=false;
lookingUp=true;

	
	tank = new Texture("/Textures/tanknew.png",x,y,48,48);
	turret = new Texture("/Textures/turret.png",tank.x+12,tank.y+15,24,48);
	center=new Point(tank.x+tank.w/2,tank.y+tank.h/2);
	

}
public void keyPressed(KeyEvent event) {
	switch(event.getKeyCode()) {
	case KeyEvent.VK_A:
	case KeyEvent.VK_LEFT:
		
		if(!rightPressed && !upwardPressed && !downwardPressed) {
		leftPressed=true;	
		Moving_Left=true;
		Moving_Right=false;
		Moving_Upward=false;
		Moving_Downward=false;
		TankMove=true;
		Vectorx=-2;		
		}
		break;
	case KeyEvent.VK_D:
	case KeyEvent.VK_RIGHT:
		
		if(!leftPressed && !upwardPressed && !downwardPressed) {
			rightPressed=true;
		Vectorx=2;
		Moving_Right=true;
		Moving_Upward=false;
		Moving_Downward=false;
		Moving_Left=false;
TankMove=true;
		}
		break;
	case KeyEvent.VK_W:
	case KeyEvent.VK_UP:
		
		if(!rightPressed && !leftPressed && !downwardPressed) {
			upwardPressed=true;
		Vectory=-2;
		Moving_Upward=true;
		Moving_Downward=false;
		Moving_Left=false;
		Moving_Right=false;
TankMove=true;
	
		}
		break;
	case KeyEvent.VK_S:
	case KeyEvent.VK_DOWN:
		
		if(!rightPressed && !upwardPressed && !leftPressed) {
		downwardPressed=true;
		Moving_Downward=true;
		Moving_Left=false;
		Moving_Right=false;
		Moving_Upward=false;
		Vectory=2;
TankMove=true;
	
		}
		break;
	}
	
}
public void keyReleased(KeyEvent event) {

	switch(event.getKeyCode()) {
	case KeyEvent.VK_Q:

	case KeyEvent.VK_W:
	case KeyEvent.VK_UP:

		
		upwardPressed=false;
		
		Vectory=0;
		

		break;
	case KeyEvent.VK_D:
	case KeyEvent.VK_RIGHT:
	
		
		rightPressed=false;
		
		Vectorx=0;

	break;
	case KeyEvent.VK_A:
	case KeyEvent.VK_LEFT:

		
		leftPressed=false;

		Vectorx=0;

		break;
	case KeyEvent.VK_S:
	case KeyEvent.VK_DOWN:

		
		downwardPressed=false;

		Vectory=0;

		break;
	
	}
}
public void fire(MouseEvent event) {
	switch(event.getButton()) {
	case MouseEvent.BUTTON1:
		
		double dy=Math.sin(turretRotateRatio)*turret.h;
		double dx=Math.cos(turretRotateRatio)*turret.h;
	
		fireXY=new Point(  (int)(dx+center.x) , (int)(dy+center.y) ); 
		Bullet b= new Bullet(fireXY,window,turretRotateRatio);
		bullet.add(b);

		break;
	
	
	
	}
}
public void paint(Graphics2D g2D , ArrayList<Obstacles> obs ,Bot AI ,Texture mermi) throws InterruptedException {
	
if(FireTheTank)
{
	clips.TANKFIRE.stop();
	clips.TANKFIRE.play();
	Thread.sleep(10);
	FireTheTank=false;
}



	
trans=g2D.getTransform();
Rotate(g2D);
checkCollision(obs);
move();
tank.render(g2D,tank.x,tank.y,tank.w,tank.h);
g2D.setTransform(trans);
trans=g2D.getTransform();
RotateTurret(g2D);
turret.render(g2D, turret.x, turret.y, turret.w, turret.h);
g2D.setTransform(trans);
g2D.setColor(new Color(255,0,0));
for(int i =0 ; i < bullet.size() ; ++i)
if(!bullet.isEmpty()) {
	trans=g2D.getTransform();
	bullet.get(i).paint(g2D,mermi,angleRad);
	g2D.setTransform(trans);
}
for(int i =0 ; i < bullet.size() ; ++i) {
	if(bullet.get(i).CheckCollision(obs,this,AI) || bullet.get(i).bulletOutOfWindow() ) {
		bullet.get(i).play();
		bullet.remove(i);
		i=0;
	}
}

}
public void checkCollision(ArrayList<Obstacles> obs) {
	if(tank.x+Vectorx < 0)
		Vectorx=0;
	else if(tank.x+Vectorx+tank.getImg().getWidth() > (window.getWidth()-20))
		Vectorx=0;
	else if(tank.y+Vectory < 0 )
		Vectory=0;
	else if(tank.y+tank.getImg().getHeight()+Vectory > (window.getHeight()-40) )
		Vectory=0;
	if(!obs.isEmpty()) {
		for(int i =0 ; i< obs.size() ; ++i) {
			if(   !(tank.x+Vectorx >= obs.get(i).getObs().x+obs.get(i).getObs().w) && !(tank.x+tank.w+Vectorx <= obs.get(i).getObs().x) &&
					!(tank.y+Vectory >= obs.get(i).getObs().y + obs.get(i).getObs().h) && !(tank.y+tank.h+Vectory <= obs.get(i).getObs().y)) {
				Vectorx=0;
				Vectory=0;
			}
		}
	}
	
	
	
}
public void move() {

	tank.x += Vectorx;
	tank.y += Vectory;
	turret.x += Vectorx;
	turret.y += Vectory;
	center.x= (center.x+Vectorx);
	center.y= (center.y+ Vectory);
	


}
























public void Rotate(Graphics2D g2D) {
	if(Moving_Upward) {
		
		g2D.rotate(Math.toRadians(180),center.x,center.y);
	
	}
	else if(Moving_Downward) {
		
		
	}
	else if(Moving_Left) {
		
		g2D.rotate(Math.toRadians(90),center.x,center.y);

	}
	else if(Moving_Right) {
		
		g2D.rotate(Math.toRadians(270),center.x,center.y);

	}
}
public void CalAngle(MouseEvent event) {
	int dy= event.getY()  - center.y;
	int dx = event.getX() - center.x;

angleRad=Math.atan2(dy, dx);

}



public void RotateTurret(Graphics2D g2D) {
	
	if(  Math.abs( turretRotateRatio - angleRad)  >= 0.01) {

	if ( turretRotateRatio > angleRad && angleRad <= 0) {
		
		if( turretRotateRatio + -angleRad > 6 ) {
			System.out.println("bura");
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
public void gotDamage() {
	--health;
}




}
