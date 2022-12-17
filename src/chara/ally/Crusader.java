package chara.ally;

import chara.base.Ally;
import skill.ally.HolySmite;
import skill.ally.SwordBash;

public class Crusader extends Ally {

	public Crusader(String name) {
		super(name, "crusader", 10, 33, 0, 5, 3, 10, 6, 12, 1);
		getSkills().add(new SwordBash(this));
		getSkills().add(new HolySmite(this));
	}

}
