package com.dh.util;

import java.util.ArrayList;
import java.util.List;

import com.dh.Cache.RedisMap;
import com.dh.Cache.ServerHandler;
import com.dh.constants.ActivityConstant;
import com.dh.constants.CommonConstants;
import com.dh.constants.GameRecordConstants;
import com.dh.constants.LegionConstant;
import com.dh.constants.MailConstants;
import com.dh.constants.RaidConstant;
import com.dh.constants.StreetConstants;
import com.dh.exception.GameException;
import com.dh.game.constant.AlertEnum;
import com.dh.game.constant.TaskConstant;
import com.dh.game.vo.BaseProto.BUILDID;
import com.dh.game.vo.BaseProto.BuildInfo;
import com.dh.game.vo.BaseProto.FinalHero;
import com.dh.game.vo.BaseProto.FriendInfo;
import com.dh.game.vo.BaseProto.ItemInfo;
import com.dh.game.vo.BaseProto.MyHero;
import com.dh.game.vo.BaseProto.MyKnapsack;
import com.dh.game.vo.BaseProto.MyPlayer;
import com.dh.game.vo.BaseProto.MyPlayerAccount;
import com.dh.game.vo.BaseProto.MyTask;
import com.dh.game.vo.BaseProto.RewardInfoList;
import com.dh.game.vo.activity.ActivityProto.AnsNextResp;
import com.dh.game.vo.activity.ActivityProto.DayDoneInfo;
import com.dh.game.vo.activity.ActivityProto.DayDoneResp;
import com.dh.game.vo.activity.ActivityProto.DaySignResp;
import com.dh.game.vo.activity.WorldBossProto.HuntLogInfo;
import com.dh.game.vo.base.BaseActivityVO;
import com.dh.game.vo.base.BaseAnsVO;
import com.dh.game.vo.base.BaseHeroInfoVO;
import com.dh.game.vo.base.BaseItemVO;
import com.dh.game.vo.base.BaseMonsterVO;
import com.dh.game.vo.base.BaseRaidInfo;
import com.dh.game.vo.base.PassivesSkillVO;
import com.dh.game.vo.base.Reward;
import com.dh.game.vo.base.VipLevelVO;
import com.dh.game.vo.legion.LegionProto.BaseLegionInfo;
import com.dh.game.vo.legion.LegionProto.LEGION_LOG_TYPE;
import com.dh.game.vo.legion.LegionProto.LegionInfoResp;
import com.dh.game.vo.legion.LegionProto.LegionJionInfo;
import com.dh.game.vo.legion.LegionProto.LegionListInfo;
import com.dh.game.vo.legion.LegionProto.LogInfo;
import com.dh.game.vo.legion.LegionProto.MEM_TYPE;
import com.dh.game.vo.legion.LegionProto.MemDetails;
import com.dh.game.vo.login.PlayerLoginProto.PlayerFriendInfo;
import com.dh.game.vo.login.PlayerLoginProto.PlayerProto;
import com.dh.game.vo.login.PlayerLoginProto.PowerBuyInfo;
import com.dh.game.vo.login.PlayerLoginProto.PowerBuyInfo.Builder;
import com.dh.game.vo.message.MessageProto.MailInfo;
import com.dh.game.vo.message.MessageProto.Rewards;
import com.dh.game.vo.raid.RaidProto.MERaidInfo;
import com.dh.game.vo.street.StreetProto.BattleLogInfo;
import com.dh.game.vo.street.StreetProto.EnemyCenterResp;
import com.dh.game.vo.street.StreetProto.EnemyLogInfo;
import com.dh.game.vo.street.StreetProto.HuntResp;
import com.dh.game.vo.street.StreetProto.MonsterInfo;
import com.dh.game.vo.street.StreetProto.OPEN_STATUS;
import com.dh.game.vo.street.StreetProto.ResItemInfo;
import com.dh.game.vo.street.StreetProto.RewardInfo;
import com.dh.game.vo.street.StreetProto.StreetDefendResp;
import com.dh.game.vo.street.StreetProto.StreetGridInfo;
import com.dh.game.vo.street.StreetProto.StreetHomeResp;
import com.dh.game.vo.street.StreetProto.StreetResInfo;
import com.dh.game.vo.street.StreetProto.StreetStatus;
import com.dh.game.vo.user.BossLogVO;
import com.dh.game.vo.user.LegionLogVO;
import com.dh.game.vo.user.LegionMemVO;
import com.dh.game.vo.user.LegionVO;
import com.dh.game.vo.user.MailVO;
import com.dh.game.vo.user.PlayerAccountVO;
import com.dh.game.vo.user.PlayerActyVO;
import com.dh.game.vo.user.PlayerBuildVO;
import com.dh.game.vo.user.PlayerFriendVO;
import com.dh.game.vo.user.PlayerHeroDefVO;
import com.dh.game.vo.user.PlayerHeroVO;
import com.dh.game.vo.user.PlayerKnapsackVO;
import com.dh.game.vo.user.PlayerTaskVO;
import com.dh.game.vo.user.PlayerTimerVO;
import com.dh.game.vo.user.PlayerVO;
import com.dh.game.vo.user.PlayerWelfareVO;
import com.dh.game.vo.user.StreetBoxVO;
import com.dh.game.vo.user.StreetDefendLogVO;
import com.dh.game.vo.user.StreetEnemyVO;
import com.dh.game.vo.user.StreetMonsterVO;
import com.dh.game.vo.user.StreetResVO;
import com.dh.resconfig.AnsRes;
import com.dh.resconfig.DayDoneRes;
import com.dh.resconfig.HeroRes;
import com.dh.resconfig.ItemRes;
import com.dh.resconfig.PassivesSkillRes;
import com.dh.resconfig.RaidRes;
import com.dh.resconfig.ResourceRes;
import com.dh.resconfig.VipLevelRes;
import com.dh.service.ArenaService;
import com.dh.service.MailService;
import com.dh.service.RewardService;
import com.dh.vo.user.UserCached;
import com.dh.vo.user.UserStreet;

public class VOUtil {

