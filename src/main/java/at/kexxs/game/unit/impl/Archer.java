package at.kexxs.game.unit.impl;

import java.util.logging.Logger;

import at.kexxs.game.board.impl.Player;
import at.kexxs.game.impl.Game;
import at.kexxs.game.unit.IArcher;
import at.kexxs.game.util.Dice;

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
  public boolean shoot(Unit enemy) {
    final int attackValue = Dice.roll(getRangeAttack());
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
