package net.gerritk.vehiclebuilder.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;

import net.gerritk.vehiclebuilder.VBLauncher;
import net.gerritk.vehiclebuilder.models.*;
import net.gerritk.vehiclebuilder.resources.VehicleBuilderSaveFile;
import net.gerritk.vehiclebuilder.ui.dialogs.BuilderAboutDialog;
import net.gerritk.vehiclebuilder.ui.dialogs.BuilderInfoDialog;
import net.gerritk.vehiclebuilder.views.*;

public class VehicleBuilderController extends Controller implements ActionListener {
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
			builderView.getTxtName().setText(vehicleModel.getStructure().getName());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "export":
				vehicleModel.setName(builderView.getTxtName().getText());
				
				BuilderInfoDialog infoDialog = new BuilderInfoDialog(VBLauncher.getInstance().getFrame(), "Exportiervorgang",
						"Exportiervorgang ist fehlgeschlagen. Versuchen Sie es bitte erneut.");
				if(vehicleModel.export()) {
					infoDialog.setInfo("Exportiervorgang war erfolgreich.");
				}
				infoDialog.setVisible(true);
				
				break;
			case "save":
				File f = new File("vehicle.save");
				
				infoDialog = new BuilderInfoDialog(VBLauncher.getInstance().getFrame(), "Speichervorgang",
						"Speichervorgang ist fehlgeschlagen. Versuchen Sie es bitte erneut.");
				if(VehicleBuilderSaveFile.saveToFile(f, vehicleModel)) {
					infoDialog.setInfo("Speichervorgang war erfolgreich.");
				}
				infoDialog.setVisible(true);
				
				break;
			case "load":
				f = new File("vehicle.save");
				
				infoDialog = new BuilderInfoDialog(VBLauncher.getInstance().getFrame(), "Ladevorgang",
						"Ladevorgang ist fehlgeschlagen. Versuchen Sie es bitte erneut.");
				if(VehicleBuilderSaveFile.loadFromFile(f, vehicleModel)) {
					infoDialog.setInfo("Ladevorgang war erfolgreich.");
				}
				infoDialog.setVisible(true);
				
				break;
			case "about":
				BuilderAboutDialog aboutDialog = new BuilderAboutDialog(VBLauncher.getInstance().getFrame());
				aboutDialog.setVisible(true);
				break;
			case "quit":
				VBLauncher.getInstance().quit();
				break;
		}
	}
	
	/*
	 * Getter & Setter
	 */
	public VehicleBuilderView getVehicleBuilderView() {
		return builderView;
	}
}