	/**
	 * 基本信息
	 * 
	 * @param playerVO
	 * @return
	 */
	public static MyPlayer.Builder getPlayerInfo(UserCached userCached) {
		PlayerVO playerVO = userCached.getPlayerVO();
		MyPlayer.Builder builder = MyPlayer.newBuilder();
		builder.setPlayerId(playerVO.getPlayerId()); // 玩家编号
		builder.setNick(playerVO.getName()); // 昵称
		builder.setHeadIcon(playerVO.getHeadIcon()); // 头像
		builder.setCountry(playerVO.getCountry()); // 所属国家
		builder.setLegionId(playerVO.getLegionId()); // 帮会
		builder.setLevel(playerVO.getLevel()); // 玩家等级
		builder.setExp(playerVO.getExp()); // 当前经验
		if (playerVO.getBaseLevelVO().getExp() == 0) {
			builder.setMaxexp(999999); // 升级所需最大经验
		} else {
			builder.setMaxexp(playerVO.getBaseLevelVO().getExp()); // 升级所需最大经验
		}

		builder.setRenown(playerVO.getRenown()); // 声望
		builder.setHonor(playerVO.getHonor()); // 军衔
		builder.setCombat(playerVO.getCombat()); // 战斗力
		builder.setVip(playerVO.getVip()); // vipf
		builder.setMaxexpc(playerVO.getBaseLevelVO().getMaxExpc());// 经验池上限
		return builder;
	}

	public static MyPlayerAccount.Builder getPlayerAccount(UserCached userCached) {
		PlayerAccountVO playerAccountVO = userCached.getPlayerAccountVO();
		MyPlayerAccount.Builder builder = MyPlayerAccount.newBuilder();

		builder.setMoney(playerAccountVO.getGmMoney()); // 游戏 币
		builder.setRmb(playerAccountVO.getGmRmb()); // 元宝
		builder.setExploit(playerAccountVO.getExploit()); // 功勋
		builder.setPvpm(playerAccountVO.getPvp()); // pvp值
		builder.setPower(playerAccountVO.getPower()); // 行动力
		builder.setMaxPower(CommonConstants.POWER_UP_LIMIT); // 行动力上限
		builder.setPowerLifeTime(playerAccountVO.getLifeTime()); // 行动力倒计时(unit:s)
		builder.setExpc(playerAccountVO.getExpc());// 经验池
		builder.setGiftgod(playerAccountVO.getGiftgod());// 礼金
		builder.setScore(playerAccountVO.getScore());// 积分
		return builder;
	}

	/**
	 * 任务信息
	 * 
	 * @param playerSoldierVO
	 * @return
	 */
	public static MyTask.Builder getTask(PlayerTaskVO playerTaskVO) {
		MyTask.Builder task = MyTask.newBuilder();
		task.setTaskId(playerTaskVO.getTaskId());
		// task.setTaskName(playerTaskVO.getTaskName());
		task.setTaskType(playerTaskVO.getTaskType());
		task.setStatus(playerTaskVO.getStatus());
		task.setFinishnum(playerTaskVO.getFinishNum());
		return task;
	}

	/**
	 * 加载任务信息
	 * 
	 * @param userCached
	 * @param playerProto
	 */
	public static void loadTaskList(UserCached userCached, PlayerProto.Builder playerProto) {
		if (userCached != null && userCached.getTaskList() != null && userCached.getTaskList().size() > 0) {
			int level = userCached.getPlayerVO().getLevel();
			for (PlayerTaskVO task : userCached.getTaskList()) {
				if (task.getBaseTaskVO().getType() == TaskConstant.TASK_TYPE_MAIN || level >= task.getBaseTaskVO().getLevel()) {
					playerProto.addTask(getTask(task));
				}
			}
		}
	}

	/**
	 * 背包格子信息
	 * 
	 * @param userCached
	 * @param playerProto
	 */
	public static void loadMyKnapsack(UserCached userCached, PlayerProto.Builder playerProto) {
		if (userCached != null && userCached.getUserKnapsack() != null) {
			playerProto.setMyKnapsack(getMyKnapsack(userCached));

		}
	}

	public static MyKnapsack.Builder getMyKnapsack(UserCached userCached) {
		MyKnapsack.Builder builder = MyKnapsack.newBuilder();
		builder.setOpenGrid(userCached.getPlayerAccountVO().getKnapsack());
		builder.setCanGrid(userCached.getPlayerAccountVO().getKnapsack2());
		builder.setRemaintime((int) (userCached.getUserTimer().getPlayerTimerVO().getKnpsackTime() / 1000));
		return builder;
	}

	public static void loadBuildLevel(UserCached userCached, PlayerProto.Builder playerProto) {
		if (userCached != null && userCached.getUserKnapsack() != null) {
			PlayerBuildVO playerBuildVO = userCached.getUserTimer().getPlayerBuildVO();
			BuildInfo.Builder buildInfo = null;

			buildInfo = BuildInfo.newBuilder();
			buildInfo.setBuildId(BUILDID.YISITANG);
			buildInfo.setBuildLevel(playerBuildVO.getB1());
			playerProto.addBuildInfo(buildInfo);

			buildInfo = BuildInfo.newBuilder();
			buildInfo.setBuildId(BUILDID.YANGXINGDIAN);
			buildInfo.setBuildLevel(playerBuildVO.getB2());
			playerProto.addBuildInfo(buildInfo);

			buildInfo = BuildInfo.newBuilder();
			buildInfo.setBuildId(BUILDID.SHANGHUI);
			buildInfo.setBuildLevel(playerBuildVO.getB3());

			playerProto.addBuildInfo(buildInfo);
		}
	}

	public static void loadHeroList(UserCached userCached, PlayerProto.Builder playerProto) {
		if (userCached != null && userCached.getUserHero().getHeroList() != null && userCached.getUserHero().getHeroList().size() > 0) {
			for (PlayerHeroVO playerHeroVO : userCached.getUserHero().getHeroList()) {
				playerProto.addMyHero(getHeroInfo(playerHeroVO));
			}
		}
	}

