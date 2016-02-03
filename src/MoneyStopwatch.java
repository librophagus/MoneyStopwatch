import javax.swing.JFrame;


public class MoneyStopwatch {

	public static void main(String[] args) {
		JFrame frame = new JFrame("MoneyStopwatch");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Monitor panel = new Monitor();
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
		//System.out.println("Begin!");
	}

}
