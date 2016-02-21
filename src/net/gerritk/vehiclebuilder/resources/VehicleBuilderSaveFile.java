package net.gerritk.vehiclebuilder.resources;

import com.esotericsoftware.jsonbeans.Json;
import com.esotericsoftware.jsonbeans.JsonReader;
import com.esotericsoftware.jsonbeans.JsonValue;
import net.gerritk.vehiclebuilder.VBLauncher;
import net.gerritk.vehiclebuilder.items.Cabin;
import net.gerritk.vehiclebuilder.items.Child;
import net.gerritk.vehiclebuilder.items.Structure;
import net.gerritk.vehiclebuilder.items.Template;
import net.gerritk.vehiclebuilder.models.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class VehicleBuilderSaveFile {
	private File file;
	private VehicleModel vehicleModel;

	private VehicleBuilderSaveFile(File f, VehicleModel vehicleModel) {
		this.vehicleModel = vehicleModel;
		if(!f.getName().endsWith(".vbsf")) {
			f = new File(f.getAbsoluteFile() + ".vbsf");
		}

		this.file = f;
	}

	private boolean save() {
		Json json = new Json();

		try {
			json.setWriter(new FileWriter(this.file));
		} catch (IOException var5) {
			var5.printStackTrace();
			return false;
		}

		json.writeObjectStart();
		json.writeValue("name", this.vehicleModel.getName());
		json.writeValue("template", this.vehicleModel.getTemplate().toString());
		json.writeObjectStart("cabin");
		json.writeValue("id", this.vehicleModel.getCabin().toString());
		json.writeValue("ox", this.vehicleModel.getCabinOffsetX());
		json.writeValue("oy", this.vehicleModel.getCabinOffsetY());
		json.writeObjectEnd();
		json.writeObjectStart("structure");
		json.writeValue("id", this.vehicleModel.getStructure().toString());
		json.writeValue("ox", this.vehicleModel.getStructureOffsetX());
		json.writeValue("oy", this.vehicleModel.getStructureOffsetY());
		json.writeObjectEnd();
		json.writeArrayStart("childs");

		for (Child e : this.vehicleModel.getChilds()) {
			json.writeObjectStart();
			json.writeValue("id", e.toString());
			json.writeValue("x", e.getX());
			json.writeValue("y", e.getY());
			json.writeValue("customName", e.getCustomName());
			json.writeValue("isBehind", e.isBehind());
			json.writeObjectEnd();
		}

		json.writeArrayEnd();
		json.writeObjectEnd();

		try {
			json.getWriter().close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean load() {
		if(!file.exists()) {
			return false;
		} else {
			JsonValue save = (new JsonReader()).parse(this.file);
			this.vehicleModel.setName(this.loadName(save.getString("name")));
			this.vehicleModel.setTemplate(this.loadTemplate(save.getString("template")));
			JsonValue cabin = save.get("cabin");
			this.vehicleModel.setCabin(this.loadCabin(cabin.getString("id")));
			this.vehicleModel.setCabinOffsetX(cabin.getInt("ox"));
			this.vehicleModel.setCabinOffsetY(cabin.getInt("oy"));
			JsonValue structure = save.get("structure");
			this.vehicleModel.setStructure(this.loadStructure(structure.getString("id")));
			this.vehicleModel.setStructureOffsetX(structure.getInt("ox"));
			this.vehicleModel.setStructureOffsetY(structure.getInt("oy"));
			JsonValue childs = save.get("childs");
			this.vehicleModel.setChilds(this.loadChilds(childs));
			return true;
		}
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
			if(c.getName().equals(value)) {
				return c;
			}
		}

		return null;
	}

	private Structure loadStructure(String value) {
		StructureModel structureModel = VBLauncher.getInstance().getModel(StructureModel.class);

		for (Structure s : structureModel.getStructures()) {
			if (s.getName().equals(value)) {
				return s;
			}
		}

		return null;
	}

	private Template loadTemplate(String value) {
		TemplateModel templateModel = VBLauncher.getInstance().getModel(TemplateModel.class);

		for (Template t : templateModel.getTemplates()) {
			if (t.getName().equals(value)) {
				return t;
			}
		}

		return null;
	}

	private ArrayList<Child> loadChilds(JsonValue value) {
		ChildModel childModel = VBLauncher.getInstance().getModel(ChildModel.class);
		ArrayList childs = new ArrayList();

		for (JsonValue child = value.child(); child != null; child = child.next()) {

			for (Child c : childModel.getChilds()) {
				if (c.toString().equals(child.getString("id"))) {
					Child cc = c.clone();
					cc.setX(child.getInt("x"));
					cc.setY(child.getInt("y"));
					cc.setCustomName(child.getString("customName"));
					cc.setBehind(child.getBoolean("isBehind"));
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
