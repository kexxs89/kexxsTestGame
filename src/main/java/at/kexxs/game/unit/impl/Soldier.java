package at.kexxs.game.unit.impl;

import at.kexxs.game.board.impl.Player;
import at.kexxs.game.unit.ISoldier;

/**
 * @Author
 */

public class Soldier extends Unit implements ISoldier {

  private static final long serialVersionUID = 8783712320733914134L;

  public static final String RED = "resources/soldier_red.png";
  public static final String BLUE = "resources/soldier_blue.png";

  private final int defense = 2;
  private final int attack = 1;
  private final int movement = 5;

  public Soldier(Player player, String imagePath) {
    super(player, imagePath);
    setDefense(defense);
    setAttack(attack);
    setMovement(movement);

  }

}
