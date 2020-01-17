package org.vrmframework;

import org.vrmframework.core.EngineCoreFactory;
import org.vrmframework.parser.Situation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调用sif中的方法进行场景判定
 */
public class ExecRule {

	public static String checkCase(EngineCoreFactory.EngineCore enginCore, Class<?> class1, Object... params)
			throws  InstantiationException, IllegalAccessException, SecurityException, IllegalArgumentException {
		PageModelParsedConfig pageModelParsedConfig = enginCore.getPageModelParsedConfig(class1);
		List<Situation> situations = pageModelParsedConfig.getSituations();
		Map<String, Boolean> invoke = new HashMap<>();
		for (Situation v : situations) {
			Method m4condition = v.getM4condition();
			if (null != m4condition) {
				try {
					if (params.length == 0 && m4condition.getParameterCount() == 0) {
						Object invoke2 = m4condition.invoke(v.getClaz4conditionMethod().newInstance(), params);
						invoke.put(v.getConditionValue(), Boolean.parseBoolean(invoke2.toString()));
						break;
					} else {
						Parameter[] parameters = m4condition.getParameters();
						List<Integer> getmParams4condition = v.getmParams4condition();
						Object[] useParams = new Object[getmParams4condition.size()];
						for (int i = 0; i < getmParams4condition.size(); i++) {
							useParams[i] = params[getmParams4condition.get(i)];
						}
						boolean isFound = false;
						for (int i = 0; i < parameters.length && i < useParams.length; i++) {
							if (!useParams[i].getClass().equals(parameters[i].getType())) {
								isFound = false;
								break;
							} else {
								isFound = true;
							}
						}
						if (isFound) {
							Object invoke2 = m4condition.invoke(v.getClaz4conditionMethod().newInstance(), useParams);
							invoke.put(v.getConditionValue(), Boolean.parseBoolean(invoke2.toString()));
						} else {
							invoke.put(v.getConditionValue(), false);
						}
					}
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				}
			}
		}

		for (String key : invoke.keySet()) {
			if (invoke.get(key))
				return key;
		}
		return "default";// 默认情况

	}


	public static String checkCase2(EngineCoreFactory.EngineCore enginCore, Class<?> class1, Object... params) {
		PageModelParsedConfig pageModelParsedConfig = enginCore.getPageModelParsedConfig(class1);
		List<Situation> situations = pageModelParsedConfig.getSituations();
		Map<String, Boolean> invoke = new HashMap<>();
		for (Situation v : situations) {
			Method m4condition = v.getM4condition();
			if (null != m4condition) {
				try {
					if (params.length == 0 && m4condition.getParameterCount() == 0) {
						Object invoke2 = m4condition.invoke(v.getClaz4conditionMethod().newInstance(), params);
						invoke.put(v.getConditionValue(), Boolean.parseBoolean(invoke2.toString()));
						break;
					} else if (params.length == 1 && params.length == m4condition.getParameterCount()
							&& params[0].getClass().equals(m4condition.getParameters()[0].getType())) {
						Object invoke2 = m4condition.invoke(v.getClaz4conditionMethod().newInstance(), params);
						invoke.put(v.getConditionValue(), Boolean.parseBoolean(invoke2.toString()));
						break;
					} else {
						Parameter[] parameters = m4condition.getParameters();
						boolean isFound = false;
						for (int i = 0; i < parameters.length && i < params.length; i++) {
							if (!params[i].getClass().equals(parameters[i].getType())) {
								isFound = false;
								break;
							} else {
								isFound = true;
							}
						}
						if (isFound) {
							Object invoke2 = m4condition.invoke(v.getClaz4conditionMethod().newInstance(), params);
							invoke.put(v.getConditionValue(), Boolean.parseBoolean(invoke2.toString()));
						} else {
							invoke.put(v.getConditionValue(), false);
						}
					}
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				}
			}
		}

		for (String key : invoke.keySet()) {
			if (invoke.get(key))
				return key;
		}
		return "default";// 默认情况

	}



}
