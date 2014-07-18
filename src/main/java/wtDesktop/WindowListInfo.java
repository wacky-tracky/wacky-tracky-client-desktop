package wtDesktop;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import wackyTracky.clientbindings.java.model.ItemList;

public class WindowListInfo extends JFrame {

	public WindowListInfo(ItemList itemList) {
		JTextArea txtInfo = new JTextArea();
		this.setIconImage(Main.getIcon());

		txtInfo.append("ID:" + itemList.id + "\n");
		txtInfo.append("Title: " + itemList.title + "\n");
		txtInfo.setEditable(false);
		this.add(txtInfo);

		this.setBounds(100, 100, 320, 240);
		this.setLocationRelativeTo(null);

		this.setVisible(true);
	}
}
