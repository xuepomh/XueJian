package com.dh.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dh.dao.YunZhiFuMapper;
import com.dh.game.vo.user.RechargeInfoVO;

@Component
public class RechargeService {
	@Resource
	private YunZhiFuMapper yunZhiFuMapper;

//	public RechargeInfoVO getRechargeInfo(String token) {
//		return yunZhiFuMapper.getRechargeInfo(token);
//	}

	public void insertRechargeInfo(RechargeInfoVO rechargeInfoVO) {
		yunZhiFuMapper.insertRechargeInfo(rechargeInfoVO);
	}

	public void updateRechargeInfo(RechargeInfoVO rechargeInfoVO) {
		yunZhiFuMapper.updateRechargeInfo(rechargeInfoVO);
	}

}
