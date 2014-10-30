package wtDesktop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import wackyTracky.clientbindings.java.model.ItemList;

class PopupMenuList extends JPopupMenu {
	public PopupMenuList(final ItemList value) {
		JMenuItem mniListInfo = new JMenuItem("Info");
		mniListInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new WindowListInfo(value);
			}
		});

		this.add(mniListInfo);
		System.out.println("foo");
	}
}