	/**
	 * 英雄信息
	 * 
	 * @param playerHeroVO
	 * @return
	 */
	public static MyHero getHeroInfo(PlayerHeroVO playerHeroVO) {
		MyHero.Builder myHero = MyHero.newBuilder();
		myHero.setId(playerHeroVO.getId());
		myHero.setCfgId(playerHeroVO.getCfgId()); // 配置id
		myHero.setName(playerHeroVO.getName()); // 名称
		myHero.setUrl(playerHeroVO.getBaseHeroInfoVO().getUrl()); // 资源名称
		myHero.setLevel(playerHeroVO.getLevel()); // 等级
		myHero.setStar(playerHeroVO.getBaseHeroInfoVO().getStar()); // 星级
		myHero.setExp(playerHeroVO.getExp()); // 当前经验
		myHero.setMaxExp(playerHeroVO.getBaseLevelVO().getExp()); // 升级所需经验
		myHero.setHp(playerHeroVO.getFinal_hp()); // 生命
		myHero.setAtk(playerHeroVO.getFinal_atk()); // 远程攻击
		myHero.setDef(playerHeroVO.getFinal_def()); // 远程防御
		myHero.setMatk(playerHeroVO.getFinal_matk()); // 远程攻击
		myHero.setMdef(playerHeroVO.getFinal_mdef()); // 远程防御
		myHero.setAtkSpeed(playerHeroVO.getBaseHeroInfoVO().getAtk_speed()); // 攻速
		myHero.setCirRate(playerHeroVO.getFinal_cir_rate()); // 暴击率
		myHero.setMoveSpeed(playerHeroVO.getBaseHeroInfoVO().getMove_speed()); // 移动速度
		myHero.setHit(playerHeroVO.getFinal_hit()); // 命中
		myHero.setDodge(playerHeroVO.getFinal_dodge()); // 闪避
		myHero.setRange(playerHeroVO.getBaseHeroInfoVO().getAtk_range()); // 攻击范围
		myHero.setRace(playerHeroVO.getBaseHeroInfoVO().getRace()); // 职业id
		myHero.setHunger(playerHeroVO.getHungre()); // 饥饿值 疲劳值
		myHero.setStatus(playerHeroVO.getLineStatus()); //
		myHero.setSkillid(playerHeroVO.getBaseHeroInfoVO().getSkill_id());
		myHero.setSkilllevel(playerHeroVO.getSkillLevel());
		myHero.setMaxLevel(playerHeroVO.getBaseHeroInfoVO().getMaxLevel());
		myHero.setCombat(playerHeroVO.getCombat());
		myHero.setHungryStatus(playerHeroVO.getHang_status());
		myHero.setBeat(playerHeroVO.getBaseHeroInfoVO().getBeat());
		myHero.setCommonAtk(playerHeroVO.getBaseHeroInfoVO().getCommon_atk());
		myHero.setYzhp(playerHeroVO.getYzhp());
		myHero.setYzanger(playerHeroVO.getYzanger());

		if (playerHeroVO.getNpassivesSkill() != null) {
			int i = 0;
			for (PassivesSkillVO passivesSkillVO : playerHeroVO.getNpassivesSkill()) {
				if (passivesSkillVO == null) {
					if (playerHeroVO.getLevel() >= PassivesSkillRes.openLevel[i]) {
						myHero.addPassivesSkill(0);
					} else {
						myHero.addPassivesSkill(-PassivesSkillRes.openLevel[i]);
					}
				} else {
					myHero.addPassivesSkill(passivesSkillVO.getId());
				}
				i++;
			}
		}

		if (playerHeroVO.getEquipList().size() > 0) {
			for (PlayerKnapsackVO playerKnapsackVO : playerHeroVO.getEquipList()) {
				myHero.addItemInfo(getItemInfo(playerKnapsackVO));
			}
		}

		return myHero.build();
	}

	/**
	 * 背包物品
	 * 
	 * @param userCached
	 * @param playerProto
	 */
	public static void loadKnaspackList(UserCached userCached, PlayerProto.Builder playerProto) {
		// playerProto.setCurOpenNumber(userCached.getPlayerAccountVO().getKnapsack());
		for (PlayerKnapsackVO playerKnapsackVO : userCached.getUserKnapsack().getKnapsackList()) {
			playerProto.addItems(VOUtil.getItemInfo(playerKnapsackVO));
		}
	}

	/**
	 * 物品装备信息
	 * 
	 * @param playerKnapsackVO
	 * @return
	 */
	public static ItemInfo.Builder getItemInfo(PlayerKnapsackVO playerKnapsackVO) {
		ItemInfo.Builder builder = ItemInfo.newBuilder();

		builder.setId(playerKnapsackVO.getItemid());
		builder.setCfgId(playerKnapsackVO.getCfgId());
		builder.setIsBind(playerKnapsackVO.getBind());
		builder.setEnhance(playerKnapsackVO.getEnhance());
		builder.setNum(playerKnapsackVO.getNumber());
		builder.setType(playerKnapsackVO.getBaseItemVO().getType());
		// 宝石
		builder.addGemCfgId(playerKnapsackVO.getGem1());
		builder.addGemCfgId(playerKnapsackVO.getGem2());
		builder.addGemCfgId(playerKnapsackVO.getGem3());
		builder.addGemCfgId(playerKnapsackVO.getGem4());

		builder.setHp(playerKnapsackVO.getFinal_hp());
		builder.setDef(playerKnapsackVO.getFinal_def());
		builder.setMdef(playerKnapsackVO.getFinal_mdef());
		builder.setAtk(playerKnapsackVO.getFinal_atk());
		builder.setMatk(playerKnapsackVO.getFinal_matk());
		builder.setHit(playerKnapsackVO.getFinal_hit());
		builder.setDodge(playerKnapsackVO.getFinal_dodge());
		builder.setCirRate(playerKnapsackVO.getFinal_cir_rate());

		builder.setAddhp(playerKnapsackVO.getAdd_hp());
		builder.setAdddef(playerKnapsackVO.getAdd_def());
		builder.setAddmdef(playerKnapsackVO.getAdd_mdef());
		builder.setAddatk(playerKnapsackVO.getAdd_atk());
		builder.setAddmatk(playerKnapsackVO.getAdd_matk());
		builder.setAddhit(playerKnapsackVO.getAdd_hit());
		builder.setAdddodge(playerKnapsackVO.getAdd_dodge());
		builder.setAddcirRate(playerKnapsackVO.getAdd_cir_rate());

		builder.setProtectLevel(playerKnapsackVO.getProtectLevel());
		builder.setProtectExp(playerKnapsackVO.getProtectExp());
		builder.setCombat(playerKnapsackVO.getCombat());

		if (playerKnapsackVO.getBaseProteVO() != null) {
			builder.setProtectRate(playerKnapsackVO.getBaseProteVO().getStats());
		} else {
			builder.setProtectRate(0);
		}

		return builder;
	}

