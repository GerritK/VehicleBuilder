package net.gerritk.vehiclebuilder.ui.dialogs;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuilderInfoDialog extends BuilderDialog {
	private JLabel lblInfo;

	public BuilderInfoDialog(Frame frame, String title, String info) {
		super(frame, title);
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
					FormSpecs.RELATED_GAP_ROWSPEC,
					FormSpecs.DEFAULT_ROWSPEC,
					FormSpecs.RELATED_GAP_ROWSPEC,
					FormSpecs.DEFAULT_ROWSPEC,
					FormSpecs.RELATED_GAP_ROWSPEC,}));

		lblInfo = new JLabel(info);
		getContentPane().add(lblInfo, "2, 2, center, default");

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnOk.setToolTipText("Fenster schließen");
		getContentPane().add(btnOk, "2, 4, center, default");

		pack();
	}

	public void setInfo(String info) {
		lblInfo.setText(info);
		pack();
	}
}
