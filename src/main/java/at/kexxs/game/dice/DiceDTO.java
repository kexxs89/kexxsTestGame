package at.kexxs.game.dice;


import javax.swing.*;
import java.awt.*;

public class DiceDTO {
	
	private Image image;
	private long value = 1;
	private JLabel label = new JLabel();
	
	public Image getImage() {
		return image;
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
	
	public long getValue() {
		return value;
	}
	
	public void setValue(long value) {
		this.value = value;
	}
	
	public JLabel getLabel() {
		return label;
	}
	
	public void setLabel(JLabel label) {
		this.label = label;
	}
	
	@Override
	public String toString() {
		return "DiceDTO{" +
				"image=" + image +
				", value=" + value +
				", label=" + label +
				'}';
	}
}
