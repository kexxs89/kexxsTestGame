package at.kexxs.game.unit.impl;

import at.kexxs.game.board.impl.Player;
import at.kexxs.game.unit.IKnight;

public class Knight extends Unit implements IKnight {

  private static final long serialVersionUID = 8783712320733914134L;

  public static final String RED = "resources/knight_red.png";
  public static final String BLUE = "resources/knight_blue.png";

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

}
