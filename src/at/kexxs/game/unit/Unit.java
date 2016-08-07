package at.kexxs.game.unit;

import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import at.kexxs.game.board.GameField;
import at.kexxs.game.board.Player;

/**
 * @author Markus
 */
public class Unit extends JLabel {

  private static final long serialVersionUID = 1L;
  private final int attack = 1;
  private final int defense = 1;
  private final int movement = 2;
  private long wounds = 1;
  private ImageIcon image;
  private String name = "unit";
  private GameField gameField;
  private final Player player;

  private static final Logger log = Logger.getLogger(Unit.class.getName());

  public Unit(Player player) {
    this.player = player;
    if (player.getId() == 1) {
      image = new ImageIcon("resources/red.png");
    } else {
      image = new ImageIcon("resources/blue.png");
    }
    player.getUnits().add(this);

    setIcon(image);
    setHorizontalAlignment(JLabel.CENTER);
    repaint();
    setImage(image);
  }

  public ImageIcon getImage() {
    return image;
  }

  public void setImage(ImageIcon image) {
    this.image = image;
  }

  public int getAttack() {
    return attack;
  }

  public int getDefense() {
    return defense;
  }

  public int getMovement() {
    return movement;
  }

  public long getWounds() {
    return wounds;
  }

  public void setWounds(long wounds) {
    this.wounds = wounds;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  public GameField getGameField() {
    return gameField;
  }

  public void setGameField(GameField gameField) {
    this.gameField = gameField;
  }

  public Player getPlayer() {
    return player;
  }

  @Override
  public String toString() {
    return "Unit [attack=" + attack + ", defense=" + defense + ", movement=" + movement + ", wounds=" + wounds + ", toString()=" + super.toString() + "]";
  }

  public boolean checkIfMovementIsValid(GameField newField) {

    final int oldX = gameField.getPosY();
    final int oldY = gameField.getPosX();
    final int newX = newField.getPosY();
    final int newY = newField.getPosX();

    // check if unit is on this field
    if (newField.getUnit() != null) {
      return false;
    }

    if (oldX == newX) {
      final int difference = Math.abs(oldY - newY);
      if (difference <= movement) {
        return true;
      } else {
        return false;
      }
    }

    if (oldY == newY) {
      final int difference = Math.abs(oldX - newX);
      if (difference <= movement) {
        return true;
      } else {
        return false;
      }
    }

    return false;
  }

  public boolean move(GameField newGameField) {
    if (!checkIfMovementIsValid(newGameField)) {
      gameField.getBoard().setSelectedUnit(null);
      log.info(getName() + "ist nicht erlaubt sich auf das Feld zu bewegen " + newGameField.getName());
      return false;
    }
    gameField.getBoard().removeUnit(getGameField().getPosY(), getGameField().getPosX());
    gameField.getBoard().setUnit(this, newGameField.getPosY(), newGameField.getPosX());
    log.info(getName() + "befindet sich jetzt auf dem Feld " + newGameField.getName());
    return true;
  }

}
