package org.vrmframework.core;

import org.vrmframework.ViewModel;
import org.vrmframework.parser.PageModelParsedConfig;
import org.vrmframework.parser.PageModelXmlParser;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.Map;


/**
 * ENGINE FACTORY
 */
public class EngineCoreFactory {

	private static EngineCore engineCore;

	private static boolean isRunning = false;

	private static final String DEFAULT_PACKAGE_PATH = "com\\application\\xml";

	public static EngineCore getEngineCore() {
		if (null != engineCore)
			return engineCore;
		else
			return engineCore = new EngineCore(DEFAULT_PACKAGE_PATH);
	}

	public static EngineCore getEngineCore(boolean ref) {
		if (ref) {
			return engineCore = new EngineCore(DEFAULT_PACKAGE_PATH);
		} else {
			if (null != engineCore)
				return engineCore;
			else
				return engineCore = new EngineCore(DEFAULT_PACKAGE_PATH);
		}
	}

	public static EngineCore initEngineCore(boolean ref) {
		if (ref) {
			return engineCore = new EngineCore(DEFAULT_PACKAGE_PATH);
		} else {
			if (null != engineCore)
				return engineCore;
			else
				return engineCore = new EngineCore(DEFAULT_PACKAGE_PATH);
		}
	}

	public static EngineCore initEngineCore(String url,boolean ref) {
		if (ref) {
			return engineCore = new EngineCore(url);
		} else {
			if (null != engineCore)
				return engineCore;
			else
				return engineCore = new EngineCore(url);
		}
	}

	public static EngineCore getEngineCore(String filePath, boolean ref)
			throws	SecurityException {
		if (ref) {
			return engineCore = new EngineCore(filePath);
		} else {
			if (null != engineCore)
				return engineCore;
			else
				return engineCore = new EngineCore(filePath);
		}
	}

	/**
	 * ENGINE
	 */
	public static class EngineCore {
		private Map<Class<? extends ViewModel>, PageModelParsedConfig> pageModelParsedConfigs;

		private EngineCore(String xmlDirPath) {
			if (!isRunning) {
				System.out.println("doInit");
				initXmlConfig(xmlDirPath);
				isRunning = true;
			} else {
				refreshEngine(xmlDirPath);
				System.out.println("doRef");
			}
		}

		private void refreshEngine(String xmlDirPath) {
			this.pageModelParsedConfigs = null;
			isRunning = false;
			initXmlConfig(xmlDirPath);

		}

		private void initXmlConfig(String xmlDirPath) {
			String path = getClass().getClassLoader().getResource("").getPath();
			String url = path + xmlDirPath ;
			File file = new File(url);
			if(file.exists()){
				File[] files = file.listFiles(new FileFilter() {
					@Override
					public boolean accept(File pathname) {
						return pathname.getName().endsWith(".xml");
					}
				});
				if(files.length > 0 ){
					PageModelXmlParser pageModelXmlParser = new PageModelXmlParser();
					pageModelParsedConfigs = new HashMap<>();
					for (File file1 : files) {
						PageModelParsedConfig parsePageModelObj = pageModelXmlParser.parsePageModelXml(url+file1.getName());
						pageModelParsedConfigs.put(parsePageModelObj.getViewModel().getClass(), parsePageModelObj);
					}
					setPageModelParsedConfigs(pageModelParsedConfigs);

				}
			}

			isRunning = true;
		}

		public PageModelParsedConfig getPageModelParsedConfig(Class<? extends ViewModel> pageClass) {
			return pageModelParsedConfigs.get(pageClass);
		}

		public Map<Class<? extends ViewModel>, PageModelParsedConfig> getPageModelParsedConfigs() {
			return pageModelParsedConfigs;
		}

		public void setPageModelParsedConfigs(
				Map<Class<? extends ViewModel>, PageModelParsedConfig> pageModelParsedConfigs) {
			this.pageModelParsedConfigs = pageModelParsedConfigs;
		}

		@Override
		public String toString() {
			return "EngineCore [pageModelParsedConfigs=" + pageModelParsedConfigs + "]";
		}

	}

}
