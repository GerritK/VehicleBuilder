package net.gerritk.vehiclebuilder.models;

import java.util.ArrayList;

import net.gerritk.vehiclebuilder.Launcher;
import net.gerritk.vehiclebuilder.items.Cabin;

public class CabinModel extends Model {
	private ArrayList<Cabin> cabins;
	
	public CabinModel() {
		cabins = new ArrayList<Cabin>();
		cabins.addAll(Launcher.getInstance().getResourceLoader().loadCabins());
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
