package at.kexxs.game.board;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import at.kexxs.game.Game;
import at.kexxs.game.unit.Unit;

public class GameField extends JPanel implements MouseListener {

  /**
   * @author Markus
   */
  private static final long serialVersionUID = 1L;
  private int posY;
  private int posX;
  private Unit unit;
  private Color background;
  private final Board board;

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
      background = bg;
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
    setBackground(background);

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
    return false;
  }

  public void removeUnit() {
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
      Game.setText("Folgende Figur wurde ausgewählt: " + unit.getName());
      setBackground(Color.BLUE);
      board.checkAvailableFields(board.getField(posY, posX), unit);
      unit.setGameField(board.getField(posY, posX));
      board.setSelectedUnit(unit);
    } else {
      Game.setText("Keine Figuar auf diesem Feld!");
    }
  }

  public void moveUnit(Unit unit) {
    if (unit.move(this)) {
      board.setSelectedUnit(null);
      board.nextPlayer();
    } else {
      Game.setText("Dieser Zug ist nicht erlaubt!");
    }
  }

}
