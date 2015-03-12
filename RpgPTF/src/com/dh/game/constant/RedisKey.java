package com.dh.game.constant;

public class RedisKey {
	// sql放在redis里
	public final static String MAP_SQL = "MAP_SQL";

	// 预判值
	public final static String HAVE_BATTLE_RECORED = "HAVE_BATTLE_RECORED";// 是否已经载入了战报

	// playervo
	public final static String PLAYERVO_MAP = "PLAYERVO_MAP"; // playerVO 用户基本信息
	public final static String NAME_MAP = "NAME_MAP"; // accountMap 用户基本信息
	public final static String NICK_MAP = "NICK_MAP"; // 尼称

	// playerAccountVO
	public final static String PLAYER_ACCOUNT_MAP = "P_A_C"; // accountMap
																// 用户基本信息

	// 竞技场
	public final static String HERO_DEF_MAP = "HERO_DEF_MAP";// 竞技场英雄数据
	public final static String ARENA_MAP = "ARENA_MAP"; // 竞技场排名数据 map 名
	public final static String BATTLE_RECORED = "BATTLE_RECORED_";// 战报

	// 消息系统
	public final static String MSG_NOTICE_STREET = "M" + "_" + "STREET";

	// 排行
	public final static String ARENA_SORTLIST = "ARENA_SORTLIST"; // 竞技场排行
	public final static String PLAYERLEVEL_SORTLIST = "PLAYERLEVEL_SORTLIST"; // 角色等级排行

	// 游戏重启后:数据库数据还原
	public final static String MAX_PLAYER_ID = "MAX_PLAYER_ID";

	// 玩家江湖资源点[StreetResVO]playerStreetResVO
	public final static String PLAYER_STREET_RES = "P_S_R";//
	// playerHero
	public final static String PLAYER_HERO = "P_H";
	// 空闲玩家
	public final static String STREET_RES_FREE_PLAYER = "R_F_P";
	// 战斗状态玩家
	public final static String STREET_RES_BATTLE_PLAYER = "R_B_P";
	// 生产状态玩家
	public final static String STREET_RES_PRODUCE_PLAYER = "R_P_P";

	public final static String PLAYER_MAIL_ID = "P_MAIL_ID";

	public final static String GM_MAX_LOGIN_TODAY = "G_M_L_T";

}
