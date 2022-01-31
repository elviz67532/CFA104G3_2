package com.activity_photo.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ActivityPhotoVO implements Serializable {
	private Integer id;
	private Integer activityId;
	private byte[] photo;
}
