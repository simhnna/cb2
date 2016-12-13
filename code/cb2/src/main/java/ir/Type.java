package ir;

import java.util.Set;

public interface Type extends Name {
	public Set<Method> getMethods();

	public Set<Field> getFields();
}
