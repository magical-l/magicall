package me.magicall.game.sub.round.abs;

import java.util.Collection;

import me.magicall.game.Game;
import me.magicall.game.player.Player;
import me.magicall.game.skill.Skill;
import me.magicall.game.skill.SkillManager;
import me.magicall.game.sub.round.Round;
import me.magicall.game.sub.round.RoundGame;
import me.magicall.game.sub.round.RoundGamePlayerRole;

public abstract class AbsRoundGamePlayerRole<G extends RoundGame, R extends Round> implements RoundGamePlayerRole {

	private final G game;
	private final String name;

	private final int order;

	private Player player;

	protected final SkillManager skillManager = new SkillManager();

	protected AbsRoundGamePlayerRole(final G game, final String name, final int order) {
		super();
		this.game = game;
		this.name = name;
		this.order = order;
	}

	protected AbsRoundGamePlayerRole(final G game, final String name) {
		this(game, name, 0);
	}

	protected AbsRoundGamePlayerRole(final G game, final int order) {
		super();
		this.game = game;
		name = getClass().getSimpleName();
		this.order = order;
	}

	protected AbsRoundGamePlayerRole(final G game) {
		this(game, 0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void play(final Round round) {
		playInternal((R) round);
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

	protected abstract void playInternal(final R round);

	@Override
	public int getOrder() {
		return order;
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public boolean hasSkill(final Skill skill) {
		return skillManager.hasSkill(skill);
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public void setPlayer(final Player player) {
		this.player = player;
	}

	@Override
	public Game getGame() {
		return game;
	}
}
