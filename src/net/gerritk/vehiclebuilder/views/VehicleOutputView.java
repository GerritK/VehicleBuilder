package net.gerritk.vehiclebuilder.views;

import net.gerritk.vehiclebuilder.controllers.Controller;
import javax.swing.border.EtchedBorder;

public class VehicleOutputView extends View {
	private static final long serialVersionUID = 5415431084556862556L;

	public VehicleOutputView(Controller controller) {
		super(controller);
		setBorder(new EtchedBorder());
	}
}
