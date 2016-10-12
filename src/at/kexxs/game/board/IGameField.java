package at.kexxs.game.board;

import java.awt.datatransfer.Transferable;
import java.awt.event.MouseListener;

import at.kexxs.game.unit.IRange;
import at.kexxs.game.unit.impl.Unit;

public interface IGameField extends MouseListener, Transferable {

  void setAndPaintUnit(Unit unit);

  void moveUnit(Unit unit);

  void removeUnit();

  boolean checkIfUnitofPlayer(Unit unit);

  boolean checkIfHasUnit();

  void selectUnit();

  void attackUnit(Unit unit);

  void shootUnit(IRange unit);

}
