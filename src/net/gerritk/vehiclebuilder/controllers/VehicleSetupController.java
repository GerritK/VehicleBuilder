package net.gerritk.vehiclebuilder.controllers;

import net.gerritk.vehiclebuilder.items.*;
import net.gerritk.vehiclebuilder.models.*;
import net.gerritk.vehiclebuilder.views.VehicleSetupView;

import javax.swing.*;
import java.awt.event.*;
import java.util.Observable;

public class VehicleSetupController extends Controller implements ActionListener, ItemListener, MouseListener {
	private CabinModel cabinModel;
	private StructureModel structureModel;
	private TemplateModel templateModel;
	private ChildModel childModel;
	private VehicleModel vehicleModel;
	private OutputModel outputModel;
	private VehicleSetupOffsetController offsetController;
	private VehicleSetupView setupView;

	public VehicleSetupController(CabinModel cabinModel, StructureModel structureModel, TemplateModel templateModel, ChildModel childModel, VehicleModel vehicleModel, OutputModel outputModel) {
		this.cabinModel = cabinModel;
		this.structureModel = structureModel;
		this.templateModel = templateModel;
		this.childModel = childModel;
		this.vehicleModel = vehicleModel;
		this.outputModel = outputModel;
		this.offsetController = new VehicleSetupOffsetController(vehicleModel);
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
            getLogger().log("VehicleSetupController", "Updating from cabin model...");
            setupView.getSelectorCabin().removeAllItems();
			for(Cabin c : ((CabinModel) o).getCabins()) {
				setupView.getSelectorCabin().addItem(c);
			}
		} else if(o instanceof StructureModel) {
            getLogger().log("VehicleSetupController", "Updating from structure model...");
            setupView.getSelectorStructure().removeAllItems();
			for(Structure s : ((StructureModel) o).getStructures()) {
				setupView.getSelectorStructure().addItem(s);
			}
		} else if(o instanceof TemplateModel) {
            getLogger().log("VehicleSetupController", "Updating from template model...");
            setupView.getSelectorTemplate().removeAllItems();
			for(Template t : ((TemplateModel) o).getTemplates()) {
				setupView.getSelectorTemplate().addItem(t);
			}
		} else if(o instanceof ChildModel) {
            getLogger().log("VehicleSetupController", "Updating from child model...");
            setupView.getSelectorChild().removeAllItems();
			for(Child c : ((ChildModel) o).getChilds()) {
				setupView.getSelectorChild().addItem(c);
			}
		} else if(o instanceof VehicleModel) {
            getLogger().log("VehicleSetupController", "Updating from vehicle model...");
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
                getLogger().log("VehicleSetupController", "Adding child to vehicle...");
                Child sc = ((Child) setupView.getSelectorChild().getSelectedItem()).clone();
				if(sc != null) {
					vehicleModel.getChilds().add(sc);
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
                getLogger().log("VehicleSetupController", "Changed cabin selection to '" + c + "'.");
                vehicleModel.setCabin(c);
				vehicleModel.notifyObservers();
			} else if(i instanceof Structure) {
				Structure s = (Structure) i;
                getLogger().log("VehicleSetupController", "Changed structure selection to '" + s + "'.");
                vehicleModel.setStructure(s);
				vehicleModel.notifyObservers();
			} else if(i instanceof Template) {
				Template t = (Template) i;
                getLogger().log("VehicleSetupController", "Changed template selection to '" + t + "'.");
                vehicleModel.setTemplate(t);
				vehicleModel.notifyObservers();
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		showPopup(e);
	}

	public void mouseReleased(MouseEvent e) {
		showPopup(e);
	}

	@SuppressWarnings("unchecked")
	private void showPopup(MouseEvent e) {
		if (e.isPopupTrigger() && e.getSource() instanceof JComboBox) {
			JComboBox source = (JComboBox) e.getSource();
			if (source == setupView.getSelectorCabin()) {
                getLogger().log("VehicleSetupController", "Showing popup menu for cabin...");
                setupView.showPopup(source, offsetController.getVehicleSetupOffsetView(0));
			} else if (source == setupView.getSelectorStructure()) {
                getLogger().log("VehicleSetupController", "Showing popup menu for structure...");
                setupView.showPopup(source, offsetController.getVehicleSetupOffsetView(1));
			}
		}
	}

	public VehicleSetupView getVehicleSetupView() {
		return setupView;
	}
}
