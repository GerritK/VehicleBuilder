package net.gerritk.vehiclebuilder.controllers;

import net.gerritk.vehiclebuilder.models.VehicleModel;
import net.gerritk.vehiclebuilder.views.VehicleSetupOffsetView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Observable;

public class VehicleSetupOffsetController extends Controller implements ChangeListener {
    public static final int CABIN = 0;
    public static final int STRUCTURE = 1;
    private VehicleModel vehicleModel;
    private VehicleSetupOffsetView view;
    private int type;

    public VehicleSetupOffsetController(VehicleModel vehicleModel) {
        this.vehicleModel = vehicleModel;
        this.view = new VehicleSetupOffsetView(this);

        this.vehicleModel.addObserver(this);
    }

    public void update(Observable o, Object arg) {
        if (o == this.vehicleModel) {
            getLogger().log("VehicleSetupOffsetController", "Updating from vehicle model...");

            if (this.type == CABIN) {
                view.getSpinnerOffsetX().setValue(vehicleModel.getCabinOffsetX());
                view.getSpinnerOffsetY().setValue(vehicleModel.getCabinOffsetY());
            } else if (this.type == STRUCTURE) {
                view.getSpinnerOffsetX().setValue(vehicleModel.getStructureOffsetX());
                view.getSpinnerOffsetY().setValue(vehicleModel.getStructureOffsetY());
            }
        }

    }

    public VehicleSetupOffsetView getVehicleSetupOffsetView(int type) {
        this.type = type;
        vehicleModel.notifyObservers(true);
        return view;
    }

    public void stateChanged(ChangeEvent e) {
        if (e.getSource() instanceof JSpinner) {
            JSpinner src = (JSpinner) e.getSource();
            if (type == CABIN) {
                if (src == view.getSpinnerOffsetX()) {
                    vehicleModel.setCabinOffsetX((Integer) src.getValue());
                } else if (src == view.getSpinnerOffsetY()) {
                    vehicleModel.setCabinOffsetY((Integer) src.getValue());
                }

                vehicleModel.notifyObservers();
            } else if (type == STRUCTURE) {
                if (src == view.getSpinnerOffsetX()) {
                    vehicleModel.setStructureOffsetX((Integer) src.getValue());
                } else if (src == view.getSpinnerOffsetY()) {
                    vehicleModel.setStructureOffsetY((Integer) src.getValue());
                }

                vehicleModel.notifyObservers();
            }
        }

    }
}
