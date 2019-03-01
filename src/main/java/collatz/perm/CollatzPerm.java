package collatz.perm;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

class CollatzPerm extends Panel implements ActionListener {
	StringBuffer mSCollatz;
	long mAktintZahl;
	int mII;
	int cn1;
	int cn2;
	int cn3;
	int cn4;
	int cni;
	int cprt = 0;
	String scn1;
	String scn2;
	String scn3;
	String scn4;
	String scni;
	String scprt;
	int nr_an;
	int y0;
	int yn;
	int yofs;
	double nr_and;
	boolean prm_start;
	Prim_Thread prim_zhl;
	int n;
	int camu;
	String s_cn1;
	String s_cni;
	String str;
	String l_z = "-------------------------------------------";
	final int[][] DATA1 = { { 60, 1, 1 }, { 67, 1, 1 }, { 81, 4, 1 } };
	final int[][] DATA2 = { { 70, 1, 1 }, { 64, 4, 1 }, { 62, 4, 1 } };
	Label lb1 = new Label("   C o l l a t z   P e r m u t a t i o n");
	Label lbS = new Label("  S.&.J.");
	Label lb2 = new Label(" P e r m u t a t i o n e n ");
	Label lb3 = new Label("Eine  Zahl ");
	Label lb4 = new Label("zwischen ");
	Label lb5 = new Label("1  und  60 ");
	Label lb6 = new Label("eingeben: ");
	List ls = new List(10, true);
	TextField tf = new TextField("5", 2);
	Button bt = new Button("S t a r t");
	Button bt1 = new Button("S t o p");

	public CollatzPerm() {
		setLayout(null);
		setBackground(new Color(255, 130, 0));

		this.lb1.setBackground(Color.blue);
		this.lb1.setFont(new Font("Dialog", 1, 14));
		this.lb1.setForeground(Color.white);
		this.lb1.setBounds(5, 5, 645, 20);
		add(this.lb1);

		this.lbS.setBackground(Color.blue);
		this.lbS.setFont(new Font("Dialog", 1, 11));
		this.lbS.setForeground(Color.yellow);
		this.lbS.setBounds(650, 5, 45, 20);
		add(this.lbS);

		this.lb2.setFont(new Font("Dialog", 1, 12));
		this.lb2.setBounds(10, 40, 380, 20);
		add(this.lb2);

		this.ls.setBackground(Color.black);
		this.ls.setForeground(Color.yellow);
		this.ls.setFont(new Font("Monospaced", 1, 12));
		this.ls.setBounds(10, 60, 800, 600);
		add(this.ls);

		this.yofs = 60;
		this.y0 = 60;
		this.yn = 15;

		this.lb3.setFont(new Font("Dialog", 1, 12));
		this.lb3.setBounds(840, this.y0 + 0 * this.yn + this.yofs, 100, 20);
		this.lb3.setForeground(Color.blue);
		add(this.lb3);
		this.lb4.setFont(new Font("Dialog", 1, 12));
		this.lb4.setBounds(840, this.y0 + 1 * this.yn + this.yofs, 100, 20);
		this.lb4.setForeground(Color.blue);
		add(this.lb4);
		this.lb5.setFont(new Font("Dialog", 1, 12));
		this.lb5.setBounds(840, this.y0 + 2 * this.yn + this.yofs, 100, 20);
		this.lb5.setForeground(Color.blue);
		add(this.lb5);
		this.lb6.setFont(new Font("Dialog", 1, 12));
		this.lb6.setBounds(840, this.y0 + 3 * this.yn + this.yofs, 100, 20);
		this.lb6.setForeground(Color.blue);
		add(this.lb6);

		this.tf.setBackground(Color.black);
		this.tf.setFont(new Font("Dialog", 1, 14));
		this.tf.setForeground(Color.yellow);
		this.tf.setBounds(840, 130 + this.yofs, 70, 20);
		add(this.tf);

		this.bt.setBackground(Color.blue);
		this.bt.setFont(new Font("Dialog", 1, 12));
		this.bt.setForeground(Color.white);
		this.bt.setBounds(840, 160 + this.yofs, 70, 20);
		add(this.bt);

		this.bt.addActionListener(this);

		this.bt1.setBackground(Color.blue);
		this.bt1.setFont(new Font("Dialog", 1, 12));
		this.bt1.setForeground(Color.white);
		this.bt1.setBounds(840, 190 + this.yofs, 70, 20);
		add(this.bt1);

		this.bt1.addActionListener(this);
	}

