package wtDesktop;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import wackyTracky.clientbindings.java.WtConnMonitor;
import wackyTracky.clientbindings.java.WtConnMonitor.Listener;
import wackyTracky.clientbindings.java.WtRequest.ConnError;

public class ComponentStatusBar extends JPanel {
	private final JLabel lblLastRequestStatus = new JLabel("<status>");
	private final JLabel lblOther = new JLabel("...");
	private final JLabel lblVersion = new JLabel(Main.getVersion());

	public ComponentStatusBar(WindowMain windowMain) {
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

		this.setupComponents();
	}

	private void setupComponents() {
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		WtConnMonitor.listeners.add(new Listener() {

			@Override
			public void onError(ConnError err) {
				ComponentStatusBar.this.lblLastRequestStatus.setBackground(Color.RED);
				ComponentStatusBar.this.lblLastRequestStatus.setText("Err: " + err);
				ComponentStatusBar.this.lblLastRequestStatus.setToolTipText(err.description);
			}

			@Override
			public void onOk() {
				ComponentStatusBar.this.lblLastRequestStatus.setBackground(Color.GREEN);
				ComponentStatusBar.this.lblLastRequestStatus.setText("Last request: OK");
				ComponentStatusBar.this.lblLastRequestStatus.setToolTipText("");
			}

		});

		this.lblLastRequestStatus.setOpaque(true);

		this.add(this.lblLastRequestStatus);

		this.add(new JSeparator(SwingConstants.VERTICAL));
		this.add(this.lblOther);
		this.add(new JSeparator(SwingConstants.VERTICAL));
		this.add(this.lblVersion);
	}
}
