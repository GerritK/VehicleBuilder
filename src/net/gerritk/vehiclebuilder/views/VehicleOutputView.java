package net.gerritk.vehiclebuilder.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import net.gerritk.vehiclebuilder.controllers.Controller;
import net.gerritk.vehiclebuilder.resources.IconSet;

import javax.swing.JCheckBox;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeListener;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JLabel;
import javax.swing.JButton;

public class VehicleOutputView extends View {
	private static final long serialVersionUID = 5415431084556862556L;
	
	private JSlider slColors[];
	private JCheckBox cbBindSliders;
	private JLabel lblScale;
	private JButton btnBluelight;
	private BufferedImage output;
	private Rectangle selection;

	public VehicleOutputView(Controller controller) {
		super(controller);
		setBorder(new EtchedBorder());
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		btnBluelight = new JButton(IconSet.BLUELIGHT);
		btnBluelight.setOpaque(false);
		btnBluelight.setActionCommand("bluelight");
		btnBluelight.addActionListener((ActionListener) controller);
		btnBluelight.setToolTipText("Blaulicht an- bzw. ausschalten");
		add(btnBluelight, "1, 5");
		
		lblScale = new JLabel();
		add(lblScale, "5, 1, default, top");
		
		slColors = new JSlider[3];
		for(int i = 0; i < slColors.length; i++) {
			slColors[i] = new JSlider();
			slColors[i].setPreferredSize(new Dimension(100, 25));
			slColors[i].setMinimum(0);
			slColors[i].setMaximum(255);
			slColors[i].addChangeListener((ChangeListener) controller);
		}
		slColors[0].setName("red");
		slColors[0].setBackground(Color.RED);
		slColors[1].setName("green");
		slColors[1].setBackground(Color.GREEN);
		slColors[2].setName("blue");
		slColors[2].setBackground(Color.BLUE);
		
		cbBindSliders = new JCheckBox("Farben aneinander binden");
		cbBindSliders.setActionCommand("bindColors");
		cbBindSliders.addActionListener((ActionListener) controller);
		
		this.addMouseListener((MouseListener) controller);
		this.addMouseWheelListener((MouseWheelListener) controller);
		this.addMouseMotionListener((MouseMotionListener) controller);
		this.addFocusListener((FocusListener) controller);
		this.addKeyListener((KeyListener) controller);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(output != null) {
			int x = this.getWidth() / 2 - output.getWidth() / 2;
			int y = this.getHeight() / 2 - output.getHeight() / 2;
			
			g.drawImage(output, x, y, null);
			g.setColor(UIManager.getColor("TextField.inactiveForeground"));
			if(selection != null) {
				g.drawRect(selection.x + x, selection.y + y, selection.width, selection.height);
			}
		}
	}
	
	public void showPopupMenu(Point p) {
		JPopupMenu popupMenu = new JPopupMenu();
		
		for(JSlider slider : slColors) {
			popupMenu.add(slider);
		}
		
		popupMenu.add(cbBindSliders);
		
		popupMenu.show(this, p.x, p.y);
	}
	
	/*
	 * Getter & Setter
	 */
	public JSlider[] getSliders() {
		return slColors;
	}
	
	public JLabel getLblScale() {
		return lblScale;
	}
	
	public BufferedImage getOutput() {
		return output;
	}
	
	public void setOutput(BufferedImage output) {
		this.output = output;
		repaint();
	}
	
	public JButton getBtnBluelight() {
		return btnBluelight;
	}

	public Rectangle getSelection() {
		return selection;
	}

	public void setSelection(Rectangle selection) {
		this.selection = selection;
		repaint();
	}
	
	public JCheckBox getCbBindSliders() {
		return cbBindSliders;
	}
}
