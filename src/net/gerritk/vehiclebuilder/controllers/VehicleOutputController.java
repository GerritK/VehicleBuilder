package net.gerritk.vehiclebuilder.controllers;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.text.DecimalFormat;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.gerritk.vehiclebuilder.models.OutputModel;
import net.gerritk.vehiclebuilder.models.VehicleModel;
import net.gerritk.vehiclebuilder.resources.IconSet;
import net.gerritk.vehiclebuilder.views.VehicleOutputView;

public class VehicleOutputController extends Controller implements MouseListener, MouseWheelListener, ChangeListener, ActionListener, FocusListener {
	private VehicleModel vehicleModel;
	private OutputModel outputModel;
	private VehicleOutputView outputView;
	
	public VehicleOutputController(VehicleModel vehicleModel, OutputModel outputModel) {
		this.vehicleModel = vehicleModel;
		this.outputModel = outputModel;
		
		this.outputView = new VehicleOutputView(this);
		
		this.vehicleModel.addObserver(this);
		this.outputModel.addObserver(this);
		
		this.outputView.addMouseListener(this);
		this.outputView.addMouseWheelListener(this);
		this.outputView.addFocusListener(this);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o == outputModel) {
			outputView.setBackground(outputModel.getBackground());
			outputView.getSliders()[0].setValue(outputModel.getBackground().getRed());
			outputView.getSliders()[1].setValue(outputModel.getBackground().getGreen());
			outputView.getSliders()[2].setValue(outputModel.getBackground().getBlue());
			outputView.getLblScale().setText(new DecimalFormat("0.00").format(outputModel.getScale()) + " x");
			
			if(outputModel.isBluelight()) {
				outputView.getBtnBluelight().setIcon(IconSet.BLUELIGHT);
			} else {
				outputView.getBtnBluelight().setIcon(IconSet.BLUELIGHT.DISABLED);
			}
		}
		
		ImageIcon icon = new ImageIcon(outputModel.scaleImage(vehicleModel.generateOutput(outputModel.isBluelight())));
		outputView.getLblOutput().setIcon(icon);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		outputView.requestFocusInWindow();
		
		if(e.isPopupTrigger()) {
			outputView.showPopupMenu(e.getPoint());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.isPopupTrigger()) {
			outputView.showPopupMenu(e.getPoint());
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() instanceof JSlider) {
			JSlider slider = (JSlider) e.getSource();
			
			int r = outputModel.getBackground().getRed();
			int g = outputModel.getBackground().getGreen();
			int b = outputModel.getBackground().getBlue();
			
			switch(slider.getName()) {
				case "red":
					r = slider.getValue();
					break;
				case "green":
					g = slider.getValue();
					break;
				case "blue":
					b = slider.getValue();
					break;
			}
			
			outputModel.setBackground(new Color(r, g, b));
			outputModel.notifyObservers();
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		outputModel.setScale(outputModel.getScale() + (float) -e.getWheelRotation() / 5);
		outputModel.notifyObservers();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("bluelight")) {
			outputModel.setBluelight(!outputModel.isBluelight());
			outputModel.notifyObservers();
		}
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		outputView.setBorder(BorderFactory.createEtchedBorder(UIManager.getColor("List.selectionForeground"),
				UIManager.getColor("List.selectionBackground")));
	}

	@Override
	public void focusLost(FocusEvent e) {
		outputView.setBorder(BorderFactory.createEtchedBorder());
	}
	
	/*
	 * Getter & Setter
	 */
	public VehicleOutputView getVehicleOutputView() {
		return outputView;
	}
}
