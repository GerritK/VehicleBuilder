package net.gerritk.vehiclebuilder.ui.renderer;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import net.gerritk.vehiclebuilder.items.Bluelight;
import net.gerritk.vehiclebuilder.items.Child;
import net.gerritk.vehiclebuilder.resources.IconSet;

public class ChildSelectorRenderer implements ListCellRenderer<Child> {
	protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Child> list,
			Child value, int index, boolean selected, boolean focus) {
		JLabel label = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, selected, focus);
		
		if(value instanceof Bluelight) {
			label.setIcon(IconSet.BLUELIGHT);
		} else {
			label.setIcon(IconSet.SCREW);
		}
		
		return label;
	}
}
