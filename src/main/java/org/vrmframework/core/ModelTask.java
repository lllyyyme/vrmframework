package org.vrmframework.core;

import org.vrmframework.DataModel;
import org.vrmframework.ExecRule;
import org.vrmframework.PageModelParsedConfig;
import org.vrmframework.parser.SField;
import org.vrmframework.parser.Situation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;



public class ModelTask  implements  Callable<Object> {

	private Class<?> class1; 
	private Object[] params;

	public ModelTask(Class<?> class1, Object[] params) {
		super();
		this.class1 = class1;
		this.params = params;
	}

	@Override
	public Object call() throws Exception {
		return  this.doGetPageModel(this.class1, this.params);
	}


	/** DO TASK */
	private Object doGetPageModel(Class<?> class1, Object... params) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		EngineCoreFactory.EngineCore EngineCore = null;
		try {
			EngineCore = EngineCoreFactory.getEngineCore();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<Class<?>, PageModelParsedConfig> pageModelParsedConfigs = EngineCore.pageModelParsedConfigs;
		PageModelParsedConfig pageModelParsedConfig = pageModelParsedConfigs.get(class1);
		String checkCase = ExecRule.checkCase(EngineCore, class1, params);
		List<Situation> situations = pageModelParsedConfig.getSituations();
		Situation initThis = null;
		for (Situation situation : situations) {
			if (checkCase.equals(situation.getConditionValue())) {
				initThis = situation;
				break;
			}
		}
		return createPageModel(class1, initThis, pageModelParsedConfig,params);
	}

	private Object createPageModel(Class<?> class1, Situation initThis, PageModelParsedConfig pageModelParsedConfig, Object... params2)
			throws InstantiationException, IllegalAccessException {
		List<SField> fields = initThis.getFields();

		Method[] modelDeclaredMethods = class1.getDeclaredMethods();
		Map<String, Method> pageClazMap = new HashMap<>();
		Map<String, Object> dataClazMap = new HashMap<>();
		Object newInstance =  class1.newInstance();
		for (SField sfield : fields) {
			/** fname */
			String fname = sfield.getFname();
			StringBuilder setMethod4FN = new StringBuilder();
			setMethod4FN.append("set").append(fname.substring(0, 1).toUpperCase()).append(fname.substring(1));
			for (Method method : modelDeclaredMethods) {
				if (setMethod4FN.toString().equals(method.getName())) {
					pageClazMap.put(fname, method);
				}
			}
			/** fvalue */
			String fvalue = sfield.getFvalue();
			if (!isConstValue(fvalue)) {
				DataModel<?> valueRef = sfield.getValueRef();
				Object data = valueRef.getData(params2);
				Method[] dataDeclaredMethods = data.getClass().getDeclaredMethods();
				fvalue = fvalue.substring(1, fvalue.length() - 1);
				String[] split = fvalue.split("[.]");
				if (split.length == 1) {
					StringBuilder getMethod4FV = new StringBuilder();
					getMethod4FV.append("get").append(split[0].substring(0, 1).toUpperCase()).append(split[0]);
					for (Method method : dataDeclaredMethods) {
						if (getMethod4FV.toString().equals(method.getName())) {
							try {
								Object invoke = method.invoke(data);
								dataClazMap.put(fname, invoke);
							} catch (IllegalArgumentException | InvocationTargetException e) {
								e.printStackTrace();
							}
						}
					}
				} else {
					findFieldMethod(split, 0, data, dataClazMap, fname);
				}
			} else {
				dataClazMap.put(fname, fvalue);
			}
			/** byMethod TODO */

		}
		for (String key : pageClazMap.keySet()) {
			Method value = pageClazMap.get(key);
			try {
				Object object = dataClazMap.get(key);
				value.invoke(newInstance, object);
			} catch (IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return newInstance;

	}
	/**
	 * {xxxx} �Ա�����ʽ���� 
	 * @param fvalue
	 * @return
	 */
	private boolean isConstValue(String fvalue) {
		char charAt2 = fvalue.charAt(0);
		char charAt = fvalue.charAt(fvalue.length() - 1);
		return '{' != charAt2 || '}' != charAt;
	}

	private void findFieldMethod(String[] split, int index, Object obj, Map<String, Object> dataClazMap, String key) {
		boolean foundF = true;
		if (null != obj && index < split.length && foundF) {
			StringBuilder getMethod4FV = new StringBuilder();
			StringBuilder isMethod4FV = new StringBuilder();
			getMethod4FV.append("get").append(split[index].substring(0, 1).toUpperCase())
					.append(split[index].substring(1));
			isMethod4FV.append("is").append(split[index].substring(0, 1).toUpperCase())
					.append(split[index].substring(1));
			Method[] declaredMethods = obj.getClass().getDeclaredMethods();
			Method found = null;
			for (Method method : declaredMethods) {
				if (getMethod4FV.toString().equals(method.getName())
						|| isMethod4FV.toString().equals(method.getName())) {
					found = method;
					foundF = true;
					break;
				}
			}
			if (null == found) {
				foundF = false;
				return;
			}
			index++;
			if (foundF && index == split.length) {
				Object invoke;
				try {
					invoke = found.invoke(obj);
					invoke = found.getReturnType().cast(invoke);
					dataClazMap.put(key, invoke);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			} else if (foundF && index < split.length) {
				Object invoke = null;
				try {
					invoke = found.invoke(obj);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
				findFieldMethod(split, index, invoke, dataClazMap, key);
			}

		}
	}


}