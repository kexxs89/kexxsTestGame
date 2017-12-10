package at.kexxs.game.unit.impl;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import at.kexxs.game.board.impl.Player;
import at.kexxs.game.constant.TextConstant;
import at.kexxs.game.dice.Dice;
import at.kexxs.game.dice.DiceResultDTO;
import at.kexxs.game.impl.Game;
import at.kexxs.game.unit.IArcher;
import at.kexxs.game.util.UnitAction;
import javafx.util.Callback;

import javax.swing.*;

public class Archer extends Unit implements IArcher {

  private static final long serialVersionUID = 8783712320733914134L;

  private static final Logger log = Logger.getLogger(Archer.class.getName());

  public static final String RED = "resources/archer_red.png";
  public static final String BLUE = "resources/archer_blue.png";

  private final int defense = 2;
  private final int attack = 1;
  private final int movement = 3;
  private final int range = 5;
  private final int rangeAttack = 2;

  public Archer(Player player, String imagePath) {
    super(player, imagePath);
    setDefense(defense);
    setAttack(attack);
    setMovement(movement);
    setRange(range);
    setRangeAttack(rangeAttack);
  }

  @Override
  public void shoot(final Unit enemy, final Callback callback) {
  	
  	Callback shootCallback = new Callback() {
			@Override
			public Object call(Object param) {
				boolean result = false;
				DiceResultDTO diceResultDTO = (DiceResultDTO) param;
				final long attackValue = diceResultDTO.getAttackValue();
				log.info("Shoot Attack Value is:" + attackValue);
				final long defenseValue = diceResultDTO.getDefenseValue();
				log.info("Defense Value is:" + defenseValue);
				String battleInfo = new String();
				battleInfo += "Shoot Attacks with: " + attackValue + "\n";
				battleInfo += "Defense with: " + defenseValue + "\n";
				Game.setSideText(battleInfo);
				if (diceResultDTO.isSuccess()) {
					result = true;
					
					//enemy.getPlayer().getUnits().remove(enemy);
				}
				callback.call(result);
				return null;
			};
		};
  	new Dice(shootCallback, this , enemy, getGameField().getBoard().getGame().getActionBar());
  }
	
	@Override
	public Container openActionMenu(){
		Container container = super.openActionMenu();
		final JButton modeButton = new JButton(TextConstant.MODE_SHOOT);
		modeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getGameField().getBoard().setAction(UnitAction.SHOOT);
			}
		});
		container.add(modeButton);
		return container;
	}
}
