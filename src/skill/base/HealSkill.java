package skill.base;

import java.util.ArrayList;

import chara.base.BaseCharacter;
import logic.GameLogic;

public class HealSkill extends BaseSkill {
	
	private int minHeal;
	private int maxHeal;
	private String result;

	public HealSkill(String skillName, BaseCharacter user, ArrayList<logic.rank> rank, int minHeal, int maxHeal) {
		super(skillName, user, rank);
		this.setMinHeal(minHeal);
		this.setMaxHeal(maxHeal);
	}

	@Override
	public void cast() {
		result = "";
		for (BaseCharacter each: targets) {
			int healAmount = computeHealAmount(each);
			each.setHp(each.getHp() + healAmount);
			if(targets.size() > 1) {
				result +="Restore " + healAmount + " HP!,";
			}
			else {
				result += "Restore " + healAmount + " HP!";
			}
			GameLogic.getCurrentStage().getStageCharaPane().updateHpBar(each,100);
		}
		targets.clear();
	}
	
	private boolean isCrit() {
		return GameLogic.randomInt() < user.getCrit() ? true : false;
	}
	
	private int computeHealAmount(BaseCharacter target) {
		int healAmount = GameLogic.randomRange(minHeal, maxHeal);
		return (int) (isCrit() ? (healAmount * 1.5) : healAmount);
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

	@Override
	public void playAnimation() {}
	
	public String getResult() {
		return result;
	}

}
