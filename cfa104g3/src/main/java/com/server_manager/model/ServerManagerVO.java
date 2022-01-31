package com.server_manager.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ServerManagerVO implements Serializable{
	private Integer smgrId;
	private String smgrEmail;
	private String smgrAccount;
	private String smgrPassword;
	private String smgrName;
	private String smgrPhone;
	private Integer smgrGender;
	private String smgrAddress;
}
