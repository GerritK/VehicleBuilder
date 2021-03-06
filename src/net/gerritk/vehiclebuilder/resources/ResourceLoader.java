package net.gerritk.vehiclebuilder.resources;

import net.gerritk.vehiclebuilder.items.*;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class ResourceLoader {
	private String path;

	public ResourceLoader(String path) throws FileNotFoundException {
		File dir = new File(path);
		if (!dir.exists()) {
			throw new FileNotFoundException("can not find resources folder");
		} else {
			this.path = path;
		}
	}

	public ArrayList<Structure> loadStructures() {
		ArrayList<Structure> structures = new ArrayList<>();

		for(File file : getFiles("structures", new FileNameExtensionFilter("Bilder", "png"))) {
			try {
				Structure s = new Structure(file.getName().replace(".png", ""), ImageIO.read(file));

				structures.add(s);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}

		return structures;
	}

	public ArrayList<Cabin> loadCabins() {
		ArrayList<Cabin> cabins = new ArrayList<>();

		for(File file : getFiles("cabins", new FileNameExtensionFilter("Bilder", "png"))) {
			try {
				Cabin c = new Cabin(file.getName().replace(".png", ""), ImageIO.read(file));

				cabins.add(c);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}

		return cabins;
	}

	public ArrayList<Template> loadTemplates() {
		ArrayList<Template> templates = new ArrayList<>();

		for(File dir : getFiles("templates", null)) {
			if(dir.isDirectory()) {
				BufferedImage left = null;
				BufferedImage base = null;
				BufferedImage right = null;
				BufferedImage extra = null;

				for(File file : getFiles("templates/" + dir.getName(), new FileNameExtensionFilter("Bilder", "png"))) {
					BufferedImage img = null;

					try {
						img = ImageIO.read(file);
					} catch (IOException e) {
						e.printStackTrace();
					}

					if(img != null) {
						if(file.getName().startsWith("left")) {
							left = img;
						} else if(file.getName().startsWith("base")) {
							base = img;
						} else if(file.getName().startsWith("right")) {
							right = img;
						} else if(file.getName().startsWith("extra")) {
							extra = img;
						}
					}
				}
				// TODO include setup file

				templates.add(new Template(dir.getName(), base, left, right, extra));
			}
		}

		return templates;
	}

	public ArrayList<Child> loadChilds() {
		ArrayList<Child> childs = new ArrayList<>();

		for(File file : getFiles("childs", new FileNameExtensionFilter("Bilder", "png"))) {
			try {
				if(file.isDirectory()) continue;
				Child c = new Child(file.getName().replace(".png", ""), ImageIO.read(file), 0, 0);

				childs.add(c);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}

		return childs;
	}

	public ArrayList<Child> loadBluelights() {
        ArrayList<Child> childs = new ArrayList<>();

		for(File file : getFiles("childs/bluelights", new FileNameExtensionFilter("Bilder", "png"))) {
			try {
				if(file.getName().contains("_on")) continue;

				File on = new File(path + "/childs/bluelights/" + file.getName().replace(".png", "") + "_on.png");
				Bluelight b = new Bluelight(file.getName().replace(".png", ""), ImageIO.read(file), ImageIO.read(on), 0, 0);

				childs.add(b);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}

		return childs;
	}

	public File[] getFiles(String subdir, FileFilter filter) {
		ArrayList<File> files = new ArrayList<>();
		File dir = new File(path + "/" + subdir);

		if(dir.exists() && dir.isDirectory()) {
            File[] dirFiles = dir.listFiles();
            if (dirFiles != null) {
                for (File file : dirFiles) {
                    if (filter == null || filter.accept(file)) {
                        files.add(file);
                    }
                }
			}
		}

		File[] result = new File[files.size()];
		files.toArray(result);

		return result;
	}

	/*
	 * Getter & Setter
	 */
	public String getPath() {
		return path;
	}
}
