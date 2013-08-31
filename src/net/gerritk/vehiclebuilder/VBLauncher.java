package net.gerritk.vehiclebuilder;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.gerritk.vehiclebuilder.controllers.VehicleBuilderController;
import net.gerritk.vehiclebuilder.controllers.VehicleChildController;
import net.gerritk.vehiclebuilder.controllers.VehicleOutputController;
import net.gerritk.vehiclebuilder.controllers.VehicleSetupController;
import net.gerritk.vehiclebuilder.models.*;
import net.gerritk.vehiclebuilder.resources.IconSet;
import net.gerritk.vehiclebuilder.resources.ResourceLoader;
import net.gerritk.vehiclebuilder.ui.dialogs.BuilderConfirmDialog;

public class VBLauncher {
	public static String VERSION = "0.0.3.0";
	
	private static VBLauncher instance;
	
	private JFrame frame;
	private ResourceLoader resourceLoader;
	private ArrayList<Model> models = new ArrayList<Model>();
	
	private VBLauncher() {
		instance = this;
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		resourceLoader = new ResourceLoader("resources/items");		
		
		frame = new JFrame("Vehicle Builder für Leitstellenspiel.de");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		CabinModel cabinModel = new CabinModel();
		StructureModel structureModel = new StructureModel();
		TemplateModel templateModel = new TemplateModel();
		ChildModel childModel = new ChildModel();
		VehicleModel vehicleModel = new VehicleModel(cabinModel, structureModel, templateModel);
		OutputModel outputModel = new OutputModel();
		
		models.add(cabinModel);
		models.add(structureModel);
		models.add(templateModel);
		models.add(childModel);
		models.add(vehicleModel);
		models.add(outputModel);
		
		VehicleSetupController vsetupController = new VehicleSetupController(cabinModel, structureModel, 
				templateModel, childModel, vehicleModel, outputModel);
		
		VehicleChildController vchildController = new VehicleChildController(vehicleModel, outputModel);
		
		VehicleOutputController voutputController = new VehicleOutputController(vehicleModel, outputModel);
		
		VehicleBuilderController vbuilderController = new VehicleBuilderController(vehicleModel,
				vsetupController.getVehicleSetupView(), vchildController.getVehicleChildView(),
				voutputController.getVehicleOutputView());
		
		frame.add(vbuilderController.getVehicleBuilderView());
		
		// Notify ALL
		cabinModel.notifyObservers();
		structureModel.notifyObservers();
		templateModel.notifyObservers();
		childModel.notifyObservers();
		vehicleModel.notifyObservers();
		outputModel.notifyObservers();
		
		frame.setIconImages(IconSet.loadIcons());
		frame.pack();
		frame.setMinimumSize(frame.getSize());

		setFrameToScreenCenter();
		
		frame.setVisible(true);
	}
	
	public void setFrameToScreenCenter() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point wp = new Point((int) (screenSize.getWidth() - frame.getWidth()) / 2, (int) (screenSize.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(wp);
	}
	
	public void quit() {
		BuilderConfirmDialog sure = new BuilderConfirmDialog(frame, "Beenden", "Wollen Sie das Programm wirklich beenden?");
		sure.setVisible(true);
		if(sure.isConfirmed()) {
			frame.dispose();
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Model> T getModel(Class<T> c) {
		for(Model model : models) {
			if(c.equals(model.getClass())) {
				return (T) model;
			}
		}
		return null;
	}
	
	/*
	 * Getter & Setter
	 */
	public JFrame getFrame() {
		return frame;
	}
	
	public ResourceLoader getResourceLoader() {
		return resourceLoader;
	}
	/*
	 * Static
	 */
	public static VBLauncher getInstance() {
		return instance;
	}
	
	public static void main(String[] args) {
		new VBLauncher();
	}
}
