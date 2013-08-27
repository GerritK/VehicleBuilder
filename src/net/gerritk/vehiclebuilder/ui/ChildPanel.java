package net.gerritk.vehiclebuilder.ui;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import net.gerritk.vehiclebuilder.VehicleBuilder;
import net.gerritk.vehiclebuilder.handlers.ButtonHandler;
import net.gerritk.vehiclebuilder.handlers.KeyHandler;
import net.gerritk.vehiclebuilder.handlers.PopupMenuHandler;
import net.gerritk.vehiclebuilder.items.Child;
import net.gerritk.vehiclebuilder.resources.IconSet;
import net.gerritk.vehiclebuilder.ui.renderer.ChildListRenderer;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ChildPanel extends JPanel {
	private static final long serialVersionUID = -4797409299773424992L;

	private VehicleBuilder owner;
	private DefaultListModel<Child> model;
	private JList<Child> list;
	private PopupMenuHandler popupMenuHandler;
	private JButton btnBehind;
	private JButton btnDown;
	private JButton btnUp;
	private JButton btnDelete;
	private JButton btnClear;
	
	public ChildPanel(VehicleBuilder owner) {
		this.owner = owner;
		this.addKeyListener(new KeyHandler());
		
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("top:default"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("top:default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		// Handlers
		ButtonHandler buttonHandler = new ButtonHandler();
		MouseHandler mouseHandler = new MouseHandler();
		popupMenuHandler = new PopupMenuHandler();
		
		model = new DefaultListModel<Child>();
		for(Child child : owner.getSelectedChilds()) {
			model.addElement(child);
		}
		list = new JList<Child>(model);
		list.addListSelectionListener(new ListHandler());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setCellRenderer(new ChildListRenderer());
		list.addMouseListener(mouseHandler);
		list.addMouseMotionListener(mouseHandler);
		list.setDragEnabled(false);
		
		JScrollPane scrollList = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollList.setBorder(BorderFactory.createEtchedBorder());
		add(scrollList, "1, 1, 3, 5, fill, fill");
		
		btnUp = new JButton(IconSet.UP);
		btnUp.setToolTipText("Zusatz nach oben ordnen");
		btnUp.setActionCommand("childUp");
		btnUp.addActionListener(buttonHandler);
		btnUp.setEnabled(false);
		add(btnUp, "5, 1");
		
		btnDown = new JButton(IconSet.DOWN);
		btnDown.setToolTipText("Zusatz nach unten ordnen");
		btnDown.setActionCommand("childDown");
		btnDown.addActionListener(buttonHandler);
		btnDown.setEnabled(false);
		add(btnDown, "5, 3");
		
		btnBehind = new JButton(IconSet.ONTOP);
		btnBehind.setToolTipText("Auf dem Fahrzeug zeichnen");
		btnBehind.setActionCommand("childBehind");
		btnBehind.addActionListener(buttonHandler);
		btnBehind.setEnabled(false);
		add(btnBehind, "5, 5");
		
		btnClear = new JButton("Alle l\u00F6schen");
		btnClear.setToolTipText("Alle Zusätze löschen");
		btnClear.setActionCommand("clearChilds");
		btnClear.addActionListener(buttonHandler);
		btnClear.setEnabled(false);
		add(btnClear, "1, 7, right, default");
		
		btnDelete = new JButton("L\u00F6schen");
		btnDelete.setToolTipText("Gewählten Zusatz löschen");
		btnDelete.setActionCommand("removeChild");
		btnDelete.addActionListener(buttonHandler);
		btnDelete.setEnabled(false);
		add(btnDelete, "3, 7, left, default");
	}
	
	public void onChildsChange() {
		if(owner.getSelectedChilds().isEmpty()) {
			model.clear();
		} else {
			for(int i = 0; i < model.getSize(); i++) {
				if(!owner.getSelectedChilds().contains(model.get(i))) {
					model.remove(i);
				}
			}
			
			for(Child child : owner.getSelectedChilds()) {
				if(!model.contains(child)) {
					model.addElement(child);
				}
			}
		}
		
		if(getSelectedChild() != null) {
			btnBehind.setEnabled(true);
			btnUp.setEnabled(true);
			btnDown.setEnabled(true);
			btnDelete.setEnabled(true);
			
			if(getSelectedChild().isBehind()) {
				btnBehind.setIcon(IconSet.ONTOP);
				btnBehind.setToolTipText("Auf dem Fahrzeug zeichnen");
			} else {
				btnBehind.setIcon(IconSet.BEHIND);
				btnBehind.setToolTipText("Unter dem Fahrzeug zeichnen");
			}
		} else {
			btnBehind.setEnabled(false);
			btnUp.setEnabled(false);
			btnDown.setEnabled(false);
			btnDelete.setEnabled(false);
		}
		
		if(model.isEmpty()) {
			btnClear.setEnabled(false);
		} else {
			btnClear.setEnabled(true);
		}
	}
	
	public void onMenuShow() {
		Rectangle r = list.getCellBounds(list.getSelectedIndex(), list.getSelectedIndex());
		if(r != null) {
			JPopupMenu popupMenu = new JPopupMenu();
			
			JMenuItem miSetName = new JMenuItem("Namen festlegen");
			miSetName.setActionCommand("setCustomName");
			miSetName.addActionListener(popupMenuHandler);
			popupMenu.add(miSetName);
			
			Child c = getSelectedChild();
			if(c.getCustomName() != null && !c.getCustomName().isEmpty()) {
				JMenuItem miRemoveName = new JMenuItem("Namen entfernen");
				miRemoveName.setActionCommand("removeCustomName");
				miRemoveName.addActionListener(popupMenuHandler);
				popupMenu.add(miRemoveName);
			}
			
			popupMenu.show(list, 5, r.y + r.height);
		}
	}
	
	public void selectionWorkaround(Point p) {
		int index = list.getSelectedIndex();
		if(index >= 0) {
			Rectangle r = list.getCellBounds(index, index);
			if(!r.contains(p)) {
				list.clearSelection();
			}
		}
	}
	
	public Child getSelectedChild() {
		return list.getSelectedValue();
	}
	
	public void setSelectedChild(Child child) {
		if(model.contains(child)) {
			list.setSelectedValue(child, true);
		}
	}
	
	public DefaultListModel<Child> getModel() {
		return model;
	}
	
	public JList<Child> getList() {
		return list;
	}
	
	class ListHandler implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			owner.onChildsChange();
		}
	}
	
	class MouseHandler implements MouseListener, MouseMotionListener {
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
		public void mousePressed(MouseEvent e) {
			selectionWorkaround(e.getPoint());
			
			if(e.isPopupTrigger()) {
				onMenuShow();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			selectionWorkaround(e.getPoint());
			
			if(e.isPopupTrigger() && getSelectedChild() != null) {
				onMenuShow();
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			selectionWorkaround(e.getPoint());
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
		}
	}
}
