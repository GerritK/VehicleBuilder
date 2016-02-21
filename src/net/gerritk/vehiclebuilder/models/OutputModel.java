package net.gerritk.vehiclebuilder.models;

import net.gerritk.vehiclebuilder.items.Child;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OutputModel extends Model {
	private Color background;
	private float scale;
	private Child selectedChild;
	private boolean bluelight;

	public OutputModel() {
		background = Color.LIGHT_GRAY;
		scale = 5.0f;
	}

	public BufferedImage scaleImage(BufferedImage img) {
		BufferedImage scaled = new BufferedImage((int) (img.getWidth() * scale), (int) (img.getHeight() * scale), BufferedImage.TRANSLUCENT);
		Graphics2D g = scaled.createGraphics();

		g.scale(scale, scale);
		g.drawImage(img, 0, 0, null);

		return scaled;
	}

	public Rectangle generateSelectionBorder(VehicleModel vehicleModel) {
		return this.generateSelectionBorder(vehicleModel, selectedChild);
	}

	public Rectangle generateSelectionBorder(VehicleModel vehicleModel, Child selectedChild) {
		Rectangle r = null;

		if(selectedChild != null) {
			Point min = Child.getMinPositions(vehicleModel.getChilds());
			r = new Rectangle();
			int border = 0;
			int bottomBorder = 0;

			if(vehicleModel.getTemplate() != null) {
				border = vehicleModel.getTemplate().getBorder();

				if(vehicleModel.getTemplate().getExtra() != null) {
					bottomBorder = vehicleModel.getTemplate().getExtra().getHeight();
				} else {
					bottomBorder = border;
				}
			}

			r.x = selectedChild.getX() - min.x + border;
			r.y = selectedChild.getY() - min.y + vehicleModel.getOutputDimension().height - bottomBorder - vehicleModel.getVehicleDimension().height;
			r.width = selectedChild.getWidth();
			r.height = selectedChild.getHeight();

			r.x *= scale;
			r.y *= scale;
			r.width *= scale;
			r.height *= scale;
		}

		return r;
	}

	/*
	 * Getter & Setter
	 */
	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
		setChanged();
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		if(scale < 1) scale = 1;
		if(scale > 10) scale = 10;
		this.scale = scale;
		setChanged();
	}

	public Child getSelectedChild() {
		return selectedChild;
	}

	public void setSelectedChild(Child selectedChild) {
		this.selectedChild = selectedChild;
		setChanged();
	}

	public boolean isBluelight() {
		return bluelight;
	}

	public void setBluelight(boolean bluelight) {
		this.bluelight = bluelight;
		setChanged();
	}
}
