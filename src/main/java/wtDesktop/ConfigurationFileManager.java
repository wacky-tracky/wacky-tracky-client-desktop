package wtDesktop;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

class ConfigurationFileManager {
	static class Configuration {
		String lastSessionId;
	}

	private File configFile;

	public Configuration configuration = new Configuration();

	public void load() {
		this.setupConfigFile();

		FileReader reader;

		try {
			reader = new FileReader(this.configFile);
			this.configuration = new Gson().fromJson(reader, Configuration.class);
		} catch (Exception e) {
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
