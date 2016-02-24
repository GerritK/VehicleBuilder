package net.gerritk.vehiclebuilder.controllers;

import net.gerritk.vehiclebuilder.VBLauncher;
import net.gerritk.vehiclebuilder.logging.Logger;

import java.util.Observer;

public abstract class Controller implements Observer {
    protected Logger getLogger() {
        return VBLauncher.getLogger();
    }
}
