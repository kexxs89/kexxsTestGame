package at.kexxs.game.gui;

import at.kexxs.game.board.impl.GameField;
import javafx.util.Callback;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Logger;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Dice {
	
	private static final Logger log = Logger.getLogger(Dice.class.getName());
	
	private JLabel firstDice;
	private JLabel secondDice;
	private JFrame frame;
	
	private final ScheduledExecutorService scheduler =
			Executors.newScheduledThreadPool(1);
	
	public void beepForAnHour() {

	}
	
	
	public Dice(final Callback callback) throws InterruptedException {
		frame = new JFrame();
		frame.setSize(300, 300);
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());
		firstDice = new JLabel("0",JLabel.CENTER);
		secondDice = new JLabel("0",JLabel.CENTER);
		frame.add(firstDice, BorderLayout.EAST);
		frame.add(secondDice, BorderLayout.WEST);
		
		final Runnable rollerRunnable = new Runnable() {
			public void run() { rollDice();}
		};
		final ScheduledFuture<?> roller =
				scheduler.scheduleAtFixedRate(rollerRunnable, 500, 500, MILLISECONDS);
		
		scheduler.schedule(new Runnable() {
			public void run() {
				roller.cancel(true);
				Long resultFirstDice = Long.parseLong(firstDice.getText());
				Long resultSecondDice = Long.parseLong(secondDice.getText());
				if(resultFirstDice > resultSecondDice){
					log.info("Fist Dice Won");
				}else{
					log.info("Second Dice Won");
				}
				callback.call(resultFirstDice);
			}
		}, 5, SECONDS);
		


		
	}
	
	private void rollDice(){
		firstDice.setText(getRandomDiceNumber());
		secondDice.setText(getRandomDiceNumber());
		frame.repaint();
	}
	
	private String getRandomDiceNumber(){
		Double randomNumber = Math.random() * 5 + 1;
		return String.valueOf(Math.round(randomNumber));
	}
}
