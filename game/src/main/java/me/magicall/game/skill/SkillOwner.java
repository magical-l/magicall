package me.magicall.game.skill;

import java.util.Collection;

/**
 * 拥有技能的实体。
 * 
 * @author MaGiCalL
 */
public interface SkillOwner {

	boolean hasSkill(Skill skill);

	Collection<? extends Skill> getSkills();

	void addSkill(Skill skill);

	void removeSkill(Skill skill);

	Skill getSkill(String name);
}
