package com.dh.game.vo.base;

/**
 * 被动技能
 * 
 * @author RickYu
 * 
 */
public class PassivesSkillVO {
	private int id;
	private int url;
	private String name;
	private int type;
	private int open;
	private int level;
	private String dec;
	private int target;
	private int probability;
	private int atktype;
	private int numtype;
	private int value;
	private String buff;
	private int bufType;
	private int energy;
	private int cd;
	private int effect1;
	private int effect2;
	private int effect3;
	private int refresh;
	private int state;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUrl() {
		return url;
	}

	public void setUrl(int url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getOpen() {
		return open;
	}

	public void setOpen(int open) {
		this.open = open;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getDec() {
		return dec;
	}

	public void setDec(String dec) {
		this.dec = dec;
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	public int getProbability() {
		return probability;
	}

	public void setProbability(int probability) {
		this.probability = probability;
	}

	public int getAtktype() {
		return atktype;
	}

	public void setAtktype(int atktype) {
		this.atktype = atktype;
	}

	public int getNumtype() {
		return numtype;
	}

	public void setNumtype(int numtype) {
		this.numtype = numtype;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getBuff() {
		return buff;
	}

	public void setBuff(String buff) {
		this.buff = buff;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public int getCd() {
		return cd;
	}

	public void setCd(int cd) {
		this.cd = cd;
	}

	public int getEffect1() {
		return effect1;
	}

	public void setEffect1(int effect1) {
		this.effect1 = effect1;
	}

	public int getEffect2() {
		return effect2;
	}

	public void setEffect2(int effect2) {
		this.effect2 = effect2;
	}

	public int getEffect3() {
		return effect3;
	}

	public void setEffect3(int effect3) {
		this.effect3 = effect3;
	}

	public int getRefresh() {
		return refresh;
	}

	public void setRefresh(int refresh) {
		this.refresh = refresh;
	}

	public int getBufType() {
		return bufType;
	}

	public void setBufType(int bufType) {
		this.bufType = bufType;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public PassivesSkillVO clone() {
		PassivesSkillVO ps = new PassivesSkillVO();
		ps.setId(this.getId());
		ps.setLevel(this.getLevel());
		ps.setState(this.getState());
		return ps;
	}

}
