package com.dh.constants;

import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import org.springframework.beans.BeanUtils;

import com.dh.game.vo.base.BaseHeroInfoVO;
import com.dh.game.vo.base.BaseMonsterGroupVO;
import com.dh.game.vo.base.BaseMonsterVO;
import com.dh.game.vo.user.PlayerHeroVO;
import com.dh.game.vo.user.PlayerVO;
import com.dh.resconfig.LevelRes;
import com.dh.resconfig.MonsterGroupRes;
import com.dh.resconfig.MonsterRes;
import com.dh.resconfig.NameRes;
import com.dh.util.CombatUtil;
import com.dh.vo.user.UserCached;
import com.dh.vo.user.UserHero;
import com.dh.vo.user.UserStreet;

/**
 *
 */
/**
 * 
 */
public class StreetConstants {
	public final static TIntObjectHashMap<List<List<UserCached>>> STREET_ROBOTS = new TIntObjectHashMap<List<List<UserCached>>>();
	public final static TIntObjectHashMap<List<UserCached>> STREET_ROBOTS_MAP = new TIntObjectHashMap<List<UserCached>>();

	// playerId映射
	// public final static TIntObjectHashMap<UserCached>
	// STREET_ROBOT_PLAYER_ID_MAP = new TIntObjectHashMap<UserCached>();

	/** 机器人数据存取方法:首先按照resId存取,然后以玩家playeId作为key,存入,特别地需要主要回收 */
	public final static TIntObjectHashMap<TIntObjectHashMap<UserCached>> STREET_ROBOT_RESID_PID_MAP = new TIntObjectHashMap<TIntObjectHashMap<UserCached>>(30);// 资源点id对应的机器人
	// 江湖机器人
	public final static int ROBOT_TOTAL_LEVEL = 95;// 总等级
	public final static int ROBOT_COMBAT_SMALL = 30000;// 总等级
	public final static int ROBOT_COMBAT_MID = 200000;// 总等级
	public final static int ROBOT_COMBAT_BIG = 450000;// 总等级
	public final static int ROBOT_LEVEL_NUM = 3;// 每一个等级,星级产生的机器人数目
	public final static int ROBOT_LEVEL_STAR_NUM = 3;// //星级

	public final static int STREET_BATTLE_PVP_COST = 10;// 攻打资源点消耗竞技值
	public final static int STREET_BATTLE_MONSTER_PVP_COST = 5;// 攻打野怪消耗竞技值
	public final static int STREET_BATTLE_HUNGREY_WIN_COST = 10;// 消耗疲劳值
	public final static int STREET_BATTLE_HUNGREY_LOST_COST = 20;// 消耗疲劳值

	// 江湖开启等级
	public final static int STREET_OPEN_LEVEL = 26;

	public final static int MAX_GRIDS_NUM = 81;
	public final static int CENTER_GRID_NUM = MAX_GRIDS_NUM / 2;
	public final static int H_GRIDS_NUM = 9;// 水平格子数
	public final static int V_GRIDS_NUM = 9;// 垂直格子数

	public final static int RES_STATE_FREE = 0;
	public final static int RES_STATE_DEFEND = 1;
	public final static int RES_STATE_OCCUPY = 2;// 占领
	public final static int RES_STATE_PRODUCED = 3;// 可收获

	// [状态{0:空闲,1:正在战斗,2:战斗失败,3:战斗成功,4:生产状态,5:生产完成}]
	public final static int PLAYER_RES_STATUS_FREE = 0;
	public final static int PLAYER_RES_STATUS_BATTLE_DEF = 1;// 正在进行防守
	public final static int PLAYER_RES_STATUS_BATTLE_ATK = 2;// 正在进行进攻
	public final static int PLAYER_RES_STATUS_FAIL = 3;
	public final static int PLAYER_RES_STATUS_ATK_SUCC = 4;// 攻击成功
	public final static int PLAYER_RES_STATUS_PRODUCING = 5;
	public final static int PLAYER_RES_STATUS_ATK_PREPARE = 7;// 战斗准备中

	public final static int FRESH_PERCENT = 2;// 刷怪和刷宝箱百分比

	// public final static long BOX_FRESH_PERIOD = 1 * 60 * 1000;
	public final static int BOX_FRESH_PERIOD = 3 * 60 * 60;
	public final static int MONSTER_FRESH_PERIOD = 2 * 60 * 60;
	/** 最长战斗时间:10min */
	public final static long MAX_BATTLE_TIME = 10 * 60 * 1000;//

