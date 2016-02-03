import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.NumberFormat;

import javax.swing.*;

@SuppressWarnings("serial")
public class Monitor extends JPanel{
	private JButton start = new JButton("Start");
	private JButton clear = new JButton("Clear");
	private JLabel dollars = new JLabel("$0.00");
	private double dollarvalue = 0;
	private MonitorActionListener actionListener = new MonitorActionListener();
	private Timer timer = new Timer(0, actionListener);
	private NumberFormat formatter = NumberFormat.getCurrencyInstance();
	private final double payRate = 11.10;
	private double payPerSecond;
	private JTextField txtHour;
	private JTextField txtMinute;
	private int secondMod = 0;
	private int minuteMod = 0;
	private int hourMod = 0;

	public Monitor() {
		payPerSecond = (payRate/60)/60;
		timer.setDelay(1000);
		setPreferredSize(new Dimension(272, 86));
		start.setBounds(12, 12, 70, 25);
		start.addActionListener(actionListener);
		clear.setBounds(94, 12, 71, 25);
		clear.addActionListener(actionListener);
		setLayout(null);
		add(start);
		add(clear);
		dollars.setFont(new Font("Dialog", Font.BOLD, 14));
		dollars.setBounds(183, 12, 86, 20);
		add(dollars);

		txtHour = new JTextField();
		txtHour.setText("hour");
		txtHour.setBounds(12, 49, 59, 19);
		txtHour.addFocusListener(new MonitorFocusListener());
		add(txtHour);
		txtHour.setColumns(10);

		txtMinute = new JTextField();
		txtMinute.setText("minute");
		txtMinute.setBounds(73, 49, 52, 19);
		txtMinute.addFocusListener(new MonitorFocusListener());
		add(txtMinute);
		txtMinute.setColumns(10);

		JLabel lblAlreadyWorked = new JLabel("Already Worked");
		lblAlreadyWorked.setFont(new Font("Dialog", Font.PLAIN, 10));
		lblAlreadyWorked.setBounds(134, 51, 86, 15);
		add(lblAlreadyWorked);

	}

	private void startIt() {
		try {
			int hr = Integer.parseInt(txtHour.getText());
			int min = Integer.parseInt(txtMinute.getText());
			dollarvalue += payRate * hr;
			dollarvalue += payRate/60 * min;
			minuteMod = min;
			hourMod = hr;
		} catch (NumberFormatException n) {
			txtHour.setText("0");
			txtMinute.setText("0");
		}


		if (timer.isRunning() == true) {
			timer.stop();
			start.setText("Start");
			txtHour.setEditable(true);
			txtMinute.setEditable(true);
		} else if (timer.isRunning() == false) {
			timer.start();
			start.setText("Stop");
			txtHour.setEditable(false);
			txtMinute.setEditable(false);
		}
	}

	private void clearIt() {
		//System.out.println("Clear");
		timer.stop();
		start.setText("Start");
		dollarvalue = 0;
		txtHour.setText("0");
		txtMinute.setText("0");
		String money = formatter.format(dollarvalue);
		dollars.setText(money);
		secondMod = 0;
		minuteMod = 0;
		hourMod = 0;
		txtHour.setEditable(true);
		txtMinute.setEditable(true);
		//System.out.println("secMod: " + secondMod + " minMod: " + minuteMod + " hrMod:" + hourMod);
	}

	private void update() {
		boolean upd = false;
		//System.out.println("secMod: " + secondMod + " minMod: " + minuteMod + " hrMod:" + hourMod);
		dollarvalue += payPerSecond;
		String money = formatter.format(dollarvalue);
		dollars.setText(money);
		secondMod++;
		if (secondMod == 60) {
			minuteMod++;
			secondMod = 0;
			upd = true;
		}
		if (minuteMod == 60) {
			hourMod++;
			minuteMod = 0;
			upd = true;
		}
		int hr = 0; 
		int min = 0; 
		if (upd == true) {
			upd = false;
			try {
				hr = Integer.parseInt(txtHour.getText());
				min = Integer.parseInt(txtMinute.getText());
				if (hourMod > 0 && (minuteMod % 60 == 0)) {
					hr++;
				}
				min = minuteMod;

				txtHour.setText(String.valueOf(hr));
				txtMinute.setText(String.valueOf(min));
			} catch (NumberFormatException e) {

			}
		}
	}

	private class MonitorActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if (source == timer) {
				update();
			} else if (source == start) {
				startIt();
			} else if (source == clear) {
				clearIt();
			}	
		}
	}

	private class MonitorFocusListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent arg0) {
			JTextField src = (JTextField) arg0.getSource();
			src.setText("");
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			JTextField src = (JTextField) arg0.getSource();
			//src.setText("0");
		}

	}
}
