package com.dh.util;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.dh.handler.task.TaskCheck;
import com.dh.util.classloader.ManageClassLoader;

public class MyClassLoaderUtil extends ClassLoader {
	public final static Logger logger = Logger.getLogger(MyClassLoaderUtil.class);
	private final static String TASKCHECK = "taskscript/TaskCheckImp";
	private volatile TaskCheck taskCheck = null;
	private ApplicationContext act = null;
	private long lastUpdateTime = System.currentTimeMillis();

	private static MyClassLoaderUtil INSTANCE = new MyClassLoaderUtil();

	public static MyClassLoaderUtil getInstance() {
		return INSTANCE;
	}

	private MyClassLoaderUtil() {

	}

	public void loadTaskCheck() {
		try {
			Class<?> clz = ManageClassLoader.getInstance().loadClass(TASKCHECK);
			if (clz == null) {
				lastUpdateTime = System.currentTimeMillis();
				return;
			}
			taskCheck = null;
			taskCheck = (TaskCheck) clz.newInstance();
			taskCheck.init(act);
			System.out.println("hashcode + " + taskCheck.hashCode());
		} catch (Exception e) {
			logger.error("loadTaskCheck error ", e);
		}
	}

	public void loadApplicationContext(ApplicationContext at) {
		act = at;
	}

	public TaskCheck getTaskCheck() {
		if (taskCheck == null || (System.currentTimeMillis() - lastUpdateTime > 120000L)) {
			synchronized (this) {
				if (taskCheck == null || (System.currentTimeMillis() - lastUpdateTime > 120000L)) {
					loadTaskCheck();
					lastUpdateTime = System.currentTimeMillis();
				}
			}
		}
		return taskCheck;
	}

	public static void main(String[] args) throws Exception {

	}

}
