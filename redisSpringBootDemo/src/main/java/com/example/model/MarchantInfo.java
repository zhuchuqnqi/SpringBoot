package com.example.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class MarchantInfo extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	private String marchCode;
	
	private String marchName;
	
	private BigDecimal daySumLimited;

	public String getMarchCode() {
		return marchCode;
	}

	public void setMarchCode(String marchCode) {
		this.marchCode = marchCode;
	}

	public String getMarchName() {
		return marchName;
	}

	public void setMarchName(String marchName) {
		this.marchName = marchName;
	}

	public BigDecimal getDaySumLimited() {
		return daySumLimited;
	}

	public void setDaySumLimited(BigDecimal daySumLimited) {
		this.daySumLimited = daySumLimited;
	}
	
}
