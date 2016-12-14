package ir;

import java.util.List;

public interface Method extends Name {
	public Type getReturnType();

	public List<Type> getArgumentTypes();
}
