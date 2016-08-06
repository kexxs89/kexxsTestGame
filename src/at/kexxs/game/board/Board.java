package at.kexxs.game.board;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import at.kexxs.game.Game;
import at.kexxs.game.unit.Unit;

/**
 * @author Markus
 */
public class Board extends JPanel {
  private static final long serialVersionUID = 1L;
  private final Game game;
  private GameField[][] fields;
  public Unit selectedUnit;

  /** Creates a new instance of ChessBoard */
  public Board(Game game) {
    this.game = game;
    init();
  }

  public GameField[][] getFields() {
    return fields;
  }

  public void setFields(GameField[][] fields) {
    this.fields = fields;
  }

  public Unit getSelectedUnit() {
    return selectedUnit;
  }

  public void setSelectedUnit(Unit selectedUnit) {
    this.selectedUnit = selectedUnit;
  }

  public Game getGame() {
    return game;
  }

  public void init() {

    final GridLayout grid = new GridLayout(Game.WIDTH, Game.HEIGHT, 1, 1);
    setLayout(grid);

    fields = new GameField[Game.WIDTH][Game.HEIGHT];
    for (int row = 0; row < Game.WIDTH; row++) {
      for (int column = 0; column < Game.HEIGHT; column++) {
        fields[row][column] = new GameField(this);
        if ((row + column) % 2 == 0) {
          fields[row][column].setBackground(Color.white, true);
        } else {
          fields[row][column].setBackground(Color.black, true);
        }

        fields[row][column].setPosY(row);
        fields[row][column].setPosX(column);
        fields[row][column].setName("field_" + row + "_" + column);
        add(fields[row][column]);
      }
    }
    fields[4][9].setBackground(Color.YELLOW);
    fields[4][9].repaint();
  }

  public void setUnit(Unit unit, int row, int column) {
    unit.setGameField(getField(row, column));
    fields[row][column].setAndPaintUnit(unit);
  }

  public void removeUnit(int row, int column) {
    fields[row][column].removeUnit();
  }

  public void clearBackgroundColor() {
    for (int row = 0; row < Game.WIDTH; row++) {
      for (int column = 0; column < Game.HEIGHT; column++) {
        if ((row + column) % 2 == 0) {
          fields[row][column].setBackground(Color.white);
        } else {
          fields[row][column].setBackground(Color.black);
        }
        fields[row][column].repaint();
      }
    }
    fields[4][9].setBackground(Color.YELLOW);
    fields[4][9].repaint();
  }

  public void restart() {
    removeAll();
    init();
    validate();
  }

  public void checkAvailableFields(GameField field, Unit unit) {
    final int positionX = field.getPosY();
    final int positionY = field.getPosX();
    final int movement = unit.getMovement();

    for (int newPos = positionX + 1; newPos <= (positionX + movement); newPos++) {
      if (newPos < Game.WIDTH) {
        fields[newPos][positionY].setBackground(Color.GREEN);
      }
    }

    for (int newPos = positionX - 1; newPos >= (positionX - movement); newPos--) {
      if (newPos >= 0) {
        fields[newPos][positionY].setBackground(Color.GREEN);
      }
    }

    for (int newPos = positionY + 1; newPos <= (positionY + movement); newPos++) {
      if (newPos < Game.HEIGHT) {
        fields[positionX][newPos].setBackground(Color.GREEN);
      }
    }

    for (int newPos = positionY - 1; newPos >= (positionY - movement); newPos--) {
      if (newPos >= 0) {
        fields[positionX][newPos].setBackground(Color.GREEN);
      }
    }
    repaint();

  }

  public GameField getField(int posX, int posY) {
    return fields[posX][posY];
  }

  public void fillBoardWithUnits(Player player1, Player player2) {
    fields[4][0].setAndPaintUnit(new Unit(player1));
    fields[4][9].setAndPaintUnit(new Unit(player2));
  }

  public void nextPlayer() {

    if (getField(4, 9).getUnit() != null && getField(4, 9).getUnit().getPlayer().getId() == 1) {
      Game.setText("Glückwunsch du hast gewonnen!");
    } else {
      final Player activePlayer = game.changeActivePlayer();
      Game.setText(activePlayer.getName() + " ist an der Reihe!");
      if (activePlayer.getId() == 2) {
        final GameField gameFieldKi = findUnitOfPlayer(2);
        final GameField gameFieldPlayer = findUnitOfPlayer(1);
        final int differenceX = gameFieldKi.getPosY() - gameFieldPlayer.getPosY();
        final int differenceY = gameFieldKi.getPosX() - gameFieldPlayer.getPosX();
        final GameField newField;
        if (Math.abs(differenceX) > Math.abs(differenceY)) {
          int newX = 0;
          if (differenceX > 0) {
            newX = gameFieldKi.getPosY() - 1;
          } else {
            newX = gameFieldKi.getPosY() + 1;
          }
          newField = getField(newX, gameFieldKi.getPosX());
        } else {
          int newY = 0;
          if (differenceY > 0) {
            newY = gameFieldKi.getPosX() - 1;
          } else {
            newY = gameFieldKi.getPosX() + 1;
          }
          newField = getField(gameFieldKi.getPosY(), newY);
        }
        gameFieldKi.getUnit().setGameField(gameFieldKi);
        gameFieldKi.getUnit().move(newField);

        nextPlayer();

      }
      validate();
    }

  }

  public GameField findUnitOfPlayer(int playerId) {
    for (int row = 0; row < Game.WIDTH; row++) {
      for (int column = 0; column < Game.HEIGHT; column++) {

        if (fields[row][column].getUnit() != null) {
          if (fields[row][column].getUnit().getPlayer().getId() == playerId) {
            return fields[row][column];
          }
        }
      }
    }
    return null;
  }

}
