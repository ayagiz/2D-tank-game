import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Texture {
	
public BufferedImage img;
public int x;
public int y;
public int w;
public int h;

Texture(String path , int x , int y , int w , int h){
	
	try {
	img = ImageIO.read(getClass().getResource(path));
	
	}
catch(IOException e) {
	System.out.println(e.getMessage());

}
	this.x=x;
	this.y=y;
	this.w=w;
	this.h=h;







}
public void crop(int x,int y,int w,int h) {
img=img.getSubimage(x, y, w, h);

	
}
public void render(Graphics2D g2D,int x , int y , int w , int h) {
	
	 g2D.drawImage(img,x,y,w,h,null);
	 
}
public BufferedImage getImg() {
	return img;
}


}
