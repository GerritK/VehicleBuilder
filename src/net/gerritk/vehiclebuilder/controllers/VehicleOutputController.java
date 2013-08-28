package net.gerritk.vehiclebuilder.controllers;

import java.util.Observable;

import net.gerritk.vehiclebuilder.models.OutputModel;
import net.gerritk.vehiclebuilder.models.VehicleModel;
import net.gerritk.vehiclebuilder.views.VehicleOutputView;

public class VehicleOutputController extends Controller {
	private VehicleModel vehicleModel;
	private OutputModel outputModel;
	private VehicleOutputView outputView;
	
	public VehicleOutputController(VehicleModel vehicleModel, OutputModel outputModel) {
		this.vehicleModel = vehicleModel;
		this.outputModel = outputModel;
		
		this.outputView = new VehicleOutputView(this);
		
		this.vehicleModel.addObserver(this);
		this.outputModel.addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof OutputModel) {
			outputView.setBackground(outputModel.getBackground());
		}
	}

	/*
	 * Getter & Setter
	 */
	public VehicleOutputView getVehicleOutputView() {
		return outputView;
	}
}
