package wtDesktop;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import wackyTracky.clientbindings.java.model.Item;
import wackyTracky.clientbindings.java.model.ItemList;
import wackyTracky.clientbindings.java.model.ItemList.Listener;

public class PanelItems extends JList<String> implements Listener, PanelLists.Listener {

	private class ItemListModel extends AbstractListModel<Item> {

		public ItemListModel() {}

		public void fireChanged() {
			this.fireContentsChanged(this, 0, this.getSize());
		}

		@Override
		protected void fireContentsChanged(Object source, int index0, int index1) {
			super.fireContentsChanged(source, index0, index1);
		}

		@Override
		public Item getElementAt(int index) {
			return PanelItems.this.itemList.getItems().get(index);
		}

		@Override
		public int getSize() {
			if (PanelItems.this.itemList == null) {
				return 0;
			} else {
				int s = PanelItems.this.itemList.size();
				return s;
			}
		}

	}

	private class ItemSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {

		}

	}

	private ItemList itemList;

	private final JList<Item> jlist = new JList<Item>();

	private final JLabel lblDesc = new JLabel("...");

	private final ComponentListOptions panListOptions = new ComponentListOptions();
	private final ComponentItemInputter itemInputter = new ComponentItemInputter();

	private final ItemListModel mdl = new ItemListModel();

	public PanelItems() {
		this.jlist.setModel(this.mdl);
		this.jlist.addListSelectionListener(new ItemSelectionListener());
		this.jlist.setCellRenderer(new ComponentItemCellRenderer());

		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = Util.getNewGbc();

		gbc.weighty = 0;
		this.add(this.itemInputter, gbc);

		gbc.gridy++;
		gbc.weighty = 1;
		this.jlist.setBorder(BorderFactory.createEmptyBorder());
		JScrollPane sclList = new JScrollPane(this.jlist);
		sclList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sclList.setBorder(BorderFactory.createEmptyBorder());
		this.add(sclList, gbc);

		gbc.gridy++;
		gbc.weighty = 0;
		this.lblDesc.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		this.add(this.lblDesc, gbc);

		gbc.gridy++;
		this.add(this.panListOptions, gbc);

		this.setBackground(this.panListOptions.getBackground());
	}

	@Override
	public void listClicked(ItemList list) {
		this.panListOptions.setList(list);
	}

	@Override
	public void onItemAdded(Item i) {
		this.jlist.repaint();

		this.mdl.fireChanged();
	}

	@Override
	public void onListChanged(ItemList list) {}

	public void setList(ItemList list) {
		this.itemInputter.setItemList(list);

		this.itemList = list;
		this.jlist.updateUI();

		try {
			Main.session.reqListItems(list);
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.itemList.listeners.add(this);

		this.lblDesc.setText("Items: " + list.getItems().size());

		this.doLayout();
		this.lblDesc.invalidate();
	}
}
