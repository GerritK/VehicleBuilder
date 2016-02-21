package net.gerritk.vehiclebuilder.models;

import net.gerritk.vehiclebuilder.VBLauncher;
import net.gerritk.vehiclebuilder.items.*;
import net.gerritk.vehiclebuilder.resources.SaveFileFilter;
import net.gerritk.vehiclebuilder.resources.VehicleBuilderSaveFile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class VehicleModel extends Model {
	private String name;
	private Cabin cabin;
	private Structure structure;
	private Template template;
	private ArrayList<Child> childs;
	private Point cabinOffset;
	private Point structOffset;

	public VehicleModel(CabinModel cabins, StructureModel structures, TemplateModel templates) {
		this.cabin = cabins.getCabins().get(0);
		this.structure = structures.getStructures().get(0);
		this.template = templates.getTemplates().get(0);
		this.childs = new ArrayList<>();
		this.name = this.structure.getName();
		this.cabinOffset = new Point(0, 0);
		this.structOffset = new Point(0, 0);
	}

	public boolean save() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setFileFilter(new SaveFileFilter());
		fc.setSelectedFile(new File(this.getName() + ".vbsf"));

		int value = fc.showSaveDialog(VBLauncher.getInstance().getFrame());
		if (value == 0) {
			File f = fc.getSelectedFile();
			return VehicleBuilderSaveFile.saveToFile(f, this);
		} else {
			return true;
		}
	}

	public boolean load() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setSelectedFile(new File("vehicle.vbsf"));
		fc.setFileFilter(new SaveFileFilter());

		int value = fc.showOpenDialog(VBLauncher.getInstance().getFrame());
		if (value == 0) {
			File f = fc.getSelectedFile();
			return VehicleBuilderSaveFile.loadFromFile(f, this);
		} else {
			return true;
		}
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	public boolean export() {
		File dir = new File("output");

		if(!dir.exists()) {
			dir.mkdir();
		}

		File off = new File(dir.getPath() + "/" + name + ".png");
		try {
			ImageIO.write(generateOutput(false), "png", off);
		} catch(IOException ex) {
			ex.printStackTrace();
			return false;
		}

		File on = new File(dir.getPath() + "/" + name + "_on.png");
		try {
			ImageIO.write(generateOutput(true), "png", on);
		} catch(IOException ex) {
			ex.printStackTrace();
			off.delete();
			return false;
		}

		return true;
	}

	public BufferedImage generateOutput(boolean bluelight) {
		BufferedImage out = generateTemplate();
		if(out != null) {
			BufferedImage outVehicle = generateVehicle(bluelight);
			Graphics2D g = out.createGraphics();

			int border = template.getBorder();
			int bottomBorder = border;
			if(template.getExtra() != null) {
				bottomBorder = template.getExtra().getHeight();
			}

			g.drawImage(outVehicle, border, out.getHeight() - bottomBorder - outVehicle.getHeight(), null);
		} else {
			out = generateVehicle(bluelight);
		}

		return out;
	}

	public BufferedImage generateVehicle(boolean bluelight) {
		Dimension d = getVehicleDimension();
		BufferedImage out;

		if(d.width > 0 && d.height > 0) {
			out = new BufferedImage(d.width, d.height, BufferedImage.TRANSLUCENT);
			Graphics2D g = out.createGraphics();
			Point off = new Point(0, 0);

			drawChilds(g, d, off, true, bluelight);
			drawCabin(g, d, off);
			drawStructure(g, d, off);
			drawChilds(g, d, off, false, bluelight);
		} else {
			out = null;
		}

		return out;
	}

	public BufferedImage generateTemplate() {
		Dimension d = getOutputDimension();
		BufferedImage out;

		if(d.width > 0 && d.height > 0) {
			out = new BufferedImage(d.width, d.height, BufferedImage.TRANSLUCENT);
			Graphics2D g = out.createGraphics();

			drawTemplate(g, d);
		} else {
			out = null;
		}

		return out;
	}

	public Dimension getVehicleDimension() {
		Dimension d = new Dimension();

		if(cabin != null) {
            d.width += cabin.getWidth() - cabinOffset.getX();
            d.height = (int) (cabin.getHeight() + cabinOffset.getY());
        }

		if(structure != null) {
            d.width += structure.getWidth() + structOffset.getX();
            if (structure.getHeight() + structOffset.getY() > d.height) {
                d.height = (int) (structure.getHeight() + structOffset.getY());
            }
		}

		if(childs != null) {
			Point max = Child.getMaxPositions(childs);
			Point min = Child.getMinPositions(childs);

			if(d.width < max.x) d.width += max.x - d.width;

			if(d.width < d.width - min.x) d.width -= min.x;

			if(d.height < d.height - min.y) d.height -= min.y;
		}

		return d;
	}

	public Dimension getOutputDimension() {
		Dimension d = getVehicleDimension();

		if(template != null && template.getImage() != null) {
			if(template.getHeight() > d.height) {
				d.height = template.getHeight();
			}

			if(template.getExtra() != null) {
				d.height += template.getExtra().getHeight() - template.getBorder();
			}

			d.width += template.getBorder() * 2;
		}

		return d;
	}

	public void sortChildUp(Child child) {
		int sindex = childs.indexOf(child);

		if(sindex > 0) {
			Child tmp = childs.get(sindex - 1);

			childs.set(sindex - 1, child);
			childs.set(sindex, tmp);
			setChanged();
		}
	}

	public void sortChildDown(Child child) {
		int sindex = childs.indexOf(child);

		if(sindex + 1 < childs.size()) {
			Child tmp = childs.get(sindex + 1);

			childs.set(sindex + 1, child);
			childs.set(sindex, tmp);
			setChanged();
		}
	}

	/*
	 * Private Methods
	 */
	private void drawTemplate(Graphics2D g, Dimension d) {
		if(template != null && template.getImage() != null) {
			g.drawImage(template.getImage(d.width), 0, 0, null);
		}
	}

	private void drawChilds(Graphics2D g, Dimension d, Point p, boolean behind, boolean bluelight) {
		if(childs != null) {
			// NEGATIV VALUES!!!
			Point off = Child.getMinPositions(childs);

			for(Child child : childs) {
				int yc = child.getY() - off.y;
				int xc = child.getX() - off.x;

				if(child.isBehind() == behind) {
					if(child instanceof Bluelight && bluelight) {
						Bluelight bl = (Bluelight) child;
						g.drawImage(bl.getOn(), xc, yc, null);
					} else {
						g.drawImage(child.getImage(), xc, yc, null);
					}
				}
			}

            p.x -= off.x;
        }
	}

	private void drawCabin(Graphics2D g, Dimension d, Point p) {
		if(cabin != null) {
			g.drawImage(cabin.getImage(), p.x, (int) d.getHeight() - cabin.getHeight() - p.y - cabinOffset.y, null);
			p.x += cabin.getWidth() - cabinOffset.x;
		}
	}

	private void drawStructure(Graphics2D g, Dimension d, Point p) {
		if(structure != null) {
			g.drawImage(structure.getImage(), p.x + structOffset.x, (int) d.getHeight() - structure.getHeight() - p.y - structOffset.y, null);
			p.x += structure.getWidth() - structOffset.x;
		}
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
		setChanged();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		setChanged();
	}

	public int getCabinOffsetX() {
		return cabinOffset.x;
	}

	public void setCabinOffsetX(int offset) {
		this.cabinOffset.x = offset;
		setChanged();
	}

	public int getCabinOffsetY() {
		return cabinOffset.y;
	}

	public void setCabinOffsetY(int offset) {
		this.cabinOffset.y = offset;
		setChanged();
	}

	public int getStructureOffsetX() {
		return structOffset.x;
	}

	public void setStructureOffsetX(int offset) {
		this.structOffset.x = offset;
		setChanged();
	}

	public int getStructureOffsetY() {
		return structOffset.y;
	}

	public void setStructureOffsetY(int offset) {
		this.structOffset.y = offset;
		setChanged();
	}
}
