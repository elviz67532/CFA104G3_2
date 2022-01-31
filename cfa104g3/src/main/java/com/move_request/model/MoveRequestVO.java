package com.move_request.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class MoveRequestVO implements Serializable {
	private Integer id;
	private Integer memberId;
	private String fromAddress;
	private String toAddress;
	private Timestamp evauateDate;
	private String item;
	private Integer evauatePrice;
	private Timestamp moveDate;
	private Integer evaluateType;
	private Timestamp requestDate;
	private String status;
	private Boolean handled;
}
