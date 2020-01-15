package org.vrmframework.parser;

import org.vrmframework.DataModel;

import java.lang.reflect.Method;


public class SField {
	private String fname;
	private String fvalue;
	private Method byFMethod;
	private DataModel<?> valueRef;

	public SField() {
		super();
	}

	public SField(String fname, String fvalue, Method byFMethod, DataModel<?> valueRef) {
		super();
		this.fname = fname;
		this.fvalue = fvalue;
		this.byFMethod = byFMethod;
		this.valueRef = valueRef;
	}


	public Method getByFMethod() {
		return byFMethod;
	}

	public void setByFMethod(Method byFMethod) {
		this.byFMethod = byFMethod;
	}

	public DataModel<?> getValueRef() {
		return valueRef;
	}

	public void setValueRef(DataModel<?> valueRef) {
		this.valueRef = valueRef;
	}

	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getFvalue() {
		return fvalue;
	}
	public void setFvalue(String fvalue) {
		this.fvalue = fvalue;
	}
	@Override
	public String toString() {
		return "SField [fname=" + fname + ", fvalue=" + fvalue + ", byFMethod=" + byFMethod + ", valueRef=" + valueRef
				+ "]";
	}


}
