package net.gerritk.vehiclebuilder.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;

import javax.swing.JComboBox;

import net.gerritk.vehiclebuilder.items.*;
import net.gerritk.vehiclebuilder.models.*;
import net.gerritk.vehiclebuilder.views.VehicleSetupView;

public class VehicleSetupController extends Controller implements ActionListener, ItemListener, MouseListener {
	private CabinModel cabinModel;
	private StructureModel structureModel;
	private TemplateModel templateModel;
	private ChildModel childModel;
	private VehicleModel vehicleModel;
	private OutputModel outputModel;
	
	private VehicleSetupView setupView;
	
	public VehicleSetupController(CabinModel cabinModel, StructureModel structureModel, TemplateModel templateModel, ChildModel childModel,
			VehicleModel vehicleModel, OutputModel outputModel) {
		this.cabinModel = cabinModel;
		this.structureModel = structureModel;
		this.templateModel = templateModel;
		this.childModel = childModel;
		this.vehicleModel = vehicleModel;
		this.outputModel = outputModel;

		this.setupView = new VehicleSetupView(this);
		
		this.cabinModel.addObserver(this);
		this.structureModel.addObserver(this);
		this.templateModel.addObserver(this);
		this.childModel.addObserver(this);
		this.vehicleModel.addObserver(this);
		this.outputModel.addObserver(this);
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
				Child sc = ((Child) setupView.getSelectorChild().getSelectedItem()).clone();
				if(sc != null) {
					vehicleModel.getChilds().add(0, sc);;
					vehicleModel.notifyObservers(true);
					
					outputModel.setSelectedChild(sc);
					outputModel.notifyObservers();
				}
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
	public void mousePressed(MouseEvent e) {
		if(e.isPopupTrigger()) {
			setupView.showPopup(setupView.getSelectorCabin());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.isPopupTrigger()) {
			if(e.getSource() instanceof JComboBox<?>) {
				@SuppressWarnings("unchecked")
				JComboBox<? extends Item> source = (JComboBox<? extends Item>) e.getSource();
				setupView.showPopup(source);
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
