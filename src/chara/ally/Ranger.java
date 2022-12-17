package chara.ally;

import chara.base.Ally;
import skill.ally.ArrowStorm;
import skill.ally.KeenShot;

public class Ranger extends Ally {

	public Ranger(String name) {
		super(name, "ranger", 20, 25, 10, 10, 10, 0, 4, 8, 7);
		getSkills().add(new KeenShot(this));
		getSkills().add(new ArrowStorm(this));
	}

}
