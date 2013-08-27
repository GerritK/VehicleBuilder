package net.gerritk.vehiclebuilder.ui;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import net.gerritk.vehiclebuilder.handlers.ButtonHandler;
import net.gerritk.vehiclebuilder.handlers.MenuHandler;

public class MenuBar extends JMenuBar {
	private static final long serialVersionUID = -6304231960922275347L;

	public MenuBar() {
		MenuHandler handler = new MenuHandler();
		
		// File Menu
		JMenu mnFile = new JMenu("Datei");
		add(mnFile);
		
		JMenuItem mntmSave = new JMenuItem("Speichern");
		mntmSave.setActionCommand("save");
		mntmSave.addActionListener(new ButtonHandler());
		mnFile.add(mntmSave);
		
		mnFile.addSeparator();
		
		JMenuItem mntmQuit = new JMenuItem("Beenden");
		mntmQuit.setActionCommand("quit");
		mntmQuit.addActionListener(handler);
		mnFile.add(mntmQuit);
		
		// Help Menu
		JMenu mnHelp = new JMenu("Hilfe");
		add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("Über");
		mntmAbout.setActionCommand("about");
		mntmAbout.addActionListener(handler);
		mnHelp.add(mntmAbout);
	}

}
