package net.gerritk.vehiclebuilder.views;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import net.gerritk.vehiclebuilder.controllers.Controller;

import javax.swing.*;
import javax.swing.event.ChangeListener;

public class VehicleSetupOffsetView extends View {
    private JSpinner spnOffsetY;
    private JSpinner spnOffsetX;

    public VehicleSetupOffsetView(Controller controller) {
        super(controller);
        this.setLayout(new FormLayout(new ColumnSpec[]{
                FormSpecs.DEFAULT_COLSPEC,
                FormSpecs.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("max(40dlu;default)")},
                new RowSpec[]{
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC}));

        JLabel lblX = new JLabel("X:");
        add(lblX, "1, 1");
        spnOffsetX = new JSpinner();
        spnOffsetX.addChangeListener((ChangeListener) controller);
        add(spnOffsetX, "3, 1");

        JLabel lblY = new JLabel("Y:");
        add(lblY, "1, 3");
        spnOffsetY = new JSpinner();
        spnOffsetY.addChangeListener((ChangeListener) controller);
        add(spnOffsetY, "3, 3");
    }

    public JSpinner getSpinnerOffsetY() {
        return spnOffsetY;
    }

    public JSpinner getSpinnerOffsetX() {
        return spnOffsetX;
    }
}
