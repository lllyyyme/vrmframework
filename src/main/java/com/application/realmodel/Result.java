package com.application.realmodel;

import com.application.realmodel.pagemodel.BasePageModel;

/**
 * @author ljx
 * @time 2020��1��9��
 * @version 1.0
 *          <p>
 *          Description:
 *          <p/>
 */
public class Result{

	private String resultCode;
	private String msg;
	private BasePageModel pageModel;

	public BasePageModel getPageModel() {
		return pageModel;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setPageModel(BasePageModel pageModel) {
		this.pageModel = pageModel;
	}

}
