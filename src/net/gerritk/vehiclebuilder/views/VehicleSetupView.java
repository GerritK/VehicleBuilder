package net.gerritk.vehiclebuilder.views;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import net.gerritk.vehiclebuilder.controllers.Controller;
import net.gerritk.vehiclebuilder.items.*;
import net.gerritk.vehiclebuilder.ui.renderer.ChildSelectorRenderer;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;

public class VehicleSetupView extends View {
    private JComboBox<Template> selectorTemplate;
    private JComboBox<Child> selectorChild;
    private JComboBox<Cabin> selectorCabin;
    private JComboBox<Structure> selectorStructure;
    private JPopupMenu popupMenu;

    public VehicleSetupView(Controller controller) {
        super(controller);

        setLayout(new FormLayout(new ColumnSpec[]{
                FormSpecs.DEFAULT_COLSPEC,
                FormSpecs.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("default:grow"),},
                new RowSpec[]{
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,}));

        // Cabin
        JLabel lblCabin = new JLabel("Kabine");
        add(lblCabin, "1, 1, right, default");

        selectorCabin = new JComboBox<>();
        selectorCabin.addItemListener((ItemListener) controller);
        selectorCabin.addMouseListener((MouseListener) controller);
        selectorCabin.setToolTipText("Kabine auswählen");
        add(this.selectorCabin, "3, 1, fill, default");

        // Structure
        JLabel lblStructure = new JLabel("Aufbau");
        add(lblStructure, "1, 3, right, default");

        selectorStructure = new JComboBox<>();
        selectorStructure.addItemListener((ItemListener) controller);
        selectorStructure.addMouseListener((MouseListener) controller);
        selectorStructure.setToolTipText("Aufbau auswählen");
        add(this.selectorStructure, "3, 3, fill, default");

        // Template
        JLabel lblTemplate = new JLabel("Template");
        add(lblTemplate, "1, 5, right, default");
        selectorTemplate = new JComboBox<>();
        selectorTemplate.addItemListener((ItemListener) controller);
        selectorTemplate.setToolTipText("Template auswählen");
        add(this.selectorTemplate, "3, 5, fill, default");

        // Child
        JLabel lblChild = new JLabel("Zusatz");
        add(lblChild, "1, 7, right, default");
        selectorChild = new JComboBox<>();
        selectorChild.setToolTipText("Zusatz auswählen");
        selectorChild.setRenderer(new ChildSelectorRenderer());
        add(selectorChild, "3, 7, fill, default");

        JButton btnAddChild = new JButton("Hinzufügen");
        btnAddChild.setActionCommand("addChild");
        btnAddChild.addActionListener((ActionListener) controller);
        btnAddChild.setToolTipText("Zusatz zur Liste hinzufügen");
        this.add(btnAddChild, "3, 9");
    }

    public void showPopup(JComboBox<? extends Item> c, VehicleSetupOffsetView view) {
        this.popupMenu = new JPopupMenu();
        this.popupMenu.add(view);
        this.popupMenu.show(c, 5, c.getHeight());
    }

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