	public static FinalHero.Builder getFinalHero(PlayerHeroDefVO playerHeroVO) {
		FinalHero.Builder builder = FinalHero.newBuilder();
		BaseHeroInfoVO baseHeroInfoVO = HeroRes.getInstance().getBaseHeroInfoVO(playerHeroVO.getCfgId());

		// playerHeroVO.getBaseHeroInfoVO();
		builder.setId(playerHeroVO.getId());
		builder.setCfgId(playerHeroVO.getCfgId());
		builder.setName(playerHeroVO.getName());
		builder.setUrl(baseHeroInfoVO.getUrl());
		builder.setRace(baseHeroInfoVO.getRace());
		builder.setRange(baseHeroInfoVO.getAtk_range());
		builder.setSkillid(baseHeroInfoVO.getSkill_id());
		builder.setAtkSpeed(baseHeroInfoVO.getAtk_speed());
		builder.setLevel(playerHeroVO.getLevel());
		builder.setStar(playerHeroVO.getStar());
		builder.setHp(playerHeroVO.getHp());
		builder.setAtk(playerHeroVO.getAtk());
		builder.setDef(playerHeroVO.getDef());
		builder.setMatk(playerHeroVO.getMatk());
		builder.setMdef(playerHeroVO.getMdef());
		builder.setCirRate(playerHeroVO.getCir_rate());
		builder.setHit(playerHeroVO.getHit());
		builder.setDodge(playerHeroVO.getDodge());
		builder.setCombat(playerHeroVO.getCombat());
		builder.setSkilllevel(playerHeroVO.getSkillLevel());
		builder.setBeat(baseHeroInfoVO.getBeat());
		builder.setCommonAtk(baseHeroInfoVO.getCommon_atk());
		builder.setHpX(baseHeroInfoVO.getHpX());
		builder.setHpY(baseHeroInfoVO.getHpY());
		builder.setYzanger(playerHeroVO.getYzanger());
		builder.setYzhp(playerHeroVO.getYzhp());
		// if (CodeTool.isNotEmpty(playerHeroVO.getPassivesSkill())) {
		// for (int value : Tool.strToIntArr(playerHeroVO.getPassivesSkill())) {
		// if (value > 0) {
		// builder.addPassivesSkill(value);
		// }
		// }
		// }
		return builder;
	}

	public static FinalHero.Builder getFinalHero(BaseMonsterVO baseMonsterVO) {
		FinalHero.Builder builder = FinalHero.newBuilder();

		// playerHeroVO.getBaseHeroInfoVO();
		builder.setId(0);
		builder.setCfgId(baseMonsterVO.getCfgId());
		builder.setName(baseMonsterVO.getName());
		builder.setUrl(baseMonsterVO.getUrl());
		builder.setRace(baseMonsterVO.getRace());
		builder.setRange(baseMonsterVO.getAtk_range());
		builder.setSkillid(baseMonsterVO.getSkill_id());
		builder.setAtkSpeed(baseMonsterVO.getAtk_speed());
		builder.setLevel(baseMonsterVO.getLevel());
		builder.setStar(baseMonsterVO.getStar());
		builder.setHp(baseMonsterVO.getHp());
		builder.setAtk(baseMonsterVO.getAtk());
		builder.setDef(baseMonsterVO.getDef());
		builder.setMatk(baseMonsterVO.getMatk());
		builder.setMdef(baseMonsterVO.getMdef());
		builder.setCirRate(baseMonsterVO.getCir());
		builder.setHit(baseMonsterVO.getHit());
		builder.setDodge(baseMonsterVO.getDodge());
		builder.setCombat(0);
		builder.setSkilllevel(baseMonsterVO.getSkilllevel());
		builder.setBeat(baseMonsterVO.getBeat());
		builder.setCommonAtk(baseMonsterVO.getCommonatk());
		builder.setHpX(baseMonsterVO.getHpX());
		builder.setHpY(baseMonsterVO.getHpY());
		builder.setBossType(baseMonsterVO.getType());
		// if (CodeTool.isNotEmpty(baseMonsterVO.getPassivesSkill())) {
		// for (int value : Tool.strToIntArr(baseMonsterVO.getPassivesSkill()))
		// {
		// if (value > 0) {
		// builder.addPassivesSkill(value);
		// }
		// }
		// }
		return builder;
	}

	/**
	 * 江湖英雄数据
	 * 
	 * @param playerHeroDefVO
	 * @return
	 */
	public static FinalHero.Builder getFinalHero(PlayerHeroVO playerHeroVO) {
		FinalHero.Builder builder = FinalHero.newBuilder();
		BaseHeroInfoVO baseHeroInfoVO;
		if (playerHeroVO.getPlayerId() < 0) {
			baseHeroInfoVO = playerHeroVO.getBaseHeroInfoVO();
		} else {
			baseHeroInfoVO = HeroRes.getInstance().getBaseHeroInfoVO(playerHeroVO.getCfgId());
		}
		builder.setId(playerHeroVO.getId());
		builder.setCfgId(playerHeroVO.getCfgId());
		builder.setName(playerHeroVO.getName());
		builder.setUrl(baseHeroInfoVO.getUrl());
		builder.setRace(baseHeroInfoVO.getRace());
		builder.setRange(baseHeroInfoVO.getAtk_range());
		builder.setSkillid(baseHeroInfoVO.getSkill_id());
		builder.setAtkSpeed(baseHeroInfoVO.getAtk_speed());
		builder.setLevel(playerHeroVO.getLevel());
		builder.setStar(baseHeroInfoVO.getStar());

		builder.setHp(playerHeroVO.getFinal_hp());
		builder.setAtk(playerHeroVO.getFinal_atk());
		builder.setDef(playerHeroVO.getFinal_def());
		builder.setMatk(playerHeroVO.getFinal_atk());
		builder.setMdef(playerHeroVO.getFinal_mdef());
		builder.setCirRate(playerHeroVO.getFinal_cir_rate());
		builder.setHit(playerHeroVO.getFinal_hit());
		builder.setDodge(playerHeroVO.getFinal_dodge());

		builder.setCombat(playerHeroVO.getCombat());
		builder.setSkilllevel(playerHeroVO.getSkillLevel());
		builder.setBeat(baseHeroInfoVO.getBeat());
		builder.setCommonAtk(baseHeroInfoVO.getCommon_atk());
		builder.setHpX(baseHeroInfoVO.getHpX());
		builder.setHpY(baseHeroInfoVO.getHpY());
		// if (CodeTool.isNotEmpty(playerHeroVO.getPassivesSkill())) {
		// for (int value : Tool.strToIntArr(playerHeroVO.getPassivesSkill())) {
		// if (value > 0) {
		// builder.addPassivesSkill(value);
		// }
		// }
		// }
		return builder;
	}

	public static MailInfo getMailInfo(MailVO mailVO) {

		return MailInfo.newBuilder().setId(mailVO.getId()).setContent(mailVO.getContent()).setIsRead(mailVO.getIsRead()).setIsReward(mailVO.getIsReward())
				.setRewards(mailVO.getRewardsProto() == null ? Rewards.newBuilder().build() : mailVO.getRewardsProto()).setSenderName(mailVO.getSenderName() == null ? "" : mailVO.getSenderName())
				.setTitle(mailVO.getTitle()).setRecvTime((int) (mailVO.getRecvTime().getTime() / 1000)).setLifeTime(mailVO.getLifeTime()).build();
	}

