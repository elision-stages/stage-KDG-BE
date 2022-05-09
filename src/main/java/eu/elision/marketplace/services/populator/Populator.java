package eu.elision.marketplace.services.populator;

public interface Populator<S, T> {
    void populate(S source, T target);
}
