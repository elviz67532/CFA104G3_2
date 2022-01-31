package com.activity_report.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ActivityReportVO implements Serializable {
	private Integer id;
	private Integer activityId;
	private Integer memberId;
	private String content;
	private Integer status;
	private byte[] photo;
}
