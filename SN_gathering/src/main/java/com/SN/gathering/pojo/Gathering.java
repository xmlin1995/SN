package com.SN.gathering.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * 实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="tb_gathering")
public class Gathering implements Serializable{

	@Id
	private String id;//编号


	
	private String name;//活动名称
	private String summary;//大会简介
	private String detail;//详细说明
	private String sponsor;//主办方
	private String image;//活动图片
	private java.util.Date starttime;//开始时间
	private java.util.Date endtime;//截止时间
	private String address;//举办地点
	private java.util.Date enrolltime;//报名截止
	private String state;//是否可见
	private String city;//城市

	
	public String getId() {		
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {		
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getSummary() {		
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDetail() {		
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getSponsor() {		
		return sponsor;
	}
	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public String getImage() {		
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public java.util.Date getStarttime() {		
		return starttime;
	}
	public void setStarttime(java.util.Date starttime) {
		this.starttime = starttime;
	}

	public java.util.Date getEndtime() {		
		return endtime;
	}
	public void setEndtime(java.util.Date endtime) {
		this.endtime = endtime;
	}

	public String getAddress() {		
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public java.util.Date getEnrolltime() {		
		return enrolltime;
	}
	public void setEnrolltime(java.util.Date enrolltime) {
		this.enrolltime = enrolltime;
	}

	public String getState() {		
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {		
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}


	
}
