package net.gerritk.vehiclebuilder.controllers;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.gerritk.vehiclebuilder.models.OutputModel;
import net.gerritk.vehiclebuilder.models.VehicleModel;
import net.gerritk.vehiclebuilder.views.VehicleOutputView;

public class VehicleOutputController extends Controller implements MouseListener, ChangeListener {
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
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof OutputModel) {
			outputView.setBackground(outputModel.getBackground());
			outputView.getSliders()[0].setValue(outputModel.getBackground().getRed());
			outputView.getSliders()[1].setValue(outputModel.getBackground().getGreen());
			outputView.getSliders()[2].setValue(outputModel.getBackground().getBlue());
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
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
	
	/*
	 * Getter & Setter
	 */
	public VehicleOutputView getVehicleOutputView() {
		return outputView;
	}
}
