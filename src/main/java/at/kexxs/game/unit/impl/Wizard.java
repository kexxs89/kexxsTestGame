package at.kexxs.game.unit.impl;

import java.util.logging.Logger;

import at.kexxs.game.board.impl.Player;
import at.kexxs.game.impl.Game;
import at.kexxs.game.unit.IWizard;
import at.kexxs.game.util.Dice;

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
  public boolean shoot(Unit enemy) {
    final int attackValue = rangeAttack;
    log.info("Shoot Attack Value is:" + attackValue);
    final int defenseValue = Dice.roll(enemy.getDefense());
    log.info("Defense Value is:" + defenseValue);
    String battleInfo = new String();
    battleInfo += "Shoot Attacks with: " + attackValue + "\n";
    battleInfo += "Defense with: " + defenseValue + "\n";
    Game.setSideText(battleInfo);
    if (attackValue >= defenseValue) {
      enemy.getPlayer().getUnits().remove(enemy);
      return true;
    } else {
      return false;
    }
  }

}
