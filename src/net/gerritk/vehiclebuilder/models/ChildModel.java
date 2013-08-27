package net.gerritk.vehiclebuilder.models;

import java.util.ArrayList;

import net.gerritk.vehiclebuilder.Launcher;
import net.gerritk.vehiclebuilder.items.Child;

public class ChildModel extends Model {
	private ArrayList<Child> childs;
	
	public ChildModel() {
		childs = new ArrayList<Child>();
		childs.addAll(Launcher.getInstance().getResourceLoader().loadChilds());
		childs.addAll(Launcher.getInstance().getResourceLoader().loadBluelights());
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
