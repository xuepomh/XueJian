package com.dh.processor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.MyOpenApiV3;
import com.dh.Cache.RedisMap;
import com.dh.Cache.ServerHandler;
import com.dh.constants.GameRecordConstants;
import com.dh.constants.PlatformConstant;
import com.dh.constants.TXConstants;
import com.dh.dao.PlayerAccountMapper;
import com.dh.exception.GameException;
import com.dh.game.constant.AlertEnum;
import com.dh.game.constant.CSCommandConstant;
import com.dh.game.vo.base.BaseItemVO;
import com.dh.game.vo.base.BaseShopVO;
import com.dh.game.vo.base.BaseWelfareVO;
import com.dh.game.vo.base.DymicGiftVO;
import com.dh.game.vo.base.GameRecordVO;
import com.dh.game.vo.base.Reward;
import com.dh.game.vo.base.YellowRewardVO;
import com.dh.game.vo.gm.GMProto.RechargeTestReq;
import com.dh.game.vo.gm.GMProto.RechargeTestResp;
import com.dh.game.vo.gm.GMProto.RechargeTestResp.Builder;
import com.dh.game.vo.item.YunZiFuProto.GetYellowToken;
import com.dh.game.vo.item.YunZiFuProto.GoldIngotsRequest;
import com.dh.game.vo.item.YunZiFuProto.GoldIngotsResponse;
import com.dh.game.vo.item.YunZiFuProto.YellowLevelGiftRequest;
import com.dh.game.vo.login.PlayerLoginProto.PLAYER_PROPERTY;
import com.dh.game.vo.recharge.RechargeProto.RechargeRequest;
import com.dh.game.vo.user.PlayerAccountVO;
import com.dh.game.vo.user.PlayerTimerVO;
import com.dh.game.vo.user.PlayerVO;
import com.dh.game.vo.user.PlayerWelfareVO;
import com.dh.game.vo.user.RechargeInfoVO;
import com.dh.netty.NettyMessageVO;
import com.dh.resconfig.ItemRes;
import com.dh.resconfig.OneRechargeRes;
import com.dh.resconfig.ShopRes;
import com.dh.resconfig.VipLevelRes;
import com.dh.resconfig.WelfareRes;
import com.dh.resconfig.YellowRewardRes;
import com.dh.service.ActivityService;
import com.dh.service.BaseInfoService;
import com.dh.service.ChatService;
import com.dh.service.PlayerAccountService;
import com.dh.service.PlayerService;
import com.dh.service.PlayerTimerService;
import com.dh.service.RechargeService;
import com.dh.service.RewardService;
import com.dh.service.WelfareService;
import com.dh.sqlexe.SqlSaveThread;
import com.dh.sync.LockConstant;
import com.dh.sync.SyncLock;
import com.dh.sync.SyncUtil;
import com.dh.util.CommandUtil;
import com.dh.util.SqlBuild;
import com.dh.vo.user.UserCached;
import com.qq.open.vo.YellowTokenVO;

@Component
public class RechargeProcessor {
	private final static Logger LOGGER = Logger.getLogger(RechargeProcessor.class);
	@Resource
	private PlayerAccountService playerAccountService;
	@Resource
	private PlayerService playerService;
	@Resource
	private PlayerAccountMapper playerAccountMapper;
	@Resource
	private WelfareService welfareService;
	@Resource
	private BaseInfoService baseInfoService;
	@Resource
	private SqlBuild sqlBuild;
	@Resource
	private SqlSaveThread sqlSaveThread;
	@Resource
	private RechargeService rechargeService;
	@Resource
	private ActivityService activityService;
	@Resource
	private PlayerTimerService playerTimerService;
	@Resource
	private RewardService rewardService;

	public final static String SUB_SPLITOR = "%,";
	public final static String SPLITOR = "%;";

	public void rechargeTest(RechargeTestReq req, NettyMessageVO nettyMessageVO, List<NettyMessageVO> commandList) throws Exception {
		List<PlayerVO> playerVOs = playerService.getPlayersByUser(req.getUsers());
		Builder resp = RechargeTestResp.newBuilder().setGolding(req.getGolding());
		if (playerVOs.isEmpty()) {
			return;
		}
		if (req.getVerifyCode().getCode() == null || !req.getVerifyCode().getCode().equals("aDmin")) {// 验证
			return;
		}
		StringBuilder nameBuild = new StringBuilder();
		for (PlayerVO playerVO : playerVOs) {
			try {
				this.recharge(playerVO.getPlayerId(), req.getGolding());
				nameBuild.append(playerVO.getName()).append(SUB_SPLITOR).append(playerVO.getUserName()).append(SUB_SPLITOR).append(playerVO.getPlayerId()).append(SPLITOR);
			} catch (Exception e) {
				LOGGER.error(playerVO.getPlayerId() + "充值GM失败", e);
			}
		}
		resp.setResult(nameBuild.toString());
		nettyMessageVO.setData(resp.build().toByteArray());
		commandList.add(nettyMessageVO);
	}

