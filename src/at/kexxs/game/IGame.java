package at.kexxs.game;

import at.kexxs.game.board.impl.Player;

public interface IGame {

  void initLayout();

  void initBoard();

  void initButton();

  void initBottomText();

  Player getAcitvePlayer();

  Player getNotAcitvePlayer();

  void initSideLayout();

  void gameEnded(Player winner, Player loser);

  void restartGame();

}
