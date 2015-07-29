package me.magicall.game.io;

public abstract class AbsGameInput implements GameInput {

	private volatile InputHandler cur;

	@Override
	public void requestInput(final InputHandler inputHandler) throws OperatingException {
		//synchronized?
		cur = inputHandler;
		final Object nextInput = nextInput();
		cur.handle(nextInput);
	}

	protected abstract Object nextInput();

}