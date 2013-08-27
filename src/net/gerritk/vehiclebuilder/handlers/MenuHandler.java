package net.gerritk.vehiclebuilder.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import net.gerritk.vehiclebuilder.VehicleBuilder;

public class MenuHandler implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		VehicleBuilder builder = VehicleBuilder.getInstance();
		
		if(e.getActionCommand().equals("about")) {
			JOptionPane.showMessageDialog(builder, "Vehicle Builder Version 0.2\n(c) 2013 - K.Design - Gerrit Kaul", "Über", JOptionPane.INFORMATION_MESSAGE);
		} else if(e.getActionCommand().equals("quit")) {
			builder.quit();
		} else if(e.getActionCommand().equals("")) {
			
		}
	}
}
