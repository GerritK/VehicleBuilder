package net.gerritk.vehiclebuilder.ui.dialogs;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuilderConfirmDialog extends BuilderDialog implements ActionListener {
	private static final long serialVersionUID = -4439614621018739269L;
	private boolean value;

	public BuilderConfirmDialog(Frame frame, String title, String text) {
		super(frame, title, true);
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
					FormSpecs.RELATED_GAP_ROWSPEC,
					FormSpecs.DEFAULT_ROWSPEC,
					FormSpecs.RELATED_GAP_ROWSPEC,
					FormSpecs.DEFAULT_ROWSPEC,
					FormSpecs.RELATED_GAP_ROWSPEC,}));

		JLabel lblText = new JLabel(text);
		getContentPane().add(lblText, "2, 2, 3, 1, center, default");

		JButton btnYes = new JButton("Ja");
		btnYes.setActionCommand("yes");
		btnYes.addActionListener(this);
		btnYes.setToolTipText("Den Dialog bestätigen");
		getContentPane().add(btnYes, "2, 4");

		JButton btnNo = new JButton("Nein");
		btnNo.setActionCommand("no");
		btnNo.addActionListener(this);
		btnNo.setToolTipText("Den Dialog ablehnen");
		getContentPane().add(btnNo, "4, 4");

		pack();
	}

	@Override
	public void setVisible(boolean visible) {
		if(visible) {
			value = false;
		}

		super.setVisible(visible);
	}

	public boolean isConfirmed() {
		return value;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "yes":
				value = true;
				break;
			case "no":
				value = false;
				break;
		}

		this.setVisible(false);
	}
}
