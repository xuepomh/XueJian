package com.dh.game.vo.base;

import com.dh.game.vo.raid.RaidProto.RaidRewardinfo;

public class Reward {
	/** 奖励规则组 */
	private int id;
	/** 规则类型(0：普通 1:单选 2:随机) */
	private int mode;
	/** 奖励规则ID */
	private int orderId;
	/**
	 * <h3>奖励类型</h3> 主角:1.物品 2.钞票 3.金币 4.经验 5.贡献分 6.功勋 7.声望 <br/>
	 * 英雄:10.经验 11.健康值,12,力量 13.体质14.智力15.敏捷<br/>
	 * 其他:50.活跃度100.获得英雄101:获得士兵)
	 */
	private int type;
	/**
	 * <h3>奖励内容</h3> 当【奖励类型=1】时，此条属性启用。填入 cfg_item 中的物品ID <br/>
	 * 当【奖励类型=100】时，此条属性启用。填入 cfg_hero中的状态ID <br/>
	 * 当【奖励类型=101】时，此条属性启用。填入 cfg_soldier 中的技能ID<br/>
	 * */
	private int content;
	/** 奖励数量 */
	private int number;
	/** 概率 */
	private int probability;
	private int level;
	private int bossid;
	private transient RaidRewardinfo raidRewardinfo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getContent() {
		return content;
	}

	public void setContent(int content) {
		this.content = content;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getProbability() {
		return probability;
	}

	public void setProbability(int probability) {
		this.probability = probability;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public RaidRewardinfo getRaidRewardinfo() {
		return raidRewardinfo;
	}

	public void setRaidRewardinfo(RaidRewardinfo raidRewardinfo) {
		this.raidRewardinfo = raidRewardinfo;
	}

	public String toString() {
		return "Reward [content=" + content + ", mode=" + mode + ", number=" + number + ", orderId=" + orderId + ", rewardGroupId=" + id + ", type=" + type + "]";
	}

	public RaidRewardinfo gentoRewardinfo() {
		RaidRewardinfo.Builder builder = RaidRewardinfo.newBuilder();
		builder.setType(type);
		builder.setItemCfgId(content);
		builder.setNumber(number);
		return builder.build();
	}

	public int getBossid() {
		return bossid;
	}

	public void setBossid(int bossid) {
		this.bossid = bossid;
	}

}
