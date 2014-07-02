package wtDesktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import wackyTracky.clientbindings.java.api.Session;
import wackyTracky.clientbindings.java.model.Item;
import wackyTracky.clientbindings.java.model.ItemList;

public class PanelItems extends JList<String> {

	private class ItemCellRenderer implements ListCellRenderer<Item> {

		@Override
		public Component getListCellRendererComponent(JList<? extends Item> list, Item value, int index, boolean isSelected, boolean cellHasFocus) {
			JLabel lbl = new JLabel();
			lbl.setText(value.content);
			lbl.setOpaque(true);

			if (isSelected) {
				lbl.setBackground(Color.BLUE);
				lbl.setForeground(Color.WHITE);
			}

			return lbl;
		}

	}

	private class ItemListModel implements ListModel<Item> {

		public ItemListModel() {}

		@Override
		public void addListDataListener(ListDataListener l) {}

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

		@Override
		public void removeListDataListener(ListDataListener l) {

		}

	}

	private class ItemSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			// TODO Auto-generated method stub

		}

	}

	private ItemList itemList;

	private final JList<Item> jlist = new JList<Item>();

	private final Session session;

	private final JLabel lblDesc = new JLabel("...");

	private final JPopupMenu mnuListOptions = new JPopupMenu();

	public PanelItems(Session session) {
		this.session = session;
		this.jlist.setModel(new ItemListModel());
		this.jlist.addListSelectionListener(new ItemSelectionListener());
		this.jlist.setCellRenderer(new ItemCellRenderer());

		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(this.jlist), BorderLayout.CENTER);

		this.add(this.lblDesc, BorderLayout.SOUTH);
		this.add(this.mnuListOptions, null);

		JMenuItem mniDeleteList = new JMenuItem("Delete list");
		mniDeleteList.addActionListener(
			ae -> { System.out.println("Push da button!"); } 
		);  
		this.mnuListOptions.add(this.mnuListOptions);
	}

	public void setList(ItemList list) {
		this.itemList = list;
		this.jlist.updateUI();

		this.session.reqListItems(list);

		this.lblDesc.setText("Items: " + list.getItems().size());

		this.doLayout();
	}

}
