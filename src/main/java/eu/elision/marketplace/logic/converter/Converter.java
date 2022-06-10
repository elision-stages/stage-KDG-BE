package eu.elision.marketplace.logic.converter;

import eu.elision.marketplace.exceptions.ConversionException;
import eu.elision.marketplace.logic.converter.populator.Populator;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Convertor class to convert items to other items with the help of populators
 * @param <S> Source type
 * @param <T> Target type
 */
public abstract class Converter<S, T> {
    private final List<Populator<S, T>> populatorList;
    private final Class<T> targetClass;

    protected Converter(List<Populator<S, T>> populatorList, Class<T> targetClass) {
        this.populatorList = populatorList;
        this.targetClass = targetClass;
    }

    /**
     * Convert function that takes an input and returns a converted output
     *
     * @param source The input object
     * @return Converted object
     */
    public T convert(S source)
    {
        try
        {
            T targetInstance = targetClass.getDeclaredConstructor().newInstance();
            for (Populator<S, T> populator : populatorList)
            {
                populator.populate(source, targetInstance);
            }
            return targetInstance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e)
        {
            throw new ConversionException(String.format("Problem converting %s to %s: %s %n%s", source.getClass(), targetClass, e.getMessage(), Arrays.toString(e.getStackTrace())));
        }
    }

    /**
     * Converts a collection of inputs and returns a collection of outputs
     * @param sourceList Collection with input objects
     * @return Collection of converted objects
     */
    public Collection<T> convertAll(Collection<S> sourceList) {
        return sourceList.stream().map(this::convert).toList();
    }
}
