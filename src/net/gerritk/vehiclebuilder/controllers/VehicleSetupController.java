package net.gerritk.vehiclebuilder.controllers;

import java.util.Observable;

import net.gerritk.vehiclebuilder.items.*;
import net.gerritk.vehiclebuilder.models.*;
import net.gerritk.vehiclebuilder.views.VehicleSetupView;

public class VehicleSetupController extends Controller {
	private CabinModel cabinModel;
	private StructureModel structureModel;
	private TemplateModel templateModel;
	private ChildModel childModel;
	private VehicleModel vehicleModel;
	
	private VehicleSetupView setupView;
	
	public VehicleSetupController(CabinModel cabinModel, StructureModel structureModel, TemplateModel templateModel, ChildModel childModel,
			VehicleModel vehicleModel) {
		this.cabinModel = cabinModel;
		this.structureModel = structureModel;
		this.templateModel = templateModel;
		this.childModel = childModel;
		this.vehicleModel = vehicleModel;
		
		this.cabinModel.addObserver(this);
		this.structureModel.addObserver(this);
		this.templateModel.addObserver(this);
		this.childModel.addObserver(this);
		this.vehicleModel.addObserver(this);
		
		this.setupView = new VehicleSetupView();
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof CabinModel) {
			setupView.getSelectorCabin().removeAllItems();
			for(Cabin c : ((CabinModel) o).getCabins()) {
				setupView.getSelectorCabin().addItem(c);
			}
		}
	}

}