	public void dymicGiftProcessor(boolean isAddNewVIP, int newVip, int oldVip, int playerId, String nick, UserCached userCached, int rechargeAmount, List<NettyMessageVO> commandList) {
		if (isAddNewVIP && newVip > 0) {
			for (int i = oldVip + 1; i <= newVip; i++) {
				List<BaseWelfareVO> baseWelfareVOList = WelfareRes.getInstance().getHuangGuaGifByVip(i);
				boolean isSendSysMsg = false;
				if (baseWelfareVOList != null) {
					for (BaseWelfareVO baseWelfareVO : baseWelfareVOList) {
						// 添加黄瓜仙子的礼包
						DymicGiftVO dymicGiftVO = new DymicGiftVO();
						dymicGiftVO.setNick(nick);
						dymicGiftVO.setPlayerId(playerId);
						dymicGiftVO.setVip(i);
						dymicGiftVO.setState(baseWelfareVO.getNum());
						welfareService.addDymicGiftName(dymicGiftVO);
					}

					ChatService.sendSysMsg(ChatService.sendVipGiftPack(nick, newVip), ChatService.TAGS[0]);
					isSendSysMsg = true;
				}
				if (!isSendSysMsg && newVip >= 4) {// 4级以上发送
					ChatService.sendSysMsg(ChatService.sendVipUpdate(nick, newVip), ChatService.TAGS[0]);
				}

				updateVIPNUM(GameRecordConstants.VIP1NUM, i, 1);
				updateVIPNUM(GameRecordConstants.VIP4NUM, i, 4);
				updateVIPNUM(GameRecordConstants.VIP7NUM, i, 7);
				updateVIPNUM(GameRecordConstants.VIP8NUM, i, 8);
				updateVIPNUM(GameRecordConstants.VIP9NUM, i, 9);
				updateVIPNUM(GameRecordConstants.VIP10NUM, i, 10);
			}

			CommandUtil.packPlayerVIP(userCached, commandList);
		}

		if (userCached != null) {
			activityService.dayChange(rechargeAmount, userCached, commandList); // 每日充值
			activityService.addChangeList(userCached, rechargeAmount); // 晒充值排行榜
			activityService.addDayDone(userCached, 4, commandList);// 每日必做
		}
	}

	public void updateVIPNUM(int type, int vip, int finishvip) {
		if (vip == finishvip) {
			GameRecordVO gameRecordVO = GameRecordConstants.getGameRecordVO(type);
			switch (type) {
			case GameRecordConstants.VIP1NUM:
				gameRecordVO.setValue1(GameRecordConstants.VIP1NUM_VALUE.incrementAndGet());
				break;
			case GameRecordConstants.VIP4NUM:
				gameRecordVO.setValue1(GameRecordConstants.VIP4NUM_VALUE.incrementAndGet());
				break;
			case GameRecordConstants.VIP7NUM:
				gameRecordVO.setValue1(GameRecordConstants.VIP7NUM_VALUE.incrementAndGet());
				break;
			case GameRecordConstants.VIP8NUM:
				gameRecordVO.setValue1(GameRecordConstants.VIP8NUM_VALUE.incrementAndGet());
				break;
			case GameRecordConstants.VIP9NUM:
				gameRecordVO.setValue1(GameRecordConstants.VIP9NUM_VALUE.incrementAndGet());
				break;
			case GameRecordConstants.VIP10NUM:
				gameRecordVO.setValue1(GameRecordConstants.VIP10NUM_VALUE.incrementAndGet());
				break;
			}

			baseInfoService.updateGameRecordVO(gameRecordVO);
		}
	}

	public void recharge(RechargeRequest request, NettyMessageVO nettyMessageVO) throws Exception {
		recharge(request.getPlayerId(), request.getNumber());
	}

