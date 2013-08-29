package net.gerritk.vehiclebuilder.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionListener;

import net.gerritk.vehiclebuilder.controllers.Controller;
import net.gerritk.vehiclebuilder.resources.IconSet;

import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeListener;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JLabel;
import javax.swing.JButton;

public class VehicleOutputView extends View {
	private static final long serialVersionUID = 5415431084556862556L;
	
	private JSlider slColors[];
	private JLabel lblScale;
	private JLabel lblOutput;
	private JButton btnBluelight;

	public VehicleOutputView(Controller controller) {
		super(controller);
		setBorder(new EtchedBorder());
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		btnBluelight = new JButton(IconSet.BLUELIGHT);
		btnBluelight.setOpaque(false);
		btnBluelight.setActionCommand("bluelight");
		btnBluelight.addActionListener((ActionListener) controller);
		
		lblOutput = new JLabel();
		add(lblOutput, "1, 3, 5, 1, center, center");
		add(btnBluelight, "1, 5");
		
		lblScale = new JLabel();
		add(lblScale, "5, 1, default, top");
		
		slColors = new JSlider[3];
		for(int i = 0; i < slColors.length; i++) {
			slColors[i] = new JSlider();
			slColors[i].setPreferredSize(new Dimension(100, 25));
			slColors[i].setMinimum(0);
			slColors[i].setMaximum(255);
			slColors[i].addChangeListener((ChangeListener) controller);
		}
		slColors[0].setName("red");
		slColors[0].setBackground(Color.RED);
		slColors[1].setName("green");
		slColors[1].setBackground(Color.GREEN);
		slColors[2].setName("blue");
		slColors[2].setBackground(Color.BLUE);
		
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
	
	public JLabel getLblScale() {
		return lblScale;
	}
	
	public JLabel getLblOutput() {
		return lblOutput;
	}
	
	public JButton getBtnBluelight() {
		return btnBluelight;
	}
}
