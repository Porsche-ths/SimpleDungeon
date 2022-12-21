package skill.base;

import java.util.ArrayList;

import chara.base.BaseCharacter;
import logic.GameLogic;
import logic.rank;

public class HealSkill extends BaseSkill {
	
	private int minHeal;
	private int maxHeal;
	private int healAmount;
	private String result;
	
	public HealSkill(String skillName, BaseCharacter user, ArrayList<rank> userRank, ArrayList<rank> targetRank, int minHeal, int maxHeal) {
		super(skillName, user, userRank, targetRank);
		this.setMinHeal(minHeal);
		this.setMaxHeal(maxHeal);
	}

	@Override
	public void cast() {
		result = "";
		for (BaseCharacter each: targets) {
			computeHealAmount(each);
			each.setHp(each.getHp() + this.getHealAmount());
			if(targets.size() > 1) {
				result +="Restore " + this.getHealAmount() + " HP!,";
			}
			else {
				result += "Restore " + this.getHealAmount() + " HP!";
			}
			GameLogic.getCurrentStage().getStageCharaPane().updateHpBar(each,100);
		}
		targets.clear();
	}
	
	@Override
	public void playAnimation() {}
	
	private boolean isCrit() {
		return GameLogic.randomInt() < user.getCrit() ? true : false;
	}
	
	private void computeHealAmount(BaseCharacter target) {
		int healAmount = GameLogic.randomRange(minHeal, maxHeal);
		this.setHealAmount((int) (isCrit() ? (healAmount * 1.5) : healAmount));
	}

	public int getMinHeal() {
		return minHeal;
	}

	public void setMinHeal(int minHeal) {
		this.minHeal = minHeal;
	}

	public int getMaxHeal() {
		return maxHeal;
	}

	public void setMaxHeal(int maxHeal) {
		this.maxHeal = maxHeal;
	}
	
	public int getHealAmount() {
		return healAmount;
	}

	public void setHealAmount(int healAmount) {
		this.healAmount = healAmount;
	}

	public String getResult() {
		return result;
	}

}
