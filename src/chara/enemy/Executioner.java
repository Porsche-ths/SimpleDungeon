package chara.enemy;

import java.util.ArrayList;

import chara.base.Ally;
import chara.base.Enemy;
import javafx.util.Pair;
import logic.GameLogic;
import skill.enemy.SonicBlow;

public class Executioner extends Enemy {

	public Executioner(String name) {
		super(name, "executioner", 22, 82, 10, 5, 25, 7, 8, 1);
		getSkills().add(new SonicBlow(this));
	}

	@Override
	public void beginTurn() {
		if (this.isAlive()) {
			SonicBlow sonicBlow = (SonicBlow) getSkills().get(0);
			sonicBlow.setValid();
			if (sonicBlow.isValid()) {
				int size = 0;
				
				for (Ally hero: GameLogic.getTeam()) {
					if (hero.getRank().equals(logic.rank.first) || hero.getRank().equals(logic.rank.second)) size++;
				}
				
				if (size != 0) {
					ArrayList<Pair<Ally, Double>> targetList = new ArrayList<Pair<Ally, Double>>(size);
					
					double mod = 100/size;
					for (Ally hero: GameLogic.getTeam()) {
						if (hero.getRank().equals(logic.rank.first) || hero.getRank().equals(logic.rank.second)) {
							targetList.add(new Pair<Ally, Double>(hero, mod));
						}
					}
					
					for (int i = 0; i < size; i++) {
						Ally ally = targetList.get(i).getKey();
						for (int j = 0; j < size; j++) {
							if (targetList.get(j).getKey().equals(ally)) {
								targetList.set(j, new Pair<Ally, Double>(targetList.get(j).getKey(), targetList.get(j).getValue() + ((double) ally.getTargetPriority())));
							} else {
								targetList.set(j, new Pair<Ally, Double>(targetList.get(j).getKey(), targetList.get(j).getValue() - (((double) ally.getTargetPriority())/(size-1))));
							}
						}
					}
					
					for (int i = 0; i < size; i++) {
						Ally ally = targetList.get(i).getKey();
						double chance = ( ((double) (ally.getMaxHp() - ally.getHp())) / ((double) ally.getMaxHp()) ) * mod;
						for (int j = 0; j < size; j++) {
							if (targetList.get(j).getKey().equals(ally)) {
								targetList.set(j, new Pair<Ally, Double>(targetList.get(j).getKey(), targetList.get(j).getValue() + chance));
							} else {
								targetList.set(j, new Pair<Ally, Double>(targetList.get(j).getKey(), targetList.get(j).getValue() - (chance/(size-1))));
							}
						}
					}
					
					int result = GameLogic.randomInt();
					int check = 0;
					for (int i = 0; i < size; i++) {
						check += targetList.get(i).getValue();
						if (result < check) {
							getSkills().get(0).getTargets().add(targetList.get(i).getKey());
							break;
						} else if (i == size - 1) {
							getSkills().get(0).getTargets().add(targetList.get(i).getKey());
							break;
						}
					}
					
					getSkills().get(0).playAnimation();
					getSkills().get(0).cast();
				} else {
					GameLogic.nextTurn();
				}
			} else {
				GameLogic.nextTurn();
			}
		} else {
			GameLogic.nextTurn();
		}
	}
	
}
