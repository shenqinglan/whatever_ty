package com.whty.euicc.bizflow.engine;

import com.whty.euicc.bizflow.template.BizTemplate;

public interface BizTemplateCachePloy {

	BizTemplate get(String key);

	void set(String key, BizTemplate bt);

}
