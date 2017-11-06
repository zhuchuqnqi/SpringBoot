package com.example.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.example.constant.Constatnt;
import com.example.mapper.MarchantMapper;
import com.example.model.MarchantInfo;
import com.example.util.RedisUtil;

@Repository
public class MarchantDao {

	@Resource
	private RedisUtil redisUtil;

	@Resource
	private MarchantMapper marchantMapper;

	/**
	 * 添加商户限额配置信息
	 * 
	 * @param marchantInfo
	 * @return
	 */
	public boolean addMarchantInfo(MarchantInfo marchantInfo) {
		if (marchantMapper.insert(marchantInfo) > 0) {
			redisUtil.set(Constatnt.MARCH_LIMITED_PREFIX,
					marchantInfo.getMarchCode(),
					String.valueOf(marchantInfo.getDaySumLimited()));

			return true;
		}

		return false;
	}

	/**
	 * 根据商户号删除商户限额配置信息(数据库和Redis)
	 * 
	 * @param marchCode
	 * @return
	 */
	public boolean delMarchantInfo(String marchCode) {

		if (marchantMapper.delMarchantInfo(marchCode) > 0) {
			redisUtil.deleteWithPrefix(Constatnt.MARCH_LIMITED_PREFIX,
					marchCode);

			return true;
		}
		return false;
	}

	/**
	 * 修改商户限额信息
	 * 
	 * @param marchantInfo
	 * @return
	 */
	public boolean updateMarchantInfo(MarchantInfo marchantInfo) {
		if (marchantMapper.updateByPrimaryKey(marchantInfo) > 0) {
			redisUtil.set(Constatnt.MARCH_LIMITED_PREFIX,
					marchantInfo.getMarchCode(),
					String.valueOf(marchantInfo.getDaySumLimited()));
			return true;
		}

		return false;
	}
	
	/**
	 * 修改商户限额信息
	 * 
	 * @param marchantInfo
	 * @return
	 */
	public List<MarchantInfo> queryMarchantInfoByMarchName(String marchName) {
		return marchantMapper.queryMarchantInfoByMarchName(marchName);
	}

}
