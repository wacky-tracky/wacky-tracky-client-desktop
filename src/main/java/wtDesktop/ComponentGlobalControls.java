package wtDesktop;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import wackyTracky.clientbindings.java.api.SyncManager;

public class ComponentGlobalControls extends JPanel {
	private final JButton btnSync = new JButton("sync");
	private final JButton btnNewList = new JButton("n");
	private final JButton btnRefresh = new JButton("*");
	private final JButton btnDel = new JButton("rm");

	public ComponentGlobalControls() {
		this.setLayout(new FlowLayout());

		this.btnDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Actioner.deleteSelectedList();
			}
		});

		this.add(this.btnDel);

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

		this.btnSync.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new SyncManager(Main.datastore, Main.session).syncNow();
			}
		});

		this.add(this.btnSync);
	}

}