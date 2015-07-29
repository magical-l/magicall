package me.magicall.coll;

import java.util.Collection;
import java.util.List;

import me.magicall.coll.fixed.Fixed;


/**
 * 矩阵
 * 是一种二维的集合,长度和宽度固定.
 * 因此对于add系列方法,如果还有"空余"位置,add会把元素放到最靠前的"空余"的位置上.如果没有"空余"位置,则返回false
 * "空余(hole)"的定义:是一种特殊的元素值,通常为null.有的实现可以指定这个值.如果尝试获取"空余"位置,将返回holeElement()
 * 因此,Matrix中最好不要有null元素.如果需要允许null元素存在于Matrix对象中,请另外指定一个特殊值作为"空余"值
 * 
 * @author MaGiCalL
 * @param <E>
 */
public interface Matrix<E> extends Collection<E>, Fixed {

	int columnCount();

	int rowCount();

	List<E> getColumn(int index);

	List<E> getRow(int index);

	List<List<E>> verticalLists();

	List<List<E>> horizontalLists();

	Matrix<E> subMatrix(int fromRowIndex, int fromColumnIndex, int toRowIndex, int toColumnIndex);

	Matrix<E> subMatrix(int fromRowIndex, int fromColumnIndex);

	Matrix<E> transferOrder();

	E get(int rowIndex, int columnIndex);

	E set(int rowIndex, int columnIndex, E element);

	boolean isHole(int rowIndex, int columnIndex);

	E getHoleValue();
}