	// public final static long MONSTER_FRESH_PERIOD = 2 * 60 * 1000;
	// 资源点占有时间
	public final static int RES_OWN_PERIOD_HOUR = 12;
	public final static long RES_OWN_PERIOD = RES_OWN_PERIOD_HOUR * 60 * 60 * 1000;
	// 最后能被搜多到时间
	public final static long RES_HUNT_DEADLINE = RES_OWN_PERIOD - 10 * 60 * 1000;
	// 封山令时间
	public final static int RES_WHOSYOUDADDY = 12 * 60 * 60;

	public static final Pattern HERO_SPLIT_CHAR = Pattern.compile(";");
	public final static Pattern TEAM_HERO_SPLIT_CHAR = Pattern.compile(",");// 奖励具体类型分隔符
	public final static int STREET_BATTLE_REWARD_POWER = 2;// 防御成功奖励

	public final static int MONSTER_TYPE_GRID = 2;// 云雾下怪物
	public final static int MONSTER_TYPE_ROBOT = 1;// 机器人编队

	public final static double BATTLE_GAIN_EXP_PERCENT = 0.1;
	public final static double BATTLE_GAIN_MONEY_PERCENT = 0.1;
	private static final Object robot_save_mutex = new Object();
	int[] a;

	// /**
	// * 通过等级获得对应机器人<br/>
	// * 2014年7月22日
	// *
	// * @param level
	// * @param star星级
	// * 玩家等级
	// * @author dingqu-pc100
	// */
	// public static UserCached getRobotEnemy(int level, int star) {
	// return
	// STREET_ROBOTS.get(level).get(RandomUtil.randomInt(ROBOT_LEVEL_NUM)).get(star
	// - 1);
	// }

	public static UserCached getRobotEnemyByStar(int star) {
		int combat;
		switch (star) {
		case 1:
			combat = ROBOT_COMBAT_SMALL;
			break;
		case 2:
			combat = ROBOT_COMBAT_MID;
			break;
		case 3:
			combat = ROBOT_COMBAT_BIG;
			break;
		default:
			combat = ROBOT_COMBAT_SMALL;
			break;
		}
		List<UserCached> list = STREET_ROBOTS_MAP.get(combat);
		UserCached robot = new UserCached();
		BeanUtils.copyProperties(list.get(new Random().nextInt(list.size())), robot);
		return robot;
	}

	/**
	 * 通过playerId获得机器人 <br/>
	 * 2014年7月23日
	 * 
	 * @author dingqu-pc100
	 */
	public static UserCached getRobotById(int resId, int playerId) {
		return STREET_ROBOT_RESID_PID_MAP.get(resId).get(playerId);
	}

	public static void saveRobotCached(UserCached robot, int resId, int playerId) {
		synchronized (robot_save_mutex) {
			TIntObjectHashMap<UserCached> resMap = STREET_ROBOT_RESID_PID_MAP.get(resId);
			if (resMap == null) {
				resMap = new TIntObjectHashMap<UserCached>();
				STREET_ROBOT_RESID_PID_MAP.put(resId, resMap);
			}
			robot.setPlayerId(-playerId);
			robot.getPlayerVO().setPlayerId(-playerId);
			resMap.put(playerId, robot);
		}
	}

	/**
	 * 移除缓存的镜像机器人数据
	 * 
	 * @param resId
	 * @param playerId
	 */
	public static void removeRobotCache(int resId, int playerId) {
		TIntObjectHashMap<UserCached> resMap = STREET_ROBOT_RESID_PID_MAP.get(resId);
		if (resMap != null) {
			resMap.remove(playerId);
		}
	}

