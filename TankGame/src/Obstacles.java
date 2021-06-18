import java.awt.Graphics2D;

import javax.imageio.ImageIO;

public class Obstacles {
	Texture obs;
	boolean Destructable;
	
	public Obstacles(String path , int x , int y , int w , int h) {
		obs = new Texture(path , x ,y ,w,h);
		Destructable=false;
	}
	public void render(Graphics2D g2D) {
		obs.render(g2D, obs.x, obs.y, obs.w, obs.h);
	}
public Texture getObs() {
	return obs;
}
}
