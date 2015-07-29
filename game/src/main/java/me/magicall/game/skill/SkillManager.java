package me.magicall.game.skill;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class SkillManager {
	protected Map<Character, Skill> skills;

	public SkillManager() {
		this(new LinkedHashMap<>());
	}

	public SkillManager(final Map<Character, Skill> skills) {
		super();
		this.skills = skills;
	}

	public boolean hasSkill(final Skill skill) {
		return skills.values().contains(skill);
	}

	public Collection<Skill> getSkills() {
		return skills.values();
	}

	public void addSkill(final Skill skill) {
		skills.put(Character.toLowerCase(skill.getHotKey()), skill);
	}

	public void removeSkill(final Skill skill) {
		skills.remove(Character.toLowerCase(skill.getHotKey()));
	}

	public Skill getSkill(final String name) {
		return skills.get(Character.toLowerCase(name.charAt(0)));
	}
}
