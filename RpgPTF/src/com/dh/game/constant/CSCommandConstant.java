package com.dh.game.constant;

public interface CSCommandConstant {
	short ALERT_COMMAND = 900,// 消息提示
			/** ---------登录注册 [1000~1099]--------- * */
			USER_LOGIN = 1000, // 用户登录
			USER_RELOGIN = 1002, // 用户重连
			USER_LOGOUT = 1005, // 用户离线
			USER_CREATE = 1010,// 用户创建
			GET_ROLENAME = 1020,// 获取随机角色名
			CHECK_ROLENAME = 1030,// 判断尼称是否存在
			LOGIN_GUIDE_UP = 1031,// 新手引导

			HEARTBEAT = 1050, // 心跳包

			ICON_STATUS_LIST = 1060, // 主页面图标
			// SYS_APPEND_REGISTERROLE = 1080, // 补充注册
			/** ----1100-1099-------------------------------- **/
			GM_RELOAD_REDIS = 1100,// 重新加载redis
			GM_SEND_SYS_MAIL = 1101,// 发送邮件
			GM_CHECK_ITEM_EXIST = 1102,// 检查物品是否存在
			GM_BLACK_PLAYER = 1103,// 将玩家添加到黑名单
			RELOAD_CSV = 1104, // 重新加载配置文件
			GM_BLACK_IP = 1105,// 封锁ip
			SYS_STOP = 1108, // 暂停系统
			GM_SEND_SYS_NOTICE = 1109,// 系统通知
			GM_SEND_SYS_MSG = 1110,// 发送系统消息

			/** ------ 同步部分属性信息[1200-1299]----- */
			CMD_UPDATE_PLAYER = 1200,// 更新角色信息
			CMD_UPDATE_PROPERTY = 1240,// 更新角色英雄士兵 某些属性 信息
			CMD_UPDATE_CITY_OPEN = 1250,//
			CMD_UPDATE_POWER = 1261,// 客户端定时请求玩家最新体力

			UPDATE_MYKNAPSACK = 1270, // 刷新背包格子信息
			OPEN_MYKNAPSACK = 1272, // 开启背包格子

			/** ----同部背包信息[1300-1399]------ */
			KNAPSACK_UPDATE = 1300, // 更新背包数据

			/** -----系统刷新任务模块[1400-1499]---- */
			SYS_SHOP_REFRESH = 1401, // 商店刷新
			SYS_BOSS_FRESH = 1402,// boss刷新
			SYS_HEROHANG_REFRESH = 1403, // 英雄挂机主动回到队列
			SYS_POWER_FRESH = 1404, // 英雄挂机主动回到队列
			SYS_ARENA_RANK3 = 1405,// 竞技场排名前三通知
			SYS_TIPS_NOTICE = 1406,// 小提示,跑马灯
			SYS_GM_NOTICE = 1407,// gm公告
			SYS_ACTY_FRESH = 1408,// 活动timer
			SYS_APPEND_REGISTERROLE = 1480, // 补充注册
			SYS_RELOAD_SCRIPT = 1490, // 重新加载任务脚本

			/** ---- common 通用命令码[1500-1599]---- */
			BRIEF_INFO = 1500,// 宗主信息
			COUNT_DOWN_BOX = 1501,// 倒计时框
			COUNT_DOWN_UP = 1502,// 更新倒计时
			COMMMON_TEST = 1599,//

			/** ----------活动[1600-1699]------------------ */
			ACTIVITY_DAY_SIGN_DETAILS = 1600,// 获取每日签到详情
			ACTIVITY_DAY_SIGN = 1601,// 签到当天
			ACTIVITY_DAY_REWARD = 1602,// 领取奖励

			ACTIVITY_WORLD_BOSS = 1610,// 世界boss
			ACTIVITY_BOSS_DETAIL = 1611,// boss详情
			ACTIVITY_BOSS_CD_ACCR = 1612,// 复活加速
			ACTIVITY_BOSS_EXIT = 1613,// 退出世界boss
			ACTIVITY_BOSS_ADDTION = 1614,// 增加号令值
			ACTIVITY_BOSS_BATTLE_UPDATE = 1615,// 战斗更新后
			ACTIVITY_BOSS_OVER = 1616,// boss开始或者结束
			ACTIVITY_BOSS_NOTICE = 1617,// 被玩家攻打后通知{如果在前10带huntlogInfo,否则只更新血量}
			ACTIVITY_DAY_CHARGE = 1618,// 每日充值
			ACTIVITY_DAY_CHARGE_REWARD = 1619,// 每日回包
			ACTIVITY_CHARGE_LIST = 1620,// 每日充值
			ACTIVITY_DAY_DONE = 1621,// 每日必做
			ACTIVITY_DAY_DONE_REWARD = 1622,// 领取每日必做奖励
			ACTIVITY_DAY_DONE_UPDATE = 1623,// 积分变化后更新玩家积分,前台显示可领取状态

