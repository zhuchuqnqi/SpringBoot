package com.example.model;

import java.math.BigDecimal;

public class MarchantInfo extends BaseEntity{
	
	private String marchCode;
	
	private BigDecimal daySumLimited;

	public String getMarchCode() {
		return marchCode;
	}

	public void setMarchCode(String marchCode) {
		this.marchCode = marchCode;
	}

	public BigDecimal getDaySumLimited() {
		return daySumLimited;
	}

	public void setDaySumLimited(BigDecimal daySumLimited) {
		this.daySumLimited = daySumLimited;
	}
	
}
