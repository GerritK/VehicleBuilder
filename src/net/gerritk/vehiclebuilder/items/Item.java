package net.gerritk.vehiclebuilder.items;

import java.awt.image.BufferedImage;

public abstract  class Item implements Comparable<Item> {
	private BufferedImage img;
	private String name;
	
	public Item(String name, BufferedImage img) {
		this.name = name;
		this.img = img;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public int compareTo(Item item) {
		return name.compareTo(item.getName());
	}
	
	/*
	 * Getter & Setter
	 */
	public BufferedImage getImage() {
		return img;
	}
	
	public String getName() {
		return name;
	}
	
	public int getWidth() {
		return img.getWidth();
	}
	
	public int getHeight() {
		return img.getHeight();
	}
}
