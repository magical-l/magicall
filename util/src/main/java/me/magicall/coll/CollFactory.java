package me.magicall.coll;

import me.magicall.coll.combo.AppendList;
import me.magicall.coll.fixed.FixedArrayList;
import me.magicall.coll.fixed.OneList;
import me.magicall.coll.fixed.ThreeList;
import me.magicall.coll.fixed.TwoList;
import me.magicall.coll.numseq.LongSeqList;
import me.magicall.coll.numseq.SeqList;
import me.magicall.coll.transformed.TransformedIterator;
import me.magicall.coll.tree.Tree;
import me.magicall.coll.tree.Tree.TreeNode;
import me.magicall.coll.tree.TreeUtil;
import me.magicall.coll.unmodifiable.UnmodifiableEntryTemplate;
import me.magicall.coll.unmodifiable.UnmodifiableIteratorTemplate;
import me.magicall.coll.unmodifiable.UnmodifiableListIteratorTemplate;
import me.magicall.coll.wrap.UnmodifiableWrapSet;
import me.magicall.coll.wrap.UnmodifiableWrapTree;
import me.magicall.coll.wrap.UnmodifiableWrapTreeNode;
import me.magicall.mark.Unmodifiable;
import me.magicall.util.kit.Kits;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

/**
 * 一个奇怪的工厂
 * 
 * @author MaGicalL
 */
public class CollFactory {

	public static class C {

		public static <E> Collection<E> unmodifiable(final Collection<E> coll) {
			return Kits.COLL.unmodifiable(coll);
		}
	}

	/**
	 * list的工厂
	 * 
	 * @author MaGicalL
	 */
	public static class L {

		public static <E> List<E> asList(final E e) {
			return new OneList<>(e);
		}

		public static <E> List<E> asList(final E e1, final E e2) {
			return new TwoList<>(e1, e2);
		}

		public static <E> List<E> asList(final E e1, final E e2, final E e3) {
			return new ThreeList<>(e1, e2, e3);
		}

		@SafeVarargs
		public static <E> List<E> asList(final E... es) {
			return new FixedArrayList<>(es);
		}

		public static List<Long> seq(final long from, final int size) {
			return new LongSeqList(from, size);
		}

		public static List<Long> seq(final long from, final long step, final int size) {
			return step == 0 ? Collections.nCopies(size, from) : new LongSeqList(from, step, size);
		}

		public static List<Integer> seq(final int from, final int size) {
			return new SeqList(from, size);
		}

		public static List<Integer> seq(final int from, final int step, final int size) {
			return step == 0 ? Collections.nCopies(size, from) : new SeqList(from, step, size);
		}

		/**
		 * 正整数数列(从1开始)
		 * 
		 * @param size
		 * @return
		 */
		public static List<Integer> positiveSeq(final int size) {
			return new SeqList(1, size);
		}

		/**
		 * 自然数数列(从0开始)
		 * 
		 * @param size
		 * @return
		 */
		public static List<Integer> natureSeq(final int size) {
			return new SeqList(0, size);
		}

		public static <E> List<E> append(final List<E> list, final E e) {
			return new AppendList<>(list, e);
		}

		public static <E> List<E> append(final List<E> list, final E e1, final E e2) {
			return new AppendList<>(list, e1, e2);
		}

		public static <E> List<E> append(final List<E> list, final E e1, final E e2, final E e3) {
			return new AppendList<>(list, e1, e2, e3);
		}
	}

	/**
	 * set的工厂
	 * 
	 * @author MaGicalL
	 */
	public static class S {

		public static <E> HashSet<E> hashSet() {
			return new HashSet<>();
		}

		public static <E> HashSet<E> hashSet(final int size) {
			return new HashSet<>(size);
		}

		public static <E> TreeSet<E> treeSet() {
			return new TreeSet<>();
		}

		public static <E> TreeSet<E> treeSet(final Comparator<? super E> comparator) {
			return new TreeSet<>(comparator);
		}

		public static <E> Set<E> fromList(final List<E> list) {
			return new HashSet<>(list);
		}

		public static <E> Set<E> unmodifiable(final Set<E> set) {
			return set instanceof Unmodifiable ? set : new UnmodifiableWrapSet<>(set);
		}
	}

	/**
	 * map的工厂
	 * 
	 * @author MaGicalL
	 */
	public static class M {

		public static <K, V> HashMap<K, V> hashMap() {
			return new HashMap<>();
		}

		public static <K, V> HashMap<K, V> hashMap(final int size) {
			return new HashMap<>(size);
		}

		public static <K, V> Map<K, V> collectionBuildMap(final Collection<V> from, final KeyAccessor<? extends K, ? super V> elicitor) {
			final Map<K, V> map = new HashMap<>();
			for (final V e : from) {
				map.put(elicitor.extract(e), e);
			}
			return map;
		}

		public static <K, V> Entry<K, V> unmodifiable(final Entry<K, V> e) {
			return new UnmodifiableEntryTemplate<K, V>() {

				private static final long serialVersionUID = -8861993437550861277L;

				@Override
				public K getKey() {
					return e.getKey();
				}

				@Override
				public V getValue() {
					return e.getValue();
				}
			};
		}
	}

	public static class T {

		public static <E> Tree<E> unmodifiable(final Tree<E> tree) {
			if (tree == null) {
				return TreeUtil.emptyTree();
			}
			if (tree instanceof Unmodifiable) {
				return tree;
			}
			return new UnmodifiableWrapTree<>(tree);
		}

		public static <E> TreeNode<E> unmodifiable(final TreeNode<E> node) {
			if (node == null) {
				return null;
			}
			if (node instanceof Unmodifiable) {
				return node;
			}
			return new UnmodifiableWrapTreeNode<>(node);
		}
	}

