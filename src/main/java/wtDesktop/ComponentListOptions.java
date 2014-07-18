package wtDesktop;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import wackyTracky.clientbindings.java.model.ItemList;

public class ComponentListOptions extends JPanel {
	private final JButton btnRename = new JButton("rename");
	private final JButton btnDelete = new JButton("delete");
	private final JButton btnInfo = new JButton("info");

	private ItemList list;

	public ComponentListOptions() {
		this.setLayout(new FlowLayout());
		this.btnRename.setEnabled(false);
		this.btnRename.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Actioner.refreshLists();
			}
		});
		this.add(this.btnRename);

		this.btnDelete.setEnabled(false);
		this.add(this.btnDelete);

		this.btnInfo.setEnabled(false);

		this.btnInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new WindowListInfo(ComponentListOptions.this.list);
			}
		});

		this.add(this.btnInfo);
	}

	public void setList(ItemList list) {
		this.list = list;
		this.btnDelete.setEnabled(true);
		this.btnRename.setEnabled(true);
		this.btnInfo.setEnabled(true);
	}

}
