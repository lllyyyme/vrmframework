package com.application.cache;

public class School {
    /**学校名称*/
    private String schoolName="";
    /**学历*/
    private String educationalBackground="";
    /**是否毕业*/
    private String graduateFlag="";

    public String getGraduateFlag() {
        return graduateFlag;
    }

    public void setGraduateFlag(String graduateFlag) {
        this.graduateFlag = graduateFlag;
    }

    public String getEducationalBackground() {
        return educationalBackground;
    }

    public void setEducationalBackground(String educationalBackground) {
        this.educationalBackground = educationalBackground;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}