	public static StreetHomeResp packUserStreet(UserStreet userStreet) {
		StreetHomeResp.Builder resp = StreetHomeResp.newBuilder();
		resp.setStatus(StreetStatus.newBuilder().setIsCenterAtked(userStreet.getCenterStatus()).setIsResAtked(userStreet.getResStatus()));
		List<StreetResVO> resList = userStreet.getResList();

		for (StreetResVO streetResVO : resList) {// 增加资源点
			StreetResInfo.Builder resBuild = StreetResInfo.newBuilder().setId(streetResVO.getId());
			resBuild.setStatus(streetResVO.getStatus());
			List<Reward> rewards = new ArrayList<Reward>();
			rewards.addAll(streetResVO.getTempItems());
			rewards.addAll(streetResVO.getItems());
			for (Reward reward : rewards) {
				resBuild.addResItemInfo(ResItemInfo.newBuilder().setCfgId(reward.getId()).setType(reward.getType()));
			}
			rewards.clear();
			rewards = null;
			resp.addStreetGridInfo(StreetGridInfo.newBuilder().setId(streetResVO.getId()).setStreetResInfo(resBuild).setOpenStaus(OPEN_STATUS.OPEN_CONTENT));
		}
		for (StreetBoxVO streetBoxVO : userStreet.getBoxList()) {// 增加宝箱
			resp.addStreetGridInfo(StreetGridInfo.newBuilder().setId(streetBoxVO.getId()).setBoxId(streetBoxVO.getCfgId()).setOpenStaus(OPEN_STATUS.OPEN_CONTENT));
		}

		for (StreetMonsterVO streetMonsterVO : userStreet.getMonsterList()) {// 增加怪物
			resp.addStreetGridInfo(StreetGridInfo
					.newBuilder()
					.setId(streetMonsterVO.getId())
					.setMonsterInfo(
							MonsterInfo.newBuilder().setMonsterId(streetMonsterVO.getCfgId()).setUrl(streetMonsterVO.getBaseMonsterGroupVO().getUrl())
									.setLevel(streetMonsterVO.getBaseMonsterGroupVO().getLevel()).setName(streetMonsterVO.getBaseMonsterGroupVO().getName())).setOpenStaus(OPEN_STATUS.OPEN_CONTENT));
		}
		StreetConstants.calReachble(userStreet);
		for (int i = 0; i < StreetConstants.MAX_GRIDS_NUM; i++) {
			if (userStreet.getFreeGrids()[i] == 1) {
				resp.addStreetGridInfo(StreetGridInfo.newBuilder().setId(i).setOpenStaus(OPEN_STATUS.OPEN_NOCENTENT));
			} else if (userStreet.getGrids()[i] == 0 && userStreet.getReachbleGrids()[i] == 1) {// 没有开启的
				resp.addStreetGridInfo(StreetGridInfo.newBuilder().setId(i).setOpenStaus(OPEN_STATUS.REACHABLE));
			}
		}
		String fzIdStr = userStreet.getFzIds();
		if (fzIdStr != null && !fzIdStr.isEmpty()) {
			String[] fzIds = StreetConstants.HERO_SPLIT_CHAR.split(fzIdStr);
			for (String string : fzIds) {
				resp.addFid(Integer.valueOf(string));
			}
		}
		String mIdStr = userStreet.getFzIds();
		if (mIdStr != null && !mIdStr.isEmpty()) {
			String[] mIds = StreetConstants.HERO_SPLIT_CHAR.split(mIdStr);
			for (String string : mIds) {
				resp.addMid(Integer.valueOf(string));
			}
		}
		int now = (int) (System.currentTimeMillis() / 1000);
		resp.setBoxLeft(StreetConstants.BOX_FRESH_PERIOD - (now - userStreet.getBoxFreshTime())).setMonsterLeft(StreetConstants.MONSTER_FRESH_PERIOD - (now - userStreet.getMonsterFreshTime()));

		return resp.build();
	}

	public static StreetResInfo.Builder packStreetRes(StreetResVO streetResVO) {
		StreetResInfo.Builder resBuild = StreetResInfo.newBuilder().setId(streetResVO.getId());
		resBuild.setStatus(streetResVO.getStatus());
		List<Reward> rewards = new ArrayList<Reward>();
		rewards.addAll(streetResVO.getItems());
		rewards.addAll(streetResVO.getTempItems());
		for (Reward reward : rewards) {
			resBuild.addResItemInfo(ResItemInfo.newBuilder().setCfgId(reward.getId()).setType(reward.getType()));
		}
		rewards.clear();
		rewards = null;
		return resBuild;
	}

	public static HuntResp.Builder packHuntResp(HuntResp.Builder resp, StreetResVO streetResVO, List<PlayerHeroVO> defHeros) {
		String machineLine = null;
		int combat = 0;
		for (PlayerHeroVO playerHeroDefVO : defHeros) {
			combat += playerHeroDefVO.getCombat();
			resp.addFinalHero(getFinalHero(playerHeroDefVO));
		}

		if ((machineLine = streetResVO.getMachineLine()) != null && !machineLine.isEmpty()) {
			String[] machines = StreetConstants.HERO_SPLIT_CHAR.split(machineLine);
			for (String string : machines) {
				resp.addMachineId(Integer.valueOf(string));
			}
		}
		if (streetResVO.getFzId() != 0)
			resp.setFzId(streetResVO.getFzId());
		int timeLeft;
		if (streetResVO.getPlayerId() < 0) {
			timeLeft = (int) (StreetConstants.RES_OWN_PERIOD / 1000);
		} else {
			timeLeft = (int) (streetResVO.getBeginTime() + StreetConstants.RES_OWN_PERIOD / 1000 - System.currentTimeMillis() / 1000);
		}
		resp.setTimeLeft(Math.max(0, timeLeft));
		resp.setCombat(combat);
		return resp;
	}

	public static EnemyCenterResp.Builder packEnemyCenter(PlayerVO enemyVO, StreetResVO streetRes, List<PlayerHeroVO> defHeros) {
		EnemyCenterResp.Builder resp = EnemyCenterResp.newBuilder();
		String machineLine = null;
		int combat = 0;
		for (PlayerHeroVO playerHeroDefVO : defHeros) {
			combat += playerHeroDefVO.getCombat();
			resp.addFinalHero(VOUtil.getFinalHero(playerHeroDefVO));
		}
		if ((machineLine = streetRes.getMachineLine()) != null && !machineLine.isEmpty()) {
			String[] machines = StreetConstants.HERO_SPLIT_CHAR.split(machineLine);
			for (String string : machines) {
				resp.addMachineId(Integer.valueOf(string));
			}
		}
		if (streetRes.getFzId() != 0)
			resp.setFzId(streetRes.getFzId());
		PlayerAccountVO enemyAccount = RedisMap.getPlayerAccount(streetRes.getPlayerId());
		if (enemyAccount == null) {
			throw new GameException(AlertEnum.ACCOUNTVO_NOT_EXIST);
		}
		resp.setHeadIcon(enemyVO.getHeadIcon());
		resp.setLevel(enemyVO.getLevel());
		resp.setNick(enemyVO.getName());
		resp.setCombat(combat);
		resp.setPlayerId(enemyVO.getPlayerId());
		resp.setVip(enemyVO.getVip());
		resp.setExp(enemyAccount.getExpc());
		resp.setMoney(enemyAccount.getGmMoney());
		return resp;
	}

