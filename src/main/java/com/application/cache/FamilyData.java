package com.application.cache;

import java.io.Serializable;

/**
 * 业务模型 ,针对某个模块或者是某个功能的整体描述
 */
public class FamilyData implements Serializable  {

	private static final long serialVersionUID = 1L;

	private Person currApply;



	private Father father;
	private Mother mother;
	private Child child;
	private Address address;
	private String familyNo="";
	private String telephone="";
	private String annualIncome="";

	public Person getCurrApply() {
		return currApply;
	}

	public void setCurrApply(Person currApply) {
		this.currApply = currApply;
	}

	public Father getFather() {
		return father;
	}

	public void setFather(Father father) {
		this.father = father;
	}

	public Mother getMother() {
		return mother;
	}

	public void setMother(Mother mother) {
		this.mother = mother;
	}

	public Child getChild() {
		return child;
	}

	public void setChild(Child child) {
		this.child = child;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getFamilyNo() {
		return familyNo;
	}

	public void setFamilyNo(String familyNo) {
		this.familyNo = familyNo;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(String annualIncome) {
		this.annualIncome = annualIncome;
	}

	/**....还有其他数据*/

}
