package wtDesktop;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

import wackyTracky.clientbindings.java.WtRequest.ConnException;
import wackyTracky.clientbindings.java.model.Item;
import wackyTracky.clientbindings.java.model.ItemList;

public class ComponentItemInputter extends JPanel {

	private ItemList itemList;
	private final JTextField txtField = new JTextField();

	public ComponentItemInputter() {
		this.setBackground(Stylesheet.ITEM_INPUT_BORDER);

		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = Util.getNewGbc();
		gbc.insets = new Insets(7, 7, 7, 7);
		this.add(this.txtField, gbc);

		this.txtField.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		this.txtField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					ComponentItemInputter.this.enter();
				}
			}
		});
	}

	public void enter() {
		String content = this.txtField.getText();
		this.txtField.setText("");

		try {
			Item i = new Item(content);
			this.itemList.add(i);

			Main.session.reqCreateItem(this.itemList, content);
		} catch (ConnException e) {
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setItemList(ItemList itemList) {
		this.itemList = itemList;
		this.txtField.setEnabled(true);
	}

}
