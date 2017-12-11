package at.kexxs.game.unit;

import javax.swing.ImageIcon;

import at.kexxs.game.board.impl.GameField;
import at.kexxs.game.unit.impl.Unit;
import javafx.util.Callback;

public interface IUnit {

  public boolean move(GameField newGameField);
	
  public void select();

  public ImageIcon getImage();

  public int getAttack();

  public int getDefense();

  public int getMovement();

  public String getName();

  public GameField getGameField();

  public boolean isHasMoved();

  public boolean checkIfMovementIsValid(GameField newField);
	
	void attack(GameField newGameField, Callback methodCallback);
	
	boolean isJump();

  void setJump(boolean jump);
	

}
