package net.gerritk.vehiclebuilder.items;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Template extends Item {
	private BufferedImage left;
	private BufferedImage right;
	private BufferedImage extra;
	private int border;

	public Template(String name, BufferedImage base, BufferedImage left, BufferedImage right, BufferedImage extra) {
		super(name, base);

		this.left = left;
		this.right = right;
		this.extra = extra;
		if(left != null && right != null) {
			this.border = (left.getWidth() + right.getWidth()) / 2;
		}

	}

	public BufferedImage getImage(int width) {
		int height = getHeight();
		if(extra != null) {
			height += extra.getHeight() - getBorder();
		}
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
		Graphics2D g = img.createGraphics();

		g.drawImage(left, 0, 0, null);

		for(int i = 0; i < width - left.getWidth() - right.getWidth(); i++) {
			g.drawImage(super.getImage(), i + left.getWidth(), 0, null);
		}

		g.drawImage(right, width - right.getWidth(), 0, null);

		if(extra != null) {
			g.setColor(new Color(255, 255, 255, 0));
			g.fillRect(img.getWidth() / 2 - extra.getWidth() / 2, getHeight() - getBorder(), extra.getWidth(), extra.getHeight());
			g.drawImage(extra, img.getWidth() / 2 - extra.getWidth() / 2, getHeight() - getBorder(), null);
		}

		return img;
	}

	public BufferedImage getBase() {
		return getImage();
	}

	public BufferedImage getLeft() {
		return left;
	}

	public BufferedImage getRight() {
		return right;
	}

	public BufferedImage getExtra() {
		return extra;
	}

	@Override
	public int getWidth() {
		return 100;
	}

	public int getBorder() {
		return border;
	}

	public void setBorder(int border) {
		this.border = border;
	}
}
