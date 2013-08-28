package net.gerritk.vehiclebuilder.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.gerritk.vehiclebuilder.items.Child;
import net.gerritk.vehiclebuilder.models.VehicleModel;
import net.gerritk.vehiclebuilder.views.VehicleChildView;

public class VehicleChildController extends Controller implements ActionListener, ListSelectionListener {
	private VehicleModel vehicleModel;
	private VehicleChildView childView;
	
	public VehicleChildController(VehicleModel vehicleModel) {
		this.vehicleModel = vehicleModel;
		this.childView = new VehicleChildView(this);
		
		this.vehicleModel.addObserver(this);
		
		this.childView.getList().setModel(new DefaultListModel<Child>());
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof VehicleModel) {
			DefaultListModel<Child> model = (DefaultListModel<Child>) childView.getList().getModel();
			model.clear();
			for(Child c : vehicleModel.getChilds()) {
				model.addElement(c);
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "up":
				break;
			case "down":
				break;
			case "drawOrder":
				childView.getList().getSelectedValue().setBehind(!childView.getList().getSelectedValue().isBehind());
				childView.repaint();
				break;
			case "delete":
				vehicleModel.getChilds().remove(childView.getList().getSelectedValue());
				vehicleModel.notifyObservers(true);
				break;
			case "clear":
				vehicleModel.getChilds().clear();
				vehicleModel.notifyObservers(true);
				break;
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		
	}
	
	/*
	 * Getter & Setter
	 */
	public VehicleChildView getVehicleChildView() {
		return childView;
	}
}