			ACTIVITY_DAY_SHARE = 1630, // 每日分享

			ACTIVITY_STATUS_NOTICE = 1640,// 活动状态通知,int的二进制
			ACTIVITY_ANS_NEXT = 1641,// 获得下一题
			ACTIVITY_ANS_CHECK = 1642,// 提交答案
			ACTIVITY_ANS_SKILL = 1643,// 使用技能

			/** -------CDKey[1700-1799] ***/
			CDKEY_ENTER = 1700, // 输入cdkey

			/** ------ 消息和邮件[2000-2099]----- */
			CMD_MAIL_SEND = 2000,// 发送邮件
			CMD_MAIL_LIST = 2001,// 获取邮件列表
			CMD_MAIL_MARK_READ = 2002,// 修改邮件状态
			CMD_MAIL_REWARD = 2010, // 提取邮件奖励
			CMD_MAIL_DEL = 2011, // 删除邮件
			CMD_MAIL_ATTACHMENT = 2012, // 获得附件列表
			CMD_MAIL_NEW_MAIL = 2013, // 新邮件提醒
			CMD_MAIL_REWARD_ALL = 2014, // 一键提取

			CMD_CHAT = 2103,// 聊天
			CMD_GAG_CHAT = 2104,// 屏蔽发言
			CMD_USE_PROPS = 2105, // 使用道具
			PUSH_SYS_MSG = 2106,// system message
			CMD_SYS_MSG = 2107, // 系统消息
			CMD_FRIEND_CHAT = 2108,// 私聊
			CMD_MESSAGE_LIST = 2109, // 消息获取

			FRIEND_LIST = 2200,// 好友列表,最近联系人,黑名单
			FRIEND_MOD = 2201,// 修改好友

			/** -------帮会相关------ */
			LEGION_CREATE = 3000,// 创建帮派
			LEGION_LIST = 3001,// 获得帮派列表
			LEGION_HOME = 3002,// 获得本帮派详情
			LEGION_PUSH_INFO = 3003,// 登录&&创建成功后push
			LEGION_EDIT = 3004,// 编辑军团基本信息
			LEGION_DESTROY = 3005,// 解散血盟
			LEGION_REQ_NOTICE = 3006,// 有申请成员时给管理人员通知

			LEGION_JION = 3010,// 加入申请
			LEGION_MEM_LIST = 3011,// 成员列表
			LEGION_JION_LIST = 3012,// 申请列表

			LEGION_MEM_MANAGE = 3020,// 成员管理

			LEGION_DONATE = 3021,// 捐献
			LEGION_REWARD_MEM = 3022,// 领取个人帮贡礼包
			LEGION_REWARD_DAY = 3023,// 领取帮会礼包
			LEGION_SHOP_BUY = 3030,// 帮会购买
			LEGION_LOGS = 3040,// 帮会日志
			// 帮派活动相关
			LEGION_BOSS_DETAIL = 3050,// 帮派boss详情
			LEGION_BOSS_REWARD = 3051,// 帮派boss领奖
			LEGION_BOSS_CD_ACCR = 3052,

			/** -----英雄士兵升级[3500-3599] ----- **/
			HERO_RECRUIT_PAGE = 3500, // 进入招蓦页
			HERO_RECRUIT = 3502, // 招蓦
			HERO_RECRUIT_SHOW = 3504, // 招蓦显示(发给前台)

			HERO_LEVEL_UP = 3550, // 英雄升级

			HERO_STAR_UP = 3560, // 英雄升星
			HERO_STAR_UP_PRE = 3562, // 英雄预览升星

			HEROTOEXP = 3570, // 英雄转经验
			HEROTOPREEXP = 3572, // 预驱逐

			HERO_UPDATE_HERO = 3590,// 更新英雄
			HERO_INSERT_HERO = 3592,// 新增英雄
			HERO_DELETE_HERO = 3594,// 删除英雄

			/** ----英雄休息（疲劳值）[3600-3699] -- **/
			HERO_HUNGRY_DETAIL = 3600,// 列表
			HERO_HUNGRY_SLEEP = 3602,// 休息
			HERO_QUICK_SLEEP = 3606,// 加速英雄疲劳冷确
			HERO_HUNGRY_BACK = 3608,// 返回英雄队列
			WATER_REFRESH = 3610, // 刷新挂机泉水

