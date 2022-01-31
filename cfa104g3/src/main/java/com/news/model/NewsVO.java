package com.news.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class NewsVO implements Serializable {
	private Integer id;
	private String content;
	private byte[] image;
	private Timestamp date;
	private Integer type;
}
