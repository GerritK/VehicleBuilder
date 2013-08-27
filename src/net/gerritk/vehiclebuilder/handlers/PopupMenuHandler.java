package net.gerritk.vehiclebuilder.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import net.gerritk.vehiclebuilder.VehicleBuilder;

public class PopupMenuHandler implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		VehicleBuilder builder = VehicleBuilder.getInstance();
		
		if(e.getActionCommand().equals("setCustomName")) {
			String s = JOptionPane.showInputDialog(builder, "Wie soll der Zusatz heiﬂen?", "Namen ‰ndern", JOptionPane.PLAIN_MESSAGE,
					null, null, builder.getChildPanel().getSelectedChild().getCustomName()).toString();
			builder.getChildPanel().getSelectedChild().setCustomName(s);
		} else if(e.getActionCommand().equals("removeCustomName")) {
			builder.getChildPanel().getSelectedChild().setCustomName(null);
		}
	}

}
