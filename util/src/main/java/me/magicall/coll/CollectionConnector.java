/**
 * 
 */
package me.magicall.coll;

/**
 * @author Administrator
 */
public class CollectionConnector<E> implements ElementHandler<E> {

	private static final String DEFAULT_CONNECTOR = " ";

	private final StringBuilder sb;
	private final ElementTransformer<E, ?> transformer;
	private final String connector;

	public CollectionConnector() {
		super();
		sb = new StringBuilder();
		connector = DEFAULT_CONNECTOR;
		transformer = null;
	}

	public CollectionConnector(final StringBuilder sb, final ElementTransformer<E, ?> transformer, final String connector) {
		super();
		this.sb = sb == null ? new StringBuilder() : sb;
		this.transformer = transformer;
		this.connector = connector == null ? DEFAULT_CONNECTOR : connector;
	}

	@Override
	public boolean execute(final int index, final E element) {
		if (index != 0) {
			sb.append(connector);
		}
		sb.append(transformer == null ? element : transformer.transform(index, element));
		return true;
	}

	public String getConnector() {
		return connector;
	}

	@Override
	public String toString() {
		return sb.toString();
	}

	public StringBuilder getSb() {
		return sb;
	}

}
