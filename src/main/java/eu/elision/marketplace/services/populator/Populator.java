package eu.elision.marketplace.services.populator;

public abstract class Populator<S, T> {
    public abstract void populate(S source, T target);
}
