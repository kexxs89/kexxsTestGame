package at.kexxs.game.unit;

import at.kexxs.game.unit.impl.Unit;
import javafx.util.Callback;

public interface IRange {
	
	void shoot(Unit enemy, Callback callback);
	
	
}
