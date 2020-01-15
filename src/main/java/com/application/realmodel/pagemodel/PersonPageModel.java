package com.application.realmodel.pagemodel;

import com.application.cache.Child;
import com.application.cache.School;
import com.application.cache.Work;

/**
 * 修改人员信息页面的
 */
public class PersonPageModel extends BasePageModel {

    private String name;
    private String idNo;
    private String nation;
    private String sex;
    private String age;
    private String birthDay;
    private Work work;
    private School school;
    private String disable; /**禁用check1 和check2*/
    private String check1; /**当前申请人的*/
    private String check2; /**未成年孩子的*/
    private Child underAgeChildren;

    @Override
    public String toString() {
        return "PersonPageModel{" +
                "name='" + name + '\'' +
                ", idNo='" + idNo + '\'' +
                ", nation='" + nation + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", birthDay='" + birthDay + '\'' +
                ", work=" + work +
                ", school=" + school +
                ", disable='" + disable + '\'' +
                ", check1='" + check1 + '\'' +
                ", check2='" + check2 + '\'' +
                ", underAgeChildren=" + underAgeChildren +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public String getDisable() {
        return disable;
    }

    public void setDisable(String disable) {
        this.disable = disable;
    }

    public String getCheck1() {
        return check1;
    }

    public void setCheck1(String check1) {
        this.check1 = check1;
    }

    public String getCheck2() {
        return check2;
    }

    public void setCheck2(String check2) {
        this.check2 = check2;
    }

    public Child getUnderAgeChildren() {
        return underAgeChildren;
    }

    public void setUnderAgeChildren(Child underAgeChildren) {
        this.underAgeChildren = underAgeChildren;
    }
}