	public void recharge(int playerId, int goldIngot) throws Exception {

		List<NettyMessageVO> commandList = new ArrayList<NettyMessageVO>();
		UserCached userCached = ServerHandler.getUserCached2(playerId);
		int rechargeAmount = goldIngot;

		boolean isAddNewVIP = false;
		int newVip = 0;
		int oldVip = 0;
		String nick = "";
		if (userCached != null) {
			playerAccountService.recharge(PLAYER_PROPERTY.PROPERTY_RMB, rechargeAmount, true, userCached.getPlayerAccountVO(), commandList, "充值");
			newVip = VipLevelRes.getInstance().getVipLevel(userCached.getPlayerAccountVO().getHisrecharge());
			oldVip = userCached.getPlayerVO().getVip();

			if (newVip > oldVip) {
				SyncLock syncLock = SyncUtil.getInstance().getLock(LockConstant.LOCK_PLAYERVO + playerId);
				synchronized (syncLock) {
					PlayerVO playerVO = userCached.getPlayerVO();
					playerVO.setVip(newVip);
					playerService.updatePlayerVO(playerVO);
				}
				isAddNewVIP = true;
				nick = userCached.getPlayerVO().getName();
			}
		} else {
			SyncLock syncLock = SyncUtil.getInstance().getLock(LockConstant.LOCK_PLAYERVO + playerId);
			newVip = 0;
			PlayerAccountVO playerAccountVO = null;
			synchronized (syncLock) {
				playerAccountVO = RedisMap.getPlayerAccount(playerId);
				if (playerAccountVO == null) {
					playerAccountVO = playerAccountMapper.getPlayerAccount(playerId);
					RedisMap.updatePlayerAccount(playerAccountVO);
				}
				playerAccountVO.setGmRmb(playerAccountVO.getGmRmb() + rechargeAmount);
				playerAccountVO.setHisrecharge(playerAccountVO.getHisrecharge() + rechargeAmount);
				RedisMap.updatePlayerAccount(playerAccountVO);
				playerAccountService.updatePlayerAccountRMB(playerAccountVO);
			} // endlock

			newVip = VipLevelRes.getInstance().getVipLevel(playerAccountVO.getHisrecharge());
			syncLock = SyncUtil.getInstance().getLock(LockConstant.LOCK_PLAYERVO + playerId);
			synchronized (syncLock) {
				PlayerVO playerVO = RedisMap.getPlayerVOById(playerId);
				oldVip = playerVO.getVip();
				if (newVip > oldVip) {
					playerVO.setVip(newVip);
					nick = playerVO.getName();
					playerService.updatePlayerVO(playerVO);
					isAddNewVIP = true;
				}
			}
		} // end else

		dymicGiftProcessor(isAddNewVIP, newVip, oldVip, playerId, nick, userCached, rechargeAmount, commandList);
		rechargeRelea(playerId, goldIngot);
		if (commandList != null) {
			ServerHandler.sendMessageToPlayer(commandList, playerId);
			commandList.clear();
		}

	}

	public void rechargeRelea(int playerId, int goldIngot) {
		int index = OneRechargeRes.getInstance().findRewardId(goldIngot);
		if (index < 0) {
			return;
		}
		UserCached userCached = ServerHandler.getUserCached2(playerId);

		try {
			if (userCached == null) {
				// 暂不处理，只能在线充值
			} else {
				if (!PlatformConstant.isTXPlatform(userCached.getPlayerVO().getBchannel())) {
					return;
				}

				PlayerWelfareVO playerWelfareVO = userCached.getUserActivity().getPlayerWelfareVO();
				playerWelfareVO.setRecharge(index, playerWelfareVO.getRecharge(index) + 1);
				welfareService.updatePlayerWelfareVO(playerWelfareVO);
			}

		} catch (Exception e) {
			LOGGER.error("rechargeRelea error", e);
		}
	}

