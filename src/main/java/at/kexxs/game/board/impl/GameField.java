package at.kexxs.game.board.impl;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseEvent;
import java.awt.font.TextLayout;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.*;

import at.kexxs.game.board.IGameField;
import at.kexxs.game.impl.Game;
import at.kexxs.game.unit.IRange;
import at.kexxs.game.unit.impl.Unit;
import at.kexxs.game.util.UnitAction;
import javafx.util.Callback;

public class GameField extends JPanel implements IGameField {

  /**
   * @author Markus
   */
  private static final long serialVersionUID = 1L;
  private int posY;
  private int posX;
  private Unit unit;
  private final Board board;
  private static final Logger log = Logger.getLogger(GameField.class.getName());

  public GameField(Board board) {
    addMouseListener(this);
    this.board = board;
  }

  public Board getBoard() {
    return board;
  }

  public int getPosY() {
    return posY;
  }

  public void setPosY(int posY) {
    this.posY = posY;
  }

  public int getPosX() {
    return posX;
  }

  public void setPosX(int posX) {
    this.posX = posX;
  }

  public Unit getUnit() {
    return unit;
  }

  public void setUnit(Unit unit) {
    this.unit = unit;
  }

  @Override
  public String getName() {
    return super.getName();
  }

  @Override
  public void setName(String name) {
    super.setName(name);

  }

  public void setBackground(Color bg, boolean setDefault) {
    if (setDefault) {
    }
    super.setBackground(bg);
  };

  public void mouseClicked(MouseEvent e) {

    log.info(String.valueOf(e.getButton()));

    board.clearBackgroundColor();

    if (board.selectedUnit == null) {
      selectUnit();
    } else {
    	if(board.getAction().equals(UnitAction.MOVE)){
				moveUnit(board.selectedUnit);
			}else if(board.getAction().equals(UnitAction.ATTACK)){
				if (board.selectedUnit.getRange() == 0) {
					attackUnit(board.selectedUnit);
				} else {
					shootUnit((IRange) board.selectedUnit);
				}
			}
    }
  }

  public void mousePressed(MouseEvent e) {

  }
	
  public void mouseReleased(MouseEvent e) {

  }

  public void mouseEntered(MouseEvent e) {

  }

  public void mouseExited(MouseEvent e) {

  }

  public void setAndPaintUnit(Unit unit) {
    add(unit);
    setUnit(unit);
    repaint();
  }

  public boolean checkIfHasUnit() {
    if (unit == null) {
      return false;
    } else {
      return true;
    }
  }

  public boolean checkIfUnitofPlayer(Unit unit) {
    if (board.getGame().getAcitvePlayer().getId() == unit.getPlayer().getId()) {
      return true;
    }
    log.info("Diese Einheit gehört " + unit.getPlayer().getName());
    return false;

  }

  public void removeUnit() {
    log.info("Unit was removed from field: " + getName());
    removeAll();
    setUnit(null);
    repaint();
  }

  public void selectUnit() {
    if (checkIfHasUnit()) {
      if (!checkIfUnitofPlayer(unit)) {
        Game.setText("Nicht deine Figure diese Figur gehört " + unit.getPlayer().getName());
        return;
      }
      if (unit.isHasMoved()) {
        Game.setText("Unit already moved");
        return;
      }
      Game.setText("Folgende Figur wurde ausgewählt: " + unit.getName());
      board.checkIfRangerAttackIsAvailableFields(board.getField(posY, posX), unit);
      board.checkAvailableFields(board.getField(posY, posX), unit);
      unit.setGameField(board.getField(posY, posX));
      unit.select();
      unit.openActionMenu();
      board.setSelectedUnit(unit);
    } else {
      Game.setText("Keine Figur auf diesem Feld!");
    }
  }

  public void moveUnit(Unit unit) {
    if (unit.move(this)) {
      board.setSelectedUnit(null);
      board.getGame().changeActivePlayer();
    } else {
      Game.setText("Dieser Zug ist nicht erlaubt!");
    }
  }

  public void attackUnit(Unit unit) {
  	Callback callback = new Callback() {
			@Override
			public Object call(Object param) {
				boolean result = (Boolean) param;
				if (result) {
					board.setSelectedUnit(null);
					board.getGame().changeActivePlayer();
				} else {
					Game.setText("Dieser Zug ist nicht erlaubt!");
				}
				return null;
			}
		};
		unit.attack(this, callback);

  }

  public void shootUnit(IRange unit) {
  	Callback callback = new Callback() {
			@Override
			public Object call(Object param) {
				boolean result = (Boolean) param;
				if (result) {
					board.setSelectedUnit(null);
					removeUnit();
				}
				board.getGame().changeActivePlayer();
				return null;
			}
		};
		unit.shoot(getUnit() , callback);
  }

  public DataFlavor[] getTransferDataFlavors() {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean isDataFlavorSupported(DataFlavor flavor) {
    // TODO Auto-generated method stub
    return false;
  }

  public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
    // TODO Auto-generated method stub
    return null;
  }
	
	@Override
  public String toString() {
    return "GameField [posY=" + posY + ", posX=" + posX + ", unit=" + unit + ", board=" + board + ", toString()=" + super.toString() + "]";
  }

}
