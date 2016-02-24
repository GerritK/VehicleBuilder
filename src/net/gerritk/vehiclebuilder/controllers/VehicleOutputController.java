package net.gerritk.vehiclebuilder.controllers;

import net.gerritk.vehiclebuilder.items.Child;
import net.gerritk.vehiclebuilder.models.OutputModel;
import net.gerritk.vehiclebuilder.models.VehicleModel;
import net.gerritk.vehiclebuilder.resources.IconSet;
import net.gerritk.vehiclebuilder.views.VehicleOutputView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.Observable;

public class VehicleOutputController extends Controller implements MouseListener, MouseWheelListener, MouseMotionListener, ChangeListener, ActionListener, FocusListener, KeyListener {
	private VehicleModel vehicleModel;
	private OutputModel outputModel;
	private VehicleOutputView outputView;

	private Point mouse = new Point();

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
            getLogger().log("VehicleOutputController", "Updating from output model...");
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

        getLogger().log("VehicleOutputController", "Getting output image...");
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

	@Override
	public void mouseMoved(MouseEvent e) {
		mouse.setLocation(e.getPoint());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("bluelight")) {
            getLogger().log("VehicleOutputController", "Toggled bluelight state by button: " + !outputModel.isBluelight());
            outputModel.setBluelight(!outputModel.isBluelight());
			outputModel.notifyObservers();
		} else if (e.getActionCommand().equals("bindColors")) {
			JSlider slider = this.outputView.getSliders()[0];
			this.outputModel.setBackground(new Color(slider.getValue(), slider.getValue(), slider.getValue()));
			this.outputModel.notifyObservers();
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
            getLogger().log("VehicleOutputController", "Toggled bluelight state by key: " + !outputModel.isBluelight());
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
