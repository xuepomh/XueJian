package taskscript;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.dh.constants.ItemConstants;
import com.dh.game.constant.TaskConstant;
import com.dh.game.vo.BaseProto.BUILDID;
import com.dh.game.vo.base.BaseHeroInfoVO;
import com.dh.game.vo.base.BaseItemVO;
import com.dh.game.vo.login.PlayerLoginProto.PLAYER_PROPERTY;
import com.dh.game.vo.user.PlayerHeroVO;
import com.dh.game.vo.user.PlayerKnapsackVO;
import com.dh.game.vo.user.PlayerRaidVO;
import com.dh.game.vo.user.PlayerTaskVO;
import com.dh.handler.task.TaskCheck;
import com.dh.netty.NettyMessageVO;
import com.dh.resconfig.HeroRes;
import com.dh.resconfig.ItemRes;
import com.dh.service.HeroService;
import com.dh.service.KnapsackService;
import com.dh.service.PlayerAccountService;
import com.dh.sqlexe.SqlSaveThread;
import com.dh.util.CommandUtil;
import com.dh.util.SqlBuild;
import com.dh.vo.user.UserCached;

public class TaskCheckImp implements TaskCheck {
	private static final Logger LOGGER = Logger.getLogger(TaskCheckImp.class);
	private ApplicationContext applicationContext;
	private SqlBuild sqlBuild;
	private SqlSaveThread sqlSaveThread;
	private HeroService heroService;
	private KnapsackService knapsackService;
	private PlayerAccountService playerAccountService;

	public void init(ApplicationContext act) {
		applicationContext = act;
		sqlBuild = (SqlBuild) applicationContext.getBean("sqlBuild");
		sqlSaveThread = (SqlSaveThread) applicationContext.getBean("sqlSaveThread");
		heroService = (HeroService) applicationContext.getBean("heroService");
		knapsackService = (KnapsackService) applicationContext.getBean("knapsackService");
		playerAccountService = (PlayerAccountService) applicationContext.getBean("playerAccountService");
	}

	/**
	 * 查找玩家进行中的任务
	 * 
	 * @param reqType
	 * @return
	 */
	public PlayerTaskVO getUnderwayPlayerTaskById(UserCached userCached, int reqType) {
		for (PlayerTaskVO playerTask : userCached.getTaskList()) {
			// LOGGER.debug("playerTask = " + playerTask);
			// LOGGER.debug("getBaseTaskVO = " + playerTask.getBaseTaskVO());
			if (playerTask.getBaseTaskVO().getCompletetype() == reqType && playerTask.getStatus() == TaskConstant.TASK_UNDERWAY) {
				return playerTask;
			}
		}
		return null;
	}

	// 更改任务的完成度
	public void changeTaskNumById(PlayerTaskVO playerTask) {
		String sql = sqlBuild.getSql("com.dh.dao.PlayerTaskMapper.changeTaskNumById", playerTask);
		sqlSaveThread.putSql(playerTask.getPlayerId(),sql);
	}

	/**
	 * 改变任务的状态
	 * @param player
	 * @param playerTaskID
	 * @param status
	 */
	public void changeTaskStatus(PlayerTaskVO playerTask, List<NettyMessageVO> commandList) {
		String sql = sqlBuild.getSql("com.dh.dao.PlayerTaskMapper.changeTaskStatus", playerTask);
		sqlSaveThread.putSql(playerTask.getPlayerId(),sql);
		if (playerTask.getStatus() == TaskConstant.TASK_COMPLIETE) {
			commandList.add(CommandUtil.getTaskResponse(playerTask));
		}
	}

