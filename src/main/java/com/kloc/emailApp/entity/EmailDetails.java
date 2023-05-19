package com.kloc.emailApp.entity;

import lombok.Data;

@Data
public class EmailDetails {

	private String[] recipient;
    private String msgBody;
    private String subject;
    private String cc;
    private String bcc;
    private String attachment;
}
