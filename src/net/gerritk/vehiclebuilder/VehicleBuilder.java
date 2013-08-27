package net.gerritk.vehiclebuilder;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.gerritk.vehiclebuilder.handlers.*;
import net.gerritk.vehiclebuilder.items.*;
import net.gerritk.vehiclebuilder.resources.IconSet;
import net.gerritk.vehiclebuilder.resources.ResourceLoader;
import net.gerritk.vehiclebuilder.ui.*;

import com.jgoodies.forms.layout.*;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.*;
public class VehicleBuilder extends JPanel{
	private static final long serialVersionUID = 3091039237739443011L;
	
	private static VehicleBuilder instance;
	
	private JFrame frame;
	private OutputPanel outputPanel;
	private ChildPanel childPanel;
	
	private boolean bluelight;
	
	private JComboBox<Child> childSelector;
	
	private BufferedImage output;
	private BufferedImage outVehicle;
	private BufferedImage outTemplate;
	private Cabin selectedCabin;
	private Structure selectedStructure;
	private Template selectedTemplate;
	private ArrayList<Child> selectedChilds;
	private float scale = 1;
	
	private ArrayList<Cabin> cabins;
	private ArrayList<Structure> structures;
	private ArrayList<Child> childs;
	private ArrayList<Template> templates;
	private JTextField txtName;
	
	public VehicleBuilder() {
		// Data		
		cabins = new ArrayList<Cabin>();
		structures = new ArrayList<Structure>();
		templates = new ArrayList<Template>();
		childs = new ArrayList<Child>();
		
		ResourceLoader res = new ResourceLoader("resources/items");
		cabins.addAll(res.loadCabins());
		structures.addAll(res.loadStructures());
		templates.addAll(res.loadTemplates());
		childs.addAll(res.loadChilds());
		childs.addAll(res.loadBluelights());
		
		Collections.sort(cabins);
		Collections.sort(structures);
		Collections.sort(templates);
		Collections.sort(childs);
		
		setSelectedCabin(cabins.get(0));
		setSelectedStructure(structures.get(0));
		setSelectedTemplate(templates.get(0));
		selectedChilds = new ArrayList<Child>();
		
		// Frame & Panel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		frame = new JFrame("Vehicle Builder für Leitstellenspiel.de");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("right:default"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(0dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("top:default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		JSeparator separator = new JSeparator();
		add(separator, "2, 5, 9, 1");
		

		// Panels
		// Output
		outputPanel = new OutputPanel(this);
		add(outputPanel, "6, 7, 3, 9, fill, fill");
		// Child
		childPanel = new ChildPanel(this);
		add(childPanel, "10, 7, 1, 9, fill, fill");
		
		// Handlers
		SelectorHandler selector = new SelectorHandler();
		SliderHandler sliderHandler = new SliderHandler();
		ButtonHandler buttonHandler = new ButtonHandler();
		
		JLabel lblBanner = new JLabel(IconSet.BANNER);
		add(lblBanner, "6, 1, 3, 1, center, default");
		
		JLabel lblName = new JLabel("Name");
		add(lblName, "6, 3, right, default");
		
		txtName = new JTextField();
		add(txtName, "8, 3, fill, default");
		txtName.setColumns(10);
				
		// Cabin
		JLabel lblCabin = new JLabel("Kabine");
		add(lblCabin, "2, 7, right, default");
		
		JComboBox<Cabin> cabinSelector = new JComboBox<Cabin>();
		for(Cabin c : cabins) {
			cabinSelector.addItem(c);
		}
		cabinSelector.addItemListener(selector);
		add(cabinSelector, "4, 7, fill, default");
		
		// Color
		int sc = outputPanel.getBackground().getBlue();
		JSlider slider = new JSlider();
		slider.setToolTipText("Hintergrundfarbe der Vorschau");
		slider.setName("color");
		slider.setMaximum(255);
		slider.setValue(sc);
		slider.addChangeListener(sliderHandler);
		add(slider, "6, 17, 3, 1");
		
		// Child
		JLabel lblChild = new JLabel("Zusatz");
		add(lblChild, "2, 13, right, default");
		
		childSelector = new JComboBox<Child>();
		for(Child c : childs) {
			childSelector.addItem(c);
		}
		childSelector.addItemListener(selector);
		add(childSelector, "4, 13, fill, default");
		
		JButton btnAdd = new JButton("Hinzuf\u00FCgen");
		btnAdd.setToolTipText("Den gewählten Zusatz zur Liste hinzufügen");
		btnAdd.setActionCommand("addChild");
		btnAdd.addActionListener(buttonHandler);
		add(btnAdd, "4, 15, fill, default");
		
		// Structure
		JLabel lblStructure = new JLabel("Aufbau");
		add(lblStructure, "2, 9, right, default");
		
		JComboBox<Structure> structureSelector = new JComboBox<Structure>();
		for(Structure s : structures) {
			structureSelector.addItem(s);
		}
		structureSelector.addItemListener(selector);
		add(structureSelector, "4, 9, fill, default");
		
		// Template
		JLabel lblTemplate = new JLabel("Template");
		add(lblTemplate, "2, 11");

		JComboBox<Template> templateSelector = new JComboBox<Template>();
		for(Template t : templates) {
			templateSelector.addItem(t);
		}
		templateSelector.addItemListener(selector);
		add(templateSelector, "4, 11, fill, default");
		
		// Save
		JButton btnSave = new JButton("Speichern");
		btnSave.setActionCommand("save");
		btnSave.addActionListener(buttonHandler);
		add(btnSave, "6, 21, 3, 1, center, default");		
		
		JSeparator separator_1 = new JSeparator();
		add(separator_1, "2, 19, 9, 1");

		// Menu Bar
		JMenuBar menuBar = new MenuBar();
		frame.setJMenuBar(menuBar);
		
		// Final Frame Setup
		frame.getContentPane().add(this);
		frame.pack();
		frame.setMinimumSize(frame.getSize());
		
		setFrameToScreenCenter();
		
		frame.setVisible(true);
		repaint();
	}
	
