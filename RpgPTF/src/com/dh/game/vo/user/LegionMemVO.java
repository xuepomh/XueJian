package com.dh.game.vo.user;

import java.util.Date;

import com.dh.game.vo.base.BaseLegionBossVO;
import com.dh.game.vo.base.BaseLegionMemVO;

//军团成员
public class LegionMemVO implements Comparable<LegionMemVO> {
	private int id;
	private int playerId;
	private int type; // 职位
	private Date joinTime;
	private int donate; // 总贡献分
	private int ownDonate;// 当前实际拥有拥有捐献值
	private BaseLegionMemVO baseLegionMemVO;
	private BaseLegionBossVO baseLegionBossVO;// 当前攻打的boss
	private int bossId = 1;// 当前攻打到的bossId
	private int hp = -1;
	private int bossRewardId;// 记录领取奖励Id
	private int atkBossDate;// 上次攻打boss时间,用于每日刷新
	private int atkFailDate;
	private int bossStatus = 1;// boss状态
	private int combat;

	@Override
	public int compareTo(LegionMemVO arg0) {
		if (this.getType() != arg0.getType()) {
			return this.getType() > arg0.getType() ? -1 : 1;
		}
		if (this.getDonate() != arg0.getDonate()) {
			return this.getDonate() > arg0.getDonate() ? -1 : 1;
		}
		return joinTime.compareTo(arg0.getJoinTime());
	}

	public int getCountDown(int now) {
		if (atkFailDate == 0) {
			return 0;
		}
		int div = 60 + atkFailDate - now;
		if (div <= 0) {
			return 0;
		}
		return div;

	}

	public void nextBoss(BaseLegionBossVO baseLegionBossVO) {
		this.baseLegionBossVO = baseLegionBossVO;
		this.bossId = baseLegionBossVO.getId();
		this.hp = -1;
		// bossStatus = 0;
	}

	public void addDonate(int num) {
		donate += num;
		ownDonate += num;
	}

	public void subDonate(int num) {
		ownDonate -= num;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

	public int getDonate() {
		return donate;
	}

	public void setDonate(int donate) {
		this.donate = donate;
	}

	public BaseLegionMemVO getBaseLegionMemVO() {
		return baseLegionMemVO;
	}

	public void setBaseLegionMemVO(BaseLegionMemVO baseLegionMemVO) {
		this.baseLegionMemVO = baseLegionMemVO;
	}

	public int getOwnDonate() {
		return ownDonate;
	}

	public void setOwnDonate(int ownDonate) {
		this.ownDonate = ownDonate;
	}

	public BaseLegionBossVO getBaseLegionBossVO() {
		return baseLegionBossVO;
	}

	public void setBaseLegionBossVO(BaseLegionBossVO baseLegionBossVO) {
		this.baseLegionBossVO = baseLegionBossVO;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getBossRewardId() {
		return bossRewardId;
	}

	public void setBossRewardId(int bossRewardId) {
		this.bossRewardId = bossRewardId;
	}

	public int getBossId() {
		return bossId;
	}

	public void setBossId(int bossId) {
		this.bossId = bossId;
	}

	public int getAtkBossDate() {
		return atkBossDate;
	}

	public void setAtkBossDate(int atkBossDate) {
		this.atkBossDate = atkBossDate;
	}

	public int getBossStatus() {
		return bossStatus;
	}

	public void setBossStatus(int bossStatus) {
		this.bossStatus = bossStatus;
	}

	public int getCombat() {
		return combat;
	}

	public void setCombat(int combat) {
		this.combat = combat;
	}

	public int getAtkFailDate() {
		return atkFailDate;
	}

	public void setAtkFailDate(int atkFailDate) {
		this.atkFailDate = atkFailDate;
	}

}