package net.gerritk.vehiclebuilder.ui.dialogs;

import java.awt.Frame;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;
import javax.swing.JButton;

import net.gerritk.vehiclebuilder.VBLauncher;
import net.gerritk.vehiclebuilder.resources.IconSet;
import java.awt.Font;

public class BuilderAboutDialog extends BuilderDialog {
	private static final long serialVersionUID = 582772938954403618L;

	public BuilderAboutDialog(Frame frame) {
		super(frame, "Über");
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:default"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		JLabel lblBanner = new JLabel(IconSet.BANNER);
		getContentPane().add(lblBanner, "2, 2");
		
		JLabel lblCopy = new JLabel("\u00A9 2013 - K.Design - Gerrit Kaul");
		lblCopy.setFont(lblCopy.getFont().deriveFont(Font.BOLD));
		getContentPane().add(lblCopy, "2, 6");
		
		JLabel lblVersion = new JLabel("Version " + VBLauncher.VERSION);
		getContentPane().add(lblVersion, "2, 8");
		
		JButton btnDonate = new JButton("Spenden");
		getContentPane().add(btnDonate, "2, 12");
		
		pack();
	}
}
