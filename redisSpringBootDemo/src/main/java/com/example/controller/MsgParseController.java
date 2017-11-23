package com.example.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.message.LoUtils;

@RestController
public class MsgParseController {
	
	
	@RequestMapping("/parse")
	public String parse(HttpServletRequest request){
		return Arrays.toString(LoUtils.getRequestBodyByte(request));
	}
}
