package classCode.samples;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicSubject implements InvocationHandler {
    private Object sub;

    public DynamicSubject(Object o) {
        this.sub = o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before method:" + method);
        method.invoke(sub, args);
        System.out.println("after method:" + method);
        return null;
    }
}
