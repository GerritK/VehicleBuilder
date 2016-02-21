package net.gerritk.vehiclebuilder.ui.dialogs;

import javax.swing.*;
import java.awt.*;

public abstract class BuilderDialog extends JDialog {
	private static final long serialVersionUID = 1059329209445145521L;

	public BuilderDialog(Frame frame, String title, boolean modal) {
		super(frame, title, modal);
	}

	public BuilderDialog(Frame frame, String title) {
		this(frame, title, false);
	}

	public void setToScreenCenter() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point wp = new Point((int) (screenSize.getWidth() - (double) this.getWidth()) / 2, (int) (screenSize.getHeight() - (double) this.getHeight()) / 2);
		this.setLocation(wp);
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
