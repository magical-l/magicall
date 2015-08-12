package me.magicall.io;

import java.io.Closeable;
import java.io.IOException;

@FunctionalInterface
public interface CloseableCreater<C extends Closeable> {

	C create() throws IOException;
}
