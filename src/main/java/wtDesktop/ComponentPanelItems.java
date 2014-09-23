package wtDesktop;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import wackyTracky.clientbindings.java.model.Item;
import wackyTracky.clientbindings.java.model.ItemContainer;
import wackyTracky.clientbindings.java.model.ItemList;

public class ComponentPanelItems extends JList<String> implements ItemContainer.Listener, PanelLists.Listener {

	private final JLabel lblDesc = new JLabel("...");

	private final ComponentListOptions panListOptions = new ComponentListOptions();
	private final ComponentItemInputter itemInputter = new ComponentItemInputter();

	private final ComponentTreeItems jtree = new ComponentTreeItems();

	public ComponentPanelItems() {
		this.jtree.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					Actioner.deleteSelectedItem();
				}
			}
		});
		// this.jlist.setCellRenderer(new ComponentItemCellRenderer());

		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = Util.getNewGbc();

		gbc.weighty = 0;
		this.add(this.itemInputter, gbc);

		gbc.gridy++;
		gbc.weighty = 1;
		this.jtree.setBorder(BorderFactory.createEmptyBorder());
		JScrollPane sclList = new JScrollPane(this.jtree);
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

	public Item getselectedItem() {
		return this.jtree.getselectedItem();
	}

	@Override
	public void onItemAdded(Item i) {
		this.refresh();
	}

	@Override
	public void onItemRemoved(Item i) {
		this.refresh();
	}

	@Override
	public void onListChanged() {
	}

	@Override
	public void onListClicked(ItemList list) {
		this.panListOptions.setList(list);
	}

	public void refresh() {
		this.jtree.refresh();
	}

	public void setList(ItemList list) {
		this.itemInputter.setItemList(list);

		this.jtree.setList(list);
		this.jtree.refresh();

		try {
			Main.session.reqListItems(list);
		} catch (Exception e) {
		}

		list.container.listeners.add(this);

		this.lblDesc.setText("Items: " + list.container.getItems().size());

		this.doLayout();
		this.lblDesc.invalidate();
	}
}
