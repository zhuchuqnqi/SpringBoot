package com.example.constant;

public class Constant {

	private Constant() {
	}

	/**
	 * 商户 日限额前缀
	 */
	public static final String MARCH_LIMITED_PREFIX = "marchDayLimited";

	/**
	 * 商户 日累计交易金额
	 */
	public static final String MARCH_DAY_SUM_PAY_AMOUNT_PREFIX = "marchDaySumTranAmount";

	public static final String PAY_SUCCESS_MSG = "支付成功!!!";

	public static final String PAY_FAILED_LIMITED_MSG = "当日交易金额已超出限额，交易失败！！！";

}
