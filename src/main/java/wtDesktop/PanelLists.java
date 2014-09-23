package wtDesktop;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import wackyTracky.clientbindings.java.model.ItemList;
import wackyTracky.clientbindings.java.model.ListOfLists;

public class PanelLists extends JPanel implements ListOfLists.Listener {

	private class ItemListModel extends AbstractWtListModel<ItemList> {
		@Override
		public ItemList getElementAt(int arg0) {
			return Main.datastore.listOfLists.getLists().get(arg0);
		}

		@Override
		public int getSize() {
			return Main.datastore.listOfLists.getLists().size();
		}
	}

	private class ItemListSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			if (arg0.getValueIsAdjusting()) {
				return;
			}

			ItemList list = PanelLists.this.getSelectedList();

			if (list != null) {
				WindowMain.instance.panelItems.setList(list);

				for (Listener l : PanelLists.this.listeners) {
					l.onListClicked(list);
				}
			}
		}

	}

	public interface Listener {
		void onListClicked(ItemList newList);
	}

	public final Vector<Listener> listeners = new Vector<Listener>();

	private final JList<ItemList> itemList = new JList<ItemList>();

	private final ItemListModel mdl = new ItemListModel();

	public PanelLists() {
		this.itemList.setModel(this.mdl);
		this.itemList.setCellRenderer(new ComponentListSidebarButton());
		this.itemList.addListSelectionListener(new ItemListSelectionListener());

		Main.datastore.listOfLists.listeners.add(this);

		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.NORTH;

		this.setBackground(Color.WHITE);
		JScrollPane scl = new JScrollPane(this.itemList);
		scl.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scl.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		this.add(scl, gbc);
	}

	@Override
	public void fireNewList() {
		this.mdl.fireChanged();
		this.itemList.invalidate();
		this.itemList.doLayout();
	}

	ItemList getSelectedList() {
		if (this.itemList.getSelectedIndex() == -1) {
			return null;
		} else {
			return this.itemList.getModel().getElementAt(this.itemList.getSelectedIndex());
		}
	}

	@Override
	public void onListRemoved(ItemList list) {
		this.mdl.fireChanged();
		this.itemList.invalidate();
		this.itemList.doLayout();
	}
}
