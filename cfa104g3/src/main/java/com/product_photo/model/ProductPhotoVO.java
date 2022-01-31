package com.product_photo.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProductPhotoVO implements Serializable {
	private Integer id;
	private Integer productId;
	private byte[] photo;
}
