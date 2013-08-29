package net.gerritk.vehiclebuilder.ui.dialogs;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;

import javax.swing.JDialog;

public abstract class BuilderDialog extends JDialog {
	private static final long serialVersionUID = 1059329209445145521L;

	public BuilderDialog(Frame frame, String title) {
		super(frame, title);
	}
	
	@Override
	public void setVisible(boolean visible) {
		if(visible) {
			Point loc = getOwner().getLocation();
			Dimension size = getOwner().getSize();
			this.setLocation(loc.x + size.width / 2 - getSize().width / 2, loc.y + size.height / 2 - getSize().height / 2);
		}
		
		super.setVisible(visible);
	}
}