			/** ----英雄技能[3700-3799] -- **/
			HERO_SKILL_UPLEVEL = 3700,// 主动技能升级
			HERO_SKILL_REFRESH = 3704,// 被动技能刷新
			HERO_SKILL_REPLACE = 3706,// 被动技替换

			/** -- 副本[4000-4099]--- **/
			RAID_DETAIL = 4002, // 副本详述
			RAID_UPDATE_HEROTEAM = 4004, // 修 改英雄列
			RAID_START = 4010, // 开始副本
			RAID_END = 4020, // 副本完成

			RAID_CHAPTER_LIST = 4030,// 章列表
			RAID_PROGRESS = 4034, // 副本进度

			OPENBOX = 4040,// 翻牌

			RAID_UI_INFO = 4050, // 精英副本可战斗次数
			RAID_ELICHAPTER_LIST = 4052,// 精英副本 章列表
			RAID_ELI_PROGRESS = 4054,// 副本进度
			RAID_FIVEREWARD = 4056,// 领取首次过关奖励
			RAID_BUY_TIMES = 4060,// 购买精英副本次数
			RAID_SCORE_REWARD = 4070, // 累积星级奖励
			RAID_SCORE_CATOON = 4072, // 5星动画

			RAID_ME_DETAIL = 4080,// 金钱经验副本
			RAID_ME_ADDCOUNT = 4081,// 增加经验银两副本可攻打次数
			RAID_ME_ACCR_CD = 4082,// 加速战斗冷却

			/*-----扫荡  －[4200－4299]－－－－－－－ */

			RAID_CLEAN_OUT = 4200, // 扫荡
			RAID_CLEAN_OUTALL = 4202, // 一次扫荡

			/** ----- 场影移动----[5000-5099]------ */
			AREA_OPERATION = 5000, // 场景操作
			AREA_BROACAST_ENTER = 5010, // 下行进入场景广播
			AREA_BROACAST_MOVE = 5020, // 下行 移动 广播
			AREA_BROACAST_LEAVE = 5030, // 下行离开场景广播
			/** 领地: */
			BUILD_YISIT_LIST = 6000,// 议室堂
			BUILD_YISIT_SALART = 6010,// 领薪
			BUILD_UP_LEVEL = 6040, // 建筑升级
			BUILD_UP_LEVEL_ACC = 6050, // 建筑升级加速　
			// vip每日礼包
			DAYVIPGIFT = 6080, // vip每日奖励信息
			GET_DAYVIP = 6082, // 令取每日vip奖励
			CLEARSALARYCD = 6084, // 清理俸禄cd

			/** ------------江 湖[6000-6999]-------- ---- */
			STREET_HOME = 6100,// 江湖主界面
			STREET_OPEN = 6101,// 开启云团
			STREET_GRID_FRESH = 6102,// 格子刷新
			STREET_RES_BASEINFO = 6103,// 资源点基本信息
			STREET_HUNT = 6110,// 搜索对手

			STREET_DEFEND = 6120,// 驻守
			STREET_DEFEND_TEAM = 6121,// 获得当前驻守队伍
			STREET_QUIT_DEFEND = 6122,// 放弃驻守

			STREET_FACTORY = 6130,// 打开军器坊
			STREET_FACTORY_MAKE = 6131,// 制造
			STREET_FACTORY_UP = 6132,// 升级
			STREET_LOG_BRIEF = 6140,// 简报
			STREET_LOG_BATTLE = 6141,// 战报
			STREET_LOG_ENEMY = 6142,// 仇家
			STREET_LOG_REWARD = 6143,// 领取战报奖励
			STREET_LOG_BATTLE_UPDATE = 6144,// 更新战报

			STREET_LOG_ENEMY_UPDATE = 6145,// 复仇后更新仇家
			STREET_ENEMY_CENTER = 6146,// 获得敌对玩家门派信息
			STREET_RES_INFO = 6147,// 获取资源点信息

			STREET_RES_REWARD = 6150,// 领取资源点奖励,按类型
			STREET_BOX_REWARD = 6151,// 开启宝箱
			STREET_RES_REWARD_ALL = 6152,// 一键丰收
			STREET_GRID_UPDATE = 6160,// 更新格子信息
			STREET_GRID_ATK_UPDATE = 6161,// 资源点被攻击刷新

