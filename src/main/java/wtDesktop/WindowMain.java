package wtDesktop;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import wackyTracky.clientbindings.java.api.Session;

public class WindowMain extends JFrame {
	public static WindowMain instance = new WindowMain();
	public PanelItems panelItems;

	private WindowMain() {
		this.setBounds(100, 100, 640, 480);
		this.setLocationRelativeTo(null);
		this.setTitle("wacky-tracky");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Session session = new Session();
		try {
			session.reqAuthenticate("unittest", "unittest").submit().response().saveCookiesInSession();
		} catch (Exception e) {
			e.printStackTrace();
		}

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0;
		gbc.weighty = 1;

		JPanel panelSidebar = new JPanel();
		panelSidebar.setLayout(new GridBagLayout());
		panelSidebar.add(new PanelLogo(), gbc);

		gbc.gridx++;
		gbc.weightx = 1;
		panelSidebar.add((new PanelLists(session)), gbc);

		this.panelItems = new PanelItems(session);

		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelSidebar, this.panelItems);
		sp.setDividerLocation(this.getWidth() / 4);
		this.add(sp, BorderLayout.CENTER);

	}
}
