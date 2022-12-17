package chara.ally;

import chara.base.Ally;
import skill.ally.DivineComfort;
import skill.ally.DivineGrace;

public class Priest extends Ally{

	public Priest(String name) {
		super(name, "priest", 30, 24, 0, 5, 1, 0, 4, 8, 4);
		getSkills().add(new DivineGrace(this));
		getSkills().add(new DivineComfort(this));
	}

}
