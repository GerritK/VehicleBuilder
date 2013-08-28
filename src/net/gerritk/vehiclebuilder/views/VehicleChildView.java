package net.gerritk.vehiclebuilder.views;

import java.awt.Rectangle;
import java.awt.event.ActionListener;

import net.gerritk.vehiclebuilder.controllers.Controller;
import net.gerritk.vehiclebuilder.items.Child;
import net.gerritk.vehiclebuilder.resources.IconSet;
import net.gerritk.vehiclebuilder.ui.renderer.ChildListRenderer;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;

public class VehicleChildView extends View {
	private static final long serialVersionUID = -6297154211130256951L;
	private JButton btnUp;
	private JButton btnClear;
	private JList<Child> list;
	private JButton btnDrawOrder;
	private JButton btnDelete;
	private JButton btnDown;
	private JMenuItem miSetName;
	private JMenuItem miRemoveName;

	public VehicleChildView(Controller controller) {
		super(controller);
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		list = new JList<Child>();
		list.setCellRenderer(new ChildListRenderer());
		
		JScrollPane scrollPane = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(BorderFactory.createEtchedBorder());
		add(scrollPane, "1, 1, 3, 6, fill, fill");
		
		btnUp = new JButton(IconSet.UP);
		btnUp.setActionCommand("up");
		btnUp.addActionListener((ActionListener) controller);
		add(btnUp, "5, 1");
		
		btnDown = new JButton(IconSet.DOWN);
		btnDown.setActionCommand("down");
		btnDown.addActionListener((ActionListener) controller);
		add(btnDown, "5, 3");
		
		btnDrawOrder = new JButton(IconSet.BEHIND);
		btnDrawOrder.setActionCommand("drawOrder");
		btnDrawOrder.addActionListener((ActionListener) controller);
		add(btnDrawOrder, "5, 5, default, top");
		
		btnClear = new JButton("Alle l�schen");
		btnClear.setActionCommand("clear");
		btnClear.addActionListener((ActionListener) controller);
		add(btnClear, "1, 7");
		
		btnDelete = new JButton("L�schen");
		btnDelete.setActionCommand("delete");
		btnDelete.addActionListener((ActionListener) controller);
		add(btnDelete, "3, 7");
		
		miSetName = new JMenuItem("Namen festlegen");
		miSetName.setActionCommand("setCustomName");
		miSetName.addActionListener((ActionListener) controller);
		
		miRemoveName = new JMenuItem("Namen entfernen");
		miRemoveName.setActionCommand("removeCustomName");
		miRemoveName.addActionListener((ActionListener) controller);
	}
	
	public void showPopupMenu() {
		Rectangle r = list.getCellBounds(list.getSelectedIndex(), list.getSelectedIndex());
		if(r != null) {
			JPopupMenu popupMenu = new JPopupMenu();
			
			popupMenu.add(miSetName);
			
			Child c = list.getSelectedValue();
			if(c.getCustomName() != null && !c.getCustomName().isEmpty()) {
				popupMenu.add(miRemoveName);
			}
			
			popupMenu.show(list, 5, r.y + r.height);
		}
	}
	
	/*
	 * Getter & Setter
	 */
	public JButton getBtnUp() {
		return btnUp;
	}
	public JButton getBtnClear() {
		return btnClear;
	}
	public JList<Child> getList() {
		return list;
	}
	public JButton getBtnDrawOrder() {
		return btnDrawOrder;
	}
	public JButton getBtnDelete() {
		return btnDelete;
	}
	public JButton getBtnDown() {
		return btnDown;
	}
}
