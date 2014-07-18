package wtDesktop;

import java.awt.GridBagConstraints;

public class Util {
	public static GridBagConstraints getNewGbc() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 0;

		return gbc;
	}
}
