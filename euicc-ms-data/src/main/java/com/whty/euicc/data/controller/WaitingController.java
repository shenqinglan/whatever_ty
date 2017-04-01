package com.whty.euicc.data.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 敬请期待
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/waiting")
public class WaitingController {
	/**
	 * 显示主列表页面
	 * @dzmsoftgenerated 2016-08-03 11:27
	 * @return
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show() {
		return "modules/waiting/waitingUI";
	}
}
