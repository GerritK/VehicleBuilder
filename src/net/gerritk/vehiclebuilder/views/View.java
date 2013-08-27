package net.gerritk.vehiclebuilder.views;

import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.util.Observer;

import javax.swing.JPanel;

public abstract class View extends JPanel implements Observer {
	private static final long serialVersionUID = -6664316218757126085L;

	public View() {
		this(new FlowLayout(), true);
	}

	public View(LayoutManager layout) {
		this(layout, true);
	}

	public View(boolean isDoubleBuffered) {
		this(new FlowLayout(), isDoubleBuffered);
	}

	public View(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}
}
