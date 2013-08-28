package net.gerritk.vehiclebuilder.views;
import java.awt.event.ActionListener;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;

import net.gerritk.vehiclebuilder.controllers.Controller;
import net.gerritk.vehiclebuilder.items.*;

public class VehicleSetupView extends View {
	private static final long serialVersionUID = 1330608725627293185L;
	private JComboBox<Template> selectorTemplate;
	private JComboBox<Child> selectorChild;
	private JComboBox<Cabin> selectorCabin;
	private JComboBox<Structure> selectorStructure;

	public VehicleSetupView(Controller controller) {
		super(controller);
		
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		// Cabin
		JLabel lblCabin = new JLabel("Kabine");
		add(lblCabin, "1, 1, right, default");
		
		selectorCabin = new JComboBox<Cabin>();
		add(selectorCabin, "3, 1, fill, default");
		
		// Structure
		JLabel lblStructure = new JLabel("Aufbau");
		add(lblStructure, "1, 3, right, default");
		
		selectorStructure = new JComboBox<Structure>();
		add(selectorStructure, "3, 3, fill, default");
		
		// Template
		JLabel lblTemplate = new JLabel("Template");
		add(lblTemplate, "1, 5, right, default");
		
		selectorTemplate = new JComboBox<Template>();
		add(selectorTemplate, "3, 5, fill, default");
		
		// Child
		JLabel lblChild = new JLabel("Zusatz");
		add(lblChild, "1, 7, right, default");
		
		selectorChild = new JComboBox<Child>();
		add(selectorChild, "3, 7, fill, default");
		
		JButton btnAddChild = new JButton("Hinzuf\u00FCgen");
		btnAddChild.setActionCommand("addChild");
		btnAddChild.addActionListener((ActionListener) controller);
		add(btnAddChild, "3, 9");
	}
	
	/*
	 * Getter
	 */
	public JComboBox<Template> getSelectorTemplate() {
		return selectorTemplate;
	}
	public JComboBox<Child> getSelectorChild() {
		return selectorChild;
	}
	public JComboBox<Cabin> getSelectorCabin() {
		return selectorCabin;
	}
	public JComboBox<Structure> getSelectorStructure() {
		return selectorStructure;
	}
}
