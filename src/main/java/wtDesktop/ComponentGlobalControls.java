package wtDesktop;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ComponentGlobalControls extends JPanel {
	private final JButton btnNewList = new JButton("New List");
	private final JButton btnRefresh = new JButton("*");

	public ComponentGlobalControls() {
		this.setLayout(new FlowLayout());

		this.btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Actioner.refreshLists();
			}
		});

		this.add(this.btnRefresh);

		this.btnNewList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Actioner.newList();
			}
		});
		this.add(this.btnNewList);
	}

}