	boolean parseOpEdits() {
		try {
			this.nr_an = Integer.parseInt(this.tf.getText());
		} catch (NumberFormatException ex) {
			this.ls.removeAll();
			this.ls.add("Bitte eine Zahl zwischen 1 und 60 eingeben!");
			Toolkit.getDefaultToolkit().beep();
			return false;
		}
		return true;
	}

	public void actionPerformed(ActionEvent e) {
		this.str = e.getActionCommand();
		if (this.str.equals("S t o p")) {
			this.prm_start = false;
		}
		if (this.str.equals("S t a r t")) {
			this.prm_start = true;
			if (parseOpEdits()) {
				if ((this.nr_an >= 1) && (this.nr_an <= 60)) {
					this.ls.removeAll();

					this.mSCollatz = new StringBuffer(this.nr_an + 1);
					for (int i = 0; i < this.nr_an; i++) {
						this.mSCollatz.append(".");
					}
					this.cn1 = 0;
					this.cn2 = 0;
					this.cn3 = 0;
					this.cn4 = 0;
					this.cni = 0;
					this.cprt = 0;

					this.prim_zhl = new Prim_Thread();
					this.prim_zhl.start();
				} else {
					this.bt.setEnabled(true);
					this.ls.removeAll();
					this.ls.add("Bitte eine Zahl zwischen 1 und 60 eingeben!");
					Toolkit.getDefaultToolkit().beep();
				}
			}
		}
	}

	public Dimension getMinimunSize() {
		return getPreferredSize();
	}

	public Dimension getPreferredSize() {
		return new Dimension(700, 400);
	}

	void Bilde_Collatz_Zahl(long pZahl, long pII, long pMult) {
		if (this.prm_start) {
			this.cn1 += 1;
			long lii = pII - 1L;
			long lZahl = pZahl + pZahl;
			if (pII > 0L) {
				this.mSCollatz.setCharAt((int) pII - 1, '.');
			}
			if (lii == 0L) {
				this.cn2 += 1;
				this.mAktintZahl = lZahl;
				TV_AddlZahl(pMult);
			} else {
				Bilde_Collatz_Zahl(lZahl, lii, pMult);
			}
			this.cn3 += 1;
			lZahl = pZahl;
			long lMod = lZahl % 3L;
			lZahl /= 3L;
			this.mSCollatz.setCharAt((int) pII - 1, '!');
			if ((lMod == 1L) && (lZahl % 2L == 1L) && (lZahl > 1L)) {
				if (lii == 0L) {
					this.cn4 += 1;
					this.mAktintZahl = lZahl;
					TV_AddlZahl(pMult + 1L);
				} else {
					Bilde_Collatz_Zahl(lZahl, lii, pMult + 1L);
				}
			}
		}
	}