	/**
	 * 改变系列任务状态
	 * 
	 * @param player
	 * @param taskId
	 * @param num
	 * @param saveNum
	 */
	public void changTaskByReQTypeAcc(UserCached userCached, int reqType, int target, int num, List<NettyMessageVO> commandList) {
		try {
			PlayerTaskVO playerTask = getUnderwayPlayerTaskById(userCached, reqType);
			if (playerTask == null || (playerTask.getBaseTaskVO().getTargetid() != target && playerTask.getBaseTaskVO().getTargetid() > 0)) {
				return;
			}
			playerTask.setFinishNum(playerTask.getFinishNum() + num);
			if (playerTask.getFinishNum() >= playerTask.getBaseTaskVO().getNumber()) {
				playerTask.setStatus(TaskConstant.TASK_COMPLIETE);
				changeTaskStatus(playerTask, commandList);
			} else {
				changeTaskNumById(playerTask);
				commandList.add(CommandUtil.getTaskResponse(playerTask));
			}

		} catch (Exception e) {
			LOGGER.error("TasksService.changTaskByReQType error ", e);
		}
	}

	/**
	 * 改变系列任务状态 target取>=
	 * 
	 * @param player
	 * @param taskId
	 * @param num
	 * @param saveNum
	 */
	public void changTaskByReQTypeAcc2(UserCached userCached, int reqType, int target, int num, List<NettyMessageVO> commandList) {
		try {
			PlayerTaskVO playerTask = getUnderwayPlayerTaskById(userCached, reqType);
			if (playerTask == null || (playerTask.getBaseTaskVO().getTargetid() < target && playerTask.getBaseTaskVO().getTargetid() > 0)) {
				return;
			}
			playerTask.setFinishNum(playerTask.getFinishNum() + num);
			if (playerTask.getFinishNum() >= playerTask.getBaseTaskVO().getNumber()) {
				playerTask.setStatus(TaskConstant.TASK_COMPLIETE);
				changeTaskStatus(playerTask, commandList);
			} else {
				changeTaskNumById(playerTask);
				commandList.add(CommandUtil.getTaskResponse(playerTask));
			}

		} catch (Exception e) {
			LOGGER.error("TasksService.changTaskByReQType2 error ", e);
		}
	}

	/**
	 * 改变系列任务状态
	 * 
	 * @param player
	 * @param taskId
	 * @param num
	 * @param saveNum
	 */
	public void changTaskByReQType(UserCached userCached, int reqType, int target, int num, List<NettyMessageVO> commandList) {

		try {
			PlayerTaskVO playerTask = getUnderwayPlayerTaskById(userCached, reqType);

			if (playerTask == null || (playerTask.getBaseTaskVO().getTargetid() != target && playerTask.getBaseTaskVO().getTargetid() > 0)) {
				return;
			}

			boolean modi = false;
			if (num > playerTask.getFinishNum()) {
				playerTask.setFinishNum(num);
				modi = true;
			}

			if (playerTask.getFinishNum() >= playerTask.getBaseTaskVO().getNumber()) {
				playerTask.setStatus(TaskConstant.TASK_COMPLIETE);
				changeTaskStatus(playerTask, commandList);
				System.out.println("任务id" + playerTask.getTaskId() + "," + playerTask.getPlayerId() + " 完成");
			} else if (modi) {
				changeTaskNumById(playerTask);
				commandList.add(CommandUtil.getTaskResponse(playerTask));
			}

		} catch (Exception e) {
			LOGGER.error("TasksService.changTaskByReQType error ", e);
		}
	}

	/**
	 * N个Ｍ级英雄
	 * 
	 * @param userCached
	 * @param reqType
	 */
	public void changTaskByReQType(UserCached userCached, int reqType, List<NettyMessageVO> commandList) {
		try {
			PlayerTaskVO playerTask = getUnderwayPlayerTaskById(userCached, reqType);
			if (playerTask == null) {
				return;
			}

			int num = getTaskNum(userCached, playerTask);

			boolean modi = false;
			// if (num > playerTask.getFinishNum()) {
			playerTask.setFinishNum(num);
			modi = true;
			// }
			// LOGGER.debug("num = " + playerTask.getFinishNum() + ", modi = " + modi);

			if (playerTask.getFinishNum() >= playerTask.getBaseTaskVO().getNumber()) {
				playerTask.setStatus(TaskConstant.TASK_COMPLIETE);
				changeTaskStatus(playerTask, commandList);
			} else if (modi) {
				changeTaskNumById(playerTask);
				// LOGGER.debug("add command num = " + playerTask.getFinishNum() + ", modi = " + modi);
				commandList.add(CommandUtil.getTaskResponse(playerTask));

			}

		} catch (Exception e) {
			LOGGER.error("TasksService.changTaskByReQType error ", e);
		}
	}

