package com.example.mapper;

import java.util.List;

import com.example.model.MarchantInfo;
import com.example.util.MyMapper;

public interface MarchantMapper extends MyMapper<MarchantInfo>{
	
	/**
	 * 根据商户号删除商户限额配置信息
	 * @param marchCode
	 * @return
	 */
	public int delMarchantInfo(String marchCode);
	
	public List<MarchantInfo> queryMarchantInfoByMarchName(String marchName) ;
	
}
