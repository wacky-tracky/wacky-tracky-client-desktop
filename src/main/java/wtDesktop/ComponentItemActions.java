package wtDesktop;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import wackyTracky.clientbindings.java.model.Item;

public class ComponentItemActions extends JPanel {
	private final JLabel lbl = new JLabel("lbl");

	public ComponentItemActions() {
		this.setupComponents();
	}

	public void onItemChanged(Item selectedItem) {
		this.lbl.setText("item " + selectedItem.content + " actions: ");
	}

	private void setupComponents() {
		this.setLayout(new FlowLayout());
		this.add(this.lbl);
	}
}
