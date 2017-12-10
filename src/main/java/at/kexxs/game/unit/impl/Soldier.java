package at.kexxs.game.unit.impl;

import at.kexxs.game.board.impl.Player;
import at.kexxs.game.constant.TextConstant;
import at.kexxs.game.unit.ISoldier;
import at.kexxs.game.util.ImageBuilder;
import at.kexxs.game.util.UnitAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * @Author
 */

public class Soldier extends Unit implements ISoldier {

	private static final long serialVersionUID = 8783712320733914134L;

	public static final String RED = "resources/soldier_red.png";
	public static final String BLUE = "resources/soldier_blue.png";
	public static final String SHIELD_RED = "resources/shield_red.png";
	public static final String SHIELD_BLUE = "resources/shield_blue.png";
	
	private final int defense = 2;
	private final int attack = 1;
	private final int movement = 5;
	
  	private final int defenseDefense = 1;
	private final int defenseAttack = 4;
	private final int defenseMovement = 2;
	
  	private ModeEnum mode = ModeEnum.attack;
  
  	private enum ModeEnum{
  		attack,
		defense
	}

	public Soldier(Player player, String imagePath) {
		super(player, imagePath);
		setDefense(defense);
		setAttack(attack);
		setMovement(movement);
	}
	
	@Override
	public Container openActionMenu(){
  		Container container = super.openActionMenu();
		final JButton modeButton = new JButton(TextConstant.MODE_DEFENSE);
		modeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mode.equals(ModeEnum.attack)){
					setImageByPath(getShieldPath());
					mode = ModeEnum.defense;
				}else{
					setImageByPath(getNormalPath());
					mode = ModeEnum.attack;
				}
				getGameField().getBoard().clearBackgroundColor();
				setHasMoved(true);
			}
		});
		container.add(modeButton);
		return container;
	}
	
	private String getShieldPath(){
		if(getPlayer().getId() == 1){
			return SHIELD_RED;
		}else{
			return SHIELD_BLUE;
		}
	}
	
	private String getNormalPath(){
		if(getPlayer().getId() == 1){
			return RED;
		}else{
			return BLUE;
		}
	}
	
	@Override
	public int getAttack() {
		if(mode.equals(ModeEnum.attack)){
			return attack;
		}else{
			return defenseAttack;
		}
	}
	
	@Override
	public int getDefense() {
		if(mode.equals(ModeEnum.attack)){
			return defense;
		}else{
			return defenseDefense;
		}
	}
	@Override
	public int getMovement() {
		if(mode.equals(ModeEnum.attack)){
			return movement;
		}else{
			return defenseMovement;
		}
	}
}
