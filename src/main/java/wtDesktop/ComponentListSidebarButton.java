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
import wackyTracky.clientbindings.java.model.PendingAction;

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
		final JLabel lbl = new JLabel() {
			@Override
			public void paint(Graphics g) {

				super.paint(g);

				g.setColor(Color.GRAY);
				g.drawLine(0, this.getHeight() - 1, this.getWidth(), this.getHeight() - 1);
			}
		};

		if (value.pendingAction == PendingAction.NONE) {
			lbl.setBackground(Color.WHITE);
		} else {
			lbl.setBackground(Stylesheet.LIST_UNSELECTED_PENDING_BG);
		}

		lbl.setForeground(Color.BLACK);
		lbl.setOpaque(true);
		lbl.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		lbl.setText(value.title);

		if (value.getItemCount() > 0) {
			lbl.setText(lbl.getText() + " (" + value.getItemCount() + ")");
		}

		if (value.pendingAction != null) {
			lbl.setText(lbl.getText() + " (" + value.pendingAction + ")");
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

		return lbl;
	}
}