package com.dh.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.dh.Cache.JedisTool;
import com.dh.Cache.ServerHandler;
import com.dh.constants.MailConstants;
import com.dh.dao.PlayerMailMapper;
import com.dh.game.constant.CSCommandConstant;
import com.dh.game.constant.RedisKey;
import com.dh.game.vo.base.BaseItemVO;
import com.dh.game.vo.base.Reward;
import com.dh.game.vo.login.PlayerLoginProto.PLAYER_PROPERTY;
import com.dh.game.vo.login.PlayerLoginProto.PLAYER_UNIT_TYPE;
import com.dh.game.vo.message.MessageProto.NewMailResponse;
import com.dh.game.vo.message.MessageProto.Rewards;
import com.dh.game.vo.user.MailVO;
import com.dh.game.vo.user.PlayerVO;
import com.dh.main.InitLoad;
import com.dh.netty.NettyMessageVO;
import com.dh.resconfig.ItemRes;
import com.dh.sqlexe.SqlSaveThread;
import com.dh.util.CommandUtil;
import com.dh.util.DateUtil;
import com.dh.util.SqlBuild;
import com.dh.vo.user.UserCached;
import com.dh.vo.user.UserMail;

/**
 * @author :ZQGAME
 * @date 2014年3月13日
 */
@Service
public class MailService {
	private static Logger logger = Logger.getLogger(MailService.class);
	@Resource
	private SqlBuild sqlBuild;
	@Resource
	private SqlSaveThread sqlSaveThread;
	@Resource
	private PlayerMailMapper playerMailMapper;
	@Resource
	private BaseInfoService baseInfoService;

	public void initMail2Redis(List<PlayerVO> playerVOList) {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		boolean isNeedLoad = true;
		if (playerVOList.size() > 0) {
			int playerId = playerVOList.get(0).getPlayerId();
			int mailDB = playerMailMapper.getMaxMailId(playerId);
			String maildRedis = JedisTool.get(RedisKey.PLAYER_MAIL_ID + playerId);

			if (maildRedis != null && Integer.valueOf(maildRedis) != mailDB) {// 暂时只检查第一个
				isNeedLoad = true;
			}
		}
		if (isNeedLoad) {
			List<HashMap<Integer, Integer>> mails = getMaxMailIds();
			HashMap<Integer, Integer> keymap;
			if (mails != null) {
				Iterator<HashMap<Integer, Integer>> it = mails.iterator();
				while (it.hasNext()) {
					keymap = it.next();
					map.put(keymap.get("playerId"), keymap.get("maxId"));
				}
				JedisTool.addMailIds(map);
				logger.info("######the maxId of mail reload into redis finished#######");
			}
		}
	}

	public List<HashMap<Integer, Integer>> getMaxMailIds() {
		return playerMailMapper.getMaxMailIds();
	}

	public void loadMail(UserCached userCached) throws Exception {
		List<MailVO> mailList = playerMailMapper.getMailList(userCached.getPlayerId());
		if (mailList == null) {
			mailList = new ArrayList<MailVO>();
		}
		for (MailVO mailVO : mailList) {
			decodeRewardString2proto(mailVO);
		}
		userCached.getUserMail().setMailList(filterMails(userCached, mailList));
		userCached.getUserMail().setLastLoadTime(System.currentTimeMillis());
		Collections.sort(userCached.getUserMail().getMailList());
	}

	/**
	 * 获得用户邮件
	 * 
	 * @date d2014年2月25日
	 */
	public List<MailVO> getMailList(UserCached userCached) {
		List<MailVO> list = userCached.getUserMail().getMailList();
		return list;
	}

