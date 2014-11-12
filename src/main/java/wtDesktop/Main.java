package wtDesktop;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;

import java.awt.Image;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.UIManager;

import wackyTracky.clientbindings.java.WtConnMonitor;
import wackyTracky.clientbindings.java.WtResponse;
import wackyTracky.clientbindings.java.api.Session;
import wackyTracky.clientbindings.java.api.SyncManager;
import wackyTracky.clientbindings.java.model.DataStore;

import wtDesktop.ConfigurationFileManager;

import com.beust.jcommander.JCommander;

class Main {
	public static Session session = new Session();
	public static String username;
	public static final ConfigurationFileManager configFileManager = new ConfigurationFileManager();

	public static final DataStore datastore = DataStore.load();

	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		WtResponse.userAgent = "wtDesktop " + Main.getVersion();

		configFileManager.load();

		syncManager = new SyncManager(datastore, session);
	}

	private static Image icon;
	protected static SyncManager syncManager;

	/**
	 * This is obviously stupid, but we need the password to reauthenticate.
	 */
	public static String password;

	public static void exit() {
		Main.datastore.save();
		System.exit(0);
	}
	
	private static boolean isInJar(Class<?> c) {
		return c.getClassLoader().toString().startsWith("jar");
	}
	
	private static URL getResource(String path) {
		return Main.class.getResource(path); 
	} 

	public static Image getIcon() {
		String path = "logo.png"; 

		if (Main.icon == null) {
			try {
				if (isInJar(Main.class)) {
					Main.icon = ImageIO.read(getResource(path));
				} else {
					File f = new File("src/main/resources/" + path);
					Main.icon = ImageIO.read(f);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return Main.icon;
	}

	public static String getVersion() {
		if (isInJar(Main.class)) {
			return Main.class.getPackage().getImplementationVersion();
		} else {
			return "nojar";
		}
	}

	public static void main(String[] sysargs) throws Exception {
		Args args = new Args();
		new JCommander(args).parse(sysargs);

		WindowLogin wndLogin = new WindowLogin();

		if (Args.offline) {
			WtConnMonitor.goOffline();
		}

		if (Args.hasUsernameAndPassword()) {
			wndLogin.clickLogin();
		} else {
			wndLogin.setVisible(true);
		}
	}

	public void startGroovyUi() throws Exception {
		Binding binding = new Binding();
		binding.setVariable("dataStore", Main.datastore);

		ClassLoader parent = Main.class.getClassLoader();
		GroovyClassLoader loader = new GroovyClassLoader(parent);

		Class clazz = null;

		try {
			clazz = loader.parseClass(new File("src/main/groovy/Main.groovy"));
		} catch (Exception e) {
			System.out.println("err" + e);
		}

		System.out.println(clazz);
		clazz.newInstance();

		loader.close();

		System.out.println("done");
	}
}
