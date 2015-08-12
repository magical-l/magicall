package me.magicall.coll;

import me.magicall.coll.CollFactory.I;
import me.magicall.coll.unmodifiable.UnmodifiableListTemplate;
import me.magicall.mark.Unmodifiable;
import me.magicall.mark.Wrapper;
import me.magicall.util.kit.Kits;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MatrixUtil {

	@SuppressWarnings("unchecked")
	public static <E> Matrix<E> emptyMatrix() {
		return (Matrix) EMPTY_MATRIX;
	}

	public static <E> Matrix<E> subMatrix(final Matrix<E> source, final int fromRowIndex, final int fromColumnIndex,//
			final int toRowIndex, final int toColumnIndex) {
		checkSubMatrixIndex(source, fromRowIndex, fromColumnIndex, toRowIndex, toColumnIndex);
		return new SubMatrix<>(source, fromRowIndex, fromColumnIndex, toRowIndex, toColumnIndex);
	}

	private static <E> void checkSubMatrixIndex(final Matrix<E> source, final int fromRowIndex, final int fromColumnIndex, final int toRowIndex,
			final int toColumnIndex) {
		if (fromRowIndex < 0) {
			throw new IllegalArgumentException("fromRowIndex < 0, it's " + fromRowIndex);
		}
		if (fromColumnIndex < 0) {
			throw new IllegalArgumentException("fromColumnIndex < 0, it's " + fromColumnIndex);
		}
		final int sourceRowCount = source.rowCount();
		if (toRowIndex > sourceRowCount) {
			throw new IllegalArgumentException("toRowIndex > source.rowCount, it's " + toRowIndex + ", source's rowCount is " + sourceRowCount);
		}
		final int sourceColumnCount = source.columnCount();
		if (toColumnIndex > sourceColumnCount) {
			throw new IllegalArgumentException("columnCount > source.columnCount, it's " + toColumnIndex + ", source's columnCount is " + sourceColumnCount);
		}
	}

	public static <E> Matrix<E> unmodifiable(final Matrix<E> source) {
		return source instanceof Unmodifiable ? source : new UnmodifiableFacadingMatrix<>(source);
	}

	public static <E> Matrix<E> subMatrix(final Matrix<E> source, final int fromRowIndex, final int fromColumnIndex) {
		return subMatrix(source, fromRowIndex, fromColumnIndex, source.rowCount(), source.columnCount());
	}

	public static abstract class MatrixTemplate<E> extends AbstractCollection<E> implements Matrix<E> {

		protected MatrixTemplate() {
			super();
		}

		@Override
		public E getHoleValue() {
			return null;
		}

		@Override
		public int columnCount() {
			return horizontalLists().size();
		}

		@Override
		public int rowCount() {
			return verticalLists().size();
		}

		@Override
		public List<E> getColumn(final int index) {
			return verticalLists().get(index);
		}

		@Override
		public List<E> getRow(final int index) {
			return horizontalLists().get(index);
		}

		@Override
		public Matrix<E> subMatrix(final int fromRowIndex, final int fromColumnIndex, final int toRowIndex, final int toColumnIndex) {
			return MatrixUtil.subMatrix(this, fromRowIndex, fromColumnIndex, toRowIndex, toColumnIndex);
		}

		@Override
		public Matrix<E> subMatrix(final int fromRowIndex, final int fromColumnIndex) {
			return MatrixUtil.subMatrix(this, fromRowIndex, fromColumnIndex);
		}

		@Override
		public Matrix<E> transferOrder() {
			return new TransferOrderMatrix<>(this);
		}

		@Override
		public E get(final int rowIndex, final int columnIndex) {
			return horizontalLists().get(rowIndex).get(columnIndex);
		}

		@Override
		public Iterator<E> iterator() {
			return I.comboIterator(horizontalLists());
		}

		@Override
		public int size() {
			return rowCount() * columnCount();
		}

		@Override
		public boolean isHole(final int rowIndex, final int columnIndex) {
			return get(rowIndex, columnIndex) == getHoleValue();
		}
	}//AbsMatrix

	public static abstract class UnmodifiableMatrixTemplate<E> extends MatrixTemplate<E>//
			implements Matrix<E>, Serializable, Unmodifiable {

		private static final long serialVersionUID = 7773094656638775937L;

		protected UnmodifiableMatrixTemplate() {
			super();
		}

		protected abstract List<List<E>> verticalLists0();

		protected abstract List<List<E>> horizontalLists0();

		protected List<E> getColumn0(final int index) {
			return super.getColumn(index);
		}

		protected List<E> getRow0(final int index) {
			return super.getRow(index);
		}

		protected Matrix<E> subMatrix0(final int fromRowIndex, final int fromColumnIndex, final int toRowIndex, final int toColumnIndex) {
			return super.subMatrix(fromRowIndex, fromColumnIndex, toRowIndex, toColumnIndex);
		}

		protected Matrix<E> subMatrix0(final int fromRowIndex, final int fromColumnIndex) {
			return super.subMatrix(fromRowIndex, fromColumnIndex);
		}

		protected Matrix<E> transferOrder0() {
			return super.transferOrder();
		}

		protected Iterator<E> iterator0() {
			return super.iterator();
		}

		private static ElementTransformer<List<?>, List<?>> UNMODIFIABLE_IT = (index,
																			   element) -> Kits.LIST.unmodifiable(element);

		@SuppressWarnings("unchecked")
		protected static final <E> ElementTransformer<List<E>, List<E>> unmodifiableListTransformer() {
			return (ElementTransformer) UNMODIFIABLE_IT;
		}

		@Override
		public final List<List<E>> verticalLists() {
			final ElementTransformer<List<E>, List<E>> tf = unmodifiableListTransformer();
			return Kits.LIST.unmodifiable(Kits.LIST.transform(verticalLists0(), tf));
		}

		@Override
		public final List<List<E>> horizontalLists() {
			final ElementTransformer<List<E>, List<E>> tf = unmodifiableListTransformer();
			return Kits.LIST.unmodifiable(Kits.LIST.transform(horizontalLists0(), tf));
		}

		@Override
		public final List<E> getColumn(final int index) {
			return Kits.LIST.unmodifiable(getColumn0(index));
		}

		@Override
		public final List<E> getRow(final int index) {
			return Kits.LIST.unmodifiable(getRow0(index));
		}

		@Override
		public final Matrix<E> subMatrix(final int fromRowIndex, final int fromColumnIndex, final int toRowIndex, final int toColumnIndex) {
			return unmodifiable(subMatrix0(fromRowIndex, fromColumnIndex, toRowIndex, toColumnIndex));
		}

		@Override
		public final Matrix<E> subMatrix(final int fromRowIndex, final int fromColumnIndex) {
			return unmodifiable(subMatrix0(fromRowIndex, fromColumnIndex));
		}

		@Override
		public final Matrix<E> transferOrder() {
			return super.transferOrder();
		}

		@Override
		public final Iterator<E> iterator() {
			return I.unmodifiable(iterator0());
		}

		@Override
		public final boolean add(final E e) {
			throw new UnsupportedOperationException();
		}

		@Override
		public final boolean remove(final Object o) {
			throw new UnsupportedOperationException();
		}

		@Override
		public final boolean addAll(final Collection<? extends E> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public final boolean removeAll(final Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public final boolean retainAll(final Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public final E set(final int rowIndex, final int columnIndex, final E element) {
			throw new UnsupportedOperationException();
		}

		@Override
		public final void clear() {
			throw new UnsupportedOperationException();
		}
	}//AbsUnmodifiableMatrix

	private static final class EmptyMatrix<E> extends UnmodifiableMatrixTemplate<E> {

		private static final long serialVersionUID = -3291816324226808371L;

		private final int rowCount;
		private final int columnCount;
		private final E holeValue;
		private volatile List<E> row;
		private volatile List<E> column;
		private volatile List<List<E>> rows;
		private volatile List<List<E>> columns;

		public EmptyMatrix(final int rowCount, final int columnCount, final E holeValue) {
			super();
			this.rowCount = rowCount;
			this.columnCount = columnCount;
			this.holeValue = holeValue;
		}

		public EmptyMatrix(final int rowCount, final int columnCount) {
			this(rowCount, columnCount, null);
		}

		private List<E> row() {
			return row == null ? (row = Collections.nCopies(columnCount, holeValue)) : row;
		}

		private List<E> column() {
			return column == null ? (column = Collections.nCopies(columnCount, holeValue)) : column;
		}

		@Override
		protected List<List<E>> verticalLists0() {
			return rows == null ? (rows = Collections.nCopies(columnCount, row())) : rows;
		}

		@Override
		protected List<List<E>> horizontalLists0() {
			return columns == null ? (columns = Collections.nCopies(columnCount, column())) : columns;
		}

		@Override
		protected Iterator<E> iterator0() {
			return I.nCopy(size(), holeValue);
		}

		@Override
		public int columnCount() {
			return columnCount;
		}

		@Override
		public int rowCount() {
			return rowCount;
		}

		@Override
		public boolean contains(final Object o) {
			return !isEmpty() && o == holeValue;
		}

		@Override
		protected Matrix<E> subMatrix0(final int fromRowIndex, final int fromColumnIndex, final int toRowIndex, final int toColumnIndex) {
			checkSubMatrixIndex(this, fromRowIndex, fromColumnIndex, toRowIndex, toColumnIndex);
			return new EmptyMatrix<>(toRowIndex - fromRowIndex, toColumnIndex - fromColumnIndex, holeValue);
		}

		@Override
		protected Matrix<E> subMatrix0(final int fromRowIndex, final int fromColumnIndex) {
			return subMatrix0(fromRowIndex, rowCount, fromColumnIndex, columnCount);
		}

		@Override
		public Object[] toArray() {
			return isEmpty() ? Kits.OBJ.emptyArray() : Kits.OBJ.nCopy(size(), holeValue);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> T[] toArray(final T[] a) {
			final int size = size();
			final T[] rt = a.length >= size ? a : (T[]) Array.newInstance(a.getClass().getComponentType(), size);
			for (int i = 0; i < size; ++i) {
				rt[i] = (T) holeValue;
			}
			return rt;
		}
	}

	private static final Matrix<Object> EMPTY_MATRIX = new EmptyMatrix<>(0, 0);

	protected static class UnmodifiableFacadingMatrix<E> extends UnmodifiableMatrixTemplate<E>//
			implements Matrix<E>, Wrapper<Matrix<E>>, Serializable, Unmodifiable {

		private static final long serialVersionUID = 7132560815476119142L;

		protected final Matrix<E> matrix;

		public UnmodifiableFacadingMatrix(final Matrix<E> matrix) {
			super();
			this.matrix = matrix;
		}

		@Override
		public int columnCount() {
			return matrix.columnCount();
		}

		@Override
		public int rowCount() {
			return matrix.rowCount();
		}

		@Override
		public E get(final int rowIndex, final int columnIndex) {
			return matrix.get(rowIndex, columnIndex);
		}

		@Override
		public int size() {
			return matrix.size();
		}

		@Override
		public boolean isEmpty() {
			return matrix.isEmpty();
		}

		@Override
		public boolean contains(final Object o) {
			return matrix.contains(o);
		}

		@Override
		public Object[] toArray() {
			return matrix.toArray();
		}

		@Override
		public <T> T[] toArray(final T[] a) {
			return matrix.toArray(a);
		}

		@Override
		public boolean containsAll(final Collection<?> c) {
			return matrix.containsAll(c);
		}

		@Override
		public boolean equals(final Object o) {
			return matrix.equals(o);
		}

		@Override
		public int hashCode() {
			return matrix.hashCode();
		}

		@Override
		protected List<List<E>> verticalLists0() {
			return matrix.verticalLists();
		}

		@Override
		protected List<List<E>> horizontalLists0() {
			return matrix.horizontalLists();
		}

		@Override
		protected List<E> getColumn0(final int index) {
			return matrix.getColumn(index);
		}

		@Override
		protected List<E> getRow0(final int index) {
			return matrix.getRow(index);
		}

		@Override
		protected Matrix<E> subMatrix0(final int fromRowIndex, final int fromColumnIndex, final int toRowIndex, final int toColumnIndex) {
			return matrix.subMatrix(fromRowIndex, fromColumnIndex, toRowIndex, toColumnIndex);
		}

		@Override
		protected Matrix<E> subMatrix0(final int fromRowIndex, final int fromColumnIndex) {
			return matrix.subMatrix(fromRowIndex, fromColumnIndex);
		}

		@Override
		protected Matrix<E> transferOrder0() {
			return matrix.transferOrder();
		}

		@Override
		protected Iterator<E> iterator0() {
			return matrix.iterator();
		}

		@Override
		public Matrix<E> unwrap() {
			return this;
		}
	}//UnmodifiableFacadingMatrix

	private static class SubMatrix<E> extends UnmodifiableFacadingMatrix<E> {

		private static final long serialVersionUID = -6518726414332286935L;

		private final int rowOffset;
		private final int columnOffset;
		private final int toRowIndex;
		private final int toColumnIndex;

		private SubMatrix(final Matrix<E> matrix, final int rowOffset, final int columnOffset, final int toRowIndex, final int toColumnIndex) {
			super(matrix);
			this.rowOffset = rowOffset;
			this.columnOffset = columnOffset;
			this.toRowIndex = toRowIndex;
			this.toColumnIndex = toColumnIndex;
		}

		@Override
		public int columnCount() {
			return toColumnIndex - columnOffset;
		}

		@Override
		public int rowCount() {
			return toRowIndex - rowOffset;
		}

		@Override
		public E get(final int rowIndex, final int columnIndex) {
			return super.get(rowIndex + rowOffset, columnIndex + columnOffset);
		}

		@Override
		protected List<List<E>> verticalLists0() {
			return Kits.LIST.subList(super.verticalLists0(), columnOffset, toColumnIndex);
		}

		@Override
		protected List<List<E>> horizontalLists0() {
			return Kits.LIST.subList(super.horizontalLists0(), rowOffset, toRowIndex);
		}

		@Override
		protected List<E> getColumn0(final int index) {
			return super.getColumn0(index + columnOffset);
		}

		@Override
		protected List<E> getRow0(final int index) {
			return super.getRow0(index + rowOffset);
		}

		@Override
		protected Matrix<E> subMatrix0(final int fromRowIndex, final int fromColumnIndex, final int toRowIndex, final int toColumnIndex) {
			checkSubMatrixIndex(this, fromRowIndex, fromColumnIndex, toRowIndex, toColumnIndex);
			return matrix.subMatrix(rowOffset + fromRowIndex, columnOffset + fromColumnIndex,//
					rowOffset + toRowIndex, columnOffset + toColumnIndex);
		}

		@Override
		public boolean equals(final Object o) {
			// TODO Auto-generated method stub
			return super.equals(o);
		}

		@Override
		public int hashCode() {
			// TODO Auto-generated method stub
			return super.hashCode();
		}
	}//SubMatrix

	private static class TransferOrderMatrix<E> extends UnmodifiableFacadingMatrix<E> {

		private static final long serialVersionUID = -7501526163100975038L;

		public TransferOrderMatrix(final Matrix<E> matrix) {
			super(matrix);
		}

		@Override
		public int columnCount() {
			return super.rowCount();
		}

		@Override
		public int rowCount() {
			return super.columnCount();
		}

		@Override
		protected List<E> getColumn0(final int index) {
			return super.getRow0(index);
		}

		@Override
		protected List<E> getRow0(final int index) {
			return super.getColumn0(index);
		}

		@Override
		protected List<List<E>> verticalLists0() {
			return super.horizontalLists0();
		}

		@Override
		protected List<List<E>> horizontalLists0() {
			return super.verticalLists0();
		}

		@Override
		protected Matrix<E> subMatrix0(final int fromRowIndex, final int fromColumnIndex, final int toRowIndex, final int toColumnIndex) {
			return super.subMatrix0(fromColumnIndex, fromRowIndex, toColumnIndex, toRowIndex).transferOrder();
		}

		@Override
		protected Matrix<E> subMatrix0(final int fromRowIndex, final int fromColumnIndex) {
			return subMatrix(fromColumnIndex, fromRowIndex).transferOrder();
		}

		@Override
		protected Matrix<E> transferOrder0() {
			return matrix;
		}

		@Override
		public E get(final int rowIndex, final int columnIndex) {
			return super.get(columnIndex, rowIndex);
		}
	}//TransferOrderMatrix

	public static class CommonMatrix<E> extends MatrixTemplate<E>//
			implements Matrix<E>, Serializable {

		private static final long serialVersionUID = 7433182469406309063L;

		private final List<List<E>> list;
		private final int columnCount;
		private final E holeValue;
		private transient List<List<E>> verticalList;
		private transient List<List<E>> horizontalLists;
		private volatile int curAddIndex;

		@SuppressWarnings("unchecked")
		public CommonMatrix(final int rowCount, final int columnCount, final E holeValue) {
			super();
			final List<E>[] rows = (List<E>[]) new List<?>[rowCount];
			for (int i = 0; i < rowCount; ++i) {
				rows[i] = (List<E>) Arrays.asList(new Object[columnCount]);
			}
			list = Arrays.asList(rows);
			this.columnCount = columnCount;
			this.holeValue = holeValue;
			curAddIndex = 0;
		}

		public CommonMatrix(final int rowCount, final int columnCount) {
			this(rowCount, columnCount, null);
		}

		private class ColumnList extends UnmodifiableListTemplate<E> {

			private static final long serialVersionUID = 3184725615318929723L;

			final int columnIndex;

			public ColumnList(final int columnIndex) {
				super();
				this.columnIndex = columnIndex;
			}

			@Override
			protected E get0(final int index) {
				return CommonMatrix.this.get(index, columnIndex);
			}

			@Override
			public int size() {
				return list.size();
			}
		}

		private class VerticalList extends UnmodifiableListTemplate<List<E>> {

			private static final long serialVersionUID = -6705193624969110261L;

			@SuppressWarnings("unchecked")
			List<List<E>> columns = Arrays.asList((List<E>[]) new List<?>[columnCount]);

			@Override
			protected List<E> get0(final int index) {
				List<E> rt = columns.get(index);
				if (rt == null) {
					rt = new ColumnList(index);
					columns.set(index, rt);
				}
				return rt;
			}

			@Override
			public int size() {
				return columnCount;
			}
		}

		@Override
		public List<List<E>> verticalLists() {
			return verticalList == null ? (verticalList = new VerticalList()) : verticalList;
		}

		@Override
		public List<List<E>> horizontalLists() {
			return horizontalLists == null ? (horizontalLists = Kits.LIST.unmodifiable(list)) : horizontalLists;
		}

		@Override
		public boolean add(final E e) {
			final int rowIndex = curAddIndex / columnCount;
			final int columnIndex = curAddIndex - rowIndex * columnCount;
			if (columnIndex == columnCount) {
				if (rowIndex == list.size()) {
					return false;
				} else {
					set(rowIndex + 1, 0, e);
				}
			} else {
				set(rowIndex, columnIndex + 1, e);
			}
			curAddIndex++;
			return true;
		}

		@Override
		public boolean remove(final Object o) {
			for (final List<E> row : list) {
				for (final ListIterator<E> i = row.listIterator(); i.hasNext();) {
					final E e = i.next();
					if (o == e) {
						i.set(getHoleValue());
						return true;
					}
				}
			}
			return false;
		}

		@Override
		public boolean isEmpty() {
			return list.isEmpty() || list.get(0).isEmpty();
		}

		@Override
		public boolean removeAll(final Collection<?> c) {
			for (final List<E> row : list) {
				for (final ListIterator<E> i = row.listIterator(); i.hasNext();) {
					final E e = i.next();
					if (c.contains(e)) {
						i.set(getHoleValue());
					}
				}
			}
			return true;
		}

		@Override
		public boolean retainAll(final Collection<?> c) {
			for (final List<E> row : list) {
				for (final ListIterator<E> i = row.listIterator(); i.hasNext();) {
					final E e = i.next();
					if (!c.contains(e)) {
						i.set(getHoleValue());
					}
				}
			}
			return true;
		}

		@Override
		public void clear() {
			list.clear();
		}

		@Override
		public E set(final int rowIndex, final int columnIndex, final E element) {
			return list.get(rowIndex).set(columnIndex, element);
		}

		@Override
		public E getHoleValue() {
			return holeValue;
		}
	}
}
