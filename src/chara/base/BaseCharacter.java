package chara.base;

import java.util.ArrayList;

import skill.base.BaseSkill;

public abstract class BaseCharacter {
	protected String name, className;
	protected int maxHp, hp, accMod, dodge, crit, prot, minDmg, maxDmg, spd, calculatedSpd;
	protected ArrayList<BaseSkill> skills;
	protected logic.rank rank;
	protected boolean isAlive;

	public BaseCharacter(String name, String className, int maxHp, int accMod, int dodge, int crit, int prot, int minDmg, int maxDmg, int spd) {
		setSkills(new ArrayList<BaseSkill>());
		setName(name);
		setClassName(className);
		setMaxHp(maxHp);
		setHp(maxHp);
		setAccMod(accMod);
		setDodge(dodge);
		setCrit(crit);
		setProt(prot);
		setMinDmg(minDmg);
		setMaxDmg(maxDmg);
		setSpd(spd);
		setAlive(true);
	}
	
	public abstract void beginTurn();
	
	public abstract void checkStatus();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		if (hp < 0)
			hp = 0;
		if (hp > this.getMaxHp())
			hp = this.getMaxHp();
		this.hp = hp;
	}

	public int getAccMod() {
		return accMod;
	}

	public void setAccMod(int accMod) {
		this.accMod = accMod;
	}

	public int getDodge() {
		return dodge;
	}

	public void setDodge(int dodge) {
		this.dodge = dodge;
	}

	public int getCrit() {
		return crit;
	}

	public void setCrit(int crit) {
		this.crit = crit;
	}

	public int getProt() {
		return prot;
	}

	public void setProt(int prot) {
		this.prot = prot;
	}

	public int getMinDmg() {
		return minDmg;
	}

	public int getMaxDmg() {
		return maxDmg;
	}

	public void setMaxDmg(int maxDmg) {
		this.maxDmg = maxDmg;
	}

	public void setMinDmg(int minDmg) {
		this.minDmg = minDmg;
	}

	public int getSpd() {
		return spd;
	}

	public void setSpd(int spd) {
		this.spd = spd;
	}

	public ArrayList<BaseSkill> getSkills() {
		return skills;
	}

	public void setSkills(ArrayList<BaseSkill> skills) {
		this.skills = skills;
	}

	public logic.rank getRank() {
		return rank;
	}

	public void setRank(logic.rank rank) {
		this.rank = rank;
	}

	public int getCalculatedSpd() {
		return this.calculatedSpd;
	}

	public void setCalculatedSpd(int calculatedSpd) {
		this.calculatedSpd = calculatedSpd;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
}
