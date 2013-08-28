package net.gerritk.vehiclebuilder.models;

import java.awt.Color;

public class OutputModel extends Model {
	private Color background;
	private float scale;
	
	public OutputModel() {
		background = new Color(255, 255, 255);
		scale = 1.0f;
	}
	
	/*
	 * Getter & Setter
	 */
	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
		setChanged();
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		if(scale < 1) scale = 1;
		if(scale > 10) scale = 10;
		this.scale = scale;
		setChanged();
	}
}
