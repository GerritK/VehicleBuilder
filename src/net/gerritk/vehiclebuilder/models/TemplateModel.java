package net.gerritk.vehiclebuilder.models;

import net.gerritk.vehiclebuilder.VBLauncher;
import net.gerritk.vehiclebuilder.items.Template;

import java.util.ArrayList;

public class TemplateModel extends Model {
	private ArrayList<Template> templates;

	public TemplateModel() {
		templates = new ArrayList<>();
		templates.addAll(VBLauncher.getInstance().getResourceLoader().loadTemplates());
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
