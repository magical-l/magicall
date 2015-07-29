package me.magicall.game.skill;

import java.util.Collection;

import me.magicall.coll.CollFactory;
import me.magicall.coll.CollFactory.L;
import me.magicall.game.player.PlayerRole;
import me.magicall.util.kit.Kits;

/**
 * 一次技能的施放
 * 
 * @author MaGiCalL
 */
public class SkillOperation {

	private PlayerRole playerRole;

	private Skill skill;

	private Collection<?> targets;

	private Collection<? extends Object> skillArgs;

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(playerRole.getName()).append(' ')//
				.append(skill.getName()).append(" → ")//
				.append(targets).append(" {").append(skillArgs).append('}');
		return sb.toString();
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(final Skill skill) {
		this.skill = skill;
	}

	public <T> Collection<T> getTargets() {
		return Kits.COLL.forceSuit(targets);
	}

	public void setTarget(final Object target) {
		targets = L.asList(target);
	}

	public void setTargets(final Object... targets) {
		this.targets = L.asList(targets);
	}

	public void setTargets(final Collection<?> targets) {
		this.targets = targets;
	}

	@SuppressWarnings("unchecked")
	public <P extends PlayerRole> P getPlayerRole() {
		return (P) playerRole;
	}

	public void setPlayerRole(final PlayerRole playerRole) {
		this.playerRole = playerRole;
	}

	public <A extends Object> Collection<A> getSkillArgs() {
		return Kits.COLL.forceSuit(skillArgs);
	}

	public void setSkillArg(final Object skillArg) {
		skillArgs = L.asList(skillArg);
	}

	public void setSkillArgs(final Object... skillArgs) {
		this.skillArgs = L.asList(skillArgs);
	}

	public void setSkillArgs(final Collection<? extends Object> skillArgs) {
		this.skillArgs = skillArgs;
	}
}
