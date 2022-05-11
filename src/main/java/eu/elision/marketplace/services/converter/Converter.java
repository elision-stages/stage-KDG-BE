package eu.elision.marketplace.services.converter;

import eu.elision.marketplace.services.populator.Populator;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;


public abstract class Converter<S,T> {
    private final List<Populator<S, T>> populatorList;
    private Class<T> targetClass;

    protected Converter(List<Populator<S, T>> populatorList, Class<T> targetClass) {
        this.populatorList = populatorList;
        this.targetClass = targetClass;
    }

    public T convert(S source) {
        try {
            T targetInstance = targetClass.getDeclaredConstructor().newInstance();
            for (Populator<S, T> populator : populatorList) {
                populator.populate(source, targetInstance);
            }
            return targetInstance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public Collection<T> convertAll(Collection<S> sourceList) {
        return sourceList.stream().map(this::convert).toList();
    }
}
