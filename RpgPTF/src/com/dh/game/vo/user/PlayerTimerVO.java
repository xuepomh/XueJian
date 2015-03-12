package com.dh.game.vo.user;

import java.util.Arrays;
import java.util.Date;

import com.dh.util.CodeTool;
import com.dh.util.DateUtil;

public class PlayerTimerVO {
	private int playerId;
	private Date zmD1;
	private Date zmD2;
	private Date zmD3;
	private Date zmD4;

	private short zmFirst1;
	private short zmFirst2;
	private short zmFirst3;
	private short zmFirst4;

	private Date salaryD; // 俸禄刷新时间
	private short salaryStep;

	private Date buildD1;
	private Date buildD2;
	private Date buildD3;

	private Date powerD;// 行动力刷新时间
	private long knpsackTime; // 背包开启时间

	private Date herorestD; // 英雄挂机休息时间
	private int herorestTime; // 已使用的英雄挂机休息次数

	private String exploitShop;// 功勋
	private Date exploitShopTime;

	private String grabline; // 夺宝对手
	private Date grabTime;// 刷新时间

	private short vipReward;
	private Date vipRewardDate;
	private char[] guideArray;
	private String guide;
	private int onlineCount;// 领取次数
	private int onlineTime;// 累计在线时间
	private long lastRewardTime;// 上次使用

	private int MRaidDate;// 上次银两
	private int MRaidCount;// 银两副本次数
	private int MRaidBuyCount;// 购买次数,最高10次

	private int ERaidDate;// 上次经验时间
	private int ERaidCount;// 经验副本次数
	private int ERaidBuyCount;// 经验购买次数,最高10次
	private int MERaidFreshDate;
	private int firstGrab; // 是否首次使用夺宝

	private int yzTimes;
	private Date yzDate;

	private int buyPowerCount;// 购买体力次数
	private int chatTime;
	private int jjcBuyTimes;// 竞技值每日购买次数

	private int dayChange;// 是否充值
	private int yellowNewGift; // 是否领过黄钻新手礼包 成长礼包 <<1-8
	private int yellowDayGift;
	private int eliRaidBuyTimes;// 精英副本购买次数

	private String dayDoneStr = "";// 每日奖励
	private int[] dayDoneArray;
	private int dayDoneScore;// 活跃度积分
	private int dayDoneRewards;

	private int donateReward;// 根据捐献等级每日领取礼包
	// private int donateRewardDate;// 上次领取捐献时间
	// private int dayLegionRewardDate;// 每日奖励时间
	private int dayLegionReward;// 军团每日礼包
	private int money10; // 银两10连抽次数
	private int dayShare;// 每日分享
	private int cdkeyField;// cdkey



	public int getMERaidFreshDate() {
		return MERaidFreshDate;
	}

	public void setMERaidFreshDate(int mERaidFreshDate) {
		MERaidFreshDate = mERaidFreshDate;
	}

	public int getFirstGrab() {
		return firstGrab;
	}

	public void setFirstGrab(int firstGrab) {
		this.firstGrab = firstGrab;
	}

	public Date getExploitShopTime() {
		return exploitShopTime;
	}

	public void setExploitShopTime(Date exploitShopTime) {
		this.exploitShopTime = exploitShopTime;
	}

	public String getExploitShop() {
		return exploitShop;
	}

	public void setExploitShop(String exploitShop) {
		this.exploitShop = exploitShop;
	}

	public Date getZmD1() {
		return zmD1;
	}

	public void setZmD1(Date zmD1) {
		this.zmD1 = zmD1;
	}

	public Date getZmD2() {
		return zmD2;
	}

	public void setZmD2(Date zmD2) {
		this.zmD2 = zmD2;
	}

	public Date getZmD3() {
		return zmD3;
	}

	public void setZmD3(Date zmD3) {
		this.zmD3 = zmD3;
	}

	public Date getZmD4() {
		return zmD4;
	}

