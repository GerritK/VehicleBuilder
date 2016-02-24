package net.gerritk.vehiclebuilder.controllers;

import net.gerritk.vehiclebuilder.VBLauncher;
import net.gerritk.vehiclebuilder.models.VehicleModel;
import net.gerritk.vehiclebuilder.resources.SaveFileFilter;
import net.gerritk.vehiclebuilder.resources.VehicleBuilderSaveFile;
import net.gerritk.vehiclebuilder.ui.dialogs.BuilderAboutDialog;
import net.gerritk.vehiclebuilder.ui.dialogs.BuilderInfoDialog;
import net.gerritk.vehiclebuilder.views.VehicleBuilderView;
import net.gerritk.vehiclebuilder.views.VehicleChildView;
import net.gerritk.vehiclebuilder.views.VehicleOutputView;
import net.gerritk.vehiclebuilder.views.VehicleSetupView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.Observable;

public class VehicleBuilderController extends Controller implements ActionListener, FocusListener {
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
			builderView.getTxtName().setText(vehicleModel.getName());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
        vehicleModel.setName(builderView.getTxtName().getText());

		switch(e.getActionCommand()) {
			case "export":
                getLogger().log("VehicleBuilderController", "Exporting vehicle '" + vehicleModel.getName() + "'...");

				BuilderInfoDialog infoDialog = new BuilderInfoDialog(VBLauncher.getInstance().getFrame(), "Exportiervorgang",
						"Exportiervorgang ist fehlgeschlagen. Versuchen Sie es bitte erneut.");
				if(vehicleModel.export()) {
                    getLogger().log("VehicleBuilderController", "Export was successful!");
                    infoDialog.setInfo("Exportiervorgang war erfolgreich.");
                } else {
                    getLogger().error("VehicleBuilderController", "Export failed!");
                }

				infoDialog.setVisible(true);
				break;
			case "save":
                getLogger().log("VehicleBuilderController", "Saving vehicle '" + vehicleModel.getName() + "'...");
                JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.setFileFilter(new SaveFileFilter());
				fc.setSelectedFile(new File(vehicleModel.getName() + ".vbsf"));
				int value = fc.showSaveDialog(builderView);

				if(value == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
                    getLogger().log("VehicleBuilderController", "Destination to save vehicle chosen: " + f.getPath());

					if (!VehicleBuilderSaveFile.saveToFile(f, vehicleModel)) {
                        getLogger().error("VehicleBuilderController", "Saving vehicle failed!");
                        infoDialog = new BuilderInfoDialog(VBLauncher.getInstance().getFrame(), "Speichervorgang",
								"Speichervorgang ist fehlgeschlagen. Versuchen Sie es bitte erneut.");
						infoDialog.setVisible(true);
                    } else {
                        getLogger().error("VehicleBuilderController", "Vehicle saved successfully!");
                    }
                }
				break;
			case "load":
                getLogger().log("VehicleBuilderController", "Loading vehicle...");
                fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.setSelectedFile(new File("vehicle.vbsf"));
				fc.setFileFilter(new SaveFileFilter());
				value = fc.showOpenDialog(builderView);

				if (value == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
                    getLogger().log("VehicleBuilderController", "File to load vehicle from: " + f.getPath());

					if (VehicleBuilderSaveFile.loadFromFile(f, vehicleModel)) {
                        getLogger().log("VehicleBuilderController", "Successfully loaded vehicle '" + vehicleModel.getName() + "'.");
                        vehicleModel.notifyObservers();
					} else {
                        getLogger().error("VehicleBuilderController", "Failed to load vehicle.");
                        infoDialog = new BuilderInfoDialog(VBLauncher.getInstance().getFrame(), "Ladevorgang",
								"Ladevorgang ist fehlgeschlagen. Versuchen Sie es bitte erneut.");
						infoDialog.setVisible(true);
					}
				}
				break;
			case "about":
                getLogger().log("VehicleBuilderController", "Showing about dialog...");
                BuilderAboutDialog aboutDialog = new BuilderAboutDialog(VBLauncher.getInstance().getFrame());
				aboutDialog.setVisible(true);
				break;
			case "quit":
                getLogger().log("VehicleBuilderController", "User requested to quit...");
                VBLauncher.getInstance().quit();
				break;
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
	}

	@Override
	public void focusLost(FocusEvent e) {
		vehicleModel.setName(builderView.getTxtName().getText());
		vehicleModel.notifyObservers();
	}

	/*
	 * Getter & Setter
	 */
	public VehicleBuilderView getVehicleBuilderView() {
		return builderView;
	}
}
