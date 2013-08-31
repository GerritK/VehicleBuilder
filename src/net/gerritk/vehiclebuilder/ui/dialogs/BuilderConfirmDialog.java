package net.gerritk.vehiclebuilder.ui.dialogs;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;
import javax.swing.JButton;

public class BuilderConfirmDialog extends BuilderDialog implements ActionListener {
	private static final long serialVersionUID = -4439614621018739269L;
	
	private boolean value;
	
	public BuilderConfirmDialog(Frame frame, String title, String text) {
		super(frame, title, true);
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
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
