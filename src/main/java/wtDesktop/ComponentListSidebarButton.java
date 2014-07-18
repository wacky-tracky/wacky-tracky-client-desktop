package wtDesktop;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ListCellRenderer;

import wackyTracky.clientbindings.java.model.ItemList;

class ComponentListSidebarButton implements ListCellRenderer<ItemList> {

	private class PopupMenuSidebarButton extends JPopupMenu {

		public PopupMenuSidebarButton(final ItemList value) {
			JMenuItem mniListInfo = new JMenuItem("Info");
			mniListInfo.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					new WindowListInfo(value);
				}
			});

			this.add(mniListInfo);
		}
	}

	@Override
	public Component getListCellRendererComponent(final JList<? extends ItemList> list, final ItemList value, int index, boolean isSelected, boolean cellHasFocus) {
		final JLabel l = new JLabel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);

				g.setColor(Color.GRAY);
				g.drawLine(0, this.getHeight() - 1, this.getWidth(), this.getHeight() - 1);
			}
		};

		if (value.existsOnServer) {
			l.setBackground(Color.WHITE);
		} else {
			l.setBackground(Color.PINK);
		}

		l.setForeground(Color.BLACK);
		l.setOpaque(true);
		l.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		l.setText(value.title);

		if (value.getItemCount() > 0) {
			l.setText(l.getText() + " (" + value.getItemCount() + ")");
		}

		if (isSelected) {
			l.setBackground(Stylesheet.LIST_SELECTED_BG);
			l.setForeground(Stylesheet.LIST_SELECTED_FG);
		}

		return l;
	}
}