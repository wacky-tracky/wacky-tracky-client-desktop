package wtDesktop;

import wackyTracky.clientbindings.java.ObjectFieldSerializer;

import com.beust.jcommander.Parameter;

public class Args {
	@Parameter(names = "--username")
	public static String username;

	@Parameter(names = "--password")
	public static String password;

	@Parameter(names = "--offline")
	public static boolean offline;

	public static boolean hasUsernameAndPassword() {
		return (Args.username != null) && (Args.password != null);
	}

	@Override
	public String toString() {
		return new ObjectFieldSerializer(this).toString();
	}
}
