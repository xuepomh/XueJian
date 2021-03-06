package com.dh.game.vo.base;

//英雄等级基础数据
public class BaseLevelVO {
	private int level;
	private int exp; // 升到一下级所需要验
	private int maxExpc;// 经验池
	private int herolevel; //侠客等级上限

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getMaxExpc() {
		return maxExpc;
	}

	public void setMaxExpc(int maxExpc) {
		this.maxExpc = maxExpc;
	}

	public int getHerolevel() {
		return herolevel;
	}

	public void setHerolevel(int herolevel) {
		this.herolevel = herolevel;
	}

	
}
