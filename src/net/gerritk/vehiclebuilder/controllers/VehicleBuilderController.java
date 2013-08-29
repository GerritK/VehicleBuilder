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
		if(o == vehicleModel) {
			builderView.getTxtName().setText(vehicleModel.getStructure().getName());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "export":
				vehicleModel.setName(builderView.getTxtName().getText());
				
				if(vehicleModel.export()) {
					System.out.println("export true");
				} else {
					System.out.println("export false");
				}
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
