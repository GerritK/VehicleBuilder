package net.gerritk.vehiclebuilder.views;

import java.awt.Dimension;
import java.awt.Point;

import net.gerritk.vehiclebuilder.controllers.Controller;

import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeListener;

public class VehicleOutputView extends View {
	private static final long serialVersionUID = 5415431084556862556L;
	
	private JSlider slColors[];

	public VehicleOutputView(Controller controller) {
		super(controller);
		setBorder(new EtchedBorder());
		
		slColors = new JSlider[3];
		for(int i = 0; i < slColors.length; i++) {
			slColors[i] = new JSlider();
			slColors[i].setPreferredSize(new Dimension(100, 25));
			slColors[i].setMinimum(0);
			slColors[i].setMaximum(255);
			slColors[i].addChangeListener((ChangeListener) controller);
		}
		slColors[0].setName("red");
		slColors[1].setName("green");
		slColors[2].setName("blue");
	}
	
	public void showPopupMenu(Point p) {
		JPopupMenu popupMenu = new JPopupMenu();
		
		for(JSlider slider : slColors) {
			popupMenu.add(slider);
		}
		
		popupMenu.show(this, p.x, p.y);
	}
	
	/*
	 * Getter & Setter
	 */
	public JSlider[] getSliders() {
		return slColors;
	}
}
