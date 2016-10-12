package at.kexxs.game.unit.impl;

import at.kexxs.game.board.impl.Player;
import at.kexxs.game.unit.IKing;

public class King extends Unit implements IKing {

  public static final String RED = "resources/king_red.png";
  public static final String BLUE = "resources/king_blue.png";

  private final int defense = 2;
  private final int attack = 1;
  private final int movement = 2;

  public King(Player player, String imagePath) {
    super(player, imagePath);
    setDefense(defense);
    setAttack(attack);
    setMovement(movement);
  }

}
