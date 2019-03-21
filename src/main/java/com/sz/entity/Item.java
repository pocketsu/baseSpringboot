package com.sz.entity;

import java.math.BigDecimal;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="item")
public class Item {
	@Id
	private String id;
	private String title;
	private String memo;
	private BigDecimal money;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
}
