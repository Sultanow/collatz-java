package collatz.perm;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class Coll_Perm_ThrS

{
	public static void main(String[] args) {
		JFrame fenster = new JFrame(
				"C o l l a t z - Z a h l e n              programmiert vom Janos Sipos");

		fenster.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		CollatzPerm c = new CollatzPerm();
		fenster.getContentPane().add(c);
		fenster.pack();
		fenster.setVisible(true);
	}

}
