package net.gerritk.vehiclebuilder.models;

import java.util.ArrayList;

import net.gerritk.vehiclebuilder.Launcher;
import net.gerritk.vehiclebuilder.items.Template;

public class TemplateModel extends Model {
	private ArrayList<Template> templates;
	
	public TemplateModel() {
		templates = new ArrayList<Template>();
		templates.addAll(Launcher.getInstance().getResourceLoader().loadTemplates());
	}

	/*
	 * Getter & Setter
	 */
	public ArrayList<Template> getTemplates() {
		return templates;
	}
	
	public void setTemplates(ArrayList<Template> templates) {
		this.templates = templates;
		setChanged();
	}
}
