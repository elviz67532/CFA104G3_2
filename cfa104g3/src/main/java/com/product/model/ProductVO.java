package com.product.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class ProductVO implements Serializable {
	private Integer id;
	private Integer sellerMemberId;
	private Integer type;
	private String description;
	private Integer price;
	private String name;
	private Timestamp launchedDate;
	private String location;
	private Integer status;
}
