package net.gerritk.vehiclebuilder.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import net.gerritk.vehiclebuilder.VBLauncher;
import net.gerritk.vehiclebuilder.models.*;
import net.gerritk.vehiclebuilder.resources.VehicleBuilderSaveFile;
import net.gerritk.vehiclebuilder.ui.dialogs.BuilderAboutDialog;
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
				
				JLabel lblMessage = new JLabel("Exportiervorgang ist fehlgeschlagen. Versuchen Sie es bitte erneut.", JLabel.CENTER);
				if(vehicleModel.export()) {
					lblMessage.setText("Exportiervorgang war erfolgreich.");
				}
				JOptionPane.showMessageDialog(builderView, lblMessage, "Exportiervorgang", JOptionPane.PLAIN_MESSAGE);
				
				break;
			case "save":
				File f = new File("vehicle.save");
				
				lblMessage = new JLabel("Speichervorgang ist fehlgeschlagen. Versuchen Sie es bitte erneut.", JLabel.CENTER);
				if(VehicleBuilderSaveFile.saveToFile(f, vehicleModel)) {
					lblMessage.setText("Speichervorgang war erfolgreich.");
				}
				JOptionPane.showMessageDialog(builderView, lblMessage, "Speichervorgang", JOptionPane.PLAIN_MESSAGE);
				
				break;
			case "load":
				f = new File("vehicle.save");
				
				lblMessage = new JLabel("Ladevorgang ist fehlgeschlagen. Versuchen Sie es bitte erneut.", JLabel.CENTER);
				if(VehicleBuilderSaveFile.loadFromFile(f, vehicleModel)) {
					lblMessage.setText("Ladevorgang war erfolgreich.");
					vehicleModel.notifyObservers();
				}
				JOptionPane.showMessageDialog(builderView, lblMessage, "Ladevorgang", JOptionPane.PLAIN_MESSAGE);
				
				break;
			case "about":
				BuilderAboutDialog dialog = new BuilderAboutDialog(VBLauncher.getInstance().getFrame());
				dialog.setVisible(true);
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
