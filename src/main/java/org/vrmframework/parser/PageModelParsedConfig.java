package org.vrmframework.parser;

import org.vrmframework.ViewModel;

import java.util.List;
import java.util.Map;


public class PageModelParsedConfig {

	private Map<String, Class<?>> datamodels;

	private ViewModel viewModel;

	private Class<?> claz4PageModel;

	private List<Situation> situations;

	public ViewModel getViewModel() {
		return viewModel;
	}

	public void setViewModel(ViewModel viewModel) {
		this.viewModel = viewModel;
	}

	public Class<?> getClaz4PageModel() {
		return claz4PageModel;
	}

	public Map<String, Class<?>> getDatamodels() {
		return datamodels;
	}

	public void setDatamodels(Map<String, Class<?>> datamodels) {
		this.datamodels = datamodels;
	}

	public void setClaz4PageModel(Class<?> claz4PageModel) {
		this.claz4PageModel = claz4PageModel;
	}

	public List<Situation> getSituations() {
		return situations;
	}

	public void setSituations(List<Situation> situations) {
		this.situations = situations;
	}

	@Override
	public String toString() {
		return "PageModelParsedObject [viewModel=" + viewModel + ", claz4PageModel=" + claz4PageModel
				+ ", situations=" + situations + "]";
	}

}
