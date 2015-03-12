package com.dh.vo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.dh.constants.ActivityConstant;
import com.dh.game.vo.activity.WorldBossProto.BOSS_STATUS;
import com.dh.game.vo.activity.WorldBossProto.RankHuntLogList;
import com.dh.game.vo.base.BaseMonsterVO;
import com.dh.game.vo.user.BossLogVO;
import com.dh.game.vo.user.PlayerBossVO;
import com.dh.resconfig.MonsterRes;
import com.dh.util.VOUtil;

public class WorldBossVO {
	// public final static int[] START_TIMES = { 10, 13, 20, 23 };
	public final static int[] START_TIMES = { 10, 13, 20, 23 };
	public final static int BOSS_ONE_CFGID = 80000;
	public final static int BOSS_TWO_CFGID = 80001;
	private static WorldBossVO boss = new WorldBossVO();
	private final List<BossLogVO> RANK = new ArrayList<BossLogVO>(10);
	private final Map<Integer, BossLogVO> RANK_ALL = new HashMap<Integer, BossLogVO>();

	private PlayerBossVO maxKillInfo_boss1;
	private PlayerBossVO maxKillInfo_boss2;
	private int hp;
	private BaseMonsterVO baseMonsterVO;
	private Object mutex = new Object();
	private BOSS_STATUS boss1Status;
	private BOSS_STATUS boss2Status;
	private int process;
	private int bossId = 0;

	private WorldBossVO() {
	}

	public boolean isMax(int hunt, boolean isFirst) {
		synchronized (mutex) {
			if (isFirst) {
				if (maxKillInfo_boss1 == null || maxKillInfo_boss1.getHunt() < hunt) {
					return true;
				}
				return false;
			} else {
				if (maxKillInfo_boss2 == null || maxKillInfo_boss2.getHunt() < hunt) {
					return true;
				}
				return false;
			}
		}
	}

	public void loadMaxKill(PlayerBossVO playerBossVO1, PlayerBossVO playerBossVO2) {
		synchronized (mutex) {
			this.maxKillInfo_boss1 = playerBossVO1;
			this.maxKillInfo_boss2 = playerBossVO2;
		}
	}

	public void updateMaxKill(PlayerBossVO playerBossVO, boolean isFirst) {
		synchronized (mutex) {
			if (isFirst) {
				this.maxKillInfo_boss1 = playerBossVO;
			} else {
				this.maxKillInfo_boss2 = playerBossVO;
			}

		}
	}

	public PlayerBossVO getMaxKillInfo(boolean isFirst) {
		return isFirst ? maxKillInfo_boss1 : maxKillInfo_boss2;
	}

	// public void removeFromRank(BossLogVO bossLogVO) {
	// for (BossLogVO rankVo : RANK) {
	// if (rankVo.getPlayerId() == bossLogVO.getPlayerId()) {
	// RANK.remove(rankVo);
	// break;
	// }
	// }
	// }

	public void addBossLog(BossLogVO bossLogVO) {
		RANK_ALL.put(bossLogVO.getPlayerId(), bossLogVO);
		synchronized (mutex) {
			if (RANK.remove(bossLogVO)) {
				RANK.add(bossLogVO);
			} else {
				if (RANK.size() >= 10) {
					if (bossLogVO.getHunt() > RANK.get(9).getHunt()) {
						RANK.remove(9);
						RANK.add(bossLogVO);
					} else {
					}
				} else {
					RANK.add(bossLogVO);
				}

			}
			Collections.sort(RANK);
		}
	}

	public boolean isInBoard(int playerId) {
		synchronized (mutex) {
			Iterator<BossLogVO> it = RANK.iterator();
			int i = 0;
			while (it.hasNext()) {
				if (i < 10) {
					BossLogVO bossLogVO = it.next();
					if (bossLogVO.getPlayerId() == playerId) {
						return true;
					} else {
						i++;
					}
				} else {
					return false;
				}
			}
			return false;
		}
	}

	public RankHuntLogList.Builder getRankList() {
		RankHuntLogList.Builder resp = RankHuntLogList.newBuilder();
		synchronized (mutex) {
			Iterator<BossLogVO> it = RANK.iterator();
			int i = 0;
			while (it.hasNext()) {
				if (i < 10) {
					BossLogVO bossLogVO = it.next();
					i++;
					resp.addHuntLogInfo(VOUtil.packBossLog(bossLogVO));
				} else {
					break;
				}
			}
		}
		return resp;
	}

	public List<BossLogVO> getRANK() {
		return RANK;
	}

	public int getProcess() {
		synchronized (mutex) {
			return process;
		}
	}

	public static WorldBossVO getInstance() {
		return boss;
	}

	public void morningClear() {
		process = 0;
		boss1Status = BOSS_STATUS.BEFORE;
		boss2Status = BOSS_STATUS.BEFORE;

	}

	public boolean isFirst() {
		return this.bossId == BOSS_ONE_CFGID;
	}

	public void clearBeforeStart() {
		synchronized (mutex) {
			RANK.clear();
			RANK_ALL.clear();
		}
	}

	public void init(int bossId) {
		synchronized (mutex) {
			if (isBattle()) {
				return;
			}
			this.bossId = bossId;
			baseMonsterVO = MonsterRes.getInstance().getBaseMonsterVO(bossId);
			hp = baseMonsterVO.getHp() * ActivityConstant.BOSS_HP_RATE;
			clearBeforeStart();
			if (bossId == BOSS_ONE_CFGID) {
				boss1Status = BOSS_STATUS.BATTLING;
				boss2Status = BOSS_STATUS.BEFORE;
				process = 1;
			} else {
				boss1Status = BOSS_STATUS.END;
				boss2Status = BOSS_STATUS.BATTLING;
				process = 3;
			}
		}
	}

	/**
	 * 是否正在战斗
	 */
	public boolean isBattle() {
		synchronized (mutex) {
			return boss1Status == BOSS_STATUS.BATTLING || boss2Status == BOSS_STATUS.BATTLING;
		}
	}

	public void over() {
		synchronized (mutex) {
			// baseMonsterVO = null;
			hp = 0;
			if (bossId == BOSS_ONE_CFGID) {
				boss1Status = BOSS_STATUS.OVER;
				process = 2;
			} else if (bossId == BOSS_TWO_CFGID) {
				boss2Status = BOSS_STATUS.OVER;
				process = 4;
			}
		}
	}

	public void end() {
		synchronized (mutex) {
			// baseMonsterVO = null;
			hp = 0;
			if (bossId == BOSS_ONE_CFGID) {
				boss1Status = BOSS_STATUS.END;
				process = 2;
			} else if (bossId == BOSS_TWO_CFGID) {
				boss2Status = BOSS_STATUS.END;
				process = 4;
			}
		}
	}

	public BOSS_STATUS getBossStatus(int bossId) {
		synchronized (mutex) {
			return bossId == BOSS_ONE_CFGID ? boss1Status : boss2Status;
		}
	}

	public int getHp() {
		synchronized (mutex) {
			return hp;
		}
	}

	public int subHp(int subHp) {
		synchronized (mutex) {
			if (hp <= subHp) {
				return 0;
			}
			hp -= subHp;
			return hp;
		}
	}

	public BaseMonsterVO getBaseMonsterVO() {
		return baseMonsterVO;
	}

	public Map<Integer, BossLogVO> getRANK_ALL() {
		return RANK_ALL;
	}

}
