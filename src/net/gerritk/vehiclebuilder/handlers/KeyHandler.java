package net.gerritk.vehiclebuilder.handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import net.gerritk.vehiclebuilder.VehicleBuilder;
import net.gerritk.vehiclebuilder.items.Child;

public class KeyHandler implements KeyListener {

	@Override
	public void keyPressed(KeyEvent e) {
		VehicleBuilder builder = VehicleBuilder.getInstance();
		
		if(e.getKeyCode() == KeyEvent.VK_PLUS) {
			builder.setScale(builder.getScale() + 0.1f);
			builder.repaint();
		} else if(e.getKeyCode() == KeyEvent.VK_MINUS) {
			builder.setScale(builder.getScale() - 0.1f);
			builder.repaint();
		} else if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			Child c = builder.getChildPanel().getSelectedChild();
			if(c != null) {
				c.setY(c.getY() - 1);
			}
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			Child c = builder.getChildPanel().getSelectedChild();
			if(c != null) {
				c.setY(c.getY() + 1);
			}
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			Child c = builder.getChildPanel().getSelectedChild();
			if(c != null) {
				c.setX(c.getX() - 1);
			}
		} else if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			Child c = builder.getChildPanel().getSelectedChild();
			if(c != null) {
				c.setX(c.getX() + 1);
			}
		} else if(e.getKeyCode() == KeyEvent.VK_B) {
			builder.setBluelight(!builder.isBluelight());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
