package me.magicall.coll.transformed;

import me.magicall.coll.CollFactory.L;
import me.magicall.coll.ElementNotNull;
import me.magicall.coll.ElementTransformer;
import me.magicall.coll.unmodifiable.UnmodifiableListTemplate;

import java.io.Serializable;
import java.util.List;
import java.util.RandomAccess;


/**
 * 把一个list包装成另一种元素的list,作为源list的一种特殊的视图
 * 
 * @author MaGiCalL
 * @param <F>
 * @param <T>
 */
public class TransformedList<F, T> extends UnmodifiableListTemplate<T>//
		implements ElementNotNull, Serializable {

	private static final long serialVersionUID = -314539026805799559L;

	private final List<? extends F> list;
	private final ElementTransformer<? super F, ? extends T> transformer;

	public TransformedList(final List<? extends F> list, final ElementTransformer<? super F, ? extends T> transformer) {
		super();
		this.list = list;
		this.transformer = transformer;
	}

	@Override
	protected T get0(final int index) {
		return transformer.transform(index, list.get(index));
	}

	@Override
	public int size() {
		return list.size();
	}

	//----------------------------------------------------------
	public static class RandomAccessTransformedList<F, T> extends TransformedList<F, T>//
			implements RandomAccess {

		private static final long serialVersionUID = -6993605795869236378L;

		public RandomAccessTransformedList(final List<? extends F> list, final ElementTransformer<? super F, ? extends T> transformer) {
			super(list, transformer);
		}
	}

	public static void main(final String... arg) {
		final ElementTransformer<Integer, String> transformer = (index, element) ->
				String.valueOf(index) + ':' + String.valueOf(element);
		final List<Integer> is = L.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		final List<String> list = new TransformedList<>(is, transformer);
		System.out.println("@@@@@@" + list);

//		final Iterator<String> i = list.iterator();
//		i.next();
//		i.remove();
//		System.out.println("@@@@@@" + list);

//		final List<String> sub = list.subList(0, 2);
//		final Iterator<String> i = sub.iterator();
//		i.next();
//		i.remove();
//		System.out.println("@@@@@@" + sub);
	}
}
