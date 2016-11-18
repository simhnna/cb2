package ir;

import java.util.List;

public interface Method {
	public String getName();

	public Type getReturnType();

	public List<Type> getArgumentTypes();
}
