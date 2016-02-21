package net.gerritk.vehiclebuilder.models;

import net.gerritk.vehiclebuilder.VBLauncher;
import net.gerritk.vehiclebuilder.items.Cabin;

import java.util.ArrayList;

public class CabinModel extends Model {
	private ArrayList<Cabin> cabins;

	public CabinModel() {
		cabins = new ArrayList<Cabin>();
		cabins.addAll(VBLauncher.getInstance().getResourceLoader().loadCabins());
	}

	/*
	 * Getter & Setter
	 */
	public ArrayList<Cabin> getCabins() {
		return cabins;
	}

	public void setCabins(ArrayList<Cabin> cabins) {
		this.cabins = cabins;
		setChanged();
	}
}
