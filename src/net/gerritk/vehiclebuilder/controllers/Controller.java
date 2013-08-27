package net.gerritk.vehiclebuilder.controllers;

import java.awt.event.ActionListener;

import net.gerritk.vehiclebuilder.Launcher;

public abstract class Controller implements ActionListener {
	private Launcher launcher;
	
	public Controller(Launcher launcher) {
		
	}
	
	/*
	 * Getter & Setter
	 */
	public Launcher getLauncher() {
		return launcher;
	}
}
