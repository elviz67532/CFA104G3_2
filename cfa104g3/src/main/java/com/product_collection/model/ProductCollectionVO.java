package com.product_collection.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProductCollectionVO implements Serializable {
	private Integer memberId;
	private Integer productId;
}