	/*
	 * Public Methods
	 */
	public void setFrameToScreenCenter() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point wp = new Point((int) (screenSize.getWidth() - frame.getWidth()) / 2, (int) (screenSize.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(wp);
	}
	
	public void onCabinChange() {
		// TODO what to do when changed
		onVehicleChange();
	}
	
	public void onStructureChange() {
		// TODO what to do when changed
		onVehicleChange();
	}
	
	public void onTemplateChange() {
		Dimension d = getOutputDimension();
		
		if(d.width > 0 && d.height > 0) {
			outTemplate = new BufferedImage(d.width, d.height, BufferedImage.TRANSLUCENT);
			Graphics2D g = outTemplate.createGraphics();
			
			drawTemplate(g, d);
		} else {
			outTemplate = null;
		}
		
		repaint();
	}
	
	public void onChildsChange() {
		childPanel.onChildsChange();
		onVehicleChange();
	}
	
	public void onVehicleChange() {
		Dimension d = getVehicleDimension();
		
		if(d.width > 0 && d.height > 0) {
			outVehicle = new BufferedImage(d.width, d.height, BufferedImage.TRANSLUCENT);
			Graphics2D g = outVehicle.createGraphics();
			Point off = new Point(0, 0);
			
			//drawTemplate(g, d, off);
			drawChilds(g, d, off, true);
			drawCabin(g, d, off);
			drawStructure(g, d, off);
			drawChilds(g, d, off, false);
		} else {
			outVehicle = null;
		}
		
		onTemplateChange();
		
		repaint();
	}
	
	public void drawTemplate(Graphics2D g, Dimension d) {
		if(selectedTemplate != null && selectedTemplate.getImage() != null) {
			g.drawImage(selectedTemplate.getImage(d.width), 0, 0, null);
		}
	}
	
	public void drawChilds(Graphics2D g, Dimension d, Point p, boolean behind) {
		if(selectedChilds != null) {
			// NEGATIV VALUES!!!
			Point off = Child.getMinPositions(selectedChilds);
			
			for(Child child : selectedChilds) {
				int yc = child.getY() - off.y;
				int xc = child.getX() - off.x;
				
				if(child.isBehind() == behind) {
					if(child instanceof Bluelight && isBluelight()) {
						Bluelight bluelight = (Bluelight) child;
						g.drawImage(bluelight.getOn(), xc, yc, null);
					} else {
						g.drawImage(child.getImage(), xc, yc, null);
					}
				}
			}
			p.x -= off.x;
		}
	}
	
	public void drawCabin(Graphics2D g, Dimension d, Point p) {
		if(selectedCabin != null) {
			g.drawImage(selectedCabin.getImage(), p.x, outVehicle.getHeight() - selectedCabin.getHeight() - p.y, null);
			p.x += selectedCabin.getWidth();
		}
	}
	
	public void drawStructure(Graphics2D g, Dimension d, Point p) {
		if(selectedStructure != null) {
			g.drawImage(selectedStructure.getImage(), p.x, outVehicle.getHeight() - selectedStructure.getHeight() - p.y, null);
			p.x += selectedStructure.getWidth();
		}
	}
	
	public Dimension getOutputDimension() {
		Dimension d = getVehicleDimension();
		
		if(selectedTemplate != null && selectedTemplate.getImage() != null) {
			if(selectedTemplate.getHeight() > d.height) {
				d.height = selectedTemplate.getHeight();
			}
			
			if(selectedTemplate.getExtra() != null) {
				d.height += selectedTemplate.getExtra().getHeight() - selectedTemplate.getBorder();
			}
		
			d.width += selectedTemplate.getBorder() * 2;
		}
		
		return d;
	}
	
	public Dimension getVehicleDimension() {
		Dimension d = new Dimension();
		
		if(selectedCabin != null) {
			d.width += selectedCabin.getWidth();
			d.height = selectedCabin.getHeight();
		}
		
		if(selectedStructure != null) {
			d.width += selectedStructure.getWidth();
			if(selectedStructure.getHeight() > d.height) {
				d.height = selectedStructure.getHeight();
			}
		}
		
		if(selectedChilds != null) {
			Point max = Child.getMaxPositions(selectedChilds);
			Point min = Child.getMinPositions(selectedChilds);
			
			if(d.width < max.x) d.width += max.x - d.width;
			
			if(d.width < d.width - min.x) d.width -= min.x;
			
			if(d.height < d.height - min.y) d.height -= min.y;
		}
		
		return d;
	}
	
	@Override
	public void repaint() {
		Dimension d = new Dimension();
		if(outTemplate != null) {
			d = new Dimension(outTemplate.getWidth(), outTemplate.getHeight());
		}
		if(outVehicle != null) {
			if(outVehicle.getWidth() > d.getWidth()) {
				d.width = outVehicle.getWidth();
			}
			if(outVehicle.getHeight() > d.getHeight()) {
				d.height = outVehicle.getHeight();
			}
		}
		
		if(d.width > 0 && d.height > 0) {
			int border = 0;
			int bottomBorder = 0;
			
			if(outTemplate != null) {
				border = (outTemplate.getWidth() - outVehicle.getWidth()) / 2;
				bottomBorder = border;
				if(selectedTemplate != null && selectedTemplate.getExtra() != null) {
					bottomBorder = selectedTemplate.getExtra().getHeight();
				}
			}
			
			output = new BufferedImage(d.width, d.height, BufferedImage.TRANSLUCENT);
			Graphics2D g = output.createGraphics();
			g.drawImage(outTemplate, 0, 0, null);
			g.drawImage(outVehicle, border, d.height - bottomBorder - outVehicle.getHeight(), null);
		}
		
		super.repaint();
	}
	
	public void quit() {
		frame.dispose();
	}
	
	/*
	 * Getter & Setter
	 */
	public BufferedImage getOutput() {
		return output;
	}
	
	public ArrayList<Cabin> getCabins() {
		return cabins;
	}
	
	public ArrayList<Structure> getStructures() {
		return structures;
	}

	public Structure getSelectedStructure() {
		return selectedStructure;
	}

	public void setSelectedStructure(Structure selectedStructure) {
		this.selectedStructure = selectedStructure;
		onStructureChange();
	}

	public Cabin getSelectedCabin() {
		return selectedCabin;
	}

	public void setSelectedCabin(Cabin selectedCabin) {
		this.selectedCabin = selectedCabin;
		onCabinChange();
	}
	
	public Template getSelectedTemplate() {
		return selectedTemplate;
	}

	public void setSelectedTemplate(Template selectedTemplate) {
		this.selectedTemplate = selectedTemplate;
		onTemplateChange();
	}

	public void addSelectedChild(Child selectedChild) {
		this.selectedChilds.add(selectedChild);
		onChildsChange();
	}
	
	public void removeSelectedChild(Child selectedChild) {
		this.selectedChilds.remove(selectedChild);
		onChildsChange();
	}
	
	public ArrayList<Child> getSelectedChilds() {
		return selectedChilds;
	}

	public float getScale() {
		return scale;
	}
	
	public void setScale(float scale) {
		if(scale < 1) scale = 1;
		if(scale > 10) scale = 10;
		
		this.scale = scale;
	}
	
	public OutputPanel getOutputPanel() {
		return outputPanel;
	}
	
	public ChildPanel getChildPanel() {
		return childPanel;
	}
	
	public Child getChildSelectorItem() {
		return (Child) childSelector.getSelectedItem();
	}
	
	public boolean isBluelight() {
		return bluelight;
	}

	public void setBluelight(boolean bluelight) {
		this.bluelight = bluelight;
		onVehicleChange();
		repaint();
	}
	
	public String getVehicleName() {
		String str = txtName.getText();
		
		if(str.isEmpty()) {
			str = "Unnamed";
		}
		
		return str;
	}

	/*
	 * STATIC
	 */
	public static VehicleBuilder getInstance() {
		return instance;
	}

	/*
	 * MAIN METHOD
	 */
	public static void main(String args[]) {
		VehicleBuilder builder = new VehicleBuilder();
		instance = builder;
	}
}
