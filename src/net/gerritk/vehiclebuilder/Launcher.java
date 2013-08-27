package net.gerritk.vehiclebuilder;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.gerritk.vehiclebuilder.controllers.VehicleSetupController;
import net.gerritk.vehiclebuilder.models.*;
import net.gerritk.vehiclebuilder.resources.ResourceLoader;
import net.gerritk.vehiclebuilder.ui.MenuBar;

public class Launcher {
	private static Launcher instance = null;
	
	private JFrame frame;
	private ResourceLoader resourceLoader;
	
	private Launcher() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		resourceLoader = new ResourceLoader("resources/items");		
		
		frame = new JFrame("Vehicle Builder f�r Leitstellenspiel.de");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new MenuBar();
		frame.setJMenuBar(menuBar);
		
		// TODO add Controllers, Models & Views
		CabinModel cabinModel = new CabinModel();
		StructureModel structureModel = new StructureModel();
		TemplateModel templateModel = new TemplateModel();
		ChildModel childModel = new ChildModel();
		VehicleModel vehicleModel = new VehicleModel(cabinModel, structureModel, templateModel);
		
		VehicleSetupController vehicleSetupController = new VehicleSetupController(cabinModel, structureModel, templateModel, childModel, vehicleModel);
		
		setFrameToScreenCenter();
		
		frame.pack();
		frame.setMinimumSize(frame.getSize());
		frame.setVisible(true);
	}
	
	public void setFrameToScreenCenter() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point wp = new Point((int) (screenSize.getWidth() - frame.getWidth()) / 2, (int) (screenSize.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(wp);
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
	public static Launcher getInstance() {
		if(instance == null) {
			synchronized(Launcher.class) {
				if(instance == null) {
					instance = new Launcher();
				}
			}
		}
		return instance;
	}
	
	public static void main(String[] args) {
		getInstance();
	}
}
