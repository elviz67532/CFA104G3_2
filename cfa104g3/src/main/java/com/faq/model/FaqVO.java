package com.faq.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class FaqVO implements Serializable {
	private Integer id;
	private String question;
	private String answer;
}