	public int getTaskNum(UserCached userCached, PlayerTaskVO playerTask) {
		int num = 0;

		if (playerTask.getBaseTaskVO().getCompletetype() == TaskConstant.TASK_ANY_LEVELHERO) {
			if (userCached.getUserHero().getHeroList() != null) {
				for (PlayerHeroVO playerHeroVO : userCached.getUserHero().getHeroList()) {
					if (playerHeroVO.getLevel() >= playerTask.getBaseTaskVO().getTargetid()) {
						num++;
					}
				}
			}

		}

		else if (playerTask.getBaseTaskVO().getCompletetype() == TaskConstant.TASK_NHERO_NSTAR) {
			if (userCached.getUserHero().getHeroList() != null) {
				for (PlayerHeroVO playerHeroVO : userCached.getUserHero().getHeroList()) {
					if (playerHeroVO.getBaseHeroInfoVO().getStar() >= playerTask.getBaseTaskVO().getTargetid()) {
						num++;
					}
				}
			}

		}

		else if (playerTask.getBaseTaskVO().getCompletetype() == TaskConstant.TASK_NEQ_NSTAR) {
			if (userCached.getUserKnapsack().getKnapsackList() != null) {
				for (PlayerKnapsackVO playerKnapsackVO : userCached.getUserKnapsack().getKnapsackList()) {
					if (playerKnapsackVO.getBaseItemVO().getType() != ItemConstants.ITEM_TYPE_EQPI) {
						continue;
					}
					if (playerKnapsackVO.getBaseItemVO().getStar() >= playerTask.getBaseTaskVO().getTargetid()) {
						num++;
						if (num >= playerTask.getBaseTaskVO().getNumber()) {
							break;
						}
					}
				}

				if (num < playerTask.getBaseTaskVO().getNumber()) {
					for (PlayerHeroVO playerHeroVO : userCached.getUserHero().getHeroList()) {
						for (PlayerKnapsackVO playerKnapsackVO : playerHeroVO.getEquipList()) {
							if (playerKnapsackVO.getBaseItemVO().getStar() >= playerTask.getBaseTaskVO().getTargetid()) {
								num++;
								if (num >= playerTask.getBaseTaskVO().getNumber()) {
									break;
								}
							}
						}
					}
				} // end if
			}

		}

		return num;
	}

