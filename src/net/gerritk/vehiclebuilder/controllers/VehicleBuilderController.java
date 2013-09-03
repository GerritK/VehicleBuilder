package net.gerritk.vehiclebuilder.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Observable;

import net.gerritk.vehiclebuilder.VBLauncher;
import net.gerritk.vehiclebuilder.models.*;
import net.gerritk.vehiclebuilder.ui.dialogs.BuilderAboutDialog;
import net.gerritk.vehiclebuilder.ui.dialogs.BuilderInfoDialog;
import net.gerritk.vehiclebuilder.views.*;

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
				if(!vehicleModel.save()) {
					infoDialog = new BuilderInfoDialog(VBLauncher.getInstance().getFrame(), "Speichervorgang",
							"Speichervorgang ist fehlgeschlagen. Versuchen Sie es bitte erneut.");
					infoDialog.setVisible(true);
				}
				
				break;
			case "load":
				if(vehicleModel.load()) {
					vehicleModel.notifyObservers();
				} else {
					infoDialog = new BuilderInfoDialog(VBLauncher.getInstance().getFrame(), "Ladevorgang",
							"Ladevorgang ist fehlgeschlagen. Versuchen Sie es bitte erneut.");
					infoDialog.setVisible(true);
				}
				
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
