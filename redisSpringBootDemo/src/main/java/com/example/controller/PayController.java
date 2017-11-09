package com.example.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.constant.Constant;
import com.example.model.PayInfo;
import com.example.service.PayService;

@RestController
public class PayController {

	@Resource
	private PayService payService;

	@RequestMapping("/pay")
	public String pay(PayInfo payInfo) {
		String resultMsg = "";

		if (payService.checkLimited(payInfo)) {
			
			resultMsg = Constant.PAY_SUCCESS_MSG;
			
		} else {
			
			resultMsg = Constant.PAY_FAILED_LIMITED_MSG;
			
		}

		return resultMsg;

	}

}
