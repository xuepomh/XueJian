package com.dh.util.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ManageClassLoader {
	private Map<String, Long> ScriptLastUpdateTimeMap = new HashMap<String, Long>(10);

	private static ManageClassLoader INSTANCE = new ManageClassLoader();

	private ManageClassLoader() {
	}

	public static ManageClassLoader getInstance() {
		return INSTANCE;
	}

	// 加载类， 如果类文件修改过加载，如果没有修改，返回当前的
	public Class<?> loadClass(String dir) throws Exception {
		File file = getFile(dir);
		if (isClassModified(file, dir)) {
			return new DynamicClassLoader().instantiateClass(dir.replaceAll("/", "."), new FileInputStream(file), file.length());
		}
		return null;
	}

	// 判断是否被修改过
	private boolean isClassModified(File file, String filename) {
		boolean returnValue = false;
		if (file.lastModified() != findLastModi(filename)) {
			returnValue = true;
			ScriptLastUpdateTimeMap.put(filename, file.lastModified());
		}
		return returnValue;
	}

	private long findLastModi(String filename) {
		Long time = ScriptLastUpdateTimeMap.get(filename);
		if (time == null) {
			time = 0L;
			ScriptLastUpdateTimeMap.put(filename, time);
		}
		return time;
	}

	private File getFile(String dir) throws URISyntaxException {
		URL url = ManageClassLoader.class.getClassLoader().getResource(dir + ".class");
		File classF = new File(url.toURI());
		return classF;
	}

}