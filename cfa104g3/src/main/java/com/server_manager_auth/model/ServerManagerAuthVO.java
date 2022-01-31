package com.server_manager_auth.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ServerManagerAuthVO implements Serializable {
	private Integer smgeFuncId;
	private Integer smgrId;
}
