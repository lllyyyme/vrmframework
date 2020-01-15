package com.application;


import com.application.realmodel.pagemodel.PersonPageModel;
import org.vrmframework.ViewModel;
import org.vrmframework.core.EngineCoreExecutor;
import org.vrmframework.core.EngineCoreFactory;

import java.lang.reflect.InvocationTargetException;



public class Test {

	public static void main(String[] args)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {

		EngineCoreFactory.initEngineCore("com\\application\\config\\xml\\",false);
		String aaa = "17";
		ViewModel pageModel = new EngineCoreExecutor<PersonPageModel>().getPageModel(PersonPageModel.class, aaa);
		System.out.println(pageModel.toString());
		// String checkCase = EnginCoreExecutor.checkCase(APageModel.class,
		// aaa);
		// APageModel aPageModel = (APageModel)
		// enginCore.initCase(APageModel.class, aaa);
	}

}
