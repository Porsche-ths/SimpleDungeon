package chara.base;

import logic.GameLogic;

public class Ally extends BaseCharacter{
	
	private int deathBlowResist;
	private int targetPriority;
	
	public Ally(String name, String className, int targetPriority, int maxHp, int accMod, int dodge, int crit, int prot, int minDmg, int maxDmg, int spd) {
		super(name, className, maxHp, accMod, dodge, crit, prot, minDmg, maxDmg, spd);
		this.setTargetPriority(targetPriority);
		this.setDeathBlowResist(67);
	}

	@Override
	public void beginTurn() {
		if (!this.isAlive()) {
			GameLogic.nextTurn();
			return;
		}
		GameLogic.getCurrentStage().getBattlePane().initializeSkillMenu();
	}
	
	@Override
	public void checkStatus() {
		if (isAlive()) {
			if (getHp() == 0) {
				setAlive(false);
				for (Ally each: GameLogic.getTeam()) {
					GameLogic.getCurrentStage().getStageCharaPane().updateHpBar(each,100);
				}
				for(BaseCharacter e :GameLogic.getEnemies()) {
					GameLogic.getCurrentStage().getStageCharaPane().updateHpBar(e, 100);
				}
			}
		} else {
			if (getHp() > 0) {
				setAlive(true);
			} else {
				if (isDeathBlown()) {
					GameLogic.getTeam().remove(this);
					if (GameLogic.getQueue().contains(this)) { GameLogic.getQueue().remove(this); }
					if (GameLogic.getTeam().isEmpty()) {
						GameLogic.setGameEnd(true);
					} else {
						GameLogic.getCurrentStage().getStageCharaPane().getChildren().clear();
						GameLogic.getCurrentStage().getStageCharaPane().addCharToPane();
						int n = GameLogic.getTeam().size() - 1;
						for (Ally a: GameLogic.getTeam()) {
							switch(n) {
							case 0:
								a.setRank(logic.rank.first); break;
							case 1:
								a.setRank(logic.rank.second); break;
							case 2:
								a.setRank(logic.rank.third); break;
							case 3:
								a.setRank(logic.rank.fourth); break;
							}
							n--;
						}
						for (Ally ally: GameLogic.getTeam()) {
							GameLogic.getCurrentStage().getStageCharaPane().updateHpBar(ally,100);
						}
						for(Enemy enemy :GameLogic.getEnemies()) {
							GameLogic.getCurrentStage().getStageCharaPane().updateHpBar(enemy, 100);
						}
					}
				} else {
					setDeathBlowResist(getDeathBlowResist() - 16);
				}
			}
		}
	}
	
	@Override
	public void setHp(int hp) {
		super.setHp(hp);
		checkStatus();
	}
	
	public boolean isDeathBlown() {
		int chance = 100 - this.getDeathBlowResist();
		return GameLogic.randomInt() < chance ? true : false;
	}

	public int getTargetPriority() {
		return targetPriority;
	}

	public void setTargetPriority(int targetPriority) {
		this.targetPriority = targetPriority;
	}
	
	public int getDeathBlowResist() {
		return deathBlowResist;
	}

	public void setDeathBlowResist(int deathBlowResist) {
		this.deathBlowResist = deathBlowResist;
	}

}
