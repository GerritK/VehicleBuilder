package net.gerritk.vehiclebuilder.resources;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class SaveFileFilter extends FileFilter {
	@Override
	public boolean accept(File f) {
		return f.isDirectory() || f.getName().endsWith(".vbsf");
	}

	@Override
	public String getDescription() {
		return "Vehicle Builder Save File (.vbsf)";
	}
}
