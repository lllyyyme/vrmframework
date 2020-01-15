package org.vrmframework.parser;

import com.application.realmodel.pagemodel.BasePageModel;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.vrmframework.DataModel;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;


public class PageModelXmlParser {


	public PageModelParsedConfig parsePageModelXml(String url) {
		PageModelParsedConfig pageModelParsedObject = new PageModelParsedConfig();
		SAXReader saxReader = new SAXReader();
		Document read;
		try {
			read = saxReader.read(url);
			Element rootElement = read.getRootElement();
			Attribute attribute = rootElement.attribute("ref");
			pageModelParsedObject.setClaz4PageModel(Class.forName(attribute.getText()));
			pageModelParsedObject
					.setViewModel((BasePageModel) pageModelParsedObject.getClaz4PageModel().newInstance());
			ArrayList<Situation> arrayList = new ArrayList<>();

			Element element = rootElement.element("datamodels");
			Iterator<?> datamodels = element.elements().iterator();
			Map<String, Class<?>> datamodelsMap = new HashMap<>();
			while (datamodels.hasNext()) {
				Element datamodel = (Element) datamodels.next();
				Attribute did = datamodel.attribute("did");
				String didText = did.getText();
				Attribute dclass = datamodel.attribute("dclass");
				String dclassText = dclass.getText();

				if(!datamodelsMap.containsKey(dclassText)){
					datamodelsMap.put(didText, Class.forName(dclassText));
				}
			}
			pageModelParsedObject.setDatamodels(datamodelsMap);

			List<?> elements = rootElement.elements("situation");
			Iterator<?> iterator = elements.iterator();
			Map<String, DataModel<?>> vauleRefMap = new HashMap<>();
			while (iterator.hasNext()) {
				Situation situation = new Situation();
				Element next = (Element) iterator.next();
				Attribute sAttrId = next.attribute("sid");
				String sAttrIdText = sAttrId.getText();
				situation.setConditionValue(sAttrIdText);

				Attribute sAttrIf = next.attribute("sif");
				String sAttrIfText = sAttrIf.getText().trim();
				int lastIndexOf = sAttrIfText.lastIndexOf('.');
				if (null != sAttrIfText && !"null".equals(sAttrIfText) && !"".equals(sAttrIfText)) {
					String claz = sAttrIfText.substring(0, lastIndexOf);
					Class<?> forName = Class.forName(claz);
					String method = sAttrIfText.substring(lastIndexOf);
					int indexOf = method.indexOf('(');
					String methodName = method.substring(1, indexOf);
					String methodNameParamerters = method.substring(indexOf + 1, method.length() - 1);
					IfMethod ifMethod = new IfMethod();
					ifMethod.methodName = methodName;
					ifMethod.params = methodNameParamerters.split(",");
					Method declaredMethod = getMethodByClaz(forName, ifMethod);
					situation.setM4condition(declaredMethod);
					situation.setClaz4conditionMethod(forName);
				} else {
					situation = new Situation(sAttrIdText, null);
				}
				situation.setFields(new ArrayList<>());
				List<?> elements2 = next.elements();
				for (Object object : elements2) {
					Element fieldEle = (Element) object;
					Attribute fname = fieldEle.attribute("fname");
					Attribute fvalue = fieldEle.attribute("fvalue");
					Attribute byMethod = fieldEle.attribute("byMethod");
					Attribute vauleRef = fieldEle.attribute("v-ref");
					SField field = null;
					if (byMethod != null && !byMethod.getText().isEmpty()) {
						String fvalueText = byMethod.getText();
						int fvalueClassIndexOf = sAttrIfText.lastIndexOf('.');
						String fvalueClass = fvalueText.substring(0, fvalueClassIndexOf);
						Class<?> forName = Class.forName(fvalueClass);
						String fvalueMethod = fvalueText.substring(fvalueClassIndexOf);
						int indexOf = fvalueMethod.indexOf('(');
						String methodName = fvalueMethod.substring(1, indexOf);
						String methodNameParamerters = fvalueMethod.substring(indexOf + 1, fvalueMethod.length() - 1);
						IfMethod ifMethod = new IfMethod();
						ifMethod.methodName = methodName;
						ifMethod.params = methodNameParamerters.split(",");
						Method declaredMethod = getMethodByClaz(forName, ifMethod);
						field = new SField(fname.getText(), fvalue.getText(), declaredMethod, null);
					} else {
						field = new SField(fname.getText(), fvalue.getText(), null, null);
					}

					if (vauleRef != null && !vauleRef.getText().isEmpty()) {
						String vauleRefText = vauleRef.getText();

						if (vauleRefMap.containsKey(vauleRefText)) {
							field.setValueRef(vauleRefMap.get(vauleRefText));
						} else {
							Class<?> class1 = datamodelsMap.get(vauleRefText);
							Object newInstance = class1.newInstance();
							field.setValueRef((DataModel<?>) newInstance);
							vauleRefMap.put(vauleRefText, (DataModel<?>) newInstance);
						}
					} else {
						field.setValueRef(vauleRefMap.get(null));
					}
					situation.getFields().add(field);
				}
				arrayList.add(situation);

			}
			pageModelParsedObject.setSituations(arrayList);

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		return pageModelParsedObject;

	}

	private static Method getMethodByClaz(Class<?> forName, IfMethod ifMethod)
			throws NoSuchMethodException, SecurityException {
		assert ifMethod == null;
		Method[] declaredMethods = forName.getDeclaredMethods();
		String[] split = ifMethod.params;
		Map<String, List<Parameter[]>> map = new HashMap<>();
		for (Method method : declaredMethods) {
			String name = method.getName();

			int parameterCount = method.getParameterCount();
			Parameter[] parametersM = method.getParameters();
			List<Parameter[]> list = null;
			if (!(map.containsKey(ifMethod.methodName + "-" + parameterCount))) {
				list = new ArrayList<>();
			} else {
				list = map.get(ifMethod.methodName + "-" + parameterCount);
			}
			list.add(parametersM);
			map.put(name + "-" + parameterCount, list);
		}
		int length = split.length;
		List<Parameter[]> list = map.get(ifMethod.methodName + "-" + length);
		Parameter[] found = null;
		boolean isFound = false;
		for (Parameter[] parameters : list) {
			int length2 = parameters.length;
			for (int i = 0; i < length2 && i < length; i++) {
				if (!parameters[i].getType().getSimpleName().equals(split[i])) {
					isFound = false;
					break;
				} else {
					isFound = true;
				}
			}
			if (isFound) {
				found = parameters;
				break;
			}

		}
		Class<?>[] methodParamClazs = new Class[found.length];
		for (int i = 0; i < found.length; i++) {
			methodParamClazs[i] = found[i].getType();
		}
		Method declaredMethod = forName.getDeclaredMethod(ifMethod.methodName, methodParamClazs);
		return declaredMethod;
	}

}
