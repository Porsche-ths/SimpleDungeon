package chara.enemy;

import java.util.ArrayList;

import chara.base.Ally;
import chara.base.Enemy;
import javafx.util.Pair;
import logic.GameLogic;
import skill.enemy.BonyShot;

public class SkellyArcher extends Enemy {

	public SkellyArcher(String name) {
		super(name, "skellyArcher", 15, 82, 5, 12, 0, 3, 7, 5);
		getSkills().add(new BonyShot(this));
	}
	
	@Override
	public void beginTurn() {
		if (this.isAlive()) {
			BonyShot bonyShot = (BonyShot) getSkills().get(0);
			bonyShot.setValid();
			if (bonyShot.isValid()) {
				int size = 0;
				
				for (Ally hero: GameLogic.team) {
					if (hero.getRank().equals(logic.rank.second) || hero.getRank().equals(logic.rank.third) || hero.getRank().equals(logic.rank.fourth)) size++;
				}
				
				if (size != 0) {
					ArrayList<Pair<Ally, Double>> targetList = new ArrayList<Pair<Ally, Double>>(size);
					
					double mod = 100/size;
					for (Ally hero: GameLogic.team) {
						if (hero.getRank().equals(logic.rank.second) || hero.getRank().equals(logic.rank.third) || hero.getRank().equals(logic.rank.fourth)) {
							targetList.add(new Pair<Ally, Double>(hero, mod));
						}
					}
				
					for (int i = 0; i < size; i++) {
						Ally ally = targetList.get(i).getKey();
						for (int j = 0; j < size; j++) {
							if (targetList.get(j).getKey().equals(ally)) {
								targetList.set(j, new Pair<Ally, Double>(targetList.get(j).getKey(), targetList.get(j).getValue() + ((double) ally.getTargetPriority())));
							} else {
								targetList.set(j, new Pair<Ally, Double>(targetList.get(j).getKey(), targetList.get(j).getValue() - (((double) ally.getTargetPriority())/((double) (size-1)))));
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
								targetList.set(j, new Pair<Ally, Double>(targetList.get(j).getKey(), targetList.get(j).getValue() - (chance/((double) (size-1)))));
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