	/**
	 * 删除过期邮件,仅登录时调用一次
	 * 
	 * @date 2014年3月13日
	 */
	private List<MailVO> filterMails(UserCached userCached, List<MailVO> list) {
		boolean Readed, Rewarded;
		long lifeTime;
		int maxMailId = 0;
		for (int i = list.size() - 1; i >= 0; i--) {
			MailVO dirtyMail = null;
			MailVO mail = list.get(i);
			lifeTime = System.currentTimeMillis() - mail.getRecvTime().getTime();
			Readed = mail.getIsRead() == 1;
			Rewarded = mail.getIsReward() == 1 || mail.getRewards().isEmpty();// 已经领取或者领取列表为空
			if (lifeTime > MailConstants.M_PT_READED_LIFETIME && Readed && Rewarded) {// 已读,已经奖励或者无奖励,大于5天
				dirtyMail = mail;
			} else if (lifeTime > MailConstants.M_PT_UNREAD_LIFETIME && !Readed && Rewarded) {
				dirtyMail = mail;
			}
			if (maxMailId < mail.getId()) {
				maxMailId = mail.getId();
			}

			if (dirtyMail != null) {
				updateMailAttr(3, mail);
				list.remove(i);
			} else {
				if (Rewarded && Readed) {// 无奖励已读文本
					lifeTime = MailConstants.M_PT_READED_LIFETIME - lifeTime;
				} else if (Rewarded && !Readed) {// 无奖励未读文本
					lifeTime = MailConstants.M_PT_UNREAD_LIFETIME - lifeTime;
				} else {// 有奖励,永久
					lifeTime = -1000;
				}
				mail.setLifeTime((int) (lifeTime / 1000));
			}
		}
		// userCached.getUserMail().setMaxMailId(maxMailId);
		return list;
	}

	public void freshMailLifeTime(MailVO mailVO) {
		long lifeTime = System.currentTimeMillis() - mailVO.getRecvTime().getTime();
		boolean Readed = mailVO.getIsRead() == 1;
		boolean Rewarded = mailVO.getIsReward() == 1 || mailVO.getRewards().isEmpty();// 已经领取或者领取列表为空
		if (Rewarded && Readed) {// 无奖励已读文本
			lifeTime = MailConstants.M_PT_READED_LIFETIME - lifeTime;
		} else if (Rewarded && !Readed) {// 无奖励未读文本
			lifeTime = MailConstants.M_PT_UNREAD_LIFETIME - lifeTime;
		} else {// 有奖励,永久
			lifeTime = -1000;
		}
		mailVO.setLifeTime((int) (lifeTime / 1000L));

	}

	/**
	 * @param flags
	 *            :1,2,3分别对应已读,已领取,标记邮件过期
	 * @date 2014年3月13日
	 */
	public void updateMailAttr(int flags, MailVO mailVO) {
		String sql = null;
		switch (flags) {
		case 1:
			sql = sqlBuild.getSql("com.dh.dao.PlayerMailMapper.markMailRead", mailVO);
			break;
		case 2:
			sql = sqlBuild.getSql("com.dh.dao.PlayerMailMapper.markMailReward", mailVO);
			break;
		case 3:
			sql = sqlBuild.getSql("com.dh.dao.PlayerMailMapper.markMailValid", mailVO);
			break;
		default:
			break;
		}
		if (sql != null) {
			sqlSaveThread.putSql(mailVO.getPlayerId(),sql);
		}
	}

	public void updateMail(MailVO mailVO) {
		sqlSaveThread.putSql(mailVO.getPlayerId(),sqlBuild.getSql("com.dh.dao.PlayerMailMapper.updateMail", mailVO));
	}

	/**
	 * 删除指定邮件
	 * 
	 * @date 2014年3月13日
	 */
	public void delMail(MailVO mailVO) {
		String sql = sqlBuild.getSql("com.dh.dao.PlayerMailMapper.delMail", mailVO);
		sqlSaveThread.putSql(mailVO.getPlayerId(),sql);
	}

	@Value("${mail.lifeDay}")
	private int lifeDay;

	public void delInvalidMail() {
		String sql = sqlBuild.getSql("com.dh.dao.PlayerMailMapper.delInvalidMail", lifeDay);
		sqlSaveThread.putSql(0,sql);
	}

	/**
	 * 新邮件通知
	 * 
	 * @date 2014年3月13日
	 */
	public void newMailNotice(UserCached userCached, List<NettyMessageVO> commandList) {
		List<MailVO> mailList = getMailList(userCached);
		NewMailResponse.Builder res = NewMailResponse.newBuilder();
		for (MailVO mailVO : mailList) {
			if (mailVO.getIsRead() == 0 || mailVO.getIsReward() == 0) {
				res.setNewMail(1);
				userCached.getUserMail().setNewMail(true);
				break;
			}
		}
		NettyMessageVO resMsg = new NettyMessageVO();
		resMsg.setData(res.build().toByteArray());
		resMsg.setCommandCode(CSCommandConstant.CMD_MAIL_NEW_MAIL);

		commandList.add(resMsg);
	}

