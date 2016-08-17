package at.kexxs.game.unit;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import at.kexxs.game.Game;
import at.kexxs.game.board.GameField;
import at.kexxs.game.board.Player;
import at.kexxs.game.util.Dice;

/**
 * @author Markus
 */
public class Unit extends JLabel {

  private static final long serialVersionUID = 1L;
  private final int attack = 1;
  private final int defense = 1;
  private final int movement = 10;
  private long wounds = 1;
  private ImageIcon image;
  private String inactiveImagePath;
  private String activeImagePath;
  private String name = "unit";
  private GameField gameField;
  private final Player player;
  private boolean hasMoved = false;

  private static final Logger log = Logger.getLogger(Unit.class.getName());

  public Unit(Player player) {
    this.player = player;
    setName("Unit" + (getPlayer().getUnits().size() + 1));

    if (player.getId() == 1) {
      activeImagePath = "resources/red.png";
      inactiveImagePath = "resources/red_inactive.png";
    } else {
      activeImagePath = "resources/blue.png";
      inactiveImagePath = "resources/blue_inactive.png";
    }
    setShownImageIcon(activeImagePath);
    player.getUnits().add(this);

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

  public boolean isHasMoved() {
    return hasMoved;
  }

  public void setHasMoved(boolean hasMoved) {
    if (hasMoved) {
      setShownImageIcon(inactiveImagePath);
    } else {
      setShownImageIcon(activeImagePath);
    }

    this.hasMoved = hasMoved;
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

  private Unit attack(Unit enemy) {
    final int attackValue = getAttack() * Dice.roll();
    log.info("Attack Value is:" + attackValue);
    final int defenseValue = enemy.getDefense() * Dice.roll();
    log.info("Defense Value is:" + defenseValue);
    if (attackValue >= defenseValue) {
      enemy.getPlayer().getUnits().remove(enemy);
      return this;
    } else {
      getPlayer().getUnits().remove(this);
      return enemy;
    }
  }

  public void select() {
    String stats = new String();
    stats += "Name: " + name + "\n";
    stats += "Attack: " + attack + "\n";
    stats += "Defense: " + defense + "\n";
    stats += "Movement: " + movement + "\n";
    stats += "Wounds: " + wounds + "\n";
    Game.setSideText(stats);

  }

  private void setShownImageIcon(String imgPath) {
    BufferedImage bufferdImage = null;
    Image scaledImage = null;
    try {
      bufferdImage = ImageIO.read(new File(imgPath));
      scaledImage = bufferdImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
    } catch (final IOException e) {
      e.printStackTrace();
    }

    setIcon(new ImageIcon(scaledImage));
    setHorizontalAlignment(JLabel.CENTER);
    setVerticalAlignment(JLabel.CENTER);
    repaint();
    setImage(image);
    validate();
  }

}
