package ir;

import java.util.Set;

public interface Type {

	public String getName();

	public Set<Method> getMethods();

	public Set<Field> getFields();
}