	public static UserCached createUserCache(BaseMonsterGroupVO monsterGroup) {

		UserCached userCached = new UserCached();
		// playervo
		PlayerVO playerVO = new PlayerVO();
		playerVO.setName(NameRes.getInstance().createRandomNick());
		playerVO.setHeadIcon(new Random().nextBoolean() ? 1 : 2);
		playerVO.setLevel(monsterGroup.getLevel());
		playerVO.setPlayerId(-monsterGroup.getId());
		playerVO.setBaseLevelVO(LevelRes.getInstance().getBaseLevelVO(playerVO.getLevel()));
		userCached.setPlayerVO(playerVO);
		userCached.setPlayerId(playerVO.getPlayerId());
		// hero

		List<BaseMonsterVO> heros = monsterGroup.getMonsters();
		UserHero userHero = new UserHero();
		List<PlayerHeroVO> heroList = new ArrayList<PlayerHeroVO>();
		int playerCombat = 0;
		int heroId = 1;
		for (BaseMonsterVO baseMonsterVO : heros) {
			PlayerHeroVO playerHeroVO = new PlayerHeroVO();
			playerHeroVO.setCfgId(baseMonsterVO.getCfgId());
			playerHeroVO.setId(heroId++);
			playerHeroVO.setBaseHeroInfoVO(transMToH(baseMonsterVO));
			playerHeroVO.setPlayerId(playerVO.getPlayerId());
			playerHeroVO.setId(userHero.getMaxHeroId());
			userHero.setMaxHeroId(userHero.getMaxHeroId() + 1);
			playerHeroVO.setLevel(baseMonsterVO.getLevel());
			playerHeroVO.setStar(baseMonsterVO.getStar());
			playerHeroVO.setName(baseMonsterVO.getName());

			playerHeroVO.setFinal_hp(baseMonsterVO.getHp());
			playerHeroVO.setFinal_def(baseMonsterVO.getDef());
			playerHeroVO.setFinal_mdef(baseMonsterVO.getMdef());
			playerHeroVO.setFinal_atk(baseMonsterVO.getAtk());
			playerHeroVO.setFinal_matk(baseMonsterVO.getMatk());
			playerHeroVO.setFinal_hit(baseMonsterVO.getHit());
			playerHeroVO.setFinal_dodge(baseMonsterVO.getDodge());
			playerHeroVO.setFinal_cir_rate(baseMonsterVO.getCir());
			playerHeroVO.setSkillLevel(1);
			playerHeroVO.setStar(playerHeroVO.getBaseHeroInfoVO().getStar());
			playerHeroVO.setCombat(CombatUtil.computerCombat(playerHeroVO.getFinal_hp(), playerHeroVO.getFinal_atk(), playerHeroVO.getFinal_def(), playerHeroVO.getFinal_matk(),
					playerHeroVO.getFinal_mdef(), playerHeroVO.getFinal_cir_rate(), playerHeroVO.getFinal_hit(), playerHeroVO.getFinal_dodge()));
			playerHeroVO.setSkillLevel(1);
			playerHeroVO.setLineStatus(CENTER_GRID_NUM);
			playerCombat += playerHeroVO.getCombat();
			heroList.add(playerHeroVO);
		}
		playerVO.setCombat(playerCombat);
		userHero.setHeroList(heroList);
		userCached.setUserHero(userHero);
		// userStreet
		UserStreet userStreet = new UserStreet();
		userStreet.setPlayerId(playerVO.getPlayerId());
		userStreet.setRobotFzId(monsterGroup.getArray());
		userStreet.setRobotMachineLine(monsterGroup.getMachine());

		userCached.setUserStreet(userStreet);
		// STREET_ROBOT_PLAYER_ID_MAP.put(userCached.getPlayerId(), userCached);
		return userCached;
	}

	public static void RobotsGenerator() {
		STREET_ROBOTS_MAP.clear();
		List<BaseMonsterGroupVO> robots = MonsterGroupRes.getInstance().getRobotMonsterList();
		List<UserCached> robots_small = new ArrayList<UserCached>();
		List<UserCached> robots_mid = new ArrayList<UserCached>();
		List<UserCached> robots_big = new ArrayList<UserCached>();
		UserCached userCached = null;
		for (BaseMonsterGroupVO baseMonsterGroupVO : robots) {
			int value = baseMonsterGroupVO.getStar();
			userCached = createUserCache(baseMonsterGroupVO);

			if (value == 1) {
				robots_small.add(userCached);
			} else if (value == 2) {
				robots_mid.add(userCached);
			} else if (value == 3) {
				robots_big.add(userCached);
			}
		}
		STREET_ROBOTS_MAP.put(ROBOT_COMBAT_SMALL, robots_small);
		STREET_ROBOTS_MAP.put(ROBOT_COMBAT_MID, robots_mid);
		STREET_ROBOTS_MAP.put(ROBOT_COMBAT_BIG, robots_big);

		// for (int i = STREET_OPEN_LEVEL; i <= ROBOT_TOTAL_LEVEL; i++) {
		// List<List<UserCached>> levelRobots = new
		// ArrayList<List<UserCached>>(ROBOT_LEVEL_NUM);
		//
		// for (int j = 0; j < ROBOT_LEVEL_NUM; j++) {
		// List<UserCached> starList = new
		// ArrayList<UserCached>(ROBOT_LEVEL_STAR_NUM);
		// for (int j2 = 0; j2 < ROBOT_LEVEL_STAR_NUM; j2++) {
		// }
		// levelRobots.add(starList);
		// }
		// STREET_ROBOTS.put(i, levelRobots);
		// }
	}

