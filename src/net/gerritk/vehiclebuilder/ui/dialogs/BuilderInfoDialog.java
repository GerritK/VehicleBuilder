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

public class BuilderInfoDialog extends BuilderDialog {
	private static final long serialVersionUID = 5998607902004365380L;

	private JLabel lblInfo;
	
	public BuilderInfoDialog(Frame frame, String title, String info) {
		super(frame, title);
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		lblInfo = new JLabel(info);
		getContentPane().add(lblInfo, "2, 2, center, default");
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(btnOk, "2, 4, center, default");
		
		pack();
	}
	
	public void setInfo(String info) {
		lblInfo.setText(info);
		pack();
	}
}
