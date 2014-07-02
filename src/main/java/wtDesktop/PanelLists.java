package wtDesktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import wackyTracky.clientbindings.java.api.Session;
import wackyTracky.clientbindings.java.model.ItemList;
import wackyTracky.clientbindings.java.model.ListOfLists;

public class PanelLists extends JPanel {
	private class ItemListCellRenderer implements ListCellRenderer<ItemList> {

		@Override
		public Component getListCellRendererComponent(JList<? extends ItemList> list, ItemList value, int index, boolean isSelected, boolean cellHasFocus) {
			JLabel l = new JLabel(value.title + " (" + value.id + ")");
			l.setBackground(new Color(100, 200, 200));
			l.setForeground(Color.BLACK);
			l.setOpaque(true);
			l.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

			if (isSelected) {
				l.setText(">> " + l.getText());
				l.setBackground(Color.BLACK);
				l.setForeground(Color.WHITE);
			}

			return l;
		}

	}

	private class ItemListModel implements ListModel<ItemList> {

		@Override
		public void addListDataListener(ListDataListener arg0) {

		}

		@Override
		public ItemList getElementAt(int arg0) {
			return PanelLists.this.lol.getLists().get(arg0);
		}

		@Override
		public int getSize() {
			return PanelLists.this.lol.getLists().size();
		}

		@Override
		public void removeListDataListener(ListDataListener arg0) {
			// TODO Auto-generated method stub

		}
	}

	private class ItemListSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			ItemList list = PanelLists.this.getSelectedList();

			if (list != null) {
				WindowMain.instance.panelItems.setList(list);
			}

		}

	}

	private final JList<ItemList> itemList = new JList<ItemList>();

	private ListOfLists lol;

	public PanelLists(Session session) {
		this.itemList.setModel(new ItemListModel());
		this.itemList.setCellRenderer(new ItemListCellRenderer());
		this.itemList.addListSelectionListener(new ItemListSelectionListener());

		try {
			this.lol = session.getListLists();
			this.itemList.repaint();
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.add(this.itemList, BorderLayout.CENTER);
	}

	ItemList getSelectedList() {
		if (this.itemList.getSelectedIndex() == -1) {
			return null;
		} else {
			return this.itemList.getModel().getElementAt(this.itemList.getSelectedIndex());
		}
	}
}
