package net.gerritk.vehiclebuilder.views;

import java.awt.event.ActionListener;
import java.awt.event.FocusListener;

import net.gerritk.vehiclebuilder.VBLauncher;
import net.gerritk.vehiclebuilder.controllers.Controller;
import net.gerritk.vehiclebuilder.resources.IconSet;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSeparator;

public class VehicleBuilderView extends View {
	private static final long serialVersionUID = -1563909227461563477L;
	private JTextField txtName;

	public VehicleBuilderView(Controller controller, VehicleSetupView setupView, VehicleChildView childView,
			VehicleOutputView outputView) {
		super(controller);
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
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
		mntmAbout.setToolTipText("Informationen über das Programm anzeigen");
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
