package net.gerritk.vehiclebuilder.items;

import java.awt.image.BufferedImage;

public class Bluelight extends Child {
	private BufferedImage on;

	public Bluelight(String name, BufferedImage img, BufferedImage on, int x, int y) {
		super(name, img, x, y);
		this.on = on;
	}

	@Override
	@SuppressWarnings("CloneDoesntCallSuperClone")
	public Bluelight clone() {
		return new Bluelight(getName(), getImage(), getOn(), getX(), getY());
	}

	/*
	 * Getter & Setter
	 */
	public BufferedImage getOn() {
		return on;
	}
}
