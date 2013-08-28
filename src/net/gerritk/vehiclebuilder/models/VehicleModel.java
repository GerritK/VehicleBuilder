package net.gerritk.vehiclebuilder.models;

import java.util.ArrayList;

import net.gerritk.vehiclebuilder.items.Cabin;
import net.gerritk.vehiclebuilder.items.Child;
import net.gerritk.vehiclebuilder.items.Structure;
import net.gerritk.vehiclebuilder.items.Template;

public class VehicleModel extends Model {
	private String name;
	private Cabin cabin;
	private Structure structure;
	private Template template;
	private ArrayList<Child> childs;
	
	public VehicleModel(CabinModel cabins, StructureModel structures, TemplateModel templates) {
		cabin = cabins.getCabins().get(0);
		structure = structures.getStructures().get(0);
		template = templates.getTemplates().get(0);
		childs = new ArrayList<Child>();
	}
	
	public void save() {
		// TODO implement new saveLogic
	}
	
	public void load() {
		// TODO implement new loadLogic
	}
	
	public void export() {
		// TODO implement old saveLogic
	}
	
	/*
	 * Getter & Setter
	 */
	public Cabin getCabin() {
		return cabin;
	}
	
	public void setCabin(Cabin cabin) {
		this.cabin = cabin;
		setChanged();
	}
	
	public Structure getStructure() {
		return structure;
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
		setChanged();
	}

	public ArrayList<Child> getChilds() {
		return childs;
	}

	public void setChilds(ArrayList<Child> childs) {
		this.childs = childs;
		setChanged();
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
