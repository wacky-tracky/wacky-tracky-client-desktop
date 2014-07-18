package wtDesktop;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.UIManager;

import jwrCommonsJava.JarUtil;
import wackyTracky.clientbindings.java.api.Session;
import wackyTracky.clientbindings.java.model.DataStore;

import com.beust.jcommander.JCommander;

class Main {
	public static Session session = new Session();
	public static String username;

	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Image icon;

	public static void exit() {
		System.exit(0);
	}

	public static Image getIcon() {
		String path = "logo.png";

		if (Main.icon == null) {
			try {
				if (JarUtil.isInAJar()) {
					Main.icon = ImageIO.read(JarUtil.getResource(path));
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
		if (JarUtil.isInAJar()) {
			return Main.class.getPackage().getImplementationVersion();
		} else {
			return "nojar";
		}
	}

	public static void main(String[] sysargs) throws Exception {
		Args args = new Args();
		new JCommander(args).parse(sysargs);

		WindowLogin wndLogin = new WindowLogin();

		if (Args.hasUsernameAndPassword()) {
			wndLogin.clickLogin();
		} else {
			wndLogin.setVisible(true);
		}
	}

	public void startGroovyUi() throws Exception {
		Binding binding = new Binding();
		binding.setVariable("dataStore", DataStore.instance);

		ClassLoader parent = Main.class.getClassLoader();
		GroovyClassLoader loader = new GroovyClassLoader(parent);

		Class clazz = null;

		try {
			clazz = loader.parseClass(new File("src/main/groovy/Main.groovy"));
		} catch (Exception e) {
			System.out.println("err" + e);
		}

		System.out.println(clazz);
		Object o = clazz.newInstance();

		loader.close();

		System.out.println("done");
	}
}
