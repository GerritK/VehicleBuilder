package net.gerritk.vehiclebuilder.ui.dialogs;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import net.gerritk.vehiclebuilder.VBLauncher;
import net.gerritk.vehiclebuilder.resources.IconSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class BuilderAboutDialog extends BuilderDialog implements ActionListener {
	private static final long serialVersionUID = 582772938954403618L;

	public BuilderAboutDialog(Frame frame) {
		super(frame, "Über");
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
					FormSpecs.RELATED_GAP_ROWSPEC,
					FormSpecs.DEFAULT_ROWSPEC,
					FormSpecs.RELATED_GAP_ROWSPEC,
					FormSpecs.DEFAULT_ROWSPEC,
					FormSpecs.RELATED_GAP_ROWSPEC,
					FormSpecs.DEFAULT_ROWSPEC,
					FormSpecs.RELATED_GAP_ROWSPEC,
					FormSpecs.DEFAULT_ROWSPEC,
					FormSpecs.RELATED_GAP_ROWSPEC,
					FormSpecs.DEFAULT_ROWSPEC,
					FormSpecs.RELATED_GAP_ROWSPEC,
					FormSpecs.DEFAULT_ROWSPEC,
					FormSpecs.RELATED_GAP_ROWSPEC,
					FormSpecs.DEFAULT_ROWSPEC,
					FormSpecs.RELATED_GAP_ROWSPEC,}));

		JLabel lblBanner = new JLabel(IconSet.BANNER);
		getContentPane().add(lblBanner, "2, 2, 3, 1, center, default");

		JLabel lblCopy = new JLabel("\u00A9 2013 - K.Design - Gerrit Kaul");
		lblCopy.setFont(lblCopy.getFont().deriveFont(Font.BOLD));
		getContentPane().add(lblCopy, "2, 6, 3, 1, center, default");

		JLabel lblVersion = new JLabel("Version " + VBLauncher.VERSION);
		getContentPane().add(lblVersion, "2, 8, 3, 1, center, default");

		JButton lblSupport = new JButton("Support: http://forum.leitstellenspiel.de");
		lblSupport.setActionCommand("support");
		lblSupport.addActionListener(this);
		lblSupport.setToolTipText("Support Seite öffnen");
		lblSupport.setContentAreaFilled(false);
		lblSupport.setFocusable(false);
		getContentPane().add(lblSupport, "2, 10, 3, 1, center, default");

		JButton btnDonate = new JButton("Spenden");
		btnDonate.setActionCommand("donate");
		btnDonate.addActionListener(this);
		btnDonate.setToolTipText("Die Arbeit mit einer Spende unterstützen");
		getContentPane().add(btnDonate, "2, 14");

		JButton btnClose = new JButton("Schließen");
		btnClose.setActionCommand("close");
		btnClose.addActionListener(this);
		btnClose.setToolTipText("Das Fenster schließen");
		getContentPane().add(btnClose, "4, 14");

		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("donate")) {
			try {
				Desktop.getDesktop().browse(new URI("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=KUETYKZHNNEXC"));
			} catch (IOException | URISyntaxException ex) {
				ex.printStackTrace();
			}
		} else if(e.getActionCommand().equals("close")) {
			dispose();
		} else if(e.getActionCommand().equals("support")) {
			try {
				Desktop.getDesktop().browse(new URI("http://forum.leitstellenspiel.de/index.php?page=Thread&threadID=463"));
			} catch (IOException | URISyntaxException ex) {
				ex.printStackTrace();
			}
		}
	}
}
