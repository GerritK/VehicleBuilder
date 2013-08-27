package net.gerritk.vehiclebuilder.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import net.gerritk.vehiclebuilder.VehicleBuilder;
import net.gerritk.vehiclebuilder.handlers.ButtonHandler;
import net.gerritk.vehiclebuilder.handlers.KeyHandler;
import net.gerritk.vehiclebuilder.items.Child;
import net.gerritk.vehiclebuilder.resources.IconSet;

public class OutputPanel extends JPanel {
	private static final long serialVersionUID = 6188599312226962205L;
	
	private VehicleBuilder owner;
	
	public OutputPanel(VehicleBuilder owner) {
		this.owner = owner;
		this.setMinimumSize(new Dimension(100, 100));
		this.setPreferredSize(new Dimension(100, 100));
		this.setBackground(UIManager.getColor("TextField.inactiveBackground"));
		setBorder(BorderFactory.createEtchedBorder());
		
		MouseHandler mouseHandler = new MouseHandler();
		this.addMouseListener(mouseHandler);
		this.addMouseWheelListener(mouseHandler);
		this.addKeyListener(new KeyHandler());
		this.addFocusListener(new FocusHandler());
		
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
		layout.setHgap(0);
		layout.setVgap(0);
		this.setLayout(layout);
		
		JButton btnBluelight = new JButton(IconSet.BLUELIGHT);
		btnBluelight.setOpaque(false);
		btnBluelight.setActionCommand("toggleBluelight");
		btnBluelight.addActionListener(new ButtonHandler());
		this.add(btnBluelight);
	}
	
	/*
	 * Public Methods
	 */
	@Override
	public void paintComponent(Graphics g) {
		BufferedImage output = owner.getOutput();
		
		g.setColor(this.getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		
		if(output != null) {
			BufferedImage scaled = new BufferedImage((int) (output.getWidth() * owner.getScale()),
					(int) (output.getHeight() * owner.getScale()), BufferedImage.TRANSLUCENT);
			Graphics2D g2d = scaled.createGraphics();
			g2d.scale(owner.getScale(), owner.getScale());
			g2d.drawImage(output, 0, 0, null);
			
			int x = this.getWidth() / 2 - scaled.getWidth() / 2;
			int y = this.getHeight() / 2 - scaled.getHeight() / 2;
			
			g.drawImage(scaled, x, y, null);
			
			g.setColor(UIManager.getColor("TextField.inactiveForeground"));
			g.drawRect(x - 1, y - 1, scaled.getWidth() + 1, scaled.getHeight() + 1);
			
			Child c = owner.getChildPanel().getSelectedChild();
			if(c != null) {
				Point min = Child.getMinPositions(owner.getSelectedChilds());
				int border = owner.getSelectedTemplate().getBorder();
				int bottomBorder = border;
				if(owner.getSelectedTemplate().getExtra() != null) {
					bottomBorder = owner.getSelectedTemplate().getExtra().getHeight();
				}
				int cx = c.getX() - min.x;
				int cy = c.getY() - min.y;
				cx += border;
				cy += owner.getOutputDimension().height - bottomBorder - owner.getVehicleDimension().height;
				
				g.drawRect(x + (int) (cx * owner.getScale()),y + (int) (cy * owner.getScale()),
						(int) ((c.getWidth()) * owner.getScale()), (int) ((c.getHeight()) * owner.getScale()));
			}
		}
	}
	
	class MouseHandler implements MouseListener, MouseWheelListener {
		private boolean entered;

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			entered = true;
		}

		@Override
		public void mouseExited(MouseEvent e) {
			entered = false;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			requestFocusInWindow();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			if(entered) {
				owner.setScale(owner.getScale() + (float) -e.getWheelRotation() / 5);
				repaint();
			}
		}
		
	}

	class FocusHandler implements FocusListener {
		@Override
		public void focusGained(FocusEvent e) {
			setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		}

		@Override
		public void focusLost(FocusEvent e) {
			setBorder(BorderFactory.createEtchedBorder());
		}
	}
}
