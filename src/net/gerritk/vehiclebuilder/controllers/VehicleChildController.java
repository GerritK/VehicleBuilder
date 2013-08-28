package net.gerritk.vehiclebuilder.controllers;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Observable;

import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.gerritk.vehiclebuilder.items.Child;
import net.gerritk.vehiclebuilder.models.OutputModel;
import net.gerritk.vehiclebuilder.models.VehicleModel;
import net.gerritk.vehiclebuilder.views.VehicleChildView;

public class VehicleChildController extends Controller implements ActionListener, ListSelectionListener, MouseListener, MouseMotionListener {
	private VehicleModel vehicleModel;
	private OutputModel outputModel;
	private VehicleChildView childView;
	
	public VehicleChildController(VehicleModel vehicleModel, OutputModel outputModel) {
		this.vehicleModel = vehicleModel;
		this.outputModel = outputModel;
		this.childView = new VehicleChildView(this);
		
		this.vehicleModel.addObserver(this);
		this.outputModel.addObserver(this);
		
		this.childView.getList().addMouseListener(this);
		this.childView.getList().addMouseMotionListener(this);
		this.childView.getList().addListSelectionListener(this);
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
			if(model.contains(outputModel.getSelectedChild())) {
				childView.getList().setSelectedValue(outputModel.getSelectedChild(), true);
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
				outputModel.getSelectedChild().setBehind(!outputModel.getSelectedChild().isBehind());
				outputModel.notifyObservers(true);
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
		outputModel.setSelectedChild(childView.getList().getSelectedValue());
		outputModel.notifyObservers();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		selectionWorkaround(e.getPoint());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
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
		selectionWorkaround(e.getPoint());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		selectionWorkaround(e.getPoint());
	}
	
	public void selectionWorkaround(Point p) {
		int index = childView.getList().getSelectedIndex();
		if(index >= 0) {
			Rectangle r = childView.getList().getCellBounds(index, index);
			if(!r.contains(p)) {
				childView.getList().clearSelection();
			}
		}
	}
	
	/*
	 * Getter & Setter
	 */
	public VehicleChildView getVehicleChildView() {
		return childView;
	}
}
