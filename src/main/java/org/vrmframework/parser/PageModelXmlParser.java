package org.vrmframework.parser;


import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.vrmframework.DataModel;
import org.vrmframework.PageModelParsedConfig;
import org.vrmframework.exception.XMLException;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class PageModelXmlParser {


//	public static void main(String[] args) {
//		String path = PageModelXmlParser.class.getClassLoader().getResource("").getPath();
//		String url = path + "wya/projectap/usingconfigable/apply/xml/";
//		PageModelXmlParser pageModelXmlParser = new PageModelXmlParser();
//		PageModelParsedConfig parsePageModelObj = pageModelXmlParser.parsePageModelXml(url + "APageModel.xml");
//
//	}


	public PageModelParsedConfig parsePageModelXml(String url) throws Exception {
		PageModelParsedConfig pageModelParsedObject = new PageModelParsedConfig();
		SAXReader saxReader = new SAXReader();
		Document read = null;
		try {
			read = saxReader.read(url);
		} catch (DocumentException e) {
			System.out.println("dom4j读取文档异常，请检查xml");
			e.printStackTrace();
		}
		Element rootElement = read.getRootElement();
		Attribute attribute = rootElement.attribute(XmlType.ATTR_ROOT_REF);
		try {
			pageModelParsedObject.setClaz4PageModel(Class.forName(attribute.getText()));
		} catch (ClassNotFoundException e) {
			System.err.println("找不到类" + attribute.getText());
			e.printStackTrace();
		}
		try {
			pageModelParsedObject.setBasePageModel(pageModelParsedObject.getClaz4PageModel().newInstance());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		ArrayList<Situation> arrayList = new ArrayList<>();

		Element element = rootElement.element(XmlType.TAG_DATAMODELS);
		Iterator<?> datamodels = element.elements().iterator();
		Map<String, Class<?>> datamodelsMap = new HashMap<>();
		while (datamodels.hasNext()) {
			Element datamodel = (Element) datamodels.next();
			Attribute did = datamodel.attribute(XmlType.ATTR_DATAMODEL_DID);
			String didText = did.getText();
			Attribute dclass = datamodel.attribute(XmlType.ATTR_DATAMODEL_DCLASS);
			String dclassText = dclass.getText();

			if(!datamodelsMap.containsKey(dclassText)){
				try {
					datamodelsMap.put(didText, Class.forName(dclassText));
				} catch (ClassNotFoundException e) {
					System.err.println("找不到类" + dclassText);
					e.printStackTrace();
				}
			}
		}
		pageModelParsedObject.setDatamodels(datamodelsMap);


		Element elementRules = rootElement.element(XmlType.TAG_RULES);
		Map<String, Class<?>> ruleMap = new HashMap<>();
		if(null != elementRules){
			Iterator<?> elementRuleList = elementRules.elements().iterator();
			while (elementRuleList.hasNext()) {
				Element elementRule = (Element) elementRuleList.next();
				Attribute rname = elementRule.attribute(XmlType.ATTR_RULE_RNAME);
				String rnameText = rname.getText();
				Attribute rclass = elementRule.attribute(XmlType.ATTR_RULE_RCLASSS);
				String rclassText = rclass.getText();

				if(!datamodelsMap.containsKey(rclassText)){
					try {
						ruleMap.put(rnameText, Class.forName(rclassText));
					} catch (ClassNotFoundException e) {
						System.err.println("找不到类" + rclassText);
						e.printStackTrace();
					}
				}
			}
		}

		List<?> elements = rootElement.elements(XmlType.TAG_SITUATION);
		Iterator<?> iterator = elements.iterator();
		Map<String, DataModel<?>> vauleRefMap = new HashMap<>();
		while (iterator.hasNext()) {
			Situation situation = new Situation();
			Element next = (Element) iterator.next();
			Attribute sAttrId = next.attribute(XmlType.ATTR_SITUATION_SID);
			String sAttrIdText = sAttrId.getText();
			Attribute sAttrIf = next.attribute(XmlType.ATTR_SITUATION_SIF);
			String sAttrIfText = sAttrIf.getText().trim();
			//aPageRule.isChangeSure({1,rtype=java.lang.String})

			if (null != sAttrIfText && !"null".equals(sAttrIfText) && !"".equals(sAttrIfText)) {
				String name = sAttrIfText.split("[.]")[0];
				String[] methodNameParamerters = PatternUtils.getTypeStringByPattern("\\{(\\d+),(.*?)\\}", sAttrIfText);
				Class<?> forName = ruleMap.get(name);
				String methodName = PatternUtils.getMethodNameByPattern("\\.(.*?)\\(", sAttrIfText);
				List<Integer> typeIndexByPattern = PatternUtils.getTypeIndexByPattern("\\{(\\d+),(.*?)\\}", sAttrIfText);
				IfMethod ifMethod = new IfMethod();
				ifMethod.methodName = methodName;
				ifMethod.params = methodNameParamerters;
				Method declaredMethod = getMethodByClaz(forName, ifMethod);
				situation = new Situation(sAttrIdText, null);
				situation.setM4condition(declaredMethod);
				situation.setClaz4conditionMethod(forName);
				situation.setmParams4condition(typeIndexByPattern);
			} else {
				situation = new Situation(sAttrIdText, null);
			}
			situation.setFields(new ArrayList<>());
			List<?> elements2 = next.elements();
			for (Object object : elements2) {
				Element fieldEle = (Element) object;
				Attribute fname = fieldEle.attribute(XmlType.ATTR_FIELD_FNAME);
				Attribute fvalue = fieldEle.attribute(XmlType.ATTR_FIELD_FVALUE);
				Attribute byMethod = fieldEle.attribute(XmlType.ATTR_FIELD_BYMETHOD);
				Attribute vauleRef = fieldEle.attribute(XmlType.ATTR_FIELD_VREF);
				SField field = null;
				if (byMethod != null && !byMethod.getText().isEmpty()) {
					String fvalueText = byMethod.getText();
					int fvalueClassIndexOf = sAttrIfText.lastIndexOf('.');
					String fvalueClass = fvalueText.substring(0, fvalueClassIndexOf);
					Class<?> forName = null;
					try {
						forName = Class.forName(fvalueClass);
					} catch (ClassNotFoundException e) {
						System.err.println("找不到类" + fvalueClass);
						e.printStackTrace();
					}
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
						Object newInstance = null;
						try {
							newInstance = class1.newInstance();
						} catch (InstantiationException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
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



		return pageModelParsedObject;

	}

	private static Method getMethodByClaz(Class<?> forName, IfMethod ifMethod) throws Exception{
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
				if (!parameters[i].getType().getName().equals(split[i])) {
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
		if(found !=null){
			Class<?>[] methodParamClazs = new Class[found.length];
			for (int i = 0; i < found.length; i++) {
				methodParamClazs[i] = found[i].getType();
			}
			Method declaredMethod = null;
			try {
				declaredMethod = forName.getDeclaredMethod(ifMethod.methodName, methodParamClazs);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			return declaredMethod;
		}else{
			throw new XMLException("未定义的方法:["+ ifMethod.methodName+"]["+ Arrays.toString(ifMethod.params)+"]" );
		}


	}


}
