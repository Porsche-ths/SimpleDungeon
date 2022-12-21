package chara.enemy;

import java.util.ArrayList;

import chara.base.Ally;
import chara.base.Enemy;
import javafx.util.Pair;
import logic.GameLogic;
import skill.enemy.RavenousFeast;

public class Hemomancer extends Enemy {

	public Hemomancer(String name) {
		super(name, "hemomancer", 20, 0, 23, 10, 0, 4, 7, 6);
		getSkills().add(new RavenousFeast(this));
	}

	@Override
	public void beginTurn() {
		if (this.isAlive()) {
			RavenousFeast ravenousFeast = (RavenousFeast) getSkills().get(0);
			if (ravenousFeast.isValid()) {
				int size = GameLogic.getTeam().size();
				
				if (size != 0) {
					ArrayList<Pair<Ally, Double>> targetList = new ArrayList<Pair<Ally, Double>>(size);
					
					double mod = 100/size;
					for (Ally hero: GameLogic.getTeam()) {
						targetList.add(new Pair<Ally, Double>(hero, mod));
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
