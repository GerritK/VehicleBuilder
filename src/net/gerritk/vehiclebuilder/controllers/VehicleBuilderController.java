package net.gerritk.vehiclebuilder.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import net.gerritk.vehiclebuilder.models.*;
import net.gerritk.vehiclebuilder.views.*;

public class VehicleBuilderController extends Controller implements ActionListener {
	private VehicleModel vehicleModel;
	private VehicleBuilderView builderView;
	
	public VehicleBuilderController(VehicleModel vehicleModel, VehicleSetupView setupView, VehicleChildView childView,
			VehicleOutputView outputView) {
		this.vehicleModel = vehicleModel;
		this.builderView = new VehicleBuilderView(this, setupView, childView, outputView);
		
		this.vehicleModel.addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "exportVehicle":
				vehicleModel.setName(builderView.getName());
				vehicleModel.export();
				break;
		}
	}
	
	/*
	 * Getter & Setter
	 */
	public VehicleBuilderView getVehicleBuilderView() {
		return builderView;
	}
}