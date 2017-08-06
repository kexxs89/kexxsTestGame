package at.kexxs.game.board;

import at.kexxs.game.board.impl.GameField;
import at.kexxs.game.board.impl.Player;
import at.kexxs.game.unit.impl.Unit;

public interface IBoard {

  void setUnit(Unit unit, int row, int column);

  GameField findUnitOfPlayer(int playerId);

  void fillBoardWithUnits(Player player1, Player player2);

  void restart();

  void clearBackgroundColor();

  void removeUnit(int row, int column);

  boolean checkIfUnitisOnField(int row, int column);

}
