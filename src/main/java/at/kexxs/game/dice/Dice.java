package at.kexxs.game.dice;

import at.kexxs.game.util.ImageBuilder;
import javafx.util.Callback;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Logger;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Dice {
	
	private static final Logger log = Logger.getLogger(Dice.class.getName());
	
	private DiceDTO attackDice;
	private DiceDTO defenseDice;
	
	public static final String dice1 = "resources/dice1.png";
	public static final String dice2 = "resources/dice2.png";
	public static final String dice3 = "resources/dice3.png";
	public static final String dice4 = "resources/dice4.png";
	public static final String dice5 = "resources/dice5.png";
	public static final String dice6 = "resources/dice6.png";
	HashMap<Long, String> map = new HashMap<Long, String>();
	
	private final ScheduledExecutorService scheduler =
			Executors.newScheduledThreadPool(1);
	
	public Dice(final Callback callback, final long minAttack, final long minDefense , final Container container)  {
		
		attackDice = new DiceDTO();
		defenseDice = new DiceDTO();
		
		map = new HashMap<Long, String>();
		map.put(1L, dice1);
		map.put(2L, dice2);
		map.put(3L, dice3);
		map.put(4L, dice4);
		map.put(5L, dice5);
		map.put(6L, dice6);
		
		setDiceImage(attackDice,1L);
		setDiceImage(defenseDice,1L);
		container.add(attackDice.getLabel());
		container.add(defenseDice.getLabel());
		
		final Runnable rollerRunnable = new Runnable() {
			public void run() {
				rollDice(attackDice , minAttack);
				rollDice(defenseDice, minDefense);
				container.revalidate();
			}
		};
		final ScheduledFuture<?> roller =
				scheduler.scheduleAtFixedRate(rollerRunnable, 50, 50, MILLISECONDS);
		
		scheduler.schedule(new Runnable() {
			public void run() {
				roller.cancel(true);
				Long resultFirstDice = attackDice.getValue();
				Long resultSecondDice = defenseDice.getValue();
				DiceResultDTO result = new DiceResultDTO();
				result.setAttackValue(resultFirstDice);
				result.setDefenseValue(resultSecondDice);
				result.setSuccess(resultFirstDice > resultSecondDice);
				container.revalidate();
				
				
				callback.call(result);
				scheduler.schedule(new Runnable() {
					public void run() {
						container.removeAll();
						}
					}, 2 , SECONDS);
			}
		}, 1500, MILLISECONDS);
	}
	
	private void rollDice(DiceDTO dice, long min){
		
		setDiceImage(dice , getRandomDiceNumber(min));
	}
	
	private Long getRandomDiceNumber(long min){
		Double randomNumber = Math.random() * 6 + 1;
		if(randomNumber < min){
			randomNumber = (double) min;
		}
		return randomNumber.longValue();
	}
	
	private void setDiceImage(DiceDTO dice , Long value){
		dice.setValue(value);
		String path = map.get(value);
		Image image = ImageBuilder.getBufferdImageFromPath(path);
		dice.setImage(image);
		ImageIcon imageIcon = new ImageIcon(image);
		dice.getLabel().setIcon(imageIcon);
	}
}
