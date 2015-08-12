package me.magicall.game.skill;

import java.util.Collection;

import me.magicall.game.Game;

@FunctionalInterface
public interface TargetSelector {

	Collection<?> select(Game game, Command command) throws NoTargetToBeSelectingException;
}
