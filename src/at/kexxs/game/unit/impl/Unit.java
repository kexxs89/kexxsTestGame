package at.kexxs.game.unit.impl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import at.kexxs.game.Game;
import at.kexxs.game.board.GameField;
import at.kexxs.game.board.Player;
import at.kexxs.game.unit.IUnit;
import at.kexxs.game.util.Dice;
import at.kexxs.game.util.ImageBuilder;

/**
 * @author Markus
 */
public class Unit extends JLabel implements IUnit {

  private static final long serialVersionUID = 1L;
  private final int attack = 1;
  private final int defense = 1;
  private final int movement = 10;
  private int wounds = 1;
  private ImageIcon image;
  private String name = "unit";
  private GameField gameField;
  private final Player player;
  private boolean hasMoved = false;

  private static final Logger log = Logger.getLogger(Unit.class.getName());

  public Unit(Player player, String imagePath) {
    this.player = player;
    setName("Unit" + (getPlayer().getUnits().size() + 1));
    BufferedImage bImage = ImageBuilder.getBufferdImageFromPath(imagePath);
    bImage = ImageBuilder.colorImage(bImage, player.getColor());
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

  @Override
  public String toString() {
    return "Unit [attack=" + attack + ", defense=" + defense + ", movement=" + movement + ", wounds=" + wounds + ", toString()=" + super.toString() + "]";
  }

  @Override
  public boolean checkIfMovementIsValid(GameField newField) {

    final int oldX = gameField.getPosY();
    final int oldY = gameField.getPosX();
    final int newX = newField.getPosY();
    final int newY = newField.getPosX();

    // check if own unit is on this field
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
    log.info(getName() + "befindet sich jetzt auf dem Feld " + newGameField.getName());
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

}
