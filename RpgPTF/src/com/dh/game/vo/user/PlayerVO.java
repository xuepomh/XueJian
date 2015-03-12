package com.dh.game.vo.user;

import java.util.Date;

import com.dh.game.vo.base.BaseLevelVO;

public class PlayerVO {
	private int playerId;
	private String name;
	private String password;
	private int headIcon;
	private int sex;
	private int country;
	private int legionId;
	private int level;
	private int exp;
	private int renown; // 声望
	private int honor; // 称号
	private int hp;
	private int vip;
	private Date lastLoginDate;
	private String loginRecord;
	private Date registerdate;
	private int isLogin;
	private int scores; // 登陆次数
	private String isReward;
	private int is_online;
	private String guide;
	private int drama;
	private int open_operation;
	private int vip_isReward;
	private String userName;
	private int resolution;
	private String bchannel;
	private String functionGuide;
	private int newGuide;
	private int combat;
	private transient BaseLevelVO baseLevelVO;
	private transient long lastLoginTime = 0; // 刷新　在线时间计算用这个
	private String pfkey;
	private int is_yellow_vip; // 黄钻 0 不是1 是
	private int is_yellow_year_vip; // 是否年费黄钻 0 不是1 是
	private int yellow_vip_level; // 黄钻等级，目前最高级别为黄钻8级（如果是黄钻用户才返回此参数）
	private int is_yellow_high_vip;// 是否为豪华版黄钻用户 0 不是1 是
	private int gmGroup;// 如果type=50则为黑名单

	public boolean isVIP() {
		return vip > 0;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHeadIcon() {
		return headIcon;
	}

	public void setHeadIcon(int headIcon) {
		this.headIcon = headIcon;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getCountry() {
		return country;
	}

	public void setCountry(int country) {
		this.country = country;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getResolution() {
		return resolution;
	}

	public void setResolution(int resolution) {
		this.resolution = resolution;
	}

	public String getBchannel() {
		return bchannel;
	}

	public void setBchannel(String bchannel) {
		this.bchannel = bchannel;
	}

	public String getFunctionGuide() {
		return functionGuide;
	}

	public void setFunctionGuide(String functionGuide) {
		this.functionGuide = functionGuide;
	}

	public int getNewGuide() {
		return newGuide;
	}

	public void setNewGuide(int newGuide) {
		this.newGuide = newGuide;
	}

	public int getRenown() {
		return renown;
	}

	public void setRenown(int renown) {
		this.renown = renown;
	}

	public int getHonor() {
		return honor;
	}

	public void setHonor(int honor) {
		this.honor = honor;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getVip() {
		return vip;
	}

	public void setVip(int vip) {
		this.vip = vip;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getLoginRecord() {
		return loginRecord;
	}

	public void setLoginRecord(String loginRecord) {
		this.loginRecord = loginRecord;
	}

	public int getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(int isLogin) {
		this.isLogin = isLogin;
	}

	public int getScores() {
		return scores;
	}

	public void setScores(int scores) {
		this.scores = scores;
	}

	public String getIsReward() {
		return isReward;
	}

	public void setIsReward(String isReward) {
		this.isReward = isReward;
	}

	public int getIs_online() {
		return is_online;
	}

	public void setIs_online(int is_online) {
		this.is_online = is_online;
	}

	public String getGuide() {
		return guide;
	}

	public void setGuide(String guide) {
		this.guide = guide;
	}

	public int getDrama() {
		return drama;
	}

	public void setDrama(int drama) {
		this.drama = drama;
	}

	public int getOpen_operation() {
		return open_operation;
	}

	public void setOpen_operation(int open_operation) {
		this.open_operation = open_operation;
	}

	public int getVip_isReward() {
		return vip_isReward;
	}

	public void setVip_isReward(int vip_isReward) {
		this.vip_isReward = vip_isReward;
	}

	public BaseLevelVO getBaseLevelVO() {
		return baseLevelVO;
	}

	public void setBaseLevelVO(BaseLevelVO baseLevelVO) {
		this.baseLevelVO = baseLevelVO;
	}

	public int getCombat() {
		return combat;
	}

	public void setCombat(int combat) {
		this.combat = combat;
	}

	public long getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPfkey() {
		return pfkey;
	}

	public void setPfkey(String pfkey) {
		this.pfkey = pfkey;
	}

	public int getIs_yellow_vip() {
		return is_yellow_vip;
	}

	public void setIs_yellow_vip(int is_yellow_vip) {
		this.is_yellow_vip = is_yellow_vip;
	}

	public int getIs_yellow_year_vip() {
		return is_yellow_year_vip;
	}

	public void setIs_yellow_year_vip(int is_yellow_year_vip) {
		this.is_yellow_year_vip = is_yellow_year_vip;
	}

	public int getYellow_vip_level() {
		return yellow_vip_level;
	}

	public void setYellow_vip_level(int yellow_vip_level) {
		this.yellow_vip_level = yellow_vip_level;
	}

	public int getIs_yellow_high_vip() {
		return is_yellow_high_vip;
	}

	public void setIs_yellow_high_vip(int is_yellow_high_vip) {
		this.is_yellow_high_vip = is_yellow_high_vip;
	}

	public Date getRegisterdate() {
		return registerdate;
	}

	public void setRegisterdate(Date registerdate) {
		this.registerdate = registerdate;
	}

	public int getGmGroup() {
		return gmGroup;
	}

	public void setGmGroup(int gmGroup) {
		this.gmGroup = gmGroup;
	}

	public int getLegionId() {
		return legionId;
	}

	public void setLegionId(int legionId) {
		this.legionId = legionId;
	}
}