	/**
	 * 请求购买元宝 获取urlparams
	 * 
	 * @param request
	 * @param nettyMessageVO
	 * @param commandList
	 * @throws Exception
	 */
	public void getUrlParameter(GoldIngotsRequest request, NettyMessageVO nettyMessageVO, List<NettyMessageVO> commandList) throws Exception {
		BaseShopVO baseShopVO = ShopRes.getInstance().getMoneyBaseShopVO(request.getSerialId());
		if (baseShopVO == null) {
			throw new GameException(AlertEnum.ITEM_NOT_FOUND);
		}

		BaseItemVO baseItemVO = ItemRes.getInstance().getQQGoldIngotByCfgId(baseShopVO.getItem_id());
		if (baseItemVO == null) {
			throw new GameException(AlertEnum.ITEM_NOT_FOUND);
		}

		int playerId = ServerHandler.get(nettyMessageVO.getChannel());
		UserCached userCached = ServerHandler.getUserCached(playerId);
		PlayerVO playerVO = userCached.getPlayerVO();

		// playerVO.setUserName("12E5ECA5E6CA8BD6B910775FA40DFAFF");
		// playerVO.setPassword("66987F0305AE684F2521AAE8476B49A2");
		// playerVO.setPfkey("b1ef00f824603990223a45a245f67531");f
		System.err.println("-=======gettoken==============");
		System.err.println("playerVO.getUserName() = " + playerVO.getUserName());
		System.err.println("playerVO.getPassword() = " + playerVO.getPassword());
		System.err.println("playerVO.getPfkey() = " + playerVO.getPfkey());
		System.err.println("playerVO.getPf() = " + playerVO.getBchannel());
		String detail = MyOpenApiV3.exchange_goods(playerVO.getUserName(), playerVO.getPassword(), playerVO.getPfkey(), "" + baseItemVO.getCfgId(), baseItemVO.getName(), "" + baseItemVO.getUrl(),
				baseItemVO.getDesc(), TXConstants.tx_zone_id, baseShopVO.getPrice(), request.getCount(), playerVO.getBchannel());

		JSONObject jo = new JSONObject(detail);
		Integer ret = Integer.valueOf(jo.get("ret").toString());
		if (ret == 0) {
			String urlParams = jo.get("url_params").toString();
			String tokenid = jo.get("token").toString();

			RechargeInfoVO rechargeInfoVO = new RechargeInfoVO();

			rechargeInfoVO.setTokenid(tokenid);
			rechargeInfoVO.setGoldingot(baseItemVO.getPvalue()); // 用多少点卷
			rechargeInfoVO.setPlayerId(playerId);
			rechargeInfoVO.setState(0);
			rechargeInfoVO.setUrlparam(detail);
			rechargeInfoVO.setTxurl("");
			rechargeInfoVO.setOpenid(playerVO.getUserName());
			rechargeInfoVO.setPlatform(playerVO.getBchannel());
			rechargeInfoVO.setServerId(TXConstants.my_server_id);

			rechargeService.insertRechargeInfo(rechargeInfoVO);

			GoldIngotsResponse.Builder goldIngotsResponse = GoldIngotsResponse.newBuilder();
			goldIngotsResponse.setUrlparams(urlParams);

			CommandUtil.packageNettyMessage(CSCommandConstant.SHOP_GOLDINGOT_REQ, goldIngotsResponse.build().toByteArray(), commandList);

		} else {
			String msg = jo.get("msg").toString();
			throw new GameException(AlertEnum.RECHAGE_FAIL_EXCEPTION, msg);
		}

	}

	/**
	 * 获取黄钻token
	 * 
	 * @param nettyMessageVO
	 * @param commandList
	 * @throws Exception
	 */
	public void getYellowToken(NettyMessageVO nettyMessageVO, List<NettyMessageVO> commandList) throws Exception {

		int playerId = ServerHandler.get(nettyMessageVO.getChannel());
		UserCached userCached = ServerHandler.getUserCached(playerId);
		PlayerVO playerVO = userCached.getPlayerVO();

		// playerVO.setUserName("12E5ECA5E6CA8BD6B910775FA40DFAFF");
		// playerVO.setPassword("66987F0305AE684F2521AAE8476B49A2");
		// playerVO.setPfkey("b1ef00f824603990223a45a245f67531");
		System.err.println("-=======getYellowVIPtoken==============");
		System.err.println("playerVO.getUserName() = " + playerVO.getUserName());
		System.err.println("playerVO.getPassword() = " + playerVO.getPassword());
		System.err.println("playerVO.getPfkey() = " + playerVO.getPfkey());
		System.err.println("playerVO.getPf() = " + playerVO.getBchannel());
		YellowTokenVO yellowTokenVO = MyOpenApiV3.getToken(playerVO.getUserName(), playerVO.getPassword(), playerVO.getBchannel(), playerVO.getPfkey(), MyOpenApiV3.YELLOWACTIVEID,
				TXConstants.tx_zone_id);

		if (yellowTokenVO == null || yellowTokenVO.getRet() != 0) {
			throw new GameException(AlertEnum.YELLOWVIP_EXCEPTION, yellowTokenVO.getMsg());
		}

		GetYellowToken.Builder getYellowToken = GetYellowToken.newBuilder();
		getYellowToken.setToken(yellowTokenVO.getToken());
		getYellowToken.setActid(MyOpenApiV3.YELLOWACTIVEID);
		getYellowToken.setMid(yellowTokenVO.getMid());

		CommandUtil.packageNettyMessage(CSCommandConstant.GET_YELLOW_TOKEN, getYellowToken.build().toByteArray(), commandList);

	}

