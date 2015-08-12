package me.magicall.mark;

/**
 * 一个标记接口.表示实现类包装了另一个对象，两者实现了同一个接口，实现类将代理包装对象的方法。
 * 装饰模式
 * 
 * @author MaGicalL
 */
@FunctionalInterface
public interface Wrapper<T> {

    T unwrap();
}
