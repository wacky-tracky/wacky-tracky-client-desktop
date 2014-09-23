package wtDesktop;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import wackyTracky.clientbindings.java.WtConnMonitor;

public class WindowMain extends JFrame {
	public static WindowMain instance = new WindowMain();
	public ComponentPanelItems panelItems;
	public PanelLists panelLists;

	private final ComponentStatusBar compStatusBar = new ComponentStatusBar(this);

	private WindowMain() {
		this.setBounds(100, 100, 960, 480);
		this.setLocationRelativeTo(null);
		this.setTitle("wacky-tracky");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(Main.getIcon());

		this.setJMenuBar(new MainMenuBar());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;

		JPanel panelSidebar = new JPanel();
		panelSidebar.setLayout(new GridBagLayout());

		gbc.weighty = 0;
		panelSidebar.add(new ComponentLogo(), gbc);

		gbc.gridy++;
		gbc.weighty = 1;
		this.panelLists = new PanelLists();
		panelSidebar.add(this.panelLists, gbc);

		gbc.gridy++;
		gbc.weighty = 0;
		ComponentGlobalControls gloControls = new ComponentGlobalControls();
		panelSidebar.add(gloControls, gbc);

		this.panelItems = new ComponentPanelItems();
		this.panelLists.listeners.add(this.panelItems);

		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		sp.setLeftComponent(panelSidebar);
		sp.setRightComponent(this.panelItems);
		sp.setDividerLocation(this.getWidth() / 4);
		this.add(sp, BorderLayout.CENTER);

		this.add(this.compStatusBar, BorderLayout.SOUTH);
	}

	public void onLoggedIn() {
		this.setVisible(true);

		Actioner.refreshLists();

		if (WtConnMonitor.isOffline()) {
			this.setTitle("wacky-tracky - <offline>");
		} else {
			this.setTitle("wacky-tracky - " + Main.username);
		}
	}
}
