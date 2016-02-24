package net.gerritk.vehiclebuilder;

import net.gerritk.vehiclebuilder.controllers.VehicleBuilderController;
import net.gerritk.vehiclebuilder.controllers.VehicleChildController;
import net.gerritk.vehiclebuilder.controllers.VehicleOutputController;
import net.gerritk.vehiclebuilder.controllers.VehicleSetupController;
import net.gerritk.vehiclebuilder.logging.FileLogger;
import net.gerritk.vehiclebuilder.logging.Logger;
import net.gerritk.vehiclebuilder.models.*;
import net.gerritk.vehiclebuilder.resources.IconSet;
import net.gerritk.vehiclebuilder.resources.ResourceLoader;
import net.gerritk.vehiclebuilder.ui.dialogs.BuilderConfirmDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class VBLauncher {
	public static String VERSION = "0.0.4.0";
	private static VBLauncher instance;
    private static Logger logger;

	private JFrame frame;
	private ResourceLoader resourceLoader;
	private ArrayList<Model> models = new ArrayList<>();

	private VBLauncher() {
        getLogger().log("Launcher", "Starting VehicleBuilder V" + VERSION);
        getLogger().info("Launcher", "System: " + System.getProperty("os.name") + " " + System.getProperty("os.arch") + " " + System.getProperty("os.version"));
        getLogger().info("Launcher", "Java: " + System.getProperty("java.vendor") + " " + System.getProperty("java.version"));

		instance = this;
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

        getLogger().log("Launcher", "Creating frame...");
        frame = new JFrame("Vehicle Builder für Leitstellenspiel.de");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                quit();
            }
        });

		try {
            getLogger().log("Launcher", "Loading resources...");
            resourceLoader = new ResourceLoader("resources/items");
		} catch (FileNotFoundException e) {
            getLogger().error("Launcher", "Could not find resources folder. VehicleBuilder exits.");
            JOptionPane.showMessageDialog(null, "Der Resources-Ordner konnte nicht gefunden werden. Das Programm wird beendet.", "Vehicle Builder - Resources nicht gefunden", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return;
		}

        getLogger().log("Launcher", "Initializing models...");
        CabinModel cabinModel = new CabinModel();
		StructureModel structureModel = new StructureModel();
		TemplateModel templateModel = new TemplateModel();
		ChildModel childModel = new ChildModel();
		VehicleModel vehicleModel = new VehicleModel(cabinModel, structureModel, templateModel);
		OutputModel outputModel = new OutputModel();

        getLogger().log("Launcher", "Registering models...");
        models.add(cabinModel);
		models.add(structureModel);
		models.add(templateModel);
		models.add(childModel);
		models.add(vehicleModel);
		models.add(outputModel);

        getLogger().log("Launcher", "Initializing controllers...");
        VehicleSetupController vsetupController = new VehicleSetupController(cabinModel, structureModel,
				templateModel, childModel, vehicleModel, outputModel);

		VehicleChildController vchildController = new VehicleChildController(vehicleModel, outputModel);

		VehicleOutputController voutputController = new VehicleOutputController(vehicleModel, outputModel);

		VehicleBuilderController vbuilderController = new VehicleBuilderController(vehicleModel,
				vsetupController.getVehicleSetupView(), vchildController.getVehicleChildView(),
				voutputController.getVehicleOutputView());

        getLogger().log("Launcher", "Adding main view to frame...");
        frame.add(vbuilderController.getVehicleBuilderView());

		// Notify ALL
        getLogger().log("Launcher", "Notifying all observers...");
        cabinModel.notifyObservers();
		structureModel.notifyObservers();
		templateModel.notifyObservers();
		childModel.notifyObservers();
		vehicleModel.notifyObservers();
		outputModel.notifyObservers();

        getLogger().log("Launcher", "Loading icons and packing frame...");
        frame.setIconImages(IconSet.loadIcons());
		frame.pack();
		frame.setMinimumSize(frame.getSize());

		setFrameToScreenCenter();

        getLogger().log("Launcher", "All done. Showing Frame...");
        frame.setVisible(true);
	}

	public void setFrameToScreenCenter() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point wp = new Point((int) (screenSize.getWidth() - frame.getWidth()) / 2, (int) (screenSize.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(wp);
	}

	public void quit() {
        getLogger().log("Launcher", "User is trying to quit. Ask if he really wants to.");
        BuilderConfirmDialog sure = new BuilderConfirmDialog(frame, "Beenden", "Wollen Sie das Programm wirklich beenden?");
		sure.setVisible(true);
		if(sure.isConfirmed()) {
            getLogger().log("Launcher", "User confirmed quit. Application exits.");
            frame.dispose();
        } else {
            getLogger().log("Launcher", "User denied quit. Keep running!");
        }
    }

	@SuppressWarnings("unchecked")
	public <T extends Model> T getModel(Class<T> c) {
        getLogger().log("Launcher", "Looking for model of class '" + c.getSimpleName() + "'...");
        for(Model model : models) {
			if(c.equals(model.getClass())) {
                getLogger().log("Launcher", "Found model of class '" + c.getSimpleName() + "'.");
                return (T) model;
			}
		}
        getLogger().warn("Launcher", "Could not find model of class '" + c.getSimpleName() + "'!");
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

    public static Logger getLogger() {
        if (logger == null) {
            synchronized (VBLauncher.class) {
                if (logger == null) {
                    logger = new FileLogger(new File("log.txt"));
                }
            }
        }
        return logger;
    }

	public static void main(String[] args) {
        try {
            new VBLauncher();
        } catch (Exception e) {
            getLogger().error("Launcher", e);
        }
    }
}