	public static BattleLogInfo.Builder packStreetDefLogs(StreetDefendLogVO streetDefendLogVO, StreetEnemyVO streetEnemyVO) throws Exception {
		int type = ResourceRes.getInstance().getResourceByGridId(streetDefendLogVO.getResId()).getType();
		boolean isRevenge = false;
		if (streetEnemyVO != null) {
			isRevenge = streetEnemyVO.getIsRevenge() == 1;
		}
		BattleLogInfo.Builder resp = BattleLogInfo.newBuilder();
		resp.setAtkTime(DateUtil.getNow() - streetDefendLogVO.getAtkTime());
		resp.setIsSucc(streetDefendLogVO.getIsSucc() == 1);
		resp.setName(streetDefendLogVO.getEnemyName());
		resp.setPlayerId(streetDefendLogVO.getEnemyId());
		resp.addAllRewards(encodeRewardStrToproto(streetDefendLogVO.getRewards()));
		resp.setType(type);
		resp.setIsRevenge(isRevenge);
		resp.setId(streetDefendLogVO.getId());
		boolean isCenter = streetDefendLogVO.getResId() == StreetConstants.CENTER_GRID_NUM;
		if (isCenter) {
			List<Reward> rewards = new ArrayList<Reward>();
			rewards.add(MailService.createReward(RewardService.REWARD_TYPE_MONEY, 0, streetDefendLogVO.getMoney()));
			rewards.add(MailService.createReward(RewardService.REWARD_TYPE_HERO_EXP, 0, streetDefendLogVO.getExpc()));
			resp.addAllRewards(encodeRewardStrToproto(MailService.encoderReward(rewards)));
		}

		resp.setIsAtk(streetDefendLogVO.getIsAtk());
		resp.setIsReward(streetDefendLogVO.getIsReward());
		return resp;
	}

	public static List<RewardInfo> encodeRewardStrToproto(String rewardStr) {
		List<RewardInfo> rewardInfos = new ArrayList<RewardInfo>();
		if (rewardStr == null || rewardStr.isEmpty()) {
			return rewardInfos;
		}
		String[] rewardStrings = MailConstants.REWARD_SPLIT_CHAR.split(rewardStr);
		for (String string : rewardStrings) {
			RewardInfo.Builder rewardInfo = RewardInfo.newBuilder();
			String[] temp = MailConstants.REWARD_SPLIT_TYPE_CHAR.split(string);
			rewardInfo.setType(Integer.parseInt(temp[0]));
			BaseItemVO item = ItemRes.getInstance().getBaseItemVO(Integer.parseInt(temp[1]));
			rewardInfo.setNumber(Integer.parseInt(temp[2]));
			if (item != null) {
				rewardInfo.setContent(item.getCfgId());
			}
			rewardInfos.add(rewardInfo.build());
		}
		return rewardInfos;
	}

	public static StreetDefendResp.Builder packResDefend(List<PlayerHeroVO> defHeros, StreetResVO streetResVO) {
		StreetDefendResp.Builder resp = StreetDefendResp.newBuilder();
		resp.setResId(streetResVO.getId());
		if (streetResVO.getFzId() != 0) {
			resp.setFzId(streetResVO.getFzId());
		}
		String machines = streetResVO.getMachineLine();

		if (machines != null && !machines.isEmpty()) {
			String[] mIds = StreetConstants.HERO_SPLIT_CHAR.split(machines);
			for (String string : mIds) {
				if (string.endsWith("null")) {
					continue;
				} else {
					resp.addMachineId(Integer.valueOf(string));
				}
			}
		}
		for (PlayerHeroVO playerHeroVO : defHeros) {
			resp.addHeroId(playerHeroVO.getId());
		}
		return resp;
	}

	public static EnemyLogInfo.Builder packEnemyInfo(StreetEnemyVO streetEnemyVO) {
		EnemyLogInfo.Builder logInfo = EnemyLogInfo.newBuilder();
		logInfo.setHeadIcon(streetEnemyVO.getHeadIcon());
		logInfo.setLevel(streetEnemyVO.getLevel());
		logInfo.setName(streetEnemyVO.getName());
		logInfo.setPlayerId(streetEnemyVO.getEnemyId());
		logInfo.setWhosyourdaddy(streetEnemyVO.isHidden());
		logInfo.setIsRevenge(streetEnemyVO.getIsRevenge() == 1);
		return logInfo;
	}

	public static com.dh.game.vo.BaseProto.RewardInfoList.Builder packRewardInfos(List<Reward> rewards) {
		com.dh.game.vo.BaseProto.RewardInfoList.Builder builder = RewardInfoList.newBuilder();
		if (rewards != null) {
			for (Reward reward : rewards) {
				builder.addRewardInfos(com.dh.game.vo.BaseProto.RewardInfo.newBuilder().setContent(reward.getContent()).setNumber(reward.getNumber()).setType(reward.getType()));
			}
		}
		return builder;
	}

	public static DaySignResp.Builder packSign(final UserCached userCached, final PlayerWelfareVO playerWelfareVO) {
		DaySignResp.Builder resp = DaySignResp.newBuilder();
		int daySign = playerWelfareVO.getDaySign();
		int totalResign = VipLevelRes.getInstance().getPvpResignTimes(userCached.getPlayerVO().getVip());
		resp.setDaySign(daySign).setResignCount(totalResign - playerWelfareVO.getResignCount()).setSignReawrd(playerWelfareVO.getRewardSign());
		return resp;
	}

