package wtDesktop;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

import wackyTracky.clientbindings.java.model.Item;
import wackyTracky.clientbindings.java.model.PendingAction;

class ComponentItemCellRenderer implements TreeCellRenderer {

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		Item item = (Item) value;
		JLabel lbl = new JLabel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);

				g.setColor(Color.GRAY);
				g.drawLine(0, this.getHeight() - 1, this.getWidth(), this.getHeight() - 1);
			}
		};
		lbl.setText(item.content + " (" + item.pendingAction + ")");
		lbl.setOpaque(true);
		lbl.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

		if (item.pendingAction == PendingAction.NONE) {
			lbl.setBackground(Stylesheet.LIST_UNSELECTED_BG);
		} else {
			lbl.setBackground(Stylesheet.ITEM_UNSELECTED_PENDING_BG);
		}

		if (isSelected) {
			if (lbl.getBackground().equals(Color.WHITE)) {
				lbl.setBackground(Stylesheet.LIST_SELECTED_BG);
				lbl.setForeground(Stylesheet.LIST_SELECTED_FG);
			} else {
				lbl.setBackground(lbl.getBackground().darker());
				lbl.setForeground(Color.WHITE);
			}
		}

		if (!item.container.hasItems()) {
			lbl.setText("[ ] " + lbl.getText());
		} else if (expanded) {
			lbl.setText("[-] " + lbl.getText());
		} else {
			lbl.setText("[+] " + lbl.getText());
		}

		return lbl;
	}
}
