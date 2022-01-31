package com.activity_attend.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ActivityAttendVO implements Serializable {
	private Integer memberId;
	private Integer activityId;
	private String comment;
	private String note;
	private Integer status;
}