package net.gerritk.vehiclebuilder.items;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import net.gerritk.vehiclebuilder.VehicleBuilder;

public class Child extends Item implements Cloneable {

	private int x;
	private int y;
	private boolean behind = true;
	private String customName;
	
	public Child(String name, BufferedImage img, int x, int y) {
		super(name, img);
		
		this.x = x;
		this.y = y;
	}

	@Override
	public Child clone() {
		return new Child(getName(), getImage(), getX(), getY());
	}
	
	/*
	 * Getter & Setter
	 */
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		VehicleBuilder.getInstance().onChildsChange();
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		VehicleBuilder.getInstance().onChildsChange();
	}

	public boolean isBehind() {
		return behind;
	}

	public void setBehind(boolean behind) {
		this.behind = behind;
	}
	/*
	 * STATIC
	 */
	public static Point getMaxPositions(ArrayList<Child> list) {
		Point p = new Point(0, 0);
		
		for(Child child : list) {
			if(child.getX() + child.getWidth() > p.x) {
				p.x = child.getX() + child.getWidth();
			}
			
			if(child.getY() + child.getHeight() > p.y) {
				p.y = child.getY() + child.getHeight();
			}
		}
		
		return p;
	}
	
	public static Point getMinPositions(ArrayList<Child> list) {
		Point p = new Point(0, 0);
		
		for(Child child : list) {
			if(child.getX() < p.x) {
				p.x = child.getX();
			}
			
			if(child.getY() < p.y) {
				p.y = child.getY();
			}
		}
		
		return p;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}
}
