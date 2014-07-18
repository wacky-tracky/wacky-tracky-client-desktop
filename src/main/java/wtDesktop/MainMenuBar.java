package wtDesktop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import wackyTracky.clientbindings.java.WtConnMonitor;
import wackyTracky.clientbindings.java.model.DataStore;

public class MainMenuBar extends JMenuBar {
	private final JMenuItem mniPrintln = new JMenuItem("println");
	private final JMenuItem mniRenameCurrent = new JMenuItem("rename");
	private final JMenuItem mniToggleOffline = new JMenuItem("Go offline");
	private final JMenuItem mniExit = new JMenuItem("Exit");
	private final JMenuItem mniRefreshLists = new JMenuItem("Refresh lists");
	private final JMenu mnuMain = new JMenu("Debug");
	private final WindowMain wndMain;

	public MainMenuBar(WindowMain wndMain) {
		this.wndMain = wndMain;

		this.mniPrintln.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DataStore.instance.println();
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
				WtConnMonitor.toggleForceOffline();

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
