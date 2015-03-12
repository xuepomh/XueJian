package com.dh.timer;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dh.game.constant.CSCommandConstant;
import com.dh.game.vo.gm.GMProto.WorldBossStartReq;
import com.dh.netty.NettyMessageVO;
import com.dh.queue.LocalCommandQueue;

@Component
public class BossTimer {
	private static Logger logger = Logger.getLogger(BossTimer.class);

	private final static NettyMessageVO NETTYMESSAGEVO = new NettyMessageVO();

	static {
		NETTYMESSAGEVO.setCommandCode(CSCommandConstant.SYS_BOSS_FRESH);
		NETTYMESSAGEVO.setData(WorldBossStartReq.newBuilder().setOpenBoss(0).build().toByteArray());
	}

	// 定时触发
	@Scheduled(cron = "0 0 10,13,20,23 * * ?")
	public void run() {
		logger.debug("===========WORLD BOSS=============");
		LocalCommandQueue.getInstance().put(NETTYMESSAGEVO);
	}
}
