package eu.elision.marketplace.logic.populator;

/**
 * Populator interface to convert an object
 * @param <S> Source type
 * @param <T> Target type
 */
public interface Populator<S, T> {
    /**
     * Populate function that converts an object
     * @param source Source object
     * @param target Target object (result)
     */
    void populate(S source, T target);
}
