package net.gerritk.vehiclebuilder.views;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import net.gerritk.vehiclebuilder.controllers.Controller;
import net.gerritk.vehiclebuilder.items.*;
import net.gerritk.vehiclebuilder.ui.renderer.ChildSelectorRenderer;

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
		selectorCabin.addItemListener((ItemListener) controller);
		//selectorCabin.addMouseListener((MouseListener) controller);
		selectorCabin.setToolTipText("Kabine auswählen");
		add(selectorCabin, "3, 1, fill, default");
		
		// Structure
		JLabel lblStructure = new JLabel("Aufbau");
		add(lblStructure, "1, 3, right, default");
		
		selectorStructure = new JComboBox<Structure>();
		selectorStructure.addItemListener((ItemListener) controller);
		//selectorStructure.addMouseListener((MouseListener) controller);
		selectorStructure.setToolTipText("Aufbau auswählen");
		add(selectorStructure, "3, 3, fill, default");
		
		// Template
		JLabel lblTemplate = new JLabel("Template");
		add(lblTemplate, "1, 5, right, default");
		
		selectorTemplate = new JComboBox<Template>();
		selectorTemplate.addItemListener((ItemListener) controller);
		selectorTemplate.setToolTipText("Template auswählen");
		add(selectorTemplate, "3, 5, fill, default");
		
		// Child
		JLabel lblChild = new JLabel("Zusatz");
		add(lblChild, "1, 7, right, default");
		
		selectorChild = new JComboBox<Child>();
		selectorChild.setToolTipText("Zusatz auswählen");
		selectorChild.setRenderer(new ChildSelectorRenderer());
		add(selectorChild, "3, 7, fill, default");
		
		JButton btnAddChild = new JButton("Hinzuf\u00FCgen");
		btnAddChild.setActionCommand("addChild");
		btnAddChild.addActionListener((ActionListener) controller);
		btnAddChild.setToolTipText("Zusatz zur Liste hinzufügen");
		add(btnAddChild, "3, 9");
	}
	
	public void showPopup(JComboBox<? extends Item> c) {
		JPopupMenu popupMenu = new JPopupMenu();
		
		JPanel xPanel = new JPanel();
		xPanel.add(new JLabel("X:"));
		
		xPanel.add(new JLabel("0"));
		
		JButton btnIncX = new JButton("+");
		btnIncX.setMargin(new Insets(0, 0, 0, 0));
		btnIncX.setPreferredSize(new Dimension(20, 20));
		xPanel.add(btnIncX);
		
		JButton btnDecX = new JButton("-");
		btnDecX.setMargin(new Insets(0, 0, 0, 0));
		btnDecX.setPreferredSize(new Dimension(20, 20));
		xPanel.add(btnDecX);
		
		popupMenu.add(xPanel);
		
		JPanel yPanel = new JPanel();
		yPanel.add(new JLabel("Y:"));
		
		yPanel.add(new JLabel("0"));
		
		JButton btnIncY = new JButton("+");
		btnIncY.setMargin(new Insets(0, 0, 0, 0));
		btnIncY.setPreferredSize(new Dimension(20, 20));
		yPanel.add(btnIncY);
		
		JButton btnDecY = new JButton("-");
		btnDecY.setMargin(new Insets(0, 0, 0, 0));
		btnDecY.setPreferredSize(new Dimension(20, 20));
		yPanel.add(btnDecY);
		
		popupMenu.add(yPanel);
		
		popupMenu.show(c, 5, c.getHeight());
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
