package net.gerritk.vehiclebuilder.resources;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class IconSet extends ImageIcon {	
	private static final long serialVersionUID = 6869366467690943187L;
	
	public static final IconSet BANNER = new IconSet("vb_banner"),
			UP = new IconSet("up"),
			DOWN = new IconSet("down"),
			BEHIND = new IconSet("behind"),
			ONTOP = new IconSet("ontop"),
			BLUELIGHT = new IconSet("bluelight"),
			CLEAR = new IconSet("clear"),
			SCREW = new IconSet("screw");
	
	public final Icon DISABLED;
	
	private IconSet(String icon) {
		try {
			this.setImage(ImageIO.read(IconSet.class.getClassLoader().getResource(icon + ".png")));
		} catch(IOException e) {
			System.err.println("Load Icon \"" + icon + "\" failed.");
		}

		System.out.println("Load Icon \"" + icon + "\" successed.");
		
		DISABLED = new JLabel(this).getDisabledIcon();
	}
	
	public static List<Image> loadIcons() {
		List<Image> icons = new ArrayList<Image>();
		
		icons.add(new IconSet("icon/icon_256x256").getImage());
		icons.add(new IconSet("icon/icon_128x128").getImage());
		icons.add(new IconSet("icon/icon_96x96").getImage());
		icons.add(new IconSet("icon/icon_64x64").getImage());
		icons.add(new IconSet("icon/icon_48x48").getImage());
		icons.add(new IconSet("icon/icon_32x32").getImage());
		icons.add(new IconSet("icon/icon_24x24").getImage());
		icons.add(new IconSet("icon/icon_16x16").getImage());
		
		return icons;
	}
}