	/**
	 * 检查有新邮件
	 * 
	 * @date 2014年3月18日
	 */
	public void checkNewMail(UserCached userCached) {
		userCached.getUserMail().setNewMail(false);
		List<MailVO> mailList = getMailList(userCached);
		for (MailVO mailVO : mailList) {
			if (mailVO.getIsRead() == 0) {
				userCached.getUserMail().setNewMail(true);
				return;
			}
		}
	}

	public void addNewMailTaskMarket(int playerId, String title, int itemCfgId, int num) throws Exception {
		List<Reward> list = new ArrayList<Reward>(1);
		list.add(createReward(1, itemCfgId, 1));
		addNewMail(null, playerId, title, title, null, 0, list);
	}

	public void addNewMail(UserCached userCached, int playerId, String title, String content, String senderName, int senderId, List<Reward> rewards) throws Exception {
		if (senderId == 0 && senderName == null) {
			senderName = MailConstants.MAIL_SENDER_SYS;
		}
		addNewMail(userCached, playerId, creatMailVoWithOutId(title, content, senderName, senderId, rewards));
	}

	public void addNewMail(UserCached userCached, int playerId, MailVO mailVO) {
		if (userCached == null) {
			userCached = ServerHandler.getUserCached2(playerId);
		}
		int maxMailId = JedisTool.incr(RedisKey.PLAYER_MAIL_ID + playerId);
		mailVO.setId(maxMailId);
		mailVO.setPlayerId(playerId);
		sqlSaveThread.putSql(mailVO.getPlayerId(),sqlBuild.getSql("com.dh.dao.PlayerMailMapper.insertMail", mailVO));
		if (userCached != null) {
			MailVO mailVO2 = new MailVO();
			BeanUtils.copyProperties(mailVO, mailVO2);
			freshMailLifeTime(mailVO2);
			getMailList(userCached).add(0, mailVO2);
			sendNewNotice(userCached);
		}
	}

	public void sendNewNotice(UserCached userCached) {
		ServerHandler.sendMessageToPlayer(CommandUtil.packageAnyProperties(PLAYER_UNIT_TYPE.UNIT_PLAYER, userCached.getPlayerId(), PLAYER_PROPERTY.PROPERYTY_MAIL, 1), userCached.getChannel());
	}

	public String addNewMailBatchExec(UserCached userCached, int playerId, MailVO mailVO) {
		if (userCached == null) {
			userCached = ServerHandler.getUserCached2(playerId);
		}
		int maxMailId = JedisTool.incr(RedisKey.PLAYER_MAIL_ID + playerId);
		mailVO.setId(maxMailId);
		mailVO.setPlayerId(playerId);
		if (userCached != null) {
			MailVO mailVO2 = new MailVO();
			BeanUtils.copyProperties(mailVO, mailVO2);
			freshMailLifeTime(mailVO2);
			getMailList(userCached).add(0, mailVO2);
			sendNewNotice(userCached);
		}
		return sqlBuild.getSql("com.dh.dao.PlayerMailMapper.insertMail", mailVO);
	}

	/**
	 * 没有邮件id
	 * 
	 * @throws Exception
	 */
	public static MailVO creatMailVoWithOutId(String title, String content, String senderName, int senderId, List<Reward> rewards) throws Exception {
		MailVO mailvo = new MailVO();
		mailvo.setIsRead(0);
		mailvo.setIsReward(0);
		mailvo.setIsValid(0);
		mailvo.setRecvTime(new Date());
		mailvo.setContent(content);
		String rewardStr = encoderReward(rewards);
		mailvo.setRewards(rewardStr);
		decodeRewardString2proto(mailvo);
		mailvo.setSenderName(senderName);
		mailvo.setSenderId(senderId);
		mailvo.setTitle(title);
		return mailvo;
	}

