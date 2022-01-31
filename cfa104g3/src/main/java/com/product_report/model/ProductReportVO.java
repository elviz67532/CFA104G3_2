package com.product_report.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class ProductReportVO implements Serializable{
	private Integer productId;
	private Integer memberId;
	private String content;
	private Timestamp date;
	private byte[] photo;
	private Integer status;
}
