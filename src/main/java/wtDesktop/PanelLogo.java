package wtDesktop;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class PanelLogo extends JLabel {
	public PanelLogo() {
		this.setText("wacky-tracky");
		this.setBackground(Color.white);
		this.setForeground(Color.BLUE);
		this.setFont(this.getFont().deriveFont(16f));
		this.setOpaque(true);
		this.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
	}
}
