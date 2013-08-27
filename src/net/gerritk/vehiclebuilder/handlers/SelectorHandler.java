package net.gerritk.vehiclebuilder.handlers;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import net.gerritk.vehiclebuilder.VehicleBuilder;
import net.gerritk.vehiclebuilder.items.*;

public class SelectorHandler implements ItemListener {
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
			Object i = e.getItem();
			VehicleBuilder builder = VehicleBuilder.getInstance();
			
			if(i instanceof Cabin) {
				Cabin c = (Cabin) i;
				
				builder.setSelectedCabin(c);
			} else if(i instanceof Structure) {
				Structure s = (Structure) i;
				
				builder.setSelectedStructure(s);
			} else if(i instanceof Template) {
				Template t = (Template) i;
				
				builder.setSelectedTemplate(t);
			}
		}
	}
}
