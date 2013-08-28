package net.gerritk.vehiclebuilder.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Observable;

import net.gerritk.vehiclebuilder.items.*;
import net.gerritk.vehiclebuilder.models.*;
import net.gerritk.vehiclebuilder.views.VehicleSetupView;

public class VehicleSetupController extends Controller implements ActionListener, ItemListener {
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
		
		this.setupView = new VehicleSetupView(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof CabinModel) {
			setupView.getSelectorCabin().removeAllItems();
			for(Cabin c : ((CabinModel) o).getCabins()) {
				setupView.getSelectorCabin().addItem(c);
			}
		} else if(o instanceof StructureModel) {
			setupView.getSelectorStructure().removeAllItems();
			for(Structure s : ((StructureModel) o).getStructures()) {
				setupView.getSelectorStructure().addItem(s);
			}
		} else if(o instanceof TemplateModel) {
			setupView.getSelectorTemplate().removeAllItems();
			for(Template t : ((TemplateModel) o).getTemplates()) {
				setupView.getSelectorTemplate().addItem(t);
			}
		} else if(o instanceof ChildModel) {
			setupView.getSelectorChild().removeAllItems();
			for(Child c : ((ChildModel) o).getChilds()) {
				setupView.getSelectorChild().addItem(c);
			}
		} else if(o instanceof VehicleModel) {
			VehicleModel vm = (VehicleModel) o;
			setupView.getSelectorCabin().setSelectedItem(vm.getCabin());
			setupView.getSelectorStructure().setSelectedItem(vm.getStructure());
			setupView.getSelectorTemplate().setSelectedItem(vm.getTemplate());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "addChild":
				vehicleModel.getChilds().add(((Child) setupView.getSelectorChild().getSelectedItem()).clone());
				vehicleModel.notifyObservers(true);
				break;
			case "export":
				vehicleModel.export();
				break;
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
			Item i = (Item) e.getItem();
			
			if(i instanceof Cabin) {
				Cabin c = (Cabin) i;
				vehicleModel.setCabin(c);
				vehicleModel.notifyObservers();
			} else if(i instanceof Structure) {
				Structure s = (Structure) i;
				vehicleModel.setStructure(s);
				vehicleModel.notifyObservers();
			} else if(i instanceof Template) {
				Template t = (Template) i;
				vehicleModel.setTemplate(t);
				vehicleModel.notifyObservers();
			}
		}
	}

	/*
	 * Getter & Setter
	 */
	public VehicleSetupView getVehicleSetupView() {
		return setupView;
	}
}
