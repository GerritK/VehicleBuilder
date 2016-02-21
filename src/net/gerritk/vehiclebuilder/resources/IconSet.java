package net.gerritk.vehiclebuilder.resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IconSet extends ImageIcon {
    public static final IconSet BANNER = new IconSet("vb_banner");
    public static final IconSet UP = new IconSet("up");
    public static final IconSet DOWN = new IconSet("down");
    public static final IconSet BEHIND = new IconSet("behind");
    public static final IconSet ONTOP = new IconSet("ontop");
    public static final IconSet BLUELIGHT = new IconSet("bluelight");
    public static final IconSet CLEAR = new IconSet("clear");
    public static final IconSet SCREW = new IconSet("screw");

    public final Icon DISABLED;

    private IconSet(String icon) {
        try {
            this.setImage(ImageIO.read(IconSet.class.getClassLoader().getResource(icon + ".png")));
        } catch (IOException e) {
            System.err.println("Load Icon \"" + icon + "\" failed.");
        }

        System.out.println("Load Icon \"" + icon + "\" successful.");
        this.DISABLED = (new JLabel(this)).getDisabledIcon();
    }

    public static List<Image> loadIcons() {
        ArrayList<Image> icons = new ArrayList<>();

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
