package net.gerritk.vehiclebuilder.controllers;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.gerritk.vehiclebuilder.items.Child;
import net.gerritk.vehiclebuilder.models.OutputModel;
import net.gerritk.vehiclebuilder.models.VehicleModel;
import net.gerritk.vehiclebuilder.resources.IconSet;
import net.gerritk.vehiclebuilder.views.VehicleOutputView;

public class VehicleOutputController extends Controller implements MouseListener, MouseWheelListener, MouseMotionListener, ChangeListener, ActionListener, FocusListener,
		KeyListener {
	private VehicleModel vehicleModel;
	private OutputModel outputModel;
	private VehicleOutputView outputView;

	public VehicleOutputController(VehicleModel vehicleModel, OutputModel outputModel) {
		this.vehicleModel = vehicleModel;
		this.outputModel = outputModel;

		this.outputView = new VehicleOutputView(this);

		this.vehicleModel.addObserver(this);
		this.outputModel.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o == outputModel) {
			outputView.setBackground(outputModel.getBackground());
			outputView.getSliders()[0].setValue(outputModel.getBackground().getRed());
			outputView.getSliders()[1].setValue(outputModel.getBackground().getGreen());
			outputView.getSliders()[2].setValue(outputModel.getBackground().getBlue());
			outputView.getLblScale().setText(new DecimalFormat("0.00").format(outputModel.getScale()) + " x");

			if(outputModel.isBluelight()) {
				outputView.getBtnBluelight().setIcon(IconSet.BLUELIGHT);
			} else {
				outputView.getBtnBluelight().setIcon(IconSet.BLUELIGHT.DISABLED);
			}
		}

		BufferedImage output = outputModel.scaleImage(vehicleModel.generateOutput(outputModel.isBluelight()));
		outputView.setOutput(output);

		outputView.setSelection(outputModel.generateSelectionBorder(vehicleModel));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		outputView.requestFocusInWindow();

		if(e.getButton() == MouseEvent.BUTTON1) {
			int x = outputView.getWidth() / 2 - outputView.getOutput().getWidth() / 2;
			int y = outputView.getHeight() / 2 - outputView.getOutput().getHeight() / 2;
			for(Child c : vehicleModel.getChilds()) {
				Rectangle selection = outputModel.generateSelectionBorder(vehicleModel, c);
				selection.x += x;
				selection.y += y;
	
				if(selection.contains(e.getPoint())) {
					outputModel.setSelectedChild(c);
					outputModel.notifyObservers();
					break;
				}
			}
		}

		if(e.isPopupTrigger()) {
			outputView.showPopupMenu(e.getPoint());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.isPopupTrigger()) {
			outputView.showPopupMenu(e.getPoint());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() instanceof JSlider) {
			JSlider slider = (JSlider) e.getSource();

			int r = outputModel.getBackground().getRed();
			int g = outputModel.getBackground().getGreen();
			int b = outputModel.getBackground().getBlue();
			
			if(outputView.getCbBindSliders().isSelected()) {
				r = slider.getValue();
				g = slider.getValue();
				b = slider.getValue();
			} else {
				switch(slider.getName()) {
					case "red":
						r = slider.getValue();
						break;
					case "green":
						g = slider.getValue();
						break;
					case "blue":
						b = slider.getValue();
						break;
				}
			}

			outputModel.setBackground(new Color(r, g, b));
			outputModel.notifyObservers();
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		outputModel.setScale(outputModel.getScale() + (float) -e.getWheelRotation() / 5);
		outputModel.notifyObservers();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Child c = outputModel.getSelectedChild();
		if(c != null) {
			Point d = new Point((int) ((e.getPoint().x - mouse.x) / outputModel.getScale()), (int) ((e.getPoint().y - mouse.y) / outputModel.getScale()));
			
			c.setX(c.getX() + d.x);
			c.setY(c.getY() + d.y);
			
			if(d.x != 0 || d.y != 0) {
				mouse.setLocation(e.getPoint());
			}
			
			outputModel.notifyObservers(true);
		}
	}
	
	private Point mouse = new Point();
	@Override
	public void mouseMoved(MouseEvent e) {
		mouse.setLocation(e.getPoint());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("bluelight")) {
			outputModel.setBluelight(!outputModel.isBluelight());
			outputModel.notifyObservers();
		} else if(e.getActionCommand().equals("bindColors")) {
			JSlider slider = outputView.getSliders()[0];
			outputModel.setBackground(new Color(slider.getValue(), slider.getValue(), slider.getValue()));
			outputModel.notifyObservers();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Child child = outputModel.getSelectedChild();

		if(child != null) {
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				child.setX(child.getX() + 1);
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				child.setX(child.getX() - 1);
			}
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				child.setY(child.getY() - 1);
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				child.setY(child.getY() + 1);
			}
			outputModel.notifyObservers(true);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_B) {
			outputModel.setBluelight(!outputModel.isBluelight());
			outputModel.notifyObservers();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void focusGained(FocusEvent e) {
		outputView.setBorder(BorderFactory.createEtchedBorder(UIManager.getColor("List.selectionForeground"), UIManager.getColor("List.selectionBackground")));
	}

	@Override
	public void focusLost(FocusEvent e) {
		outputView.setBorder(BorderFactory.createEtchedBorder());
	}

	/*
	 * Getter & Setter
	 */
	public VehicleOutputView getVehicleOutputView() {
		return outputView;
	}
}