	public void setZmD4(Date zmD4) {
		this.zmD4 = zmD4;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public Date getSalaryD() {
		return salaryD;
	}

	public void setSalaryD(Date salaryD) {
		this.salaryD = salaryD;
	}

	public Date getBuildD1() {
		return buildD1;
	}

	public void setBuildD1(Date buildD1) {
		this.buildD1 = buildD1;
	}

	public Date getBuildD2() {
		return buildD2;
	}

	public void setBuildD2(Date buildD2) {
		this.buildD2 = buildD2;
	}

	public Date getBuildD3() {
		return buildD3;
	}

	public void setBuildD3(Date buildD3) {
		this.buildD3 = buildD3;
	}

	public short getZmFirst1() {
		return zmFirst1;
	}

	public void setZmFirst1(short zmFirst1) {
		this.zmFirst1 = zmFirst1;
	}

	public short getZmFirst2() {
		return zmFirst2;
	}

	public void setZmFirst2(short zmFirst2) {
		this.zmFirst2 = zmFirst2;
	}

	public short getZmFirst3() {
		return zmFirst3;
	}

	public void setZmFirst3(short zmFirst3) {
		this.zmFirst3 = zmFirst3;
	}

	public short getZmFirst4() {
		return zmFirst4;
	}

	public void setZmFirst4(short zmFirst4) {
		this.zmFirst4 = zmFirst4;
	}

	public Date getPowerD() {
		return powerD;
	}

	public void setPowerD(Date powerD) {
		this.powerD = powerD;
	}

	public long getKnpsackTime() {
		return knpsackTime;
	}

	public void setKnpsackTime(long knpsackTime) {
		this.knpsackTime = knpsackTime;
	}

	public short getSalaryStep() {
		return salaryStep;
	}

	public void setSalaryStep(short salaryStep) {
		this.salaryStep = salaryStep;
	}

	public Date getHerorestD() {
		return herorestD;
	}

	public void setHerorestD(Date herorestD) {
		this.herorestD = herorestD;
	}

	public int getHerorestTime() {
		return herorestTime;
	}

	public void setHerorestTime(int herorestTime) {
		this.herorestTime = herorestTime;
	}

	public String getGrabline() {
		return grabline;
	}

	public void setGrabline(String grabline) {
		this.grabline = grabline;
	}

	public Date getGrabTime() {
		return grabTime;
	}

	public void setGrabTime(Date grabTime) {
		this.grabTime = grabTime;
	}

	public short getVipReward() {
		return vipReward;
	}

	public void setVipReward(short vipReward) {
		this.vipReward = vipReward;
	}

	public Date getVipRewardDate() {
		return vipRewardDate;
	}

	public void setVipRewardDate(Date vipRewardDate) {
		this.vipRewardDate = vipRewardDate;
	}

	public char[] getGuideArray() {
		return guideArray;
	}

	public void setGuideArray(char[] guideArray) {
		this.guideArray = guideArray;
	}

	public String getGuide() {
		// System.out.println("now GET the guideline:" + this.guide);
		return guide;
	}

	public void setGuide(String guide) {
		this.guide = guide;
		// System.out.println("now SET the guideline:" + this.guide);
	}

	public int getOnlineCount() {
		return onlineCount;
	}

	public void setOnlineCount(int onlineCount) {
		this.onlineCount = onlineCount;
	}

	public int getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(int onlineTime) {
		this.onlineTime = onlineTime;
	}

	public long getLastRewardTime() {
		return lastRewardTime;
	}

	public void setLastRewardTime(long lastRewardTime) {
		this.lastRewardTime = lastRewardTime;
	}

	public int getERaidDate() {
		return ERaidDate;
	}

	public void setERaidDate(int eRaidDate) {
		ERaidDate = eRaidDate;
	}

	public int getMRaidCount() {
		return MRaidCount;
	}

	public void setMRaidCount(int mRaidCount) {
		MRaidCount = mRaidCount;
	}

	public int getERaidCount() {
		return ERaidCount;
	}

	public void setERaidCount(int eRaidCount) {
		ERaidCount = eRaidCount;
	}

	public int getMRaidDate() {
		return MRaidDate;
	}

	public void setMRaidDate(int mRaidDate) {
		MRaidDate = mRaidDate;
	}

	public int getMRaidBuyCount() {
		return MRaidBuyCount;
	}

	public void setMRaidBuyCount(int mRaidBuyCount) {
		MRaidBuyCount = mRaidBuyCount;
	}

	public int getERaidBuyCount() {
		return ERaidBuyCount;
	}

	public void setERaidBuyCount(int eRaidBuyCount) {
		ERaidBuyCount = eRaidBuyCount;
	}

	public int getYzTimes() {
		return yzTimes;
	}

	public void setYzTimes(int yzTimes) {
		this.yzTimes = yzTimes;
	}

	public Date getYzDate() {
		return yzDate;
	}

	public void setYzDate(Date yzDate) {
		this.yzDate = yzDate;
	}

	public int getBuyPowerCount() {
		return buyPowerCount;
	}

	public void setBuyPowerCount(int buyPowerCount) {
		this.buyPowerCount = buyPowerCount;
	}

	public int getChatTime() {
		return chatTime;
	}

	public void setChatTime(int chatTime) {
		this.chatTime = chatTime;
	}

	public int getJjcBuyTimes() {
		return jjcBuyTimes;
	}

	public void setJjcBuyTimes(int jjcBuyTimes) {
		this.jjcBuyTimes = jjcBuyTimes;
	}

	public int getDayChange() {
		return dayChange;
	}

	public void setDayChange(int dayChange) {
		this.dayChange = dayChange;
	}

	public int getYellowNewGift() {
		return yellowNewGift;
	}

	public void setYellowNewGift(int yellowNewGift) {
		this.yellowNewGift = yellowNewGift;
	}

	public int getYellowDayGift() {
		return yellowDayGift;
	}

	public void setYellowDayGift(int yellowDayGift) {
		this.yellowDayGift = yellowDayGift;
	}

	public int getEliRaidBuyTimes() {
		return eliRaidBuyTimes;
	}

	public void setEliRaidBuyTimes(int eliRaidBuyTimes) {
		this.eliRaidBuyTimes = eliRaidBuyTimes;
	}

	public void dayClear() {
		this.setBuyPowerCount(0);
		this.setOnlineCount(0);
		this.setOnlineTime(0);
		this.setLastRewardTime(0);
		this.setJjcBuyTimes(0);// 竞技值购买次数
		this.setDayChange(0);// 每日充值
		this.setYellowDayGift(0);
		this.setEliRaidBuyTimes(0);// 精英副本购买次数
		this.setMoney10(0); // 每日银两招蓦次数
		this.setDayShare(0);// 清理每日分享

		this.setMERaidFreshDate(DateUtil.getNow());
		this.setERaidBuyCount(0);
		this.setERaidCount(0);
		this.setERaidDate(0);
		this.setMRaidBuyCount(0);
		this.setMRaidCount(0);
		this.setMRaidDate(0);

		Arrays.fill(this.getDayDoneArray(), 0);
		this.setDayDoneStr(CodeTool.arrayToString(this.getDayDoneArray(), ","));
		this.setDayDoneScore(0);
		this.setDayDoneRewards(0);
		this.setDayLegionReward(0);
	}

	public String getDayDoneStr() {
		return dayDoneStr;
	}

	public void setDayDoneStr(String dayDoneStr) {
		this.dayDoneStr = dayDoneStr;
	}

	public int[] getDayDoneArray() {
		return dayDoneArray;
	}

	public void setDayDoneArray(int[] dayDoneArray) {
		this.dayDoneArray = dayDoneArray;
	}

	public int getDayDoneScore() {
		return dayDoneScore;
	}

	public void setDayDoneScore(int dayDoneScore) {
		this.dayDoneScore = dayDoneScore;
	}

	public int getDayDoneRewards() {
		return dayDoneRewards;
	}

	public void setDayDoneRewards(int dayDoneRewards) {
		this.dayDoneRewards = dayDoneRewards;
	}

	public int getDonateReward() {
		return donateReward;
	}

	public void setDonateReward(int donateReward) {
		this.donateReward = donateReward;
	}

	public int getDayLegionReward() {
		return dayLegionReward;
	}

	public void setDayLegionReward(int dayLegionReward) {
		this.dayLegionReward = dayLegionReward;
	}

	public int getMoney10() {
		return money10;
	}

	public void setMoney10(int money10) {
		this.money10 = money10;
	}

	public int getDayShare() {
		return dayShare;
	}

	public void setDayShare(int dayShare) {
		this.dayShare = dayShare;
	}

	public int getCdkeyField() {
		return cdkeyField;
	}

	public void setCdkeyField(int cdkeyField) {
		this.cdkeyField = cdkeyField;
	}

}
