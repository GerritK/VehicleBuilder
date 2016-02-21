package net.gerritk.vehiclebuilder.ui.renderer;

import net.gerritk.vehiclebuilder.items.Bluelight;
import net.gerritk.vehiclebuilder.items.Child;
import net.gerritk.vehiclebuilder.resources.IconSet;

import javax.swing.*;
import java.awt.*;

public class ChildSelectorRenderer implements ListCellRenderer<Child> {
    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    public ChildSelectorRenderer() {
    }

    public Component getListCellRendererComponent(JList<? extends Child> list, Child value, int index, boolean selected, boolean focus) {
        JLabel label = (JLabel) this.defaultRenderer.getListCellRendererComponent(list, value, index, selected, focus);
        if (value instanceof Bluelight) {
            label.setIcon(IconSet.BLUELIGHT);
        } else {
            label.setIcon(IconSet.SCREW);
        }

        return label;
    }
}