	/**
	 * 将reward对象转换为字符串,存数据使用
	 * 
	 * @throws Exception
	 * 
	 * @see decodeRewardString
	 * */
	public static String encoderReward(List<Reward> list) throws Exception {
		StringBuffer target = new StringBuffer("");
		if (list == null || list.isEmpty()) {
			return target.toString();
		}
		List<Reward> newRList = new ArrayList<Reward>();
		for (Reward reward : list) {
			boolean notExist = true;
			for (Reward newReward : newRList) {
				if (reward.getType() == newReward.getType() && reward.getContent() == newReward.getContent()) {
					newReward.setNumber(reward.getNumber() + newReward.getNumber());
					notExist = false;
					break;
				}
			}
			if (notExist) {
				Reward reward2 = new Reward();
				BeanUtils.copyProperties(reward, reward2);
				newRList.add(reward2);
			}
		}
		for (Reward reward : newRList) {
			target.append(reward.getType() + MailConstants.REWARD_SPLIT_TYPE_CHAR.toString());
			if (reward.getType() == RewardService.REWARD_TYPE_GOODS) {
				try {
					target.append(reward.getContent() + MailConstants.REWARD_SPLIT_TYPE_CHAR.toString());
				} catch (Exception e) {
					throw new Exception("content不能为空");
				}
			} else if (reward.getType() == RewardService.REWARD_TYPE_HERO) {
				try {
					target.append(reward.getContent() + MailConstants.REWARD_SPLIT_TYPE_CHAR.toString());
				} catch (Exception e) {
					throw new Exception("content不能为空");
				}

			} else {
				target.append(0 + "" + MailConstants.REWARD_SPLIT_TYPE_CHAR);
			}
			target.append(reward.getNumber());
			target.append(MailConstants.REWARD_SPLIT_CHAR);
		}
		return target.toString();
	}

	/**
	 * 将赠送物品转化为reward()
	 */
	public static Reward createReward(int type, int content, int number) {
		Reward reward = new Reward();
		reward.setType(type);
		reward.setContent(content);
		reward.setNumber(number);
		return reward;
	}

	public static List<Reward> createRewardList(List<Reward> rewards, int type, int content, int number) {
		if (rewards == null) {
			rewards = new ArrayList<Reward>();
		}
		rewards.add(createReward(type, content, number));
		return rewards;
	}

	/**
	 * 将数据库的字符串拆分成proto对象返回
	 * 
	 * @param rewardString
	 *            格式("type1,content1,number1;type2,content2,number2;")
	 * @see Reward
	 * */
	public static void decodeRewardString2proto(MailVO mailVO) {
		Rewards.Builder rewards = Rewards.newBuilder();
		String rewardString = mailVO.getRewards();
		if (mailVO.getIsReward() == 0 && !rewardString.isEmpty()) {
			String[] rewardStrings = MailConstants.REWARD_SPLIT_CHAR.split(rewardString);
			for (String string : rewardStrings) {
				String[] temp = MailConstants.REWARD_SPLIT_TYPE_CHAR.split(string);
				com.dh.game.vo.message.MessageProto.Reward.Builder rewardVo = com.dh.game.vo.message.MessageProto.Reward.newBuilder();
				rewardVo.setType(Integer.parseInt(temp[0]));
				BaseItemVO item = ItemRes.getInstance().getBaseItemVO(Integer.parseInt(temp[1]));
				rewardVo.setNumber(Integer.parseInt(temp[2]));
				if (item != null) {
					rewardVo.setContent(item.getCfgId());
				}
				rewards.addRewards(rewardVo);
			}
		}
		mailVO.setRewardsProto(rewards.build());
	}

	/**
	 * 将数据库的字符串拆分成对象返回
	 * 
	 * @param rewardString
	 *            格式("type1,content1,number1|type2,content2,number2|")
	 * @see Reward
	 * */
	public static List<Reward> decodeRewardString(String rewardString) {
		List<Reward> rewards = new ArrayList<Reward>();
		if (rewardString == null || rewardString.isEmpty()) {
			return rewards;
		}
		String[] rewardStrings = MailConstants.REWARD_SPLIT_CHAR.split(rewardString);
		for (String string : rewardStrings) {
			String[] temp = MailConstants.REWARD_SPLIT_TYPE_CHAR.split(string);
			Reward reward = new Reward();
			reward.setType(Integer.parseInt(temp[0]));
			reward.setContent(Integer.parseInt(temp[1]));
			reward.setNumber(Integer.parseInt(temp[2]));
			rewards.add(reward);
		}
		return rewards;
	}

