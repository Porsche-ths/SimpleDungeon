package chara.base;

import logic.GameLogic;

public class Enemy extends BaseCharacter {
	
	public Enemy(String name, String className, int maxHp, int accMod, int dodge, int crit, int prot, int minDmg, int maxDmg, int spd) {
		super(name, className, maxHp, accMod, dodge, crit, prot, minDmg, maxDmg, spd);
	}

	@Override
	public void beginTurn() {}

	@Override
	public void checkStatus() {
		if (isAlive()) {
			if (getHp() == 0) {
				setAlive(false);
				super.setHp(10);
				setDodge(0);
				setProt(0);
				setMaxHp(10);
				GameLogic.getCurrentStage().getStageCharaPane().getChildren().clear();
				GameLogic.getCurrentStage().getStageCharaPane().addCharToPane();
				for(BaseCharacter e :GameLogic.getTeam()) {
					GameLogic.getCurrentStage().getStageCharaPane().updateHpBar(e, 100);
				}
				for(BaseCharacter e :GameLogic.getEnemies()) {
					GameLogic.getCurrentStage().getStageCharaPane().updateHpBar(e, 100);
				}
				boolean allDead = true;
				for (Enemy e: GameLogic.getEnemies()) {
					if (e.isAlive()) {
						allDead = false;
						break;
					}
				}
				if (allDead) {
					GameLogic.setStageCleared(true);
				}
			}
		} else {
			if (getHp() == 0) {
				GameLogic.getEnemies().remove(this);
				if (GameLogic.getQueue().contains(this)) { GameLogic.getQueue().remove(this); }
				if (GameLogic.getEnemies().isEmpty()) {
					GameLogic.setStageCleared(true);
				} else {
					GameLogic.getCurrentStage().getStageCharaPane().getChildren().clear();
					GameLogic.getCurrentStage().getStageCharaPane().addCharToPane();
					int n = 0;
					for (Enemy e: GameLogic.getEnemies()) {
						switch(n) {
						case 0:
							e.setRank(logic.rank.first); break;
						case 1:
							e.setRank(logic.rank.second); break;
						case 2:
							e.setRank(logic.rank.third); break;
						case 3:
							e.setRank(logic.rank.fourth); break;
						}
						n++;
					}
					for(BaseCharacter e :GameLogic.getTeam()) {
						GameLogic.getCurrentStage().getStageCharaPane().updateHpBar(e, 100);
					}
					for(BaseCharacter e :GameLogic.getEnemies()) {
						GameLogic.getCurrentStage().getStageCharaPane().updateHpBar(e, 100);
					}
				}
			}
		}
	}
	
	@Override
	public void setHp(int hp) {
		super.setHp(hp);
		checkStatus();
	}
	
}