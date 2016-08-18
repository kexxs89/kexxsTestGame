package at.kexxs.game.board;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import at.kexxs.game.unit.impl.Unit;

public class Player {

  int id;
  String name;
  boolean active;
  List<Unit> units;
  Color color;
  final Logger log = Logger.getLogger(Player.class.getName());

  public Player(int id, String name, Color color, boolean active) {
    this.id = id;
    this.name = name;
    this.active = active;
    this.color = color;
    units = new ArrayList<>();
    log.info("Neuer Spieler wurde erstellt: " + name);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public List<Unit> getUnits() {
    return units;
  }

  public void setUnits(List<Unit> units) {
    this.units = units;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public boolean checkIfUnitsCanMove() {
    for (final Unit unit : units) {
      if (!unit.isHasMoved()) {
        log.info(unit.getName() + " can still move!");
        return true;
      }
    }
    log.info("All Units have moved");
    return false;
  }

  public void resestAllUnits() {
    for (final Unit unit : units) {
      unit.setHasMoved(false);
    }
  }

}
