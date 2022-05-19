package eu.elision.marketplace.logic.populator;

public interface Populator<S, T> {
    void populate(S source, T target);
}
