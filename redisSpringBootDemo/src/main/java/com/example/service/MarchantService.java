package com.example.service;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.example.dao.MarchantDao;
import com.example.model.MarchantInfo;

@Service
public class MarchantService {
	
	@Resource
	private MarchantDao marchantDao;
	
	/**
	 * 添加商户限额配置信息
	 * 
	 * @param marchantInfo
	 * @return
	 */
	public boolean addMarchantInfo(MarchantInfo marchantInfo) {
		return marchantDao.addMarchantInfo(marchantInfo);
	}

	/**
	 * 根据商户号删除商户限额配置信息(数据库和Redis)
	 * 
	 * @param marchCode
	 * @return
	 */
	public boolean delMarchantInfo(String marchCode) {

		
		return marchantDao.delMarchantInfo(marchCode);
	}

	/**
	 * 修改商户限额信息
	 * 
	 * @param marchantInfo
	 * @return
	 */
	public boolean updateMarchantInfo(MarchantInfo marchantInfo) {
		
		return marchantDao.updateMarchantInfo(marchantInfo);
	}
	
	/**
	 * 根据商户名称查询商户信息
	 * 
	 * @param marchantInfo
	 * @return
	 */
	public List<MarchantInfo> queryMarchantInfoByMarchName(String marchName) {
		return marchantDao.queryMarchantInfoByMarchName(marchName);
	}
	
	public List<MarchantInfo> queryAllMarchInfo(){
		return marchantDao.queryAllMarchInfo();
	}
}
