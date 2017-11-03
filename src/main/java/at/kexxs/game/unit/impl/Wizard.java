package at.kexxs.game.unit.impl;

import java.util.logging.Logger;

import at.kexxs.game.board.impl.Player;
import at.kexxs.game.dice.Dice;
import at.kexxs.game.dice.DiceResultDTO;
import at.kexxs.game.impl.Game;
import at.kexxs.game.unit.IWizard;
import javafx.util.Callback;

public class Wizard extends Unit implements IWizard {
  private static final long serialVersionUID = 8783512320234214134L;

  private static final Logger log = Logger.getLogger(Wizard.class.getName());

  public static final String RED = "resources/wizard_red.png";
  public static final String BLUE = "resources/wizard_blue.png";

  private final int defense = 1;
  private final int attack = 1;
  private final int movement = 1;
  private final int rangeAttack = 5;
  private final int range = 5;

  public Wizard(Player player, String imagePath) {
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
				final int attackValue = rangeAttack;
				log.info("Shoot Attack Value is:" + attackValue);
				final long defenseValue = diceResultDTO.getDefenseValue();
				log.info("Defense Value is:" + defenseValue);
				String battleInfo = new String();
				battleInfo += "Shoot Attacks with: " + attackValue + "\n";
				battleInfo += "Defense with: " + defenseValue + "\n";
				Game.setSideText(battleInfo);
				if (diceResultDTO.isSuccess()) {
					enemy.getPlayer().getUnits().remove(enemy);
				}
				callback.call(result);
				return null;
			};
		};
		new Dice(shootCallback, rangeAttack , enemy.getDefense() ,getGameField().getBoard().getGame().getActionBar());
	}

}