	public void receiveReady(UserCached userCached, PlayerTaskVO playerTask) {
		int num = 0;
		switch (playerTask.getBaseTaskVO().getCompletetype()) {

		case TaskConstant.TASK_ROLE_UPLEVEL:// 角色升级
			if (userCached.getPlayerVO().getLevel() >= playerTask.getBaseTaskVO().getNumber()) {
				playerTask.setStatus(TaskConstant.TASK_COMPLIETE);
			}
			break;

		case TaskConstant.TASK_RAID: // // 如果副本打完了,接了就完成
			if (judgeScore(userCached, playerTask)) {
				playerTask.setStatus(TaskConstant.TASK_COMPLIETE);
			}
			break;
		case TaskConstant.TASK_BUILD_UPLEVEL: // 建筑升级
			int target = playerTask.getBaseTaskVO().getTargetid();
			int targetLevel = playerTask.getBaseTaskVO().getNumber();
			if (target == 0) {
				if (userCached.getUserTimer().getPlayerBuildVO().getB1() >= targetLevel || userCached.getUserTimer().getPlayerBuildVO().getB2() >= targetLevel
						|| userCached.getUserTimer().getPlayerBuildVO().getB3() >= targetLevel) {
					playerTask.setStatus(TaskConstant.TASK_COMPLIETE);
				}
			} else if (target > 0) {
				int level = 0;

				if (target == BUILDID.YISITANG_VALUE) {
					level = userCached.getUserTimer().getPlayerBuildVO().getB1();
				} else if (target == BUILDID.YANGXINGDIAN_VALUE) {
					level = userCached.getUserTimer().getPlayerBuildVO().getB2();
				}

				playerTask.setFinishNum(level);
				if (level >= targetLevel) {
					playerTask.setStatus(TaskConstant.TASK_COMPLIETE);
				}

			}

			break;

		case TaskConstant.TASK_ANY_LEVELHERO: // 拥有Targetid级的英雄number个
			int count = 0;
			for (PlayerHeroVO playerHeroVO : userCached.getUserHero().getHeroList()) {
				if (playerHeroVO.getLevel() >= playerTask.getBaseTaskVO().getTargetid()) {
					count++;
				}
			}

			playerTask.setFinishNum(Math.min(count, playerTask.getBaseTaskVO().getNumber()));
			if (count >= playerTask.getBaseTaskVO().getNumber()) {
				playerTask.setStatus(TaskConstant.TASK_COMPLIETE);
				break;
			}

			break;

		case TaskConstant.TASK_HERO_UPSTAR:
			for (PlayerHeroVO playerHeroVO : userCached.getUserHero().getHeroList()) {
				if (playerTask.getBaseTaskVO().getTargetid() == 0 && playerHeroVO.getBaseHeroInfoVO().getStar() >= playerTask.getBaseTaskVO().getNumber()) {
					playerTask.setStatus(TaskConstant.TASK_COMPLIETE);
				} else if (playerTask.getBaseTaskVO().getTargetid() == playerHeroVO.getCfgId() && playerHeroVO.getBaseHeroInfoVO().getStar() >= playerTask.getBaseTaskVO().getNumber()) {
					playerTask.setStatus(TaskConstant.TASK_COMPLIETE);
				}
			}

			break;
		case TaskConstant.TASK_HERO_UPLEVEL:
			for (PlayerHeroVO playerHeroVO : userCached.getUserHero().getHeroList()) {
				if (playerTask.getBaseTaskVO().getTargetid() == 0 && playerHeroVO.getLevel() >= playerTask.getBaseTaskVO().getNumber()) {
					playerTask.setStatus(TaskConstant.TASK_COMPLIETE);
				} else if (playerTask.getBaseTaskVO().getTargetid() == playerHeroVO.getCfgId() && playerHeroVO.getLevel() >= playerTask.getBaseTaskVO().getNumber()) {
					playerTask.setStatus(TaskConstant.TASK_COMPLIETE);
				}
			}

			break;

		case TaskConstant.TASK_NHERO_NSTAR:
		case TaskConstant.TASK_NEQ_NSTAR:
			num = getTaskNum(userCached, playerTask);
			if (num >= playerTask.getBaseTaskVO().getNumber()) {
				playerTask.setFinishNum(num);
				playerTask.setStatus(TaskConstant.TASK_COMPLIETE);
			}
			break;
		case TaskConstant.TASK_NPSKILL:
			for (PlayerHeroVO playerHeroVO : userCached.getUserHero().getHeroList()) {
				if (playerHeroVO.getPassNum() >= playerTask.getBaseTaskVO().getNumber()) {
					playerTask.setFinishNum(num);
					playerTask.setStatus(TaskConstant.TASK_COMPLIETE);
					break;
				}
			}
			break;

		case TaskConstant.TASK_SKILL_UPLEVEL:
			for (PlayerHeroVO playerHeroVO : userCached.getUserHero().getHeroList()) {
				if (playerTask.getBaseTaskVO().getTargetid() == 0 && playerHeroVO.getSkillLevel() >= playerTask.getBaseTaskVO().getNumber()) {
					playerTask.setStatus(TaskConstant.TASK_COMPLIETE);
					break;
				} else if (playerTask.getBaseTaskVO().getTargetid() == playerHeroVO.getCfgId() && playerHeroVO.getLevel() >= playerTask.getBaseTaskVO().getNumber()) {
					playerTask.setStatus(TaskConstant.TASK_COMPLIETE);
					break;
				}
			}

			break;
		}
	}

