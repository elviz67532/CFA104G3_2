package com.move_photo.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class MovePhotoVO implements Serializable {
	private Integer id;
	private Integer moveRequestId;
	private byte[] photo;
}
