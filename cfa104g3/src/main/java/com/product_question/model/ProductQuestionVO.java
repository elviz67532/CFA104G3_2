package com.product_question.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class ProductQuestionVO implements Serializable {
	private Integer id;
	private Integer memberId;
	private Integer productId;
	private String problem;
	private String reply;
	private Timestamp problemDate;
	private Timestamp replyDate;
}