	void TV_AddlZahl(long pMult) {
		this.cprt += 1;

		DecimalFormat df = new DecimalFormat(",##0");

		String smsC = new String(this.mSCollatz.toString());

		smsC = smsC + "1";

		String s_mII = new String(String.valueOf(this.mII).toString());

		String s_pMult = new String(String.valueOf(pMult).toString());
		if (s_pMult.length() < 2) {
			s_pMult = " " + s_pMult;
		}
		DecimalFormat df1 = new DecimalFormat("00");

		String snr_an = df1.format(this.nr_an);

		String sKey = new String(snr_an + '.' + s_pMult);

		String s_mAktintZahl = new String(String.valueOf(this.mAktintZahl)
				.toString());

		String sss = df.format(this.mAktintZahl);
		while (sss.length() < 60) {
			sss = " " + sss;
		}
		this.scprt = new String(String.valueOf(this.cprt).toString());
		while (this.scprt.length() < 7) {
			this.scprt = (" " + this.scprt);
		}
		this.ls.add(sKey + ' ' + smsC + ' ' + sss + " = n " + this.scprt + " x");

		this.ls.select(this.ls.getItemCount() - 1);

		this.cni += 1;
	}

	public void play_my_soundsss(int[][] DATA) throws Exception {
		Synthesizer synth = MidiSystem.getSynthesizer();
		synth.open();
		Receiver rcvr = synth.getReceiver();

		ShortMessage msg = new ShortMessage();
		for (int i = 0; i < DATA.length; i++) {
			for (int j = 0; j < DATA[i][2]; j++) {
				msg.setMessage(144, 0, DATA[i][0], 64);
				rcvr.send(msg, -1L);
				try {
					Thread.sleep(DATA[i][1] * 200);
				} catch (Exception e) {
				}
				msg.setMessage(128, 0, DATA[i][0], 0);
				rcvr.send(msg, -1L);
			}
		}
		synth.close();
	}

	public void play_my_soundss() throws Exception {
		int[][] DATA = { { 60, 1, 1 }, { 62, 1, 1 }, { 64, 1, 1 },
				{ 65, 1, 1 }, { 67, 2, 2 }, { 69, 1, 4 }, { 67, 4, 1 },
				{ 69, 1, 4 }, { 67, 4, 1 }, { 65, 1, 4 }, { 64, 2, 2 },
				{ 62, 1, 4 }, { 60, 4, 1 } };

		Synthesizer synth = MidiSystem.getSynthesizer();
		synth.open();
		Receiver rcvr = synth.getReceiver();

		ShortMessage msg = new ShortMessage();
		for (int i = 0; i < DATA.length; i++) {
			for (int j = 0; j < DATA[i][2]; j++) {
				msg.setMessage(144, 0, DATA[i][0], 64);
				rcvr.send(msg, -1L);
				try {
					Thread.sleep(DATA[i][1] * 100);
				} catch (Exception e) {
				}
				msg.setMessage(128, 0, DATA[i][0], 0);
				rcvr.send(msg, -1L);
			}
		}
		synth.close();
	}

	public void init() {
		if (this.prim_zhl == null) {
			this.prim_zhl = new Prim_Thread();
		}
	}

	public void start() {
		if (this.prim_zhl == null) {
			this.prim_zhl = new Prim_Thread();
			this.prim_zhl.start();
		}
	}

	public void stop() {
		if (this.prim_zhl != null) {
			this.prim_zhl.interrupt();
			this.prim_zhl = null;
		}
	}

	public void destroy() {
		if (this.prim_zhl != null) {
			this.prim_zhl.interrupt();
			this.prim_zhl = null;
		}
	}

	class Prim_Thread extends Thread {
		Prim_Thread() {
		}

		public void run() {
			CollatzPerm.this.bt.setEnabled(false);

			CollatzPerm.this.Bilde_Collatz_Zahl(1L, CollatzPerm.this.nr_an, 0L);

			CollatzPerm.this.bt.setEnabled(true);

			CollatzPerm.this.ls.add(CollatzPerm.this.l_z);
			CollatzPerm.this.ls.select(CollatzPerm.this.ls.getItemCount() - 1);
			try {
				if (CollatzPerm.this.prm_start) {
					CollatzPerm.this.play_my_soundsss(CollatzPerm.this.DATA1);
				} else {
					CollatzPerm.this.play_my_soundsss(CollatzPerm.this.DATA2);
				}
			} catch (Exception e) {
			}
		}
	}
}
