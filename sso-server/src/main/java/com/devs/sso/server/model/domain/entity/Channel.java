package com.devs.sso.server.model.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CHANNEL")
@Entity
public class Channel {

	@Id
	@Column(name = "CHANNEL_CODE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CHANNEL_CODE_SEQ_GENERATOR")
	@SequenceGenerator(name = "CHANNEL_CODE_SEQ_GENERATOR", sequenceName = "CHANNEL_CODE_SEQ", initialValue = 1, allocationSize = 1)
	private int channel_code;

	@Column(name = "MEMBER_CODE")
	private int member_code;

	@Column(name = "CHANNEL_TYPE")
	private String channel_type;

	@Column(name = "CHANNEL_NAME")
	private String channel_name;

	@Column(name = "CHANNEL_IMG_ORIGINAL_NAME")
	private String channel_img_original_name;

	@Column(name = "CHANNEL_IMG_SERVER_NAME")
	private String channel_img_server_name;

	@Column(name = "CHANNEL_IMG_PATH")
	private String channel_img_path;

	@Column(name = "CHANNEL_WEBSITE")
	private String channel_website;

	@Column(name = "CHANNEL_PAY")
	private String channel_pay;

	@Column(name = "CHANNEL_INTRODUCE")
	private String channel_introduce;

}