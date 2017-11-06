package com.example.service;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.example.constant.Constant;
import com.example.model.PayInfo;
import com.example.util.RedisUtil;

@Service
public class PayService {

	@Resource
	private RedisUtil redisUtil;

	public boolean checkLimited(PayInfo payInfo) {

		String sumLimitAmount = redisUtil.get(
				Constant.MARCH_DAY_SUM_LIMITED_PREFIX, payInfo.getTranDate()
						+ ":" + payInfo.getMarchCode());

		String marchLimited = redisUtil.get(Constant.MARCH_LIMITED_PREFIX,
				payInfo.getMarchCode());

		BigDecimal dayMarchLimited = new BigDecimal(marchLimited);

		// 非当日第一次支付
		if (StringUtils.isNotBlank(sumLimitAmount)) {
			BigDecimal daySumLimitAmount = new BigDecimal(sumLimitAmount);

			// 原有限额加上本次支付金额
			BigDecimal sunLimt = daySumLimitAmount.add(payInfo.getPayAmount());

			// 超出商户日累计限额
			if (sunLimt.compareTo(dayMarchLimited) > 0) {
				return false;
			}

			// 更新商户当日累计交易金额
			redisUtil.set(Constant.MARCH_DAY_SUM_LIMITED_PREFIX,
					payInfo.getTranDate() + ":" + payInfo.getMarchCode(),
					String.valueOf(sunLimt));

		} else {
			if (payInfo.getPayAmount().compareTo(dayMarchLimited) > 0) {
				return false;
			}
		}

		return true;

	}

}
