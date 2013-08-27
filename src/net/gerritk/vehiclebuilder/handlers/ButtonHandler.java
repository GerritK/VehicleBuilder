package net.gerritk.vehiclebuilder.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import net.gerritk.vehiclebuilder.VehicleBuilder;
import net.gerritk.vehiclebuilder.items.Child;
import net.gerritk.vehiclebuilder.ui.ChildPanel;

public class ButtonHandler implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		VehicleBuilder builder = VehicleBuilder.getInstance();
		
		if(e.getActionCommand().equals("addChild")) {
			builder.addSelectedChild((Child) builder.getChildSelectorItem().clone());
		} else if(e.getActionCommand().equals("clearChilds")) {
			builder.getSelectedChilds().clear();
			builder.onChildsChange();
		} else if(e.getActionCommand().equals("removeChild")) {
			builder.removeSelectedChild(builder.getChildPanel().getSelectedChild());
		} else if(e.getActionCommand().equals("save")) {
			File dir = new File("output");
			builder.getChildPanel().getList().clearSelection();
			
			if(!dir.exists()) {
				dir.mkdir();
			}
			
			boolean tmp = builder.isBluelight();
			builder.setBluelight(false);
			File off = new File(dir.getPath() + "/" + builder.getVehicleName() + ".png");
			try {
				ImageIO.write(builder.getOutput(), "png", off);
			} catch(IOException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(builder, "Der Speichervorgang ist fehlgeschlagen. Versuchen Sie es erneut.",
						"Speichern fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			builder.setBluelight(true);
			File on = new File(dir.getPath() + "/" + builder.getVehicleName() + "_on.png");
			try {
				ImageIO.write(builder.getOutput(), "png", on);
			} catch(IOException ex) {
				ex.printStackTrace();
				off.delete();
				JOptionPane.showMessageDialog(builder, "Der Speichervorgang ist fehlgeschlagen. Versuchen Sie es erneut.",
						"Speichern fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			builder.setBluelight(tmp);
			
			JOptionPane.showMessageDialog(builder, "Der Speichervorgang war erfolgreich.", "Speichern erfolgreich", JOptionPane.INFORMATION_MESSAGE);
		} else if(e.getActionCommand().equals("childUp")) {
			ChildPanel cp = builder.getChildPanel();
			int sindex = cp.getList().getSelectedIndex();
			
			if(sindex > 0) {
				Child tmp = cp.getModel().get(sindex - 1);
				
				cp.getModel().setElementAt(cp.getSelectedChild(), sindex - 1);
				cp.getModel().setElementAt(tmp, sindex);
				cp.getList().setSelectedIndex(sindex - 1);

				builder.getSelectedChilds().set(sindex - 1, cp.getSelectedChild());
				builder.getSelectedChilds().set(sindex, tmp);
			}
		} else if(e.getActionCommand().equals("childDown")) {
			ChildPanel cp = builder.getChildPanel();
			int sindex = cp.getList().getSelectedIndex();
			
			if(cp.getModel().getSize() > sindex + 1) {
				Child tmp = cp.getModel().get(sindex + 1);
			
				cp.getModel().setElementAt(cp.getSelectedChild(), sindex + 1);
				cp.getModel().setElementAt(tmp, sindex);
				cp.getList().setSelectedIndex(sindex + 1);
				
				builder.getSelectedChilds().set(sindex + 1, cp.getSelectedChild());
				builder.getSelectedChilds().set(sindex, tmp);
			}
		} else if(e.getActionCommand().equals("childBehind")) {
			Child c = builder.getChildPanel().getSelectedChild();
			c.setBehind(!c.isBehind());
			builder.onChildsChange();
		} else if(e.getActionCommand().equals("toggleBluelight")) {
			builder.setBluelight(!builder.isBluelight());
		}
	}

}
