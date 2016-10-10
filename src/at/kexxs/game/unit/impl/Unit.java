package at.kexxs.game.unit.impl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import at.kexxs.game.Game;
import at.kexxs.game.board.impl.GameField;
import at.kexxs.game.board.impl.Player;
import at.kexxs.game.unit.IUnit;
import at.kexxs.game.util.Dice;
import at.kexxs.game.util.ImageBuilder;

/**
 * @author Markus
 */
public class Unit extends JLabel implements IUnit {

  private static final long serialVersionUID = 1L;
  private int attack = 1;
  private int defense = 1;
  private int movement = 2;
  private int wounds = 1;
  private ImageIcon image;
  private String name = "unit";
  private GameField gameField;
  private final Player player;
  private boolean hasMoved = false;

  public static final String UNIT_RED = "resources/unit_red.png";
  public static final String UNIT_BLUE = "resources/unit_blue.png";

  private static final Logger log = Logger.getLogger(Unit.class.getName());

  public Unit(Player player, String imagePath) {
    this.player = player;
    setName("Unit" + (getPlayer().getUnits().size() + 1));
    final BufferedImage bImage = ImageBuilder.getBufferdImageFromPath(imagePath);
    setShownImageIcon(ImageBuilder.scaleBufferdImage(bImage, 50, 50));
    player.getUnits().add(this);

  }

  @Override
  public ImageIcon getImage() {
    return image;
  }

  public void setImage(ImageIcon image) {
    this.image = image;
  }

  @Override
  public int getAttack() {
    return attack;
  }

  @Override
  public int getDefense() {
    return defense;
  }

  @Override
  public int getMovement() {
    return movement;
  }

  public int getWounds() {
    return wounds;
  }

  public void setWounds(int wounds) {
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

  @Override
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
  public boolean isHasMoved() {
    return hasMoved;
  }

  public void setHasMoved(boolean hasMoved) {
    this.hasMoved = hasMoved;
  }

  public void setAttack(int attack) {
    this.attack = attack;
  }

  public void setDefense(int defense) {
    this.defense = defense;
  }

  public void setMovement(int movement) {
    this.movement = movement;
  }

  @Override
  public String toString() {
    return "Unit [attack=" + attack + ", defense=" + defense + ", movement=" + movement + ", wounds=" + wounds + ", toString()=" + super.toString() + "]";
  }

  @Override
  public boolean checkIfMovementIsValid(GameField newField) {

    final int oldY = gameField.getPosY();
    final int oldX = gameField.getPosX();
    final int newY = newField.getPosY();
    final int newX = newField.getPosX();

    // check if own unit is on this field
    if (newField.getUnit() != null) {
      return false;
    }

    if (oldX == newX) {
      final int difference = Math.abs(oldY - newY);
      if (difference <= movement) {
        if (checkIfUnitIsInTheWayX(oldY, newY, newX)) {
          return false;
        }
        return true;
      } else {
        return false;
      }
    }

    if (oldY == newY) {
      final int difference = Math.abs(oldX - newX);
      if (difference <= movement) {
        if (checkIfUnitIsInTheWayY(oldX, newX, newY)) {
          return false;
        }
        return true;
      } else {
        return false;
      }
    }

    return false;
  }

  @Override
  public boolean move(GameField newGameField) {
    if (!checkIfMovementIsValid(newGameField)) {
      if (checkIfAttackIsPossible(newGameField)) {
        final Unit victoriusUnit = attack(newGameField.getUnit());
        gameField.getBoard().removeUnit(getGameField().getPosY(), getGameField().getPosX());
        gameField.getBoard().removeUnit(newGameField.getPosY(), newGameField.getPosX());
        gameField.getBoard().setUnit(victoriusUnit, newGameField.getPosY(), newGameField.getPosX());
        setHasMoved(true);
        log.info(victoriusUnit.getName() + "hat den Kampf gewonnen und befindet sich jetzt auf dem Feld " + newGameField.getName());
        return true;
      }

      gameField.getBoard().setSelectedUnit(null);
      log.info(getName() + "ist nicht erlaubt sich auf das Feld zu bewegen " + newGameField.getName());
      return false;
    }
    gameField.getBoard().removeUnit(getGameField().getPosY(), getGameField().getPosX());
    gameField.getBoard().setUnit(this, newGameField.getPosY(), newGameField.getPosX());
    log.info(getName() + " befindet sich jetzt auf dem Feld " + newGameField.getName());
    setHasMoved(true);
    return true;
  }

  private boolean checkIfAttackIsPossible(GameField newGameField) {
    if (newGameField.getUnit() != null && newGameField.getUnit().getPlayer().getId() != getPlayer().getId()) {
      return true;
    }
    return false;

  }

  @Override
  public Unit attack(Unit enemy) {
    final int attackValue = getAttack() * Dice.roll();
    log.info("Attack Value is:" + attackValue);
    final int defenseValue = enemy.getDefense() * Dice.roll();
    log.info("Defense Value is:" + defenseValue);
    String battleInfo = new String();
    battleInfo += "Attacks with: " + attackValue + "\n";
    battleInfo += "Defense with: " + defenseValue + "\n";
    Game.setSideText(battleInfo);
    if (attackValue >= defenseValue) {
      enemy.getPlayer().getUnits().remove(enemy);
      return this;
    } else {
      getPlayer().getUnits().remove(this);
      return enemy;
    }
  }

  @Override
  public void select() {
    String stats = new String();
    stats += "Name: " + name + "\n";
    stats += "Attack: " + attack + "\n";
    stats += "Defense: " + defense + "\n";
    stats += "Movement: " + movement + "\n";
    stats += "Wounds: " + wounds + "\n";
    Game.setSideText(stats);

  }

  private void setShownImageIcon(Image scaledImage) {
    setIcon(new ImageIcon(scaledImage));
    setHorizontalAlignment(JLabel.CENTER);
    setVerticalAlignment(JLabel.CENTER);
    repaint();
    validate();
  }

  private boolean checkIfUnitIsInTheWayX(int row1, int row2, int column) {
    int start;
    int end;
    if (row1 < row2) {
      start = row1;
      end = row2;
    } else {
      start = row2;
      end = row1 - 1;
    }
    start++;
    for (int index = start; index <= end; index++) {
      if (gameField.getBoard().checkIfUnitisOnField(index, column)) {
        log.info(gameField.getBoard().getField(index, column).getUnit().getName() + " steht im Weg!");

        return true;
      }
    }
    return false;
  }

  private boolean checkIfUnitIsInTheWayY(int column1, int column2, int row) {
    int start;
    int end;
    if (column1 < column2) {
      start = column1 + 1;
      end = column2;
    } else {
      start = column2;
      end = column1 - 1;
    }
    for (int index = start; index <= end; index++) {
      if (gameField.getBoard().checkIfUnitisOnField(row, index)) {
        log.info(gameField.getBoard().getField(row, index).getUnit().getName() + " steht im Weg!");
        return true;
      }
    }
    return false;
  }

}