	public void insertMailForTest() throws Exception {
		Reward r1 = createReward(1, 10001, 1);
		Reward r4 = createReward(1, 10005, 1);
		Reward r2 = createReward(4, 0, 30000000);
		Reward r3 = createReward(5, 0, 300000000);

		ArrayList<Reward> list1 = new ArrayList<Reward>();
		ArrayList<Reward> list2 = new ArrayList<Reward>();
		ArrayList<Reward> list3 = new ArrayList<Reward>();
		list2.add(r1);
		list1.add(r2);
		list2.add(r2);
		list2.add(r3);
		list3.add(r3);
		list3.add(r4);
		for (int i = 0; i < 200; i++) {
			addNewMail(null, 1001354, creatMailVoWithOutId("第" + i + "封测试邮件", "测试邮件,hello\n换行\t空格哈哈哈 注意邮件格式格式", MailConstants.MAIL_SENDER_SYS, 0, list1));
			logger.info("第" + i + "封邮件插入");
		}
		for (int i = 200; i < 400; i++) {
			addNewMail(
					null,
					1001354,
					creatMailVoWithOutId("第" + i + "测试带奖励邮件", "测试邮件,hello\n换行\t空格哈哈哈 注意邮件格式格式,测试文字排版.朱贤智是帅哥\n测试文字排版.朱贤智是帅哥\n测试文字排版.朱贤智是帅哥\n测试文字排版.朱贤智是帅哥\n测试文字排版.朱贤智是帅哥\n测试文字排版.朱贤智是帅哥\n",
							MailConstants.MAIL_SENDER_SYS, 0, list2));

			logger.info("第" + i + "封邮件插入");
		}
	}

	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = null;
		PropertyConfigurator.configure("config/log4j.properties");
		ctx = new FileSystemXmlApplicationContext("config/applicationContext.xml");
		MailService mailService = (MailService) ctx.getBean("mailService");
		InitLoad initLoad = (InitLoad) ctx.getBean("initLoad");
		initLoad.init();
		mailService.insertMailForTest();
		System.out.println("插入结束");
	}

	/**
	 * 重新加载邮件,5min一次
	 * 
	 * @param userCached
	 */
	public void reLoadMail(UserCached userCached) {
		UserMail userMail = userCached.getUserMail();
		int playerId = userCached.getPlayerVO().getPlayerId();
		if (DateUtil.hasOverTime(userMail.getLastLoadTime(), MailConstants.MAIL_LOAD_PERRIOD)) {
			MailVO mailVO = new MailVO();
			mailVO.setId(getPlayerMaxId(userMail.getMailList()));
			mailVO.setPlayerId(playerId);
			List<MailVO> newMails = playerMailMapper.reloadMails(mailVO);
			for (MailVO mailVO2 : newMails) {
				if (checkMailNotExist(userMail.getMailList(), mailVO2)) {// 主要是针对当前邮件邮件直接加载缓存考虑,如果直接加载斤缓存.就无需此步骤
					userMail.getMailList().add(0, mailVO2);
				}
			}
		}

	}

	private int getPlayerMaxId(List<MailVO> mails) {
		int maxId = 0;
		for (MailVO mailVO : mails) {
			maxId = Math.max(mailVO.getId(), maxId);
		}
		return maxId;
	}

	/**
	 * 避免邮件重复加载
	 * 
	 * @param mails
	 * @param newMail
	 * @return
	 */
	private boolean checkMailNotExist(List<MailVO> mails, MailVO newMail) {
		for (MailVO mailVO3 : mails) {
			if (newMail.getId() == mailVO3.getId()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 融合同类型reward
	 * 
	 * @param rewards
	 * @return
	 */
	public static List<Reward> mergeReward(List<Reward> rewards) {
		List<Reward> newRList = new ArrayList<Reward>();
		if (rewards == null || rewards.isEmpty()) {
			return newRList;
		}
		for (Reward reward : rewards) {
			boolean notExist = true;
			for (Reward newReward : newRList) {
				if (reward.getType() == newReward.getType() && reward.getContent() == newReward.getContent()) {
					newReward.setNumber(newReward.getNumber() + reward.getNumber());
					notExist = false;
					break;
				}
			}
			if (notExist) {
				Reward newRewardVO = new Reward();
				BeanUtils.copyProperties(reward, newRewardVO);
				newRList.add(newRewardVO);
			}
		}
		return newRList;
	}
}
