package me.magicall.convenient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import me.magicall.coll.Subcollection;
import me.magicall.util.kit.Kits;

public class ResultView<E> implements Subcollection<E> {

	private static final long serialVersionUID = 7361775847682598557L;

	private static final ResultView<Object> EMPTY = new ResultView<>(Collections.emptyList());

	@SuppressWarnings("unchecked")
	public static <T> ResultView<T> emptyResultView() {
		return (ResultView<T>) EMPTY;
	}

	public static <T> ResultView<T> newResultView() {
		return new ResultView<>();
	}

	private Collection<E> list;
	private int count;
	private int start;
	private int end;
	private int pagesize;

	public ResultView() {
		super();
	}

	public ResultView(final Collection<E> other) {
		setList(other);
	}

	public ResultView(final Collection<E> list, final Integer count, final Integer offset, final Integer pagesize) {
		setList(list);
		setTheNumbers(count, offset, pagesize);
	}

	public Collection<E> getList() {
		return list;
	}

	public void setList(final Collection<E> list) {
		this.list = Kits.COLL.checkToEmptyValue(list);
	}

	@Override
	public int getCount() {
		return count;
	}

	public void setCount(final int count) {
		this.count = count;
	}

	@Override
	public int getStart() {
		return start;
	}

	public void setStart(final int start) {
		this.start = start;
	}

	@Override
	public int getEnd() {
		return end;
	}

	public void setEnd(final int end) {
		this.end = end;
	}

	/**
	 * 设置总数(count),本页开始数字(start),本页结束数字(end).
	 * 使用了本方法就不用setCount,setStart,setEnd了.
	 * 并且会自动判断一些东西,比如end<start,或start>count之类.
	 * 注意参数,不是count,start,end,而是count,offset,pagesize
	 * 
	 * @param _count
	 * @param offset
	 * @param pagesize
	 */
	@Override
	public void setTheNumbers(final Integer _count, Integer offset, final Integer pagesize) {
		if (_count == null || _count <= 0) {
			if (list != null) {
				count = list.size();
			} else {
				count = 0;
			}
		} else {
			count = _count;
		}

		if (offset == null || offset <= 0) {
			offset = 0;
		}
		start = offset + 1;
		if (start > count) {
			start = count;
		}

		if (pagesize == null || pagesize <= 0) {
			end = count;
		} else {
			end = offset + pagesize;
			if (end > count) {
				end = count;
			}
		}

		this.pagesize = pagesize == null ? 0 : pagesize;
	}

	public Iterator<E> iterator() {
		return list.iterator();
	}

	public int size() {
		return list.size();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + count;
		result = prime * result + end;
		result = prime * result + (list == null ? 0 : list.hashCode());
		result = prime * result + start;
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ResultView<?> other = (ResultView<?>) obj;
		if (count != other.count) {
			return false;
		}
		if (end != other.end) {
			return false;
		}
		if (list == null) {
			if (other.list != null) {
				return false;
			}
		} else if (!list.equals(other.list)) {
			return false;
		}
		if (start != other.start) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return list.toString() + " count=" + count + ",start=" + start + ",end=" + end;
	}

	@Override
	public int getPageSize() {
		return pagesize;
	}

	public int getPageCount() {
		if (pagesize == 0) {
			return 0;
		}
		return (count + pagesize - 1) / pagesize;
	}

	/**
	 * 根据一个Collection返回一个ResultView
	 * 
	 * @param <T>
	 * @param col
	 * @param curpage
	 * @param pagesize
	 * @return
	 */
	public static <T> ResultView<T> getResultViewFrom(final Collection<T> col, final int curpage, final int pagesize) {
		if (col == null || col.isEmpty()) {
			return emptyResultView();
		}
		final ResultView<T> result = new ResultView<>();
		final int total = col.size();
		final int fromIndex = Math.min(curpage * pagesize, total);
		final int toIndex = Math.min((curpage + 1) * pagesize, total);
		List<T> subList = null;
		if (fromIndex >= toIndex) {
			subList = Collections.emptyList();
		} else {
			if (col instanceof List<?>) {
				subList = ((List<T>) col).subList(fromIndex, toIndex);
			} else {
				// 只好递归实现了
				subList = new ArrayList<>(pagesize);
				int i = 0;
				for (final T t : col) {
					if (i >= toIndex) {
						break;
					}
					if (i >= fromIndex && i < toIndex) {
						subList.add(t);
					}
					i++;
				}
			}
		}
		result.setList(subList);
		result.setTheNumbers(total, curpage * pagesize, pagesize);
		return result;
	}

	/**
	 * 工厂方法
	 * 
	 * @param <T>
	 * @param list
	 * @param count
	 * @param curpage
	 * @param pagesize
	 * @return
	 */
	public static <T> ResultView<T> createInstance(final Collection<T> list, final Integer count, final Integer curpage, final Integer pagesize) {
		final int offset = curpage * pagesize;
		return new ResultView<>(list, count, offset, pagesize);
	}

	public static void main(final String... args) {
		final List<Integer> list = new ArrayList<>();
		list.add(5);
		list.add(1);
		list.add(45);
		list.add(562);
		list.add(2487);
		list.add(6);
		int count = list.size();
		final int curpage = 0;
		final int pagesize = 3;
		final int offset = curpage * pagesize;
		System.out.println("@@@@@@ResultView.main():" + new ResultView<>(list.subList(offset, offset + pagesize), count,
				offset, pagesize));

		count = -1;
		System.out.println("@@@@@@ResultView.main():" + new ResultView<>(list.subList(offset, offset + pagesize), count,
				offset, pagesize));

		System.out.println("@@@@@@ResultView.main():" + new ResultView<>(list.subList(offset, offset + pagesize), count,
				offset, -2));

		System.out.println("@@@@@@ResultView.main():" + new ResultView<>(list.subList(offset, offset + pagesize), count,
				-3, pagesize));
	}
}
