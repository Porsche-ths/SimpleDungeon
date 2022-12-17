package skill.base;

import java.util.ArrayList;

import character.base.BaseCharacter;

public abstract class BaseSkill {
	
	protected String skillName;
	protected Character user;
	protected ArrayList<BaseCharacter> targets;
	
	public abstract void cast();

}
