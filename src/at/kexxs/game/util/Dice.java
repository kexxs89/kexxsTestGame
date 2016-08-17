package at.kexxs.game.util;

public class Dice {

  public static int roll() {
    return (int) (6.0 * Math.random()) + 1;
  }

}
