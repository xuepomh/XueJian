package com.dh.game.vo.base;

public class BaseShopVO {
	private int id;
	private int item_id;
	private int money_type; // 1游戏币 2 元宝
	private int label;
	private int price;
	private int number;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getItem_id() {
		return item_id;
	}

	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}

	public int getMoney_type() {
		return money_type;
	}

	public void setMoney_type(int money_type) {
		this.money_type = money_type;
	}

	public int getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}


}
