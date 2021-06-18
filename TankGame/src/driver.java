import javax.swing.JFrame;

public class driver extends JFrame{

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		GameScreen game = new GameScreen();

while(true) {
	game.repaint();
	Thread.sleep(10);
}
	}

}














