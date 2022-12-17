package chara.ally;

import chara.base.Ally;
import skill.ally.DaggersPlay;
import skill.ally.StabInTheFace;

public class Rogue extends Ally {

	public Rogue(String name) {
		super(name, "rogue", 20, 20, 0, 20, 7, 0, 4, 8, 9);
		getSkills().add(new DaggersPlay(this));
		getSkills().add(new StabInTheFace(this));
	}

}
