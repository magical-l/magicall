/**
 * 
 */
package me.magicall.coll.bridge;

import me.magicall.coll.unmodifiable.UnmodifiableListTemplate;

import java.util.List;
import java.util.Map;


/**
 * 我们通常会遇到这样一种情况,手上抓着一个List,装的是某种类E的id(任何类型的),它叫idList.
 * 然后作为参数调用接口得到一个Map,其key为id,value为具体的货E,
 * 但我们最后要返回一个E的List,顺序要跟id的List一致,它叫eList.
 * 因此我们就要把eList给new出来,然后for循环一遍idList,从map中依次根据id取得E,把E装到eList里.
 * 如果你还在为以上的事情烦躁的话,告诉你一个好消息!这个类就是为了解决这种无聊的问题而来的.
 * 把idList和map都丢进来,然后你就得到了eList!
 * 再也不用写一大坨代码啦!
 * 
 * @author MaGiCalL
 * @email wenjian.liang@opi-corp.com
 * @version Apr 25, 2011 10:10:11 PM
 */
public class IdMapOrderingList<K, E> extends UnmodifiableListTemplate<E> {

	private static final long serialVersionUID = -2421609473062132536L;

	private final List<K> keys;
	private final Map<K, E> map;

	public IdMapOrderingList(final List<K> keys, final Map<K, E> map) {
		super();
		this.keys = keys;
		this.map = map;
	}

	@Override
	protected E get0(final int index) {
		return map.get(keys.get(index));
	}

	@Override
	public int size() {
		return keys.size();
	}

}
