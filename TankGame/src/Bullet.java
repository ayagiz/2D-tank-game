import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Bullet {

	private double x;
	private double y;	
	Sound clips;
	private double Vectorx;
	private double Vectory;
	private double distanceX;
	private double distanceY;
	private double hypetenüs;
	private double fireline;
	JFrame window;
	
	private int Mousex;
	private int Mousey;
	public int getX() {
		return (int) x;
	}
	public int getY() {
		return (int) y;
	}
	public Bullet(Point fireXY,JFrame window,double r){
	clips= new Sound();
		fireline=r;
		this.window=window;
		x=fireXY.x;
		y=fireXY.y;
	}
public void paint(Graphics2D g2D,Texture mermi,double angle) {
	
	
g2D.rotate(angle, x,y);
	mermi.render(g2D, (int)x, (int)y, mermi.w, mermi.h);
	 move();

	
}
public boolean CheckCollision(ArrayList<Obstacles> obs , Tank player , Bot AI) {
	if(x > player.getX() && x < player.getX() +player.getW()  && y > player.getY() && y < player.getY()+player.getH()) {
		player.gotDamage();
		return true;
	}
	if(x > AI.getX() && x < AI.getX()+AI.getW()  && y > AI.getY() && y < AI.getY()+AI.getH()) {
		AI.gotDamage();
	return true;
	}
	if(!obs.isEmpty()) {
		for( int i =0 ; i < obs.size() ; ++i) {
			
			if( (x > obs.get(i).obs.x) && ( x < obs.get(i).obs.x+obs.get(i).getObs().w) && ( y > obs.get(i).obs.y )  && (y <  obs.get(i).obs.y+obs.get(i).getObs().h))
				return true;
		}
		
	}
	
	return false;
}
public int CheckVision(ArrayList<Obstacles> obs , Tank player , Bot AI) {
	

	if(!obs.isEmpty()) {
for( int i =0 ; i < obs.size() ; ++i) {
			if( (x > obs.get(i).obs.x) && ( x < obs.get(i).obs.x+obs.get(i).getObs().w) && ( y > obs.get(i).obs.y )  && (y <  obs.get(i).obs.y+obs.get(i).getObs().h)) {
				return 3;
			}
		}

		if(x > AI.getX() && x < AI.getX()+AI.getW()  && y > AI.getY() && y < AI.getY()+AI.getH()) {
			return 2; // friendly bot is in sight
			}
		if(x > player.getX() && x < player.getX() +player.getW()  && y > player.getY() && y < player.getY()+player.getH()) {
			return 1; // player is in sight
		}
		
	}
	
	return -1; // nothing is in sight
}
public void play() throws InterruptedException {
	clips.BULLETHIT.stop();
	clips.BULLETHIT.play();
	Thread.sleep(10);
}
public boolean bulletOutOfWindow() {
	if( x > 1440 || y > 864 || x < 0 || y < 0)
		return true;
	else return false;
}
public void move() {
	double sinalfa=0;
	double cosalfa=0;

		sinalfa=Math.sin(fireline);
		cosalfa=Math.cos(fireline);
		
		Vectorx=cosalfa*5;
		Vectory=sinalfa*5;	
		
	x=x+Vectorx;
	y=y+Vectory;


}
}
