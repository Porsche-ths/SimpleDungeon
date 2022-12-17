package skill.base;

import java.util.ArrayList;

import chara.base.BaseCharacter;

public abstract class BaseSkill {
	
	protected String skillName;
	protected BaseCharacter user;
	protected ArrayList<logic.rank> rank;
	protected boolean isValid;
	protected ArrayList<BaseCharacter> targets;
	
	public BaseSkill(String skillName, BaseCharacter user, ArrayList<logic.rank> rank) {
		this.setSkillName(skillName);
		this.user = user;
		this.setRank(rank);
		this.setTargets(new ArrayList<BaseCharacter>());
	}
	
	public abstract void cast();
	
	public abstract void playAnimation();
	
	public boolean isValid() {
		return this.isValid;
	}
	
	public void setValid() {
		boolean result = false;
		if (user.isAlive()) {
			for (logic.rank r: this.getRank()) {
				if (r.equals(user.getRank())) { result = true; break; }
			}	
		}
		this.isValid = result;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public ArrayList<logic.rank> getRank() {
		return rank;
	}

	public void setRank(ArrayList<logic.rank> rank) {
		this.rank = rank;
	}

	public ArrayList<BaseCharacter> getTargets() {
		return targets;
	}

	public void setTargets(ArrayList<BaseCharacter> targets) {
		this.targets = targets;
	}

	public BaseCharacter getUser() {
		return user;
	}
	
}