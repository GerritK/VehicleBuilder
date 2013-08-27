package net.gerritk.vehiclebuilder.views;

import javax.swing.JPanel;

import net.gerritk.vehiclebuilder.controllers.Controller;

public abstract class View extends JPanel {
	private static final long serialVersionUID = -6664316218757126085L;

	public View(Controller controller) {
		super();
	}
}