	/**
	 * 特殊处理
	 * @param userCached
	 * @param playerTask
	 * @param commandList
	 * @throws Exception
	 */
	public void specProcess(UserCached userCached, PlayerTaskVO playerTask, List<NettyMessageVO> commandList) throws Exception {
		if (playerTask.getTaskId() == 2) {
			BaseHeroInfoVO baseHeroInfoVO = HeroRes.getInstance().getBaseHeroInfoVO(30024);
			heroService.addPlayerHero(baseHeroInfoVO, 5, userCached, false, commandList);
		} else if (playerTask.getTaskId() == 5) {
			BaseHeroInfoVO baseHeroInfoVO = HeroRes.getInstance().getBaseHeroInfoVO(30008);
			heroService.addPlayerHero(baseHeroInfoVO, 20, userCached, false, commandList);
		}
	}

	// 等级特别处理 a
	public void specProcess(UserCached userCached, int oldlevel, int newlevel, List<NettyMessageVO> commandList) throws Exception {
		BaseItemVO baseItemVO = null;
		if (oldlevel < 25 && 25 <= newlevel) {
			if (knapsackService.hasKnapsackGird(userCached, 2)) {
				baseItemVO = ItemRes.getInstance().getBaseItemVO(22000);
				knapsackService.addNewItem(userCached, baseItemVO, 1, commandList);
				baseItemVO = ItemRes.getInstance().getBaseItemVO(22001);
				knapsackService.addNewItem(userCached, baseItemVO, 1, commandList);
			}

			playerAccountService.addCurrency(PLAYER_PROPERTY.PROPERTY_PVP, 30, userCached.getPlayerAccountVO(), commandList, "25级加竞技值30点");
		}

		if (oldlevel < 31 && 31 <= newlevel) {
			if (knapsackService.hasKnapsackGird(userCached, 1)) {
				baseItemVO = ItemRes.getInstance().getBaseItemVO(20020);
				knapsackService.addNewItem(userCached, baseItemVO, 1, commandList);
			}
		}

		if (oldlevel < 35 && 35 <= newlevel) {
			if (knapsackService.hasKnapsackGird(userCached, 1)) {
				baseItemVO = ItemRes.getInstance().getBaseItemVO(21000);
				knapsackService.addNewItem(userCached, baseItemVO, 3, commandList);
			}
		}
		
		if (oldlevel < 12 && 12 <= newlevel) {
			playerAccountService.addCurrency(PLAYER_PROPERTY.PROPERTY_MONEY, 50000, userCached.getPlayerAccountVO(), commandList, "16级送10点行动力");
		}

		if (oldlevel < 16 && 16 <= newlevel) {
			playerAccountService.addCurrency(PLAYER_PROPERTY.PROPERTY_POWER, 10, userCached.getPlayerAccountVO(), commandList, "16级送10点行动力");
		}

		if (oldlevel < 17 && 17 <= newlevel) {
			if (knapsackService.hasKnapsackGird(userCached, 1)) {
				baseItemVO = ItemRes.getInstance().getBaseItemVO(21000);
				knapsackService.addNewItem(userCached, baseItemVO, 3, commandList);
			}

			playerAccountService.addCurrency(PLAYER_PROPERTY.PROPERTY_PVP, 30, userCached.getPlayerAccountVO(), commandList, "17级加竞技值30点");
		}

	}

	// 判断副本是否通关
	public boolean judgeScore(UserCached userCached, PlayerTaskVO playerTaskVO) {
		boolean flag = false;
		PlayerRaidVO raid = userCached.getUserRaid().getPlayerRaidVO(playerTaskVO.getBaseTaskVO().getTargetid());
		if (raid != null && raid.getScore() > 0) {
			flag = true;
		}
		return flag;
	}

}