	public static MERaidInfo.Builder packMERaid(UserCached userCached, BaseActivityVO MERaid, int now) {
		PlayerTimerVO playerTimerVO = userCached.getUserTimer().getPlayerTimerVO();
		int vipLevel = userCached.getPlayerVO().getVip();
		MERaidInfo.Builder builder = MERaidInfo.newBuilder();
		boolean isE = MERaid.getType() == 11;
		BaseActivityVO meRaid = GameRecordConstants.getMERaid(isE ? GameRecordConstants.MERAID_EXPC : GameRecordConstants.MERAID_MONEY);
		int raidId = isE ? RaidRes.ME_RAID_E : RaidRes.ME_RAID_M;
		BaseRaidInfo baseRaidInfo = RaidRes.getInstance().getBaseRaidInfo(raidId);
		builder.setName(MERaid.getName()).setOpenTip(meRaid.getDec()).setReqLevel(baseRaidInfo.getReq_level()).setReqPower(baseRaidInfo.getEnergy());
		int countDown, timeUse, totalTime, leftTime;
		if (isE) {
			builder.setType(1);
			countDown = RaidConstant.RAID_E_CD - (now - playerTimerVO.getERaidDate());
			timeUse = playerTimerVO.getERaidCount();
			totalTime = RaidConstant.RAID_E_TIME + playerTimerVO.getERaidBuyCount() - playerTimerVO.getERaidCount();
			leftTime = VipLevelRes.getInstance().getExpRaidBuyTimes(vipLevel) - playerTimerVO.getERaidBuyCount();
		} else {
			builder.setType(2);
			countDown = RaidConstant.RAID_M_CD - (now - playerTimerVO.getMRaidDate());
			timeUse = playerTimerVO.getMRaidCount();
			totalTime = RaidConstant.RAID_M_TIME + playerTimerVO.getMRaidBuyCount() - playerTimerVO.getMRaidCount();
			leftTime = VipLevelRes.getInstance().getMoneyiRaidBuyTimes(vipLevel) - playerTimerVO.getMRaidBuyCount();
		}
		builder.setCountDown(Math.max(0, countDown)).setTime(timeUse).setTotalTime(totalTime).setRaidId(raidId).setLeftTime(leftTime);
		// if (rewards != null && !rewards.isEmpty()) {
		// builder.setRewardCount(rewards.get(0).getNumber());
		// }
		builder.setRewardCount(userCached.getPlayerVO().getLevel() * RaidConstant.RAID_ME_REWARD_RATE);

		return builder;
	}

	public static Builder packBuyPower(final UserCached userCached) {
		VipLevelVO vipLevel = VipLevelRes.getInstance().getVipLevelVO(userCached.getPlayerVO().getVip());
		PlayerTimerVO playerTimerVO = userCached.getUserTimer().getPlayerTimerVO();

		int countLeft = vipLevel.getTime() - playerTimerVO.getBuyPowerCount();
		int pvpCountTimes = vipLevel.getPrivilege5();
		PowerBuyInfo.Builder resp = PowerBuyInfo.newBuilder().setCountLeft(countLeft).setCountMax(vipLevel.getTime()).setMoneyDemand(GameUtil.calPowerBuyCost(playerTimerVO.getBuyPowerCount() + 1))
				.setPvptimes(pvpCountTimes - playerTimerVO.getJjcBuyTimes()).setPvpMoney((playerTimerVO.getJjcBuyTimes() + 1) * ArenaService.RMBVPVP).setPvpCounttimes(pvpCountTimes);
		return resp;
	}

	public static FriendInfo packFriendInfo(final PlayerFriendVO playerFriendVO) {
		PlayerVO playerVO = playerFriendVO.getPlayerVO();
		return FriendInfo.newBuilder().setCombat(playerVO.getCombat()).setIsOnline(ServerHandler.isOnline(playerVO.getPlayerId())).setLevel(playerVO.getLevel()).setName(playerVO.getName())
				.setPlayerId(playerVO.getPlayerId()).setRank(playerFriendVO.getRank()).setVip(playerVO.getVip()).build();
	}

	public static void loadPlayerFriends(UserCached userCached, com.dh.game.vo.login.PlayerLoginProto.PlayerProto.Builder playerProto) {
		PlayerFriendInfo.Builder resp = PlayerFriendInfo.newBuilder();
		for (PlayerFriendVO playerFriendVO : userCached.getUserFriend().getFriends()) {
			resp.addFriends(packFriendInfo(playerFriendVO));
		}
		for (PlayerFriendVO playerFriendVO : userCached.getUserFriend().getBlacks()) {
			resp.addBlacks(packFriendInfo(playerFriendVO));
		}
		playerProto.setPlayerFriend(resp);
	}

	public static HuntLogInfo packBossLog(BossLogVO bossLogVO) {
		return HuntLogInfo.newBuilder().setHunt(bossLogVO.getHunt()).setName(bossLogVO.getName()).setAddtion(bossLogVO.getAddtion()).build();
	}

	public static DayDoneResp.Builder packDayDone(PlayerTimerVO pt) {
		DayDoneResp.Builder resp = DayDoneResp.newBuilder();
		int index = 1;
		for (int i : pt.getDayDoneArray()) {
			resp.addDayDoneInfo(DayDoneInfo.newBuilder().setCfgId(index).setDone(Math.min(DayDoneRes.getInstance().getDayDoneById(index).getNumber(), i)));
			index++;
		}
		resp.setRewards(pt.getDayDoneRewards());
		resp.setScore(pt.getDayDoneScore());
		return resp;
	}

	public static LegionListInfo.Builder packLegionListInfo(int playerId, LegionMemVO memVO, LegionVO legionVO) {
		LegionListInfo.Builder resp = LegionListInfo.newBuilder();
		resp.setId(legionVO.getId());
		resp.setLegionLevel(legionVO.getLegionLevel());
		resp.setLegionName(legionVO.getLegionName());
		resp.setLimitNum(legionVO.getBaseLegionVO().getMembers());
		resp.setMembersNum(legionVO.getRealyMems().size());
		resp.setType(memVO == null ? legionVO.isReq(playerId) : MEM_TYPE.valueOf(memVO.getType()));
		resp.setRank(legionVO.getRank());
		resp.setPlayerNick(legionVO.getPlayerNick());
		resp.setCombat(legionVO.getCombat());
		return resp;
	}

	public static BaseLegionInfo.Builder packBaseLegionInfo(LegionVO legionVO, LegionMemVO legionMemVO) {
		BaseLegionInfo.Builder resp = BaseLegionInfo.newBuilder();
		resp.setId(legionVO.getId());
		resp.setLevel(legionVO.getLegionLevel());
		resp.setName(legionVO.getLegionName());
		resp.setType(MEM_TYPE.valueOf(legionMemVO.getType()));
		return resp;
	}

