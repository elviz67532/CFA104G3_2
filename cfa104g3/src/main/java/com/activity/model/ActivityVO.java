package com.activity.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class ActivityVO implements Serializable {
	private Integer activityId;
	private Integer organizerMemberId;
	private Integer type;
	private String name;
	private String content;
	private Timestamp launchedDate;
	private Timestamp applyStartDate;
	private Timestamp applyEndDate;
	private String location;
	private Integer cost;
	private Integer applyMemberExisting;
	private Integer maxMember;
	private Integer minMember;
	private Timestamp startDate;
	private Timestamp endDate;
	private Integer status;
}