	/**
	 * 黄钻新手礼包
	 * 
	 * @param nettyMessageVO
	 * @param commandList
	 * @throws Exception
	 */
	public void getYellowNewGift(NettyMessageVO nettyMessageVO, List<NettyMessageVO> commandList) throws Exception {

		int playerId = ServerHandler.get(nettyMessageVO.getChannel());
		UserCached userCached = ServerHandler.getUserCached(playerId);
		PlayerTimerVO playerTimerVO = userCached.getUserTimer().getPlayerTimerVO();

		if (userCached.getPlayerVO().getIs_yellow_vip() == 0) {
			throw new Exception("不是黄钻不能领取成长礼包");
		}

		if ((playerTimerVO.getYellowNewGift() & 1) == 1) {
			throw new Exception("已领取过黄钻礼包");
		}

		YellowRewardVO yellowRewardVO = YellowRewardRes.getInstance().getNewYellowRewardVO();

		if (yellowRewardVO != null && yellowRewardVO.getRewards().size() > 0) {
			rewardService.checkAndReward(userCached, yellowRewardVO.getRewards(), commandList);
		}

		playerTimerVO.setYellowNewGift(playerTimerVO.getYellowNewGift() | 1);
		playerTimerService.updateYellowNewGift(playerTimerVO);

		CommandUtil.packageYellowGiftInfo(userCached, commandList);
	}

	// 黄钻成长礼包
	public void getYellowLevelGift(YellowLevelGiftRequest request, NettyMessageVO nettyMessageVO, List<NettyMessageVO> commandList) throws Exception {

		int playerId = ServerHandler.get(nettyMessageVO.getChannel());
		UserCached userCached = ServerHandler.getUserCached(playerId);
		PlayerTimerVO playerTimerVO = userCached.getUserTimer().getPlayerTimerVO();

		if (userCached.getPlayerVO().getIs_yellow_vip() == 0) {
			throw new Exception("不是黄钻不能领取成长礼包");
		}

		YellowRewardVO yellowRewardVO = YellowRewardRes.getInstance().getLevelYellowRewardVO(request.getId());
		if (yellowRewardVO.getId() > userCached.getPlayerVO().getLevel()) {
			throw new GameException(AlertEnum.ROLE_LEVEL_NO);
		}

		int index = yellowRewardVO.getId() / 10;
		if (((playerTimerVO.getYellowNewGift() >> index) & 1) == 1) {
			throw new GameException(AlertEnum.YELLOW_LEVEL_YILING);
		}

		if (yellowRewardVO != null && yellowRewardVO.getRewards().size() > 0) {
			rewardService.checkAndReward(userCached, yellowRewardVO.getRewards(), commandList);
		}

		playerTimerVO.setYellowNewGift(playerTimerVO.getYellowNewGift() | (1 << index));
		playerTimerService.updateYellowNewGift(playerTimerVO);

		CommandUtil.packageYellowGiftInfo(userCached, commandList);
	}

	/**
	 * 黄钻每日礼包
	 * 
	 * @param request
	 * @param nettyMessageVO
	 * @param commandList
	 * @throws Exception
	 */
	public void getYellowDayGift(NettyMessageVO nettyMessageVO, List<NettyMessageVO> commandList) throws Exception {

		int playerId = ServerHandler.get(nettyMessageVO.getChannel());
		UserCached userCached = ServerHandler.getUserCached(playerId);
		PlayerTimerVO playerTimerVO = userCached.getUserTimer().getPlayerTimerVO();

		if (userCached.getPlayerVO().getIs_yellow_vip() == 0) {
			throw new Exception("不是黄钻不能领取每日礼包");
		}

		if (playerTimerVO.getYellowDayGift() > 0) {
			throw new GameException(AlertEnum.YELLOW_DAY_YILING);
		}

		YellowRewardVO yellowRewardVO = YellowRewardRes.getInstance().getDayYellowRewardVO(userCached.getPlayerVO().getYellow_vip_level());
		List<Reward> list = new ArrayList<Reward>(yellowRewardVO.getRewards()); // 黄钻每日礼包

		if (userCached.getPlayerVO().getIs_yellow_year_vip() == 1) { // 年费黄钻礼包
			list.addAll(YellowRewardRes.getInstance().getYdayYellowRewardVO().getRewards());
		}

		if (list != null && list.size() > 0) {
			rewardService.checkAndReward(userCached, list, commandList);
		}

		playerTimerVO.setYellowDayGift(1);
		playerTimerService.updateYellowDayGift(playerTimerVO);

		CommandUtil.packageYellowGiftInfo(userCached, commandList);
	}

}
