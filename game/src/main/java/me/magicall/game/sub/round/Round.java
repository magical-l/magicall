package me.magicall.game.sub.round;

import java.util.Collection;

import me.magicall.game.skill.SkillOperation;

/**
 * 一个回合
 * 
 * @author MaGiCalL
 */
public interface Round {

	Collection<SkillOperation> getSkillOperations();

	void addSkillOperation(SkillOperation skillOperation);

}
