package utils;
// FROM:  http://code.runnable.com/Uu_XyuLiJBIPAAAc/get-path-of-jar-file-for-java

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

public class JarPath {

	private static String path;

	public static String getPath() {
		if (path == null) {
			URL url = JarPath.class.getProtectionDomain().getCodeSource().getLocation(); // Gets the path
			String jarPath = null;
			try {
				jarPath = URLDecoder.decode(url.getFile(), "UTF-8"); // Should fix it to be read correctly by the system
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			String parentPath = new File(jarPath).getParentFile().getPath(); // Path of the jar
			parentPath = parentPath + File.separator;

			JarPath.path = parentPath;
		}

		return JarPath.path;
	}
}