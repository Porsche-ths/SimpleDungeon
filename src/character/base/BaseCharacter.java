package character.base;

import java.util.ArrayList;

import skill.base.BaseSkill;

public abstract class BaseCharacter {
	
	protected String name, className;
	protected int maxHp, hp, accMod, dodge, crit, prot, minDmg, maxDmg, spd;
	protected int calculatedSpd;
	protected ArrayList<BaseSkill> skills;
	protected logic.rank rank;
	protected boolean isAlive;
	
	public BaseCharacter() {
		
	}

	public abstract void beginTurn();
	
}
