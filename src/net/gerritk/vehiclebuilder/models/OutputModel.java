package net.gerritk.vehiclebuilder.models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.gerritk.vehiclebuilder.items.Child;

public class OutputModel extends Model {
	private Color background;
	private float scale;
	private Child selectedChild;
	private boolean bluelight;
	
	public OutputModel() {
		background = Color.LIGHT_GRAY;
		scale = 5.0f;
	}
	
	public BufferedImage scaleImage(BufferedImage img) {
		BufferedImage scaled = new BufferedImage((int) (img.getWidth() * scale), (int) (img.getHeight() * scale), BufferedImage.TRANSLUCENT);
		Graphics2D g = scaled.createGraphics();
		
		g.scale(scale, scale);
		g.drawImage(img, 0, 0, null);
		
		return scaled;
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

	public Child getSelectedChild() {
		return selectedChild;
	}

	public void setSelectedChild(Child selectedChild) {
		this.selectedChild = selectedChild;
		setChanged();
	}

	public boolean isBluelight() {
		return bluelight;
	}

	public void setBluelight(boolean bluelight) {
		this.bluelight = bluelight;
		setChanged();
	}
}
