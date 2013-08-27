package net.gerritk.vehiclebuilder.models;

import java.util.ArrayList;

import net.gerritk.vehiclebuilder.VBLauncher;
import net.gerritk.vehiclebuilder.items.Structure;

public class StructureModel extends Model {
	private ArrayList<Structure> structures;
	
	public StructureModel() {
		structures = new ArrayList<Structure>();
		structures.addAll(VBLauncher.getInstance().getResourceLoader().loadStructures());
	}

	/*
	 * Getter & Setter
	 */
	public ArrayList<Structure> getStructures() {
		return structures;
	}
	
	public void setStructures(ArrayList<Structure> structures) {
		this.structures = structures;
		setChanged();
	}
}
