package com.example.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.MarchantInfo;
import com.example.service.MarchantService;

@RestController
public class MarchantController {

	@Resource
	private MarchantService marchantService;

	@RequestMapping("/queryMarchantInfoByMarcName")
	public List<MarchantInfo> queryMarchantInfoByMarcName(String marchName) {
		return marchantService.queryMarchantInfoByMarchName(marchName);
	}

	@RequestMapping("/addMarchantInfo")
	public String addMarchantInfo(MarchantInfo marchantInfo) {
		if (marchantService.addMarchantInfo(marchantInfo)) {
			return "添加成功！！！";
		}
		return "添加失败！！！";
	}
	
	@RequestMapping("/updateMarchantInfo")
	public String updateMarchantInfo(MarchantInfo marchantInfo) {
		if (marchantService.updateMarchantInfo(marchantInfo)) {
			return "更新成功！！！";
		}
		return "更新失败！！！";
	}
	
	
	@RequestMapping("/delMarchantInfo")
	public String delMarchantInfo(String marchCode) {
		if (marchantService.delMarchantInfo(marchCode)) {
			return "删除成功！！！";
		}
		return "删除失败！！！";
	}
	
	

}
