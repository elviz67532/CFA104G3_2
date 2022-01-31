package com.product_order.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class ProductOrderVO implements Serializable {
	private Integer id;
	private Integer productId;
	private Integer customerMemberId;
	private Integer sellerMemberId;
	private String productName;
	private String phone;
	private String address;
	private Timestamp date;
	private Integer amountOfProduct;
	private Integer status;
	private Integer amountOfPrice;
}
