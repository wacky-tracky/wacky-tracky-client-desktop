package wtDesktop;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import wackyTracky.clientbindings.java.model.Item;

class ComponentItemCellRenderer implements ListCellRenderer<Item> {
	@Override
	public Component getListCellRendererComponent(JList<? extends Item> list, Item value, int index, boolean isSelected, boolean cellHasFocus) {
		JLabel lbl = new JLabel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);

				g.setColor(Color.GRAY);
				g.drawLine(0, this.getHeight() - 1, this.getWidth(), this.getHeight() - 1);
			}
		};
		lbl.setText(value.content);
		lbl.setOpaque(true);
		lbl.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

		if (isSelected) {
			lbl.setBackground(Stylesheet.LIST_SELECTED_BG);
			lbl.setForeground(Stylesheet.LIST_SELECTED_FG);
		} else {
			lbl.setBackground(Stylesheet.LIST_UNSELECTED_BG);
		}

		return lbl;
	}
}