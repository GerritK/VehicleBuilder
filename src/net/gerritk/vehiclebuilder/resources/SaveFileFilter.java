package net.gerritk.vehiclebuilder.resources;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class SaveFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		if(f.isDirectory() || f.getName().endsWith(".vbsf")) {
			return true;
		}
		
		return false;
	}

	@Override
	public String getDescription() {
		return "Vehicle Builder Save File (.vbsf)";
	}

}
