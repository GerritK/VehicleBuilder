package net.gerritk.vehiclebuilder.models;

import java.util.ArrayList;

import net.gerritk.vehiclebuilder.VBLauncher;
import net.gerritk.vehiclebuilder.items.Child;

public class ChildModel extends Model {
	private ArrayList<Child> childs;
	
	public ChildModel() {
		childs = new ArrayList<Child>();
		childs.addAll(VBLauncher.getInstance().getResourceLoader().loadChilds());
		childs.addAll(VBLauncher.getInstance().getResourceLoader().loadBluelights());
	}
	
	/*
	 * Getter & Setter
	 */
	public ArrayList<Child> getChilds() {
		return childs;
	}
	
	public void setChilds(ArrayList<Child> childs) {
		this.childs = childs;
		setChanged();
	}
}
