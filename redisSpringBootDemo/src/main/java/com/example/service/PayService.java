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

		// 获取商户当日累计交易金额
		String sumDayPayAmount = redisUtil.get(
				Constant.MARCH_DAY_SUM_PAY_AMOUNT_PREFIX, payInfo.getTranDate()
						+ ":" + payInfo.getMarchCode());

		// 获取商户日交易限额
		String marchDayLimited = redisUtil.get(Constant.MARCH_LIMITED_PREFIX,
				payInfo.getMarchCode());

		BigDecimal dayMarchLimited = new BigDecimal(marchDayLimited);

		BigDecimal daySumPayAm = new BigDecimal("0");
		// 非当日第一次交易
		if (StringUtils.isNotBlank(sumDayPayAmount)) {
			daySumPayAm = new BigDecimal(sumDayPayAmount);

		}
		// 原有累计交易金额加上本次支付金额
		BigDecimal sumPay = daySumPayAm.add(payInfo.getPayAmount());

		// 超出商户日交易限额
		if (sumPay.compareTo(dayMarchLimited) > 0) {
			return false;
		}

		// 更新商户当日累计交易金额
		redisUtil.set(Constant.MARCH_DAY_SUM_PAY_AMOUNT_PREFIX,
				payInfo.getTranDate() + ":" + payInfo.getMarchCode(),
				String.valueOf(sumPay));

		return true;

	}

}