	public static LegionInfoResp.Builder packLegionInfo(PlayerTimerVO pt, LegionVO legionVO, LegionMemVO memVO) {
		LegionInfoResp.Builder resp = LegionInfoResp.newBuilder();
		int div = legionVO.getDestroyTime() == 0 ? -1 : (legionVO.getDestroyTime() + LegionConstant.LEGION_DESTROY_TIME - DateUtil.getNow());
		resp.setDestroyTime(div);
		resp.setDayReward(pt.getDayLegionReward() == 1);
		resp.setDonateReward(pt.getDonateReward());
		resp.setId(legionVO.getId());
		resp.setLegionLevel(legionVO.getLegionLevel());
		resp.setLegionName(legionVO.getLegionName());
		resp.setLegionNotice(legionVO.getNotice());
		resp.setLimitNum(legionVO.getBaseLegionVO().getMembers());
		resp.setMaterial(legionVO.getMaterial());
		resp.setMembersNum(legionVO.getRealyMems().size());
		resp.setMoney(legionVO.getMoney());
		resp.setType(MEM_TYPE.valueOf(memVO.getType()));
		resp.setPlayerNick(legionVO.getPlayerNick());
		resp.setQq(legionVO.getQq());
		resp.setRank(legionVO.getRank());
		resp.setDonate(memVO.getDonate());
		resp.setOwnDonate(memVO.getOwnDonate());
		return resp;
	}

	public static LegionJionInfo.Builder packLegionJionInfo(LegionMemVO legionMemVO) {
		LegionJionInfo.Builder resp = LegionJionInfo.newBuilder();
		PlayerVO playerVO = RedisMap.getPlayerVObyId(legionMemVO.getPlayerId());
		resp.setName(playerVO.getName());
		resp.setLevel(playerVO.getLevel());
		resp.setCombat(playerVO.getCombat());
		resp.setDate((int) (legionMemVO.getJoinTime().getTime() / 1000));
		resp.setPlayerId(legionMemVO.getPlayerId());
		return resp;
	}

	public static MemDetails.Builder packLegionMemVO(LegionMemVO legionMemVO) {
		MemDetails.Builder mb = MemDetails.newBuilder();
		mb.setArenaRaking(0);
		PlayerVO playerVO = RedisMap.getPlayerVObyId(legionMemVO.getPlayerId());
		mb.setCombat(playerVO.getCombat());
		mb.setDonate(legionMemVO.getDonate());
		mb.setIsOnline(ServerHandler.isOnline(legionMemVO.getPlayerId()) ? 1 : 0);
		mb.setLastLoginDate((int) (playerVO.getLastLoginDate().getTime() / 1000));
		mb.setLevel(playerVO.getLevel());
		mb.setName(playerVO.getName());
		mb.setPlayerId(legionMemVO.getPlayerId());
		mb.setType(MEM_TYPE.valueOf(legionMemVO.getType()));
		return mb;
	}

	public static LogInfo.Builder packLegionLog(LegionLogVO log) {
		LogInfo.Builder logBuild = LogInfo.newBuilder();
		logBuild.setDate((int) (log.getDate().getTime() / 1000));
		logBuild.setType(LEGION_LOG_TYPE.valueOf(log.getType()));
		int size = log.getDataList().size();
		for (int i = 0; i < size; i++) {
			String param = log.getDataList().get(i);
			switch (i) {
			case 0:
				logBuild.setParam1(param);
				break;
			case 1:
				logBuild.setParam2(param);
				break;
			case 2:
				logBuild.setParam3(param);
				break;
			case 3:
				logBuild.setParam4(param);
				break;
			default:
				break;
			}
		}
		return logBuild;
	}

	public static AnsNextResp.Builder packAnsNext(PlayerActyVO actyVO) {
		// required int32 cfgId = 1; //当前题目id
		// required int32 currId = 2; //当前第几题
		// required int32 score = 3; //当前得分
		// required int32 correct = 4; //答对玩家数目
		// repeated int32 skill = 5;
		// //当前还剩余技能数.第一个位置删除错误答案,第二个位置直接选择正确答案,第三个位置积分加倍
		// repeated AnsRankInfo ranks = 6; //排名
		// required bool isOver = 7; //用于处理回答完毕
		AnsNextResp.Builder resp = AnsNextResp.newBuilder();
		BaseAnsVO baseAnsVO = null;
		int currId;
		if (actyVO.getIndex() >= AnsRes.QUETIONS_SIZE) {
			resp.setIsOver(true);
			currId = AnsRes.QUETIONS_SIZE;
		} else {
			resp.setIsOver(false);
			currId = actyVO.getIndex() + 1;
		}
		actyVO.next(currId);

		baseAnsVO = ActivityConstant.getQuestionByIndex(currId);
		resp.setCfgId(baseAnsVO.getId());
		resp.setCurrId(currId);
		resp.setCorrect(actyVO.getCorrect());
		resp.setScore(actyVO.getScore());
		resp.addSkill(actyVO.getAnsdel());
		resp.addSkill(actyVO.getAnsright());
		resp.addSkill(actyVO.getAnsdouble());
		resp.setAnsRankList(ActivityConstant.getTop10AnsBuild());

		return resp;
	}
	// public static IconInfoResponse packageIcon(UserCached userCached) {
	// IconInfoResponse.Builder iconInfoResponse =
	// IconInfoResponse.newBuilder();
	// // 江湖追杀 世界boss
	// IconInfo.Builder iconInfo = IconInfo.newBuilder();
	// iconInfo.setId(ICONID.JHZS);
	// WorldBossVO instance = WorldBossVO.getInstance();
	// if (instance.isBattle()) {
	// iconInfo.setState(1);
	// } else {
	// iconInfo.setState(0);
	// }
	// iconInfoResponse.addIconInfo(iconInfo);
	//
	// // 替天行道 精英副本
	// iconInfo = IconInfo.newBuilder();
	// iconInfo.setId(ICONID.TTXD);
	// PlayerEliteRaidVO firstPlayerEliteRaidVO =
	// userCached.getUserRaid().getFirstPlayerEliteRaidVO();
	// if (firstPlayerEliteRaidVO != null && firstPlayerEliteRaidVO.getTimes() >
	// 0) {
	// iconInfo.setState(1);
	// } else {
	// iconInfo.setState(0);
	// }
	// iconInfoResponse.addIconInfo(iconInfo);
	//
	// // 华山论剑 远征
	// iconInfo = IconInfo.newBuilder();
	// iconInfo.setId(ICONID.HSLJ);
	// if (YuanZhenProcessor.haveYuanZhen(userCached)) {
	// iconInfo.setState(1);
	// } else {
	// iconInfo.setState(0);
	// }
	// iconInfoResponse.addIconInfo(iconInfo);
	//
	//
	// // 神机入梦 经验金钱副本
	// iconInfo = IconInfo.newBuilder();
	// iconInfo.setId(ICONID.SJRM);
	// iconInfo.setState(0);
	// iconInfoResponse.addIconInfo(iconInfo);
	//
	// return iconInfoResponse.build();
	// }
}