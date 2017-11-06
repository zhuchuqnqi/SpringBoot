package com.example.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class PayInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 商户号
	 */
	private String marchCode;

	/**
	 * 交易日期
	 */
	private String tranDate;

	/**
	 * 交易金额
	 */
	private BigDecimal payAmount;

	public String getMarchCode() {
		return marchCode;
	}

	public void setMarchCode(String marchCode) {
		this.marchCode = marchCode;
	}

	public String getTranDate() {
		return tranDate;
	}

	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

}
