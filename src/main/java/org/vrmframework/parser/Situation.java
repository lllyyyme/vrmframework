package org.vrmframework.parser;

import java.lang.reflect.Method;
import java.util.List;


public class Situation {
	
	private String conditionValue;

	private Method m4condition;
	
	private Class<?> claz4conditionMethod;
	
	private List<SField> fields;	 
	
	private List<Integer> mParams4condition;
	

	public List<Integer> getmParams4condition() {
		return mParams4condition;
	}


	public void setmParams4condition(List<Integer> mParams4condition) {
		this.mParams4condition = mParams4condition;
	}


	public Situation() {
		super();
	}


	public Situation(String conditionValue, Method m4condition) {
		super();
		this.conditionValue = conditionValue;
		this.m4condition = m4condition;
	}


	public List<SField> getFields() {
		return fields;
	}


	public void setFields(List<SField> fields) {
		this.fields = fields;
	}



	public String getConditionValue() {
		return conditionValue;
	}



	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}



	public Method getM4condition() {
		return m4condition;
	}


	public void setM4condition(Method m4condition) {
		this.m4condition = m4condition;
	}

	
	public Class<?> getClaz4conditionMethod() {
		return claz4conditionMethod;
	}


	public void setClaz4conditionMethod(Class<?> claz4conditionMethod) {
		this.claz4conditionMethod = claz4conditionMethod;
	}


	@Override
	public String toString() {
		return "Situation [conditionValue=" + conditionValue + ", m4condition=" + m4condition
				+ ", claz4conditionMethod=" + claz4conditionMethod + ", fields=" + fields + ", mParams4condition="
				+ mParams4condition + "]";
	}


	

}
