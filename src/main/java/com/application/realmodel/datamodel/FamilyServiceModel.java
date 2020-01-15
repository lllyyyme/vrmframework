package com.application.realmodel.datamodel;


import com.alibaba.fastjson.JSONObject;
import com.application.cache.FamilyData;
import com.application.cache.Father;
import com.application.cache.Person;
import lib.nio.file.NIOFile;
import org.vrmframework.DataModel;


/**
 * 使用FamilyData，实现数据模型接口，定义数据来源
 */
public class FamilyServiceModel implements DataModel<FamilyData> {


	@Override
	public FamilyData getData() {
		String path = getClass().getClassLoader().getResource("").getPath();

		String dataPath = path + "com\\application\\cache\\cache.json";

		String readNIO = NIOFile.readNIO(dataPath.substring(1));
		FamilyData parseObject = JSONObject.parseObject(readNIO, FamilyData.class);
		Person person = new Person();
		Father father = parseObject.getFather();

		person.setName(father.getName());
		person.setAge(father.getAge());
		person.setBirthDay(father.getBirthDay());
		person.setIdNo(father.getIdNo());
		person.setNation(father.getNation());


		parseObject.setCurrApply(person);
			return parseObject;
	}


}
