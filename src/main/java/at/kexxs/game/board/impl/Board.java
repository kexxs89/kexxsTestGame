package at.kexxs.game.board.impl;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.swing.JPanel;

import at.kexxs.game.board.IBoard;
import at.kexxs.game.constant.TextConstant;
import at.kexxs.game.impl.Game;
import at.kexxs.game.unit.impl.Archer;
import at.kexxs.game.unit.impl.King;
import at.kexxs.game.unit.impl.Knight;
import at.kexxs.game.unit.impl.Soldier;
import at.kexxs.game.unit.impl.Unit;
import at.kexxs.game.unit.impl.Wizard;
import at.kexxs.game.util.UnitAction;

/**
 * @author Markus
 */
public class Board extends JPanel implements IBoard {
  private static final long serialVersionUID = 12164984616L;
  private final Game game;
  private GameField[][] fields;
  public Unit selectedUnit;
	private UnitAction action = UnitAction.IDLE;
	
	
	private static final Logger log = Logger.getLogger(Board.class.getName());

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
          fields[row][column].setBackground(Color.gray, true);
        } else {
          fields[row][column].setBackground(Color.darkGray, true);
        }

        fields[row][column].setPosY(row);
        fields[row][column].setPosX(column);
        fields[row][column].setName("field_" + row + "_" + column);
        add(fields[row][column]);
      }
    }
  }

  public void setUnit(Unit unit, int row, int column) {
    unit.setGameField(getField(row, column));
    fields[row][column].setAndPaintUnit(unit);
  }

  public void removeUnit(int row, int column) {
    fields[row][column].removeUnit();
  }

  @Override
  public void clearBackgroundColor() {
    for (int row = 0; row < Game.WIDTH; row++) {
      for (int column = 0; column < Game.HEIGHT; column++) {
        if ((row + column) % 2 == 0) {
          fields[row][column].setBackground(Color.gray);
        } else {
          fields[row][column].setBackground(Color.darkGray);
        }
        fields[row][column].repaint();
      }
    }
  }

  public void restart() {
    log.info(TextConstant.GAME_RESTART);
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

  public void checkIfRangerAttackIsAvailableFields(GameField field, Unit unit) {
    final int positionX = field.getPosY();
    final int positionY = field.getPosX();
    final int range = unit.getRange();

    for (int newPos = positionX + 1; newPos <= (positionX + range); newPos++) {
      if (newPos < Game.WIDTH) {
        fields[newPos][positionY].setBackground(Color.YELLOW);
      }
    }

    for (int newPos = positionX - 1; newPos >= (positionX - range); newPos--) {
      if (newPos >= 0) {
        fields[newPos][positionY].setBackground(Color.YELLOW);
      }
    }

    for (int newPos = positionY + 1; newPos <= (positionY + range); newPos++) {
      if (newPos < Game.HEIGHT) {
        fields[positionX][newPos].setBackground(Color.YELLOW);
      }
    }

    for (int newPos = positionY - 1; newPos >= (positionY - range); newPos--) {
      if (newPos >= 0) {
        fields[positionX][newPos].setBackground(Color.YELLOW);
      }
    }
    repaint();
  }

  public GameField getField(int posY, int posX) {
    return fields[posY][posX];
  }

  public void fillBoardWithUnits(Player player1, Player player2) {
    for (int i = 0; i < 10; i++) {
      if (i == 4) {
        fields[i][0].setAndPaintUnit(new Wizard(player1, Wizard.RED));
      } else if (i == 5) {
        fields[i][0].setAndPaintUnit(new King(player1, King.RED));
      } else if (i == 3 || i == 6) {
        fields[i][0].setAndPaintUnit(new Knight(player1, Knight.RED));
      } else {
        fields[i][0].setAndPaintUnit(new Archer(player1, Archer.RED));
      }
    }
    for (int i = 0; i < 10; i++) {
      fields[i][1].setAndPaintUnit(new Soldier(player1, Soldier.RED));
    }

    for (int i = 0; i < 10; i++) {

      fields[i][Game.WIDTH - 2].setAndPaintUnit(new Soldier(player2, Soldier.BLUE));
    }

    for (int i = 0; i < 10; i++) {

      if (i == 4) {
        fields[i][Game.WIDTH - 1].setAndPaintUnit(new Wizard(player2, Wizard.BLUE));
      } else if (i == 5) {
        fields[i][Game.WIDTH - 1].setAndPaintUnit(new King(player2, King.BLUE));
      } else if (i == 3 || i == 6) {
        fields[i][Game.WIDTH - 1].setAndPaintUnit(new Knight(player2, Knight.BLUE));
      } else {
        fields[i][Game.WIDTH - 1].setAndPaintUnit(new Archer(player2, Archer.BLUE));
      }
    }
  }

  public void nextPlayer() {
    final Player activePlayer = game.changeActivePlayer();
    log.info(activePlayer.getName() + " ist an der Reihe!");
    Game.setText(activePlayer.getName() + " ist an der Reihe!");
    setAction(UnitAction.IDLE);
    validate();
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
    log.info("No Units found");
    return null;
  }

  @Override
  public String toString() {
    return "Board [game=" + game + ", fields=" + Arrays.toString(fields) + ", selectedUnit=" + selectedUnit + ", toString()=" + super.toString() + "]";
  }

  public boolean checkIfUnitisOnField(int row, int column) {
    return (fields[row][column].getUnit() != null);
  }
	
	public UnitAction getAction() {
		return action;
	}
	
	public void setAction(UnitAction action) {
		this.action = action;
	}
}
