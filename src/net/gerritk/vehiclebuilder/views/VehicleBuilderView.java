package net.gerritk.vehiclebuilder.views;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import net.gerritk.vehiclebuilder.VBLauncher;
import net.gerritk.vehiclebuilder.controllers.Controller;
import net.gerritk.vehiclebuilder.resources.IconSet;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;

public class VehicleBuilderView extends View {
	private static final long serialVersionUID = -1563909227461563477L;
	private JTextField txtName;

	public VehicleBuilderView(Controller controller, VehicleSetupView setupView, VehicleChildView childView, VehicleOutputView outputView) {
		super(controller);
		setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
					FormSpecs.RELATED_GAP_ROWSPEC,
					FormSpecs.DEFAULT_ROWSPEC,
					FormSpecs.RELATED_GAP_ROWSPEC,
					FormSpecs.DEFAULT_ROWSPEC,
					FormSpecs.RELATED_GAP_ROWSPEC,
					FormSpecs.DEFAULT_ROWSPEC,
					FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
					FormSpecs.RELATED_GAP_ROWSPEC,
					FormSpecs.DEFAULT_ROWSPEC,
					FormSpecs.RELATED_GAP_ROWSPEC,
					FormSpecs.DEFAULT_ROWSPEC,
					FormSpecs.RELATED_GAP_ROWSPEC,}));

		createMenuBar(controller);

		JLabel lblBanner = new JLabel(IconSet.BANNER);
		add(lblBanner, "4, 2, 3, 1, fill, default");

		JLabel lblName = new JLabel("Name");
		add(lblName, "4, 4, right, default");

		txtName = new JTextField();
		txtName.addFocusListener((FocusListener) controller);
		add(txtName, "6, 4, fill, default");

		// Views
		add(setupView, "2, 8, fill, fill");
		add(childView, "8, 8, fill, fill");
		add(outputView, "4, 8, 3, 1, fill, fill");

		JSeparator separator_1 = new JSeparator();
		add(separator_1, "2, 6, 7, 1");

		JSeparator separator = new JSeparator();
		add(separator, "2, 10, 7, 1");

		JButton btnExport = new JButton("Exportieren");
		btnExport.setActionCommand("export");
		btnExport.addActionListener((ActionListener) controller);
		btnExport.setToolTipText("Das erstellte Fahrzeug als Grafik exportieren");
		add(btnExport, "4, 12, 3, 1, center, default");
	}

	private void createMenuBar(Controller controller) {
		JMenuBar menuBar = new JMenuBar();

		// File Menu
		JMenu mnFile = new JMenu("Datei");
		menuBar.add(mnFile);

		JMenuItem mntmSave = new JMenuItem("Speichern");
		mntmSave.setActionCommand("save");
		mntmSave.addActionListener((ActionListener) controller);
		mntmSave.setToolTipText("Das erstellte Fahrzeug als Projekt speichern");
		mnFile.add(mntmSave);

		JMenuItem mntmLoad = new JMenuItem("Laden");
		mntmLoad.setActionCommand("load");
		mntmLoad.addActionListener((ActionListener) controller);
		mntmLoad.setToolTipText("Ein erstelltes Fahrzeug als Projekt laden");
		mnFile.add(mntmLoad);

		mnFile.addSeparator();

		JMenuItem mntmExport = new JMenuItem("Exportieren");
		mntmExport.setActionCommand("export");
		mntmExport.addActionListener((ActionListener) controller);
		mntmExport.setToolTipText("Das erstellte Fahrzeug als Grafik exportieren");
		mnFile.add(mntmExport);

		mnFile.addSeparator();

		JMenuItem mntmQuit = new JMenuItem("Beenden");
		mntmQuit.setActionCommand("quit");
		mntmQuit.addActionListener((ActionListener) controller);
		mntmQuit.setToolTipText("Das Programm beenden");
		mnFile.add(mntmQuit);

		// Help Menu
		JMenu mnHelp = new JMenu("Hilfe");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("Über");
		mntmAbout.setActionCommand("about");
		mntmAbout.addActionListener((ActionListener) controller);
		mntmAbout.setToolTipText("Informationen Über das Programm anzeigen");
		mnHelp.add(mntmAbout);
		VBLauncher.getInstance().getFrame().setJMenuBar(menuBar);
	}

	/*
	 * Getter & Setter
	 */
	public JTextField getTxtName() {
		return txtName;
	}
}
