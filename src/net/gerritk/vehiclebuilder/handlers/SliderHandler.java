package net.gerritk.vehiclebuilder.handlers;

import java.awt.Color;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.gerritk.vehiclebuilder.VehicleBuilder;

public class SliderHandler implements ChangeListener {
	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() instanceof JSlider) {
			JSlider slider = (JSlider) e.getSource();
			int value = slider.getValue();
			if(slider.getName().equals("color")) {
				VehicleBuilder.getInstance().getOutputPanel().setBackground(new Color(value, value, value));
			}
		}
	}
}
