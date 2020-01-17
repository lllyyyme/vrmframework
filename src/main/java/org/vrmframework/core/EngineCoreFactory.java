package org.vrmframework.core;

import org.vrmframework.PageModelParsedConfig;
import org.vrmframework.parser.PageModelXmlParser;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.Map;


public class EngineCoreFactory {

	private static EngineCore EngineCore;

	private static boolean isRunning = false;

	private static final String DEFAULT_PACKAGE_PATH = "wya\\projectap\\usingconfigable\\apply\\config\\xml\\";

	public static EngineCore getEngineCore() throws Exception {
		if (null != EngineCore) {
			return EngineCore;
		} else
			throw new Exception("core not initialized");
	}

	public static EngineCore initEngineCore(boolean ref) throws Exception {
		if (ref) {
			return EngineCore = new EngineCore(DEFAULT_PACKAGE_PATH);
		} else {
			if (null != EngineCore) {
				return EngineCore;
			} else {
				return EngineCore = new EngineCore(DEFAULT_PACKAGE_PATH);
			}

		}
	}

	public static EngineCore initEngineCore(String filePath, boolean ref)
			throws Exception {
		if (ref) {
			return EngineCore = new EngineCore(filePath);
		} else {
			if (null != EngineCore)
				return EngineCore;
			else
				return EngineCore = new EngineCore(filePath);
		}
	}

	public static class EngineCore {
		Map<Class<?>, PageModelParsedConfig> pageModelParsedConfigs;

		private EngineCore(String xmlDirPath) throws Exception {
			if (!isRunning) {
				System.out.println("doInit");
				initXmlConfig(xmlDirPath);
				isRunning = true;
			} else {
				refreshEngin(xmlDirPath);
				System.out.println("doRef");
			}
		}

		private void refreshEngin(String xmlDirPath) throws Exception {
			pageModelParsedConfigs = null;
			isRunning = false;
			initXmlConfig(xmlDirPath);

		}

		private void initXmlConfig(String xmlDirPath) throws Exception {
			String path = getClass().getClassLoader().getResource("").getPath();
			String url = path + xmlDirPath;
			File file = new File(url);
			File[] listFiles = file.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return pathname.getName().endsWith(".xml");
				}
			});
			pageModelParsedConfigs = new HashMap<>();
			for (File file2 : listFiles) {
				PageModelXmlParser pageModelXmlParser = new PageModelXmlParser();
				PageModelParsedConfig parsePageModelObj = pageModelXmlParser.parsePageModelXml(url + file2.getName());
				pageModelParsedConfigs.put(parsePageModelObj.getBasePageModel().getClass(), parsePageModelObj);
			}
			isRunning = true;
		}

		public PageModelParsedConfig getPageModelParsedConfig(Class<?> pageClass) {
			return pageModelParsedConfigs.get(pageClass);
		}

		@Override
		public String toString() {
			return "EnginCore [pageModelParsedConfigs=" + pageModelParsedConfigs + "]";
		}

	}

}
