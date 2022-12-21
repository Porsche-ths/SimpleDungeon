package skill.base;

import java.util.ArrayList;

import chara.base.BaseCharacter;

public abstract class BaseSkill {
	
	protected String skillName;
	protected final BaseCharacter user;
	protected ArrayList<logic.rank> userRank, targetRank;
	protected ArrayList<BaseCharacter> targets;
	
	public BaseSkill(String skillName, BaseCharacter user, ArrayList<logic.rank> userRank, ArrayList<logic.rank> targetRank) {
		this.setSkillName(skillName);
		this.user = user;
		this.setUserRank(userRank);
		this.setTargetRank(targetRank);
		this.setTargets(new ArrayList<BaseCharacter>());
	}
	
	public abstract void cast();
	
	public abstract void playAnimation();
	
	public boolean isValid() {
		return this.getUserRank().contains(user.getRank());
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public ArrayList<logic.rank> getUserRank() {
		return userRank;
	}

	public void setUserRank(ArrayList<logic.rank> userRank) {
		this.userRank = userRank;
	}

	public ArrayList<logic.rank> getTargetRank() {
		return targetRank;
	}

	public void setTargetRank(ArrayList<logic.rank> targetRank) {
		this.targetRank = targetRank;
	}

	public ArrayList<BaseCharacter> getTargets() {
		return targets;
	}

	public void setTargets(ArrayList<BaseCharacter> targets) {
		this.targets = targets;
	}

	public BaseCharacter getUser() {
		return this.user;
	}
	
}