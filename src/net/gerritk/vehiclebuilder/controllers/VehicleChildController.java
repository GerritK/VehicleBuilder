package net.gerritk.vehiclebuilder.controllers;

import net.gerritk.vehiclebuilder.items.Child;
import net.gerritk.vehiclebuilder.models.OutputModel;
import net.gerritk.vehiclebuilder.models.VehicleModel;
import net.gerritk.vehiclebuilder.resources.IconSet;
import net.gerritk.vehiclebuilder.views.VehicleChildView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;

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
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o == vehicleModel) {
            getLogger().log("VehicleChildController", "Updating from vehicle model...");
            DefaultListModel<Child> model = (DefaultListModel<Child>) childView.getList().getModel();
			model.clear();
			for(Child c : vehicleModel.getChilds()) {
				model.addElement(c);
			}

			if(model.isEmpty()) {
				childView.getBtnClear().setEnabled(false);
			} else {
				childView.getBtnClear().setEnabled(true);
			}
		} else if(o == outputModel) {
            getLogger().log("VehicleChildController", "Updating from output model...");
            if(outputModel.getSelectedChild() != null) {
				childView.getList().setSelectedValue(outputModel.getSelectedChild(), true);

				if(outputModel.getSelectedChild().isBehind()) {
					childView.getBtnDrawOrder().setIcon(IconSet.BEHIND);
				} else {
					childView.getBtnDrawOrder().setIcon(IconSet.ONTOP);
				}

				childView.getBtnUp().setEnabled(true);
				childView.getBtnDown().setEnabled(true);
				childView.getBtnDrawOrder().setEnabled(true);
				childView.getBtnDelete().setEnabled(true);
			} else {
				childView.getBtnUp().setEnabled(false);
				childView.getBtnDown().setEnabled(false);
				childView.getBtnDrawOrder().setEnabled(false);
				childView.getBtnDelete().setEnabled(false);
			}

			childView.getList().repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "up":
                getLogger().log("VehicleChildController", "Sorting child '" + outputModel.getSelectedChild() + "' up.");
                vehicleModel.sortChildUp(outputModel.getSelectedChild());
				Child tmp = outputModel.getSelectedChild();
				vehicleModel.notifyObservers();
				childView.getList().setSelectedValue(tmp, true);
				break;
			case "down":
                getLogger().log("VehicleChildController", "Sorting child '" + outputModel.getSelectedChild() + "' down.");
                vehicleModel.sortChildDown(outputModel.getSelectedChild());
				tmp = outputModel.getSelectedChild();
				vehicleModel.notifyObservers();
				childView.getList().setSelectedValue(tmp, true);
				break;
			case "drawOrder":
                getLogger().log("VehicleChildController", "Changing draw order of child '" + outputModel.getSelectedChild() + "' to "
                        + (outputModel.getSelectedChild().isBehind() ? "behind" : "in front") + ".");
                outputModel.getSelectedChild().setBehind(!outputModel.getSelectedChild().isBehind());
				outputModel.notifyObservers(true);
				break;
			case "delete":
                getLogger().log("VehicleChildController", "Deleting child '" + outputModel.getSelectedChild() + "'.");
                vehicleModel.getChilds().remove(childView.getList().getSelectedValue());
				vehicleModel.notifyObservers(true);
				break;
			case "clear":
                getLogger().log("VehicleChildController", "Clearing childs.");
                vehicleModel.getChilds().clear();
				vehicleModel.notifyObservers(true);
				break;
			case "setCustomName":
                getLogger().log("VehicleChildController", "Changing custom name of child '" + outputModel.getSelectedChild() + "'.");
                String name = JOptionPane.showInputDialog(childView.getParent(), "Wie soll der Zusatz heißen?", "Namen ändern",
                        JOptionPane.PLAIN_MESSAGE, null, null, outputModel.getSelectedChild().getCustomName()).toString();
                getLogger().log("VehicleChildController", "Changed custom name of child '" + outputModel.getSelectedChild() + "' to '" + name + "'.");
                outputModel.getSelectedChild().setCustomName(name);
				outputModel.notifyObservers(true);
				break;
			case "removeCustomName":
                getLogger().log("VehicleChildController", "Removing custom name of child '" + outputModel.getSelectedChild() + "'.");
                outputModel.getSelectedChild().setCustomName(null);
				outputModel.notifyObservers(true);
				break;
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		outputModel.setSelectedChild(childView.getList().getSelectedValue());
        getLogger().log("VehicleChildController", "Changed selection to child '" + outputModel.getSelectedChild() + "'.");
        outputModel.notifyObservers();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		selectionWorkaround(e.getPoint());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		selectionWorkaround(e.getPoint());

		if(e.isPopupTrigger()) {
			childView.showPopupMenu();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		selectionWorkaround(e.getPoint());

		if(e.isPopupTrigger()) {
			childView.showPopupMenu();
		}
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
