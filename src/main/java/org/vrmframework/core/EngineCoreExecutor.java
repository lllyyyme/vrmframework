package org.vrmframework.core;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;



public class EngineCoreExecutor {

	private static boolean start = false;

	private static ExecutorService threadPoolExecutor;

	public static void initEngineCoreExecutor() {
		if ( !start || threadPoolExecutor == null) {
			threadPoolExecutor = Executors.newCachedThreadPool();
			start = true;
		}
	}

	public static void closeEngineCoreExecutor() {
		if (start && threadPoolExecutor != null) {
			threadPoolExecutor.shutdown();
			start = false;
		}
	}

	public static Object submitModelTask(Class<?> class1, Object... params){
		if ( !start || threadPoolExecutor == null) {
			initEngineCoreExecutor();
		}

		Future<Object> submit = threadPoolExecutor.submit(new ModelTask(class1, params));
		Object t = null;
		try {
			t = submit.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return t;
	}

}
