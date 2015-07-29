package me.magicall.game.sub.chess;

import java.util.Collection;

import me.magicall.game.skill.Skill;
import me.magicall.game.skill.SkillManager;

public class BaseChessman extends ChessmanTemplate {

	private final String name;
	private final SkillManager skillManager;

	public BaseChessman(final String name) {
		this(name, new SkillManager());
	}

	public BaseChessman(final String name, final SkillManager skillManager) {
		super();
		this.name = name;
		this.skillManager = skillManager;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Collection<Skill> getSkills() {
		return skillManager.getSkills();
	}

	@Override
	public void addSkill(final Skill skill) {
		skillManager.addSkill(skill);
	}

	@Override
	public void removeSkill(final Skill skill) {
		skillManager.removeSkill(skill);
	}

	@Override
	public Skill getSkill(final String name) {
		return skillManager.getSkill(name);
	}

	@Override
	public boolean hasSkill(final Skill skill) {
		return skillManager.hasSkill(skill);
	}

}
