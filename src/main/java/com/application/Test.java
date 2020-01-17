package com.application;


import com.application.realmodel.pagemodel.BasePageModel;
import com.application.realmodel.pagemodel.PersonPageModel;
import org.vrmframework.core.EngineCoreExecutor;
import org.vrmframework.core.EngineCoreFactory;



public class Test {

	public static void main(String[] args)	throws Exception {

		EngineCoreFactory.initEngineCore("com\\application\\xml\\",false);
		String aaa = "17";
		BasePageModel pageModel = (BasePageModel) EngineCoreExecutor.submitModelTask(PersonPageModel.class, aaa);
		System.out.println(pageModel.toString());
		// String checkCase = EnginCoreExecutor.checkCase(APageModel.class,
		// aaa);
		// APageModel aPageModel = (APageModel)
		// enginCore.initCase(APageModel.class, aaa);
	}

}
