package at.kexxs.game.dice;

public class DiceResultDTO {
	
	private long attackValue;
	private long defenseValue;
	private boolean success;
	
	public long getAttackValue() {
		return attackValue;
	}
	
	public void setAttackValue(long attackValue) {
		this.attackValue = attackValue;
	}
	
	public long getDefenseValue() {
		return defenseValue;
	}
	
	public void setDefenseValue(long defenseValue) {
		this.defenseValue = defenseValue;
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	@Override
	public String toString() {
		return "DiceResult{" +
				"attackValue=" + attackValue +
				", defenseValue=" + defenseValue +
				", success=" + success +
				'}';
	}
}
