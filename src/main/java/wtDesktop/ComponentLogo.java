package wtDesktop;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class ComponentLogo extends JLabel {
	public ComponentLogo() {
		this.setText("wacky-tracky");
		this.setBackground(new Color(45, 174, 130));
		this.setForeground(Color.white);
		this.setFont(this.getFont().deriveFont(16f));
		this.setOpaque(true);
		this.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
		this.setToolTipText("Version: " + Main.getVersion());
	}
}
