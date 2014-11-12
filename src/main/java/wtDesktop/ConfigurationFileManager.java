package wtDesktop;

import java.io.File;
import java.io.FileReader;

import com.google.gson.Gson;

class ConfigurationFileManager {
	private File configFile;

	private static class Configuration {
	}

	private Configuration configuration;

	public void load() {
		setupConfigFile();

		FileReader reader;

		try {
			reader = new FileReader(configFile);
			configuration = new Gson().fromJson(reader, Configuration.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return;
	}

	public void setupConfigFile() {
		File homedir = new File(System.getProperty("user.home"));
		File wtDir = new File(homedir, ".wt");
		wtDir.mkdir();

		configFile = new File(wtDir, "config.json");
		
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
