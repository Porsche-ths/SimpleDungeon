package skill.base;

import java.util.ArrayList;

import chara.base.Ally;
import chara.base.BaseCharacter;
import logic.GameLogic;
import logic.rank;

public class DamageSkill extends BaseSkill {

	private int dmgMod;
	private int acc;
	private int critMod;
	private int damageDeal;
	protected String result;
	
	public DamageSkill(String skillName, BaseCharacter user, ArrayList<rank> userRank, ArrayList<rank> targetRank, int dmgMod, int acc, int critMod) {
		super(skillName, user, userRank, targetRank);
		this.setDmgMod(dmgMod);
		this.setAcc(acc);
		this.setCritMod(critMod);
	}

	@Override
	public void cast() {
		result = "";
		for (BaseCharacter target: this.getTargets()) {
			if (isHit(target)) {
				computeDamage(target);
				target.setHp(target.getHp() - this.getDamageDeal());
				if (this.getTargets().size() > 1) {
					result += "It dealt " + this.getDamageDeal() + " damage!,";
				} else {
					result = "It dealt " + this.getDamageDeal() + " damage!";
				}
				if(target.getHp() != 0) {
					GameLogic.getCurrentStage().getStageCharaPane().updateHpBar(target, 100);
				}
			} else {
				if (target instanceof Ally) {
					result = "You dodge!,";
				} else if (this.getTargets().size() > 1) {
					result += "It missed!,";
				} else {
					result = "It missed!";
				}
			}
		}
		targets.clear();
	}
	
	@Override
	public void playAnimation() {}
	
	private boolean isHit(BaseCharacter target) {
		int finalAcc = user.getAccMod() + getAcc() - target.getDodge();
		return GameLogic.randomInt() < finalAcc ? true : false;
	}
	
	private boolean isCrit() {
		int finalCrit = user.getCrit() + getCritMod();
		return GameLogic.randomInt() < finalCrit ? true : false;
	}
	
	private void computeDamage(BaseCharacter target) {
		float damage = (float) (GameLogic.randomRange(user.getMinDmg(), user.getMaxDmg()) * (1 + (((float)dmgMod)/100)));
		this.setDamageDeal((int) (isCrit() ? (damage * 1.5) : damage));
	}

	public int getDmgMod() {
		return dmgMod;
	}

	public void setDmgMod(int dmgMod) {
		this.dmgMod = dmgMod;
	}

	public int getAcc() {
		return acc;
	}

	public void setAcc(int acc) {
		this.acc = acc;
	}

	public int getCritMod() {
		return critMod;
	}

	public void setCritMod(int critMod) {
		this.critMod = critMod;
	}
	
	public int getDamageDeal() {
		return damageDeal;
	}

	public void setDamageDeal(int damageDeal) {
		this.damageDeal = damageDeal;
	}

	public String getResult() {
		return result;
	}
	
}
