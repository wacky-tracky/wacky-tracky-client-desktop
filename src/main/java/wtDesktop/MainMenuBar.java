package wtDesktop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import wackyTracky.clientbindings.java.WtConnMonitor;

public class MainMenuBar extends JMenuBar {
	private final JMenuItem mniPrintln = new JMenuItem("println");
	private final JMenuItem mniRenameCurrent = new JMenuItem("rename");
	private final JMenuItem mniToggleOffline = new JMenuItem("Go offline");
	private final JMenuItem mniExit = new JMenuItem("Exit");
	private final JMenuItem mniRefreshLists = new JMenuItem("Refresh lists");
	private final JMenu mnuMain = new JMenu("Debug");

	private final JMenuItem mniSave = new JMenuItem("Save");

	public MainMenuBar() {
		this.mniPrintln.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Main.datastore.println();
			}
		});

		this.mnuMain.add(this.mniPrintln);
		this.add(this.mnuMain);

		this.mniRenameCurrent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Actioner.renameCurrent();
			}
		});

		this.mnuMain.add(this.mniRenameCurrent);

		this.mniToggleOffline.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Actioner.toggleForceOffline();

				MainMenuBar.this.updateOfflineStatus();
			}
		});

		this.mnuMain.add(this.mniToggleOffline);
		this.updateOfflineStatus();

		this.mniRefreshLists.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Actioner.refreshLists();
			}
		});

		this.mnuMain.add(this.mniRefreshLists);

		this.mnuMain.addSeparator();

		this.mniExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Main.exit();
			}
		});

		this.mniSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Main.datastore.save();
			}
		});

		this.mnuMain.add(this.mniSave);

		this.mnuMain.add(this.mniExit);

	}

	private void updateOfflineStatus() {
		if (WtConnMonitor.isOffline()) {
			this.mniToggleOffline.setText("Go online");
		} else {
			this.mniToggleOffline.setText("Go offline");
		}
	}
}
