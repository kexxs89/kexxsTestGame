package at.kexxs.game.util;

public class DiceUtil {

  public static int roll() {
    return (int) (6.0 * Math.random()) + 1;
  }

  public static int roll(int numberOfDice) {
    int result = 0;
    for (int index = 0; index < numberOfDice; index++) {
      final int tmp = roll();
      if (tmp > result) {
        result = tmp;
      }
    }
    return result;
  }

}
