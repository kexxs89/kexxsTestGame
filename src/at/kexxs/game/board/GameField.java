package at.kexxs.game.board;

import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.JPanel;

import at.kexxs.game.Game;
import at.kexxs.game.unit.impl.Unit;

public class GameField extends JPanel implements MouseListener, Transferable {

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

  @Override
  public void mouseClicked(MouseEvent e) {
    board.clearBackgroundColor();

    if (board.selectedUnit == null) {
      selectUnit();
    } else {
      moveUnit(board.selectedUnit);
    }

  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
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
        Game.setText("Nicht deine Figure");
        return;
      }
      if (unit.isHasMoved()) {
        Game.setText("Unit already moved");
        return;
      }
      Game.setText("Folgende Figur wurde ausgewählt: " + unit.getName());
      board.checkAvailableFields(board.getField(posY, posX), unit);
      unit.setGameField(board.getField(posY, posX));
      unit.select();
      board.setSelectedUnit(unit);
    } else {
      Game.setText("Keine Figur auf diesem Feld!");
    }
  }

  public void moveUnit(Unit unit) {
    if (unit.move(this)) {
      board.setSelectedUnit(null);
      if (!unit.getPlayer().checkIfUnitsCanMove()) {
        Game.setText("All Units have moved please end your turn!");
      }
    } else {
      Game.setText("Dieser Zug ist nicht erlaubt!");
    }
  }

  @Override
  public DataFlavor[] getTransferDataFlavors() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isDataFlavorSupported(DataFlavor flavor) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
    // TODO Auto-generated method stub
    return null;
  }

}
