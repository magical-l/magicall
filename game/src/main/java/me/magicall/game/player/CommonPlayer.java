package me.magicall.game.player;

import java.util.Collection;

import me.magicall.game.io.GameInput;
import me.magicall.game.io.GameOutput;
import me.magicall.game.skill.Skill;
import me.magicall.game.skill.SkillManager;

public class CommonPlayer implements Player {

	private final String name;

//	private Game game;

	private GameInput gameInput;
	private GameOutput gameOutput;
	private SkillManager skillManager;

	public CommonPlayer() {
		super();
		name = getClass().getSimpleName();
	}

	public CommonPlayer(final String name) {
		super();
		this.name = name;
	}

	@Override
	public GameInput getGameInput() {
		return gameInput;
	}

	@Override
	public GameOutput getGameOutput() {
		return gameOutput;
	}

	public void setGameInput(final GameInput gameInput) {
		this.gameInput = gameInput;
	}

	public void setGameOutput(final GameOutput gameOutput) {
		this.gameOutput = gameOutput;
	}

	@Override
	public String getName() {
		return name;
	}

//	public Game getGame() {
//		return game;
//	}
//
//	public void setGame(final Game game) {
//		this.game = game;
//	}

	public boolean hasSkill(final Skill skill) {
		return skillManager.hasSkill(skill);
	}

	public Collection<Skill> getSkills() {
		return skillManager.getSkills();
	}

	public void addSkill(final Skill skill) {
		skillManager.addSkill(skill);
	}

	public void removeSkill(final Skill skill) {
		skillManager.removeSkill(skill);
	}

	public Skill getSkill(final String name) {
		return skillManager.getSkill(name);
	}

}
