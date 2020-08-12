package dev.eeasee.scenemasker.utils;

public class FunctionUtils {
    @FunctionalInterface
    public interface TriConsumer<A, B, C> { void accept(A a, B b, C c); }
}
