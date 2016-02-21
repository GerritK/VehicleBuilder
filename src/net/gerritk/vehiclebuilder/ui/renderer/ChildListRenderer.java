package net.gerritk.vehiclebuilder.ui.renderer;

import net.gerritk.vehiclebuilder.items.Bluelight;
import net.gerritk.vehiclebuilder.items.Child;
import net.gerritk.vehiclebuilder.resources.IconSet;

import javax.swing.*;
import java.awt.*;

public class ChildListRenderer implements ListCellRenderer<Child> {
	@Override
	public Component getListCellRendererComponent(JList<? extends Child> list,
			Child value, int index, boolean selected, boolean focus) {
		JPanel panel = new JPanel();
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
		layout.setVgap(1);
		panel.setLayout(layout);

		JLabel lblType = new JLabel(IconSet.SCREW);
		if(value instanceof Bluelight) {
			lblType.setIcon(IconSet.BLUELIGHT);
		}
		panel.add(lblType);


		JLabel lblDrawOrder = new JLabel(IconSet.ONTOP);
		if(value.isBehind()) {
			lblDrawOrder.setIcon(IconSet.BEHIND);
		}
		panel.add(lblDrawOrder);

		JLabel lblName = new JLabel(value.getName());
		panel.add(lblName);

		if(value.getCustomName() != null && !value.getCustomName().isEmpty()) {
			JLabel lblCustomName = new JLabel(value.getName());
			lblCustomName.setFont(lblCustomName.getFont().deriveFont(Font.ITALIC));
			lblCustomName.setForeground(UIManager.getColor("Label.disabledForeground"));
			panel.add(lblCustomName);

			lblName.setText(value.getCustomName());
		}

		if(selected) {
			panel.setBackground(UIManager.getColor("List.selectionBackground"));
			lblName.setForeground(UIManager.getColor("List.selectionForeground"));
		} else {
			panel.setBackground(UIManager.getColor("List.background"));
			lblName.setForeground(UIManager.getColor("List.foreground"));
		}

		return panel;
	}
}
