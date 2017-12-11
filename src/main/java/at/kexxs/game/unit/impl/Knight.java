package at.kexxs.game.unit.impl;

import at.kexxs.game.board.impl.GameField;
import at.kexxs.game.board.impl.Player;
import at.kexxs.game.constant.TextConstant;
import at.kexxs.game.unit.IKnight;
import at.kexxs.game.util.UnitAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Knight extends Unit implements IKnight {

	private static final long serialVersionUID = 8783712320733914134L;
	
	public static final String RED = "resources/knight_red.png";
	public static final String BLUE = "resources/knight_blue.png";
	
	private static final Logger log = Logger.getLogger(Knight.class.getName());
	
	
	private final int defense = 4;
	private final int attack = 3;
	private final int movement = 5;
	
	public Knight(Player player, String imagePath) {
	super(player, imagePath);
	setDefense(defense);
	setAttack(attack);
	setMovement(movement);
	setJump(true);
	}
	
	@Override
	public Container openActionMenu(){
		Container container = super.openActionMenu();
		final JButton modeButton = new JButton(TextConstant.MODE_TRAMPLE);
		modeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getGameField().getBoard().setAction(UnitAction.SPECIAL);
			}
		});
		container.add(modeButton);
		return container;
	}
	
	public void special(ArrayList<GameField> fields){
		log.info("No special method exist");
	};

}
