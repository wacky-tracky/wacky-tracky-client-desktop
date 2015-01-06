package wtDesktop;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.google.gson.Gson;

class ConfigurationFileManager {
	static class Configuration {
		public String lastSessionId;
		public String lastUsername = "username";  
	}

	private File configFile;

	public Configuration configuration = new Configuration();

	public void load() {
		this.setupConfigFile();

		FileReader reader;

		try {
			reader = new FileReader(this.configFile);
			this.configuration = new Gson().fromJson(reader, Configuration.class);
			
			if (configuration == null) {
				throw new IllegalArgumentException("Configuration was null, possibly the config file exists but had empty content.");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Your configuration file was found, but could not be loaded. \n\nThe most likely reason is that your configuration was corrupt. A new configuration has been setup for you.", "New configuration", JOptionPane.WARNING_MESSAGE);
			configuration = new Configuration();
			
			e.printStackTrace();
		}
		
		return;
	}

	public void save() {
		String json = new Gson().toJson(this.configuration);

		FileWriter fw;
		try {
			fw = new FileWriter(this.configFile);
			fw.write(json);
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setupConfigFile() {
		File homedir = new File(System.getProperty("user.home"));
		File wtDir = new File(homedir, ".wt");
		wtDir.mkdir();

		this.configFile = new File(wtDir, "config.json");

		if (!this.configFile.exists()) {
			try {
				this.configFile.createNewFile();
				
				save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
