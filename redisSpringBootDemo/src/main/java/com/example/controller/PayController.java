package com.example.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
			resultMsg = "可正常支付 ";
		} else {
			resultMsg = "当日交易金额已超出限额，交易失败！！！";
		}

		return resultMsg;

	}

}
