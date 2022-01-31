package com.member.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class MemberVO implements Serializable {
	private int id;
	private String email;
	private String account;
	private String password;
	private String nickname;
	private String name;
	private String phone;
	private int gender;
	private String city;
	private String cityArea;
	private String address;
	private String code;
	private byte[] avatar;
	private Timestamp registerDate;
	private int status;
}
