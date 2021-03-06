package com.dh.handler.welfare;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dh.fx.ProtocolParseForm;
import com.dh.game.constant.CSCommandConstant;
import com.dh.game.vo.activity.ActivityProto.RewardWelfareRequest;
import com.dh.game.vo.activity.ActivityProto.WelfareRequest;
import com.dh.game.vo.activity.OpenActivityProto.OneRechargeReq;
import com.dh.game.vo.activity.OpenActivityProto.OpenActiveLinQuReq;
import com.dh.handler.AbstractCommandHandler;
import com.dh.netty.NettyMessageVO;
import com.dh.processor.WelfareProcessor;

@Component
public class WelfareHandler extends AbstractCommandHandler {

	@Resource
	private WelfareProcessor welfareProcessor;

	@Override
	protected void initCommandCode() {
		this.addCommand(CSCommandConstant.WELFARE_LIST, "welfareList"); // 进入福利大厅
		this.addCommand(CSCommandConstant.WELFARE_REWARD, "rewardWelfare"); // 领取福利大厅奖励
		this.addCommand(CSCommandConstant.REWARD_ONLINE, "rewardOnline"); // 在线奖励
		this.addCommand(CSCommandConstant.REWARD_ONLINE_DETAIL, "rewardOnlineDetail");
		this.addCommand(CSCommandConstant.REWARD_FEEDBACK_VIP, "feedbackGiftGold");// 领取反馈
		this.addCommand(CSCommandConstant.GET_FEEDBACK_INFO, "freshFeedbackGiftGold");// 反馈礼金信息

		this.addCommand(CSCommandConstant.GET_ONE_RECHARGE_DETAIL, "getOneRechargeReward");// 单笔充值奖励
		this.addCommand(CSCommandConstant.GET_OPENACTIVY_DETAIL, "getOpenActivyDetail");// 单笔充值奖励
	}

	public void welfareList(NettyMessageVO nettyMessageVO, List<NettyMessageVO> commandList) throws Exception {
		WelfareRequest request = ProtocolParseForm.parseFrom(WelfareRequest.class, nettyMessageVO.getData());
		welfareProcessor.getWelfareResp(request, nettyMessageVO, commandList);
	}

	public void rewardWelfare(NettyMessageVO nettyMessageVO, List<NettyMessageVO> commandList) throws Exception {
		RewardWelfareRequest request = ProtocolParseForm.parseFrom(RewardWelfareRequest.class, nettyMessageVO.getData());
		welfareProcessor.rewardWelfare(request, nettyMessageVO, commandList);
	}

	public void rewardOnlineDetail(NettyMessageVO nettyMessageVO, List<NettyMessageVO> commandList) throws Exception {
		welfareProcessor.rewardOnlineDetail(nettyMessageVO, commandList);
	}

	public void rewardOnline(NettyMessageVO nettyMessageVO, List<NettyMessageVO> commandList) throws Exception {
		welfareProcessor.rewardOnline(nettyMessageVO, commandList);
	}

	public void feedbackGiftGold(NettyMessageVO nettyMessageVO, List<NettyMessageVO> commandList) throws Exception {
		welfareProcessor.feedbackGiftGold(nettyMessageVO, commandList);
	}

	public void freshFeedbackGiftGold(NettyMessageVO nettyMessageVO, List<NettyMessageVO> commandList) throws Exception {
		welfareProcessor.freshFeedbackGiftGold(nettyMessageVO, commandList);
	}

	public void getOneRechargeReward(NettyMessageVO nettyMessageVO, List<NettyMessageVO> commandList) throws Exception {
		OneRechargeReq request = ProtocolParseForm.parseFrom(OneRechargeReq.class, nettyMessageVO.getData());
		welfareProcessor.getOneRechargeReward(request, nettyMessageVO, commandList);
	}
	
	public void getOpenActivyDetail(NettyMessageVO nettyMessageVO, List<NettyMessageVO> commandList) throws Exception {
		OpenActiveLinQuReq request = ProtocolParseForm.parseFrom(OpenActiveLinQuReq.class, nettyMessageVO.getData());
		welfareProcessor.getOpenActivyDetail(request, nettyMessageVO, commandList);
	}
	
	

}