			/** -----------物品,装备强化----------- */
			HERO_ON_EQUIP = 7001, // 穿装备
			HERO_OFF_EQUIP = 7002, // 脱装备
			ENHANCE_EQUIP = 7050, // 装备强化
			ENHANCE_EQUIP_EASY = 7052, // 一键强化
			UPSTAR_EQUIP = 7055, // 装备升星
			COMPOSE_ITEM = 7060, // 合成物品

			/** -----------[装备镶嵌7100-7199] --------- */
			GEMSET_ITEM = 7100, // 宝石镶嵌
			GEMPICK_ITEM = 7102, // 宝石摘
			OPEN_HOLE = 7110, // 开孔

			PROTECT_ITEM = 7130,// 装备护佑

			/** ----- 背包商城[8000-8099]----- */
			ITEM_SELL = 8000,// 物品出售
			ITEM_USE = 8001,// 物品使用
			ITEM_SPLIT = 8002,// 物品拆分
			SHOP_GOLD_BUY = 8050,// 金币列表
			SHOP_BUY_POWER = 8051,// 购买体力

			SHOP_ITME_BUY = 8060,// 购买物品请求
			SHOP_LIST = 8070, // 商品列表(折扣商品)和剩余刷新时间
			SHOP_EXPLOIT_LIST = 8072, // 功勋商店 列表
			SHOP_EXPLOIT_RESET = 8074, // 功勋商店 重置

			/** ----- 竞技场[8100-8199]----- */
			ARENA_DETAIL = 8100, // 竞技场详情
			ARENA_LOOK_TEAM = 8120, // 查看对手阵容
			ARENA_REFRESH_ENEM = 8130, // 主动刷新对手
			ARENA_CD_ENEM = 8132,// 刷新对手CD清0
			ARENA_CD_FAIL = 8134,// 战斗CD清0
			ARENA_UPDATETEAM = 8140, // 更新防守阵容
			ARENA_REWARD = 8150, // 领取竞技场奖励
			ARENA_BATTLE_RECORD = 8160, // 查看战报
			ARENA_SHOP_PVP = 8170, // 增加竞技值
			ARENA_RANK_LIST = 8180, // 竞技场排行榜

			/** ----夺宝[8300-8399] ------- */
			DUOBAO_LIST = 8300, // 夺宝列表
			DUOBAO_HUAN = 8302, // 换一批对手

			/** --远证[8400-8499]------ */
			YUZHENUI = 8400, // 进入远证
			YUZHENDETAIL = 8410, // 远征层数据信息
			REFRESHYUANZHEN = 8420, // 刷新远征
			YUANZHEN_STATISTICS = 8428, // 远征统计结算

			/** -- 多人副本[8500-8599]--------- **/
			CREATE_MANYPEOPLE_TEAM = 8500,// 创建队伍
			LEAVE_MANYPEOPLE_TEAM = 8502,// 离开队伍
			JOIN_MANYPEOPLE_TEAM = 8504,// 加入队伍
			KICKOUT_MANYPEOPLE_TEAM = 8506,// 踢人
			LIST_MANYPEOPLE_TEAM = 8508,// 队伍列表

			/** -----------任务----------- */
			TASK_RECEIVE = 9000, // 接任务
			TASK_UPDATE = 9001, // 改变任务的状态
			TASK_REWARD = 9003, // 交任务

			/** --------福利大厅[9200-9299]------ */
			WELFARE_LIST = 9200, // 进入福利大厅
			WELFARE_REWARD = 9202, // 邻取福利大厅奖励
			REWARD_ONLINE = 9203, // 领取在线奖励
			REWARD_ONLINE_DETAIL = 9204, 
			REWARD_FEEDBACK_VIP = 9206,// 领取vip反馈礼金
			GET_FEEDBACK_INFO = 9208,// 获取反馈礼金信息
			
			GET_ONE_RECHARGE_DETAIL = 9230,//单笔充值奖励
			GET_OPENACTIVY_DETAIL = 9234,//开服活动列表及领取
			/** --------充值[9800-9899]------- */
			RECHARGE_RMB = 9800,//
			RECHARGE_TEST = 9888, // 充值测试用
			GET_YELLOW_TOKEN = 9810,// 获取黄钻tokdn
			GET_YELLOW_NEW_GIFT = 9820, // 领取黄钻新手礼包
			GET_YELLOW_DAY_GIFT = 9822, // 每日黄钻礼包
			GET_YELLOW_LEVEL_GIFT = 9824, // 黄钻成长礼包
			GET_YELLOW_DETAIL = 9828, // 黄钻礼包信息
			SHOP_GOLDINGOT_REQ = 9808; // 购买元宝 获取urlparams

}