	public static class I {

		@SuppressWarnings("unchecked")
		public static <E> Iterator<E> unmodifiable(final Iterator<? extends E> i) {
			return i instanceof Unmodifiable ? (Iterator<E>) i : new UnmodifiableIteratorTemplate<E>() {

				private static final long serialVersionUID = 334905501067612135L;

				@Override
				public boolean hasNext() {
					return i.hasNext();
				}

				@Override
				public E next() {
					return i.next();
				}
			};
		}

		public static <E> ListIterator<E> unmodifiable(final ListIterator<? extends E> i) {
			return new UnmodifiableListIteratorTemplate<E>() {

				private static final long serialVersionUID = 1052934987301875691L;

				@Override
				public boolean hasPrevious() {
					return i.hasPrevious();
				}

				@Override
				public E previous() {
					return i.previous();
				}

				@Override
				public int nextIndex() {
					return i.nextIndex();
				}

				@Override
				public int previousIndex() {
					return i.previousIndex();
				}

				@Override
				public boolean hasNext() {
					return i.hasNext();
				}

				@Override
				public E next() {
					return i.next();
				}
			};
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static <E> Iterator<E> emptyIterator() {
			return (Iterator) EMPTY_ITERATOR;
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static <E> ListIterator<E> emptyListIterator() {
			return (ListIterator) EMPTY_ITERATOR;
		}

		@SuppressWarnings("hiding")
		public static <F, T> Iterator<T> transformed(final Iterator<F> i, final ElementTransformer<F, T> tf) {
			return new TransformedIterator<>(i, tf);
		}

		public static <E> Iterator<E> comboIterator(final Collection<? extends Iterable<? extends E>> source) {
			return new ComboIterator<>(Kits.COLL.<Iterable<E>>forceSuit(source));
		}

		public static <E> ListIterator<E> nCopy(final int length, final E element) {
			if (length < 0) {
				throw new IllegalArgumentException("length < 0.it's " + length);
			} else if (length == 0) {
				return emptyListIterator();
			}
			return new NCopyIterator<>(element, length);
		}

		private static final class NCopyIterator<E> extends UnmodifiableListIteratorTemplate<E> {

			private static final long serialVersionUID = -8249997327013262818L;

			private final E element;
			private final int len;
			private int curIndex;

			public NCopyIterator(final E element, final int len) {
				super();
				this.element = element;
				this.len = len;
				curIndex = 0;
			}

			@Override
			public boolean hasNext() {
				return curIndex < len;
			}

			@Override
			public E next() {
				if (hasNext()) {
					curIndex++;
					return element;
				} else {
					throw new NoSuchElementException();
				}
			}

			@Override
			public boolean hasPrevious() {
				return curIndex > 0;
			}

			@Override
			public E previous() {
				if (hasPrevious()) {
					curIndex--;
					return element;
				} else {
					throw new NoSuchElementException();
				}
			}

			@Override
			public int nextIndex() {
				return curIndex + 1;
			}

			@Override
			public int previousIndex() {
				return curIndex - 1;
			}

		}

		private static final class ComboIterator<E> extends UnmodifiableIteratorTemplate<E> {

			private static final long serialVersionUID = -5542075439956793675L;

			private final Collection<Iterable<E>> source;
			private transient Iterator<Iterable<E>> curIterable;
			private transient Iterator<E> curElement;

			private ComboIterator(final Collection<Iterable<E>> source) {
				super();
				this.source = source;
			}

			private Iterator<E> curElement() {
				if (curElement == null) {
					final Iterator<Iterable<E>> curIterator = curIterable();
					if (curIterator == null) {
						return null;
					} else {
						curElement = curIterator.next().iterator();
					}
				}
				return curElement;
			}

			private Iterator<Iterable<E>> curIterable() {
				if (curIterable == null) {
					if (source.isEmpty()) {
						return null;
					} else {
						curIterable = source.iterator();
					}
				}
				return curIterable;
			}

			@Override
			public boolean hasNext() {
				Iterator<E> curElement = curElement();
				if (curElement == null) {
					return false;
				} else if (curElement.hasNext()) {
					return true;
				} else {
					final Iterator<Iterable<E>> curIterable = curIterable();
					if (curIterable.hasNext()) {
						curElement = this.curElement = curIterable.next().iterator();
						return curElement.hasNext();
					} else {
						return false;
					}
				}
			}

			@Override
			public E next() {
				return curElement.next();
			}
		}

		private static final ListIterator<Object> EMPTY_ITERATOR = new UnmodifiableListIteratorTemplate<Object>() {

			private static final long serialVersionUID = 6728633672341917796L;

			@Override
			public boolean hasNext() {
				return false;
			}

			@Override
			public Object next() {
				throw new NoSuchElementException();
			}

			@Override
			public boolean hasPrevious() {
				return false;
			}

			@Override
			public int nextIndex() {
				return 0;
			}

			@Override
			public Object previous() {
				throw new NoSuchElementException();
			}

			@Override
			public int previousIndex() {
				return -1;
			}

			private Object readResolve() {
				return EMPTY_ITERATOR;
			}
		};
	}

	@SuppressWarnings("unchecked")
	public static void main(final String... args) {
		final List<List<Integer>> source = new ArrayList<>();
		Collections.addAll(source, L.natureSeq(10), L.positiveSeq(10), L.seq(0, 10));
		System.out.println("@@@@@@" + source);
		for (final Iterator<Integer> i = I.comboIterator(source); i.hasNext();) {
			System.out.println("@@@@@@" + i.next());
		}
	}
}