	// public static void RobotsFactory() {
	// STREET_ROBOTS.clear();
	// for (int i = STREET_OPEN_LEVEL; i <= ROBOT_TOTAL_LEVEL; i++) {
	// List<List<UserCached>> levelRobots = new
	// ArrayList<List<UserCached>>(ROBOT_LEVEL_NUM);
	//
	// for (int j = 0; j < ROBOT_LEVEL_NUM; j++) {
	// List<UserCached> starList = new
	// ArrayList<UserCached>(ROBOT_LEVEL_STAR_NUM);
	// for (int j2 = 0; j2 < ROBOT_LEVEL_STAR_NUM; j2++) {
	// UserCached userCached = new UserCached();
	// // playervo
	// PlayerVO playerVO = new PlayerVO();
	// playerVO.setName(NameRes.getInstance().createRandomNick());
	// playerVO.setHeadIcon(Math.random() > 0.5 ? 1 : 2);
	// playerVO.setLevel(i);
	// playerVO.setPlayerId(-(j2 * 10000 + j * 1000 + i));
	// playerVO.setBaseLevelVO(LevelRes.getInstance().getBaseLevelVO(playerVO.getLevel()));
	// userCached.setPlayerVO(playerVO);
	// userCached.setPlayerId(playerVO.getPlayerId());
	// // hero
	// BaseMonsterGroupVO monsterGroup =
	// MonsterGroupRes.getInstance().getRobotByLevelAndStar(i, j2 + 1);
	//
	// List<BaseMonsterVO> heros = monsterGroup.getMonsters();
	// UserHero userHero = new UserHero();
	// List<PlayerHeroVO> heroList = new ArrayList<PlayerHeroVO>();
	// int playerCombat = 0;
	// int heroId = 1;
	// for (BaseMonsterVO baseMonsterVO : heros) {
	// PlayerHeroVO playerHeroVO = new PlayerHeroVO();
	// playerHeroVO.setCfgId(baseMonsterVO.getCfgId());
	// playerHeroVO.setId(heroId++);
	// playerHeroVO.setBaseHeroInfoVO(transMToH(baseMonsterVO));
	// playerHeroVO.setPlayerId(playerVO.getPlayerId());
	// playerHeroVO.setId(userHero.getMaxHeroId());
	// userHero.setMaxHeroId(userHero.getMaxHeroId() + 1);
	// playerHeroVO.setLevel(baseMonsterVO.getLevel());
	// playerHeroVO.setStar(baseMonsterVO.getStar());
	// playerHeroVO.setName(baseMonsterVO.getName());
	//
	// playerHeroVO.setFinal_hp(baseMonsterVO.getHp());
	// playerHeroVO.setFinal_def(baseMonsterVO.getDef());
	// playerHeroVO.setFinal_mdef(baseMonsterVO.getMdef());
	// playerHeroVO.setFinal_atk(baseMonsterVO.getAtk());
	// playerHeroVO.setFinal_matk(baseMonsterVO.getMatk());
	// playerHeroVO.setFinal_hit(baseMonsterVO.getHit());
	// playerHeroVO.setFinal_dodge(baseMonsterVO.getDodge());
	// playerHeroVO.setFinal_cir_rate(baseMonsterVO.getCir());
	// playerHeroVO.setSkillLevel(1);
	// playerHeroVO.setStar(playerHeroVO.getBaseHeroInfoVO().getStar());
	// playerHeroVO.setCombat(CombatUtil.computerCombat(playerHeroVO.getHp(),
	// playerHeroVO.getAtk(), playerHeroVO.getDef(), playerHeroVO.getMatk(),
	// playerHeroVO.getMdef(),
	// playerHeroVO.getCir_rate(), playerHeroVO.getHit(),
	// playerHeroVO.getDodge()));
	// playerHeroVO.setSkillLevel(1);
	// playerHeroVO.setLineStatus(CENTER_GRID_NUM);
	// playerCombat += playerHeroVO.getCombat();
	// heroList.add(playerHeroVO);
	// }
	// playerVO.setCombat(playerCombat);
	// userHero.setHeroList(heroList);
	// userCached.setUserHero(userHero);
	// // userStreet
	// UserStreet userStreet = new UserStreet();
	// userStreet.setPlayerId(playerVO.getPlayerId());
	// userStreet.setRobotFzId(monsterGroup.getArray());
	// userStreet.setRobotMachineLine(monsterGroup.getMachine());
	//
	// userCached.setUserStreet(userStreet);
	// starList.add(userCached);
	// STREET_ROBOT_PLAYER_ID_MAP.put(userCached.getPlayerId(), userCached);
	// }
	// levelRobots.add(starList);
	// }
	// STREET_ROBOTS.put(i, levelRobots);
	// }
	// }

