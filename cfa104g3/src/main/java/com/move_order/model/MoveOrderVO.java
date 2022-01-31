package com.move_order.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class MoveOrderVO implements Serializable {
	private Integer id;
	private Integer memberId;
	private String customer;
	private String phone;
	private String fromAddress;
	private String toAddress;
	private Timestamp moveDate;
	private Integer amountFirst;
	private Integer deposit;
	private Integer amountTotal;
	private String comment;
	private Timestamp orderDate;
	private Integer status;
}
