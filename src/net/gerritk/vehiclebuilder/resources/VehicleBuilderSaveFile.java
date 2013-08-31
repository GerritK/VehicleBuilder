package net.gerritk.vehiclebuilder.resources;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import net.gerritk.vehiclebuilder.VBLauncher;
import net.gerritk.vehiclebuilder.items.Cabin;
import net.gerritk.vehiclebuilder.items.Child;
import net.gerritk.vehiclebuilder.items.Structure;
import net.gerritk.vehiclebuilder.items.Template;
import net.gerritk.vehiclebuilder.models.CabinModel;
import net.gerritk.vehiclebuilder.models.ChildModel;
import net.gerritk.vehiclebuilder.models.StructureModel;
import net.gerritk.vehiclebuilder.models.TemplateModel;
import net.gerritk.vehiclebuilder.models.VehicleModel;

public class VehicleBuilderSaveFile {
	private File file;
	private VehicleModel vehicleModel;
	
	private VehicleBuilderSaveFile(File f, VehicleModel vehicleModel) {
		this.vehicleModel = vehicleModel;
		this.file = f;
	}
	
	private boolean save() {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			
			writer.write("name='" + vehicleModel.getName() + "';");
			writer.write("cabin='" + vehicleModel.getCabin().toString() + "';");
			writer.write("structure='" + vehicleModel.getStructure().toString() + "';");
			writer.write("template='" + vehicleModel.getTemplate().toString() + "';");
			writer.write("childs={");
			for(Child child : vehicleModel.getChilds()) {
				writer.write(child.toString() + "<" + child.getX() + "," + child.getY() + "," + child.getCustomName() + "," + child.isBehind() + ">#");
			}
			writer.write("}");
			
			writer.close();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private boolean load() {
		if(!file.exists()) {
			return false;
		}
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			
			String items[] = reader.readLine().split(";");
			for(String item : items) {
				String key = item.split("=")[0];
				String value = item.split("=")[1];
				
				switch(key) {
					case "name":
						vehicleModel.setName(loadName(value));
						break;
					case "cabin":
						vehicleModel.setCabin(loadCabin(value));
						break;
					case "structure":
						vehicleModel.setStructure(loadStructure(value));
						break;
					case "template":
						vehicleModel.setTemplate(loadTemplate(value));
						break;
					case "childs":
						vehicleModel.setChilds(loadChilds(value));
						break;
				}
			}
			
			reader.close();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private String loadName(String value) {
		if(value.equals("null")) {
			value = null;
		}
		
		return value;
	}
	
	private Cabin loadCabin(String value) {
		CabinModel cabinModel = VBLauncher.getInstance().getModel(CabinModel.class);
		
		for(Cabin c : cabinModel.getCabins()) {
			if(c.getName().equals(value.replace("'", ""))) {
				return c;
			}
		}
		
		return null;
	}
	
	private Structure loadStructure(String value) {
		StructureModel structureModel = VBLauncher.getInstance().getModel(StructureModel.class);

		for(Structure s : structureModel.getStructures()) {
			if(s.getName().equals(value.replace("'", ""))) {
				return s;
			}
		}
		
		return null;
	}
	
	private Template loadTemplate(String value) {
		TemplateModel templateModel = VBLauncher.getInstance().getModel(TemplateModel.class);

		for(Template t : templateModel.getTemplates()) {
			if(t.getName().equals(value.replace("'", ""))) {
				return t;
			}
		}
		
		return null;
	}
	
	private ArrayList<Child> loadChilds(String value) {
		ChildModel childModel = VBLauncher.getInstance().getModel(ChildModel.class);
		ArrayList<Child> childs = new ArrayList<Child>();
		
		String values[] = value.replace("{", "").replace("#}", "").split("#");
		for(String v : values) {
			if(v.isEmpty()) continue;
			
			for(Child c : childModel.getChilds()) {
				String name = v.split("<")[0];
				String props = v.split("<")[1];
				
				int x = Integer.parseInt(props.split(",")[0]);
				int y = Integer.parseInt(props.split(",")[1]);
				String customName = props.split(",")[2];
				boolean behind = Boolean.parseBoolean(props.split(",")[3]);
				
				if(customName.equals("null")) {
					customName = null;
				}
				
				if(c.getName().equals(name)) {
					Child cc = c.clone();
					cc.setX(x);
					cc.setY(y);
					cc.setCustomName(customName);
					cc.setBehind(behind);
					childs.add(cc);
				}
			}
		}
		
		return childs;
	}
	
	public static boolean loadFromFile(File f, VehicleModel vehicleModel) {
		VehicleBuilderSaveFile saveFile = new VehicleBuilderSaveFile(f, vehicleModel);
		
		return saveFile.load();
	}
	
	public static boolean saveToFile(File f, VehicleModel vehicleModel) {
		VehicleBuilderSaveFile saveFile = new VehicleBuilderSaveFile(f, vehicleModel);
		
		return saveFile.save();
	}
}
