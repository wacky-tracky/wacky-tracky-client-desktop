package wtDesktop;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.AbstractLayoutCache.NodeDimensions;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import wackyTracky.clientbindings.java.WtRequest.ConnException;
import wackyTracky.clientbindings.java.model.Item;
import wackyTracky.clientbindings.java.model.ItemList;
import wackyTracky.clientbindings.java.model.PendingAction;
import wtDesktop.PanelLists.Listener;

public class ComponentTreeItems extends JTree implements Listener {

	private class Modelo implements TreeModel {

		private final Vector<TreeModelListener> listeners = new Vector<TreeModelListener>();

		@Override
		public void addTreeModelListener(TreeModelListener l) {
			this.listeners.add(l);
		}

		@Override
		public Object getChild(Object parent, int index) {
			Item parentItem = (Item) parent;

			return parentItem.container.getItems().get(index);
		}

		@Override
		public int getChildCount(Object parent) {
			return ((Item) parent).container.size();
		}

		@Override
		public int getIndexOfChild(Object parent, Object child) {
			Item parentItem = (Item) parent;

			return parentItem.container.getItems().indexOf(child);
		}

		@Override
		public Object getRoot() {
			return ComponentTreeItems.this.rootItem;
		}

		@Override
		public boolean isLeaf(Object node) {
			return false;
		}

		public void reload() {
			for (TreeModelListener l : this.listeners) {
				l.treeNodesChanged(new TreeModelEvent("", ComponentTreeItems.this.getSelectionPath()));
			}
		}

		@Override
		public void removeTreeModelListener(TreeModelListener l) {
			this.listeners.remove(l);
		}

		@Override
		public void valueForPathChanged(TreePath path, Object newValue) {

		}
	}

	private class Tsl implements TreeSelectionListener {

		@Override
		public void valueChanged(TreeSelectionEvent e) {
			Item i = ComponentTreeItems.this.getselectedItem();

			try {
				Main.session.reqListItems(i);

				for (Item child : i.container.getItems()) {
					Main.session.reqListItems(child);
				}
			} catch (ConnException e1) {
				e1.printStackTrace();
			}
		}
	}

	private class WideTreeUi extends BasicTreeUI {
		@Override
		protected NodeDimensions createNodeDimensions() {
			return new NodeDimensionsHandler() {
				@Override
				public Rectangle getNodeDimensions(Object value, int row, int depth, boolean expanded, Rectangle size) {
					Rectangle dims = super.getNodeDimensions(value, row, depth, expanded, size);
					dims.width = ComponentTreeItems.this.getParent().getWidth() - this.getRowX(row, depth);

					return dims;
				}

			};
		}

		@Override
		protected void paintExpandControl(Graphics g, Rectangle clipBounds, Insets insets, Rectangle bounds, TreePath path, int row, boolean isExpanded, boolean hasBeenExpanded, boolean isLeaf) {
		}

		@Override
		protected void paintHorizontalLine(Graphics g, JComponent c, int y, int left, int right) {
		}

		@Override
		protected void paintVerticalLine(Graphics g, JComponent c, int x, int top, int bottom) {
		}
	}

	private Item rootItem;

	private final WideTreeUi wtui = new WideTreeUi();

	public ComponentTreeItems() {

		this.setModel(new Modelo());
		this.setCellRenderer(new ComponentItemCellRenderer());
		this.addTreeSelectionListener(new Tsl());
		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

	}

	public Item getselectedItem() {
		return (Item) this.getLastSelectedPathComponent();
	}

	@Override
	public void onListClicked(ItemList newList) {
		this.refresh();
	}

	public void refresh() {
		this.invalidate();
		this.validate();
		this.setUI(new WideTreeUi());
		this.expandRow(0);
		this.repaint();
	}

	public void setList(ItemList list) {
		this.rootItem = new Item("root");
		this.rootItem.pendingAction = PendingAction.NONE;
		this.rootItem.container = list.container;

		try {
			Main.session.reqListItems(list);
		} catch (ConnException e) {
			e.printStackTrace();
		}
	}

}