	public static BaseHeroInfoVO transMToH(BaseMonsterVO baseMonsterVO) {
		BaseHeroInfoVO baseHeroInfoVO = new BaseHeroInfoVO();
		baseHeroInfoVO.setName(baseMonsterVO.getName());
		baseHeroInfoVO.setUrl(baseMonsterVO.getUrl());
		baseHeroInfoVO.setRace(baseMonsterVO.getRace());
		baseHeroInfoVO.setAtk_range(baseMonsterVO.getAtk_range());
		baseHeroInfoVO.setSkill_id(baseMonsterVO.getSkill_id());
		baseHeroInfoVO.setAtk_speed(baseMonsterVO.getAtk_speed());
		baseHeroInfoVO.setStar(baseMonsterVO.getStar());
		baseHeroInfoVO.setCommon_atk(baseMonsterVO.getCommonatk());
		return baseHeroInfoVO;
	}

	/**
	 * 改格子是否可开启:东南西北有一个方向开启就可以<br/>
	 * dingqu-pc100<br/>
	 * 2014年7月17日
	 */
	public static boolean canBeReachable(int id, byte[] grids) {
		int row_num = id / StreetConstants.H_GRIDS_NUM;
		int row_remain = id % StreetConstants.H_GRIDS_NUM;

		int eid = id + 1;
		int sid = id + StreetConstants.H_GRIDS_NUM;
		int wid = id - 1;
		int nid = id - StreetConstants.H_GRIDS_NUM;
		int enid = nid + 1;
		int esid = sid + 1;
		int wsid = sid - 1;
		int wnid = nid - 1;
		boolean notEastest = row_remain != StreetConstants.H_GRIDS_NUM - 1;
		boolean notSouthest = row_num != StreetConstants.MAX_GRIDS_NUM / StreetConstants.H_GRIDS_NUM - 1;
		boolean notWestest = row_remain != 0;
		boolean notNorthest = row_num != 0;

		if (notEastest && grids[eid] == 1) {
			return true;
		}
		if (notSouthest && grids[sid] == 1) {
			return true;
		}
		if (notWestest && grids[wid] == 1) {
			return true;
		}
		if (notNorthest && grids[nid] == 1) {
			return true;
		}
		if (notNorthest && notEastest && grids[enid] == 1) {
			return true;
		}
		if (notEastest && notSouthest && grids[esid] == 1) {
			return true;
		}
		if (notSouthest && notWestest && grids[wsid] == 1) {
			return true;
		}
		if (notWestest && notNorthest && grids[wnid] == 1) {
			return true;
		}
		return false;
	}

	public static void calReachble(UserStreet userStreet) {
		byte[] grids = userStreet.getGrids();
		for (int i = 0; i < grids.length; i++) {
			boolean isReachble = canBeReachable(i, grids);
			if (isReachble) {
				userStreet.getReachbleGrids()[i] = 1;
			}
		}
	}

	public static boolean hasOvertime(int beginTime) {
		return (System.currentTimeMillis() - beginTime * 1000L) > RES_OWN_PERIOD;
	}

	public static boolean hasBattleOverTime(int battleTime) {
		return (System.currentTimeMillis() - battleTime * 1000L) > MAX_BATTLE_TIME;
	}

	public static boolean hasDaddyInTime(int beginTime) {
		return (System.currentTimeMillis() - beginTime * 1000L) < RES_WHOSYOUDADDY * 1000L;
	}

	public static void main(String[] args) {
		NameRes.getInstance().loadFile(NameRes.Path);
		MonsterRes.getInstance().loadFile(MonsterRes.Path);
		MonsterGroupRes.getInstance().loadFile(MonsterGroupRes.Path);
		RobotsGenerator();

		UserCached userCached = getRobotEnemyByStar(2);
		List<PlayerHeroVO> heros = userCached.getUserHero().getHeroList();
		for (PlayerHeroVO playerHeroVO : heros) {
			System.out.println(playerHeroVO.getCombat());
		}
		System.out.println(userCached.getPlayerId());

	}

}
