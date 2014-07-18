package wtDesktop;

import javax.swing.AbstractListModel;

public abstract class AbstractWtListModel<T> extends AbstractListModel<T> {
	public void fireChanged() {
		this.fireContentsChanged(this, 0, this.getSize());
	}

	@Override
	protected void fireContentsChanged(Object source, int index0, int index1) {
		super.fireContentsChanged(source, index0, index1);
	}
}
