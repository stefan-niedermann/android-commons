package it.niedermann.android.reactivelivedata.combinator;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.IntFunction;

import it.niedermann.android.reactivelivedata.ReactiveLiveData;

public class CombinatorLiveData<T> extends ReactiveLiveData<T[]> {

    private final IntFunction<T[]> generator;
    private final LiveData<T>[] sources;

    @SafeVarargs
    public CombinatorLiveData(@NonNull IntFunction<T[]> generator, @NonNull LiveData<T>... sources) {
        this.generator = generator;
        this.sources = sources;

        addSources();
    }

    private void addSources() {
        for (int i = 0; i < sources.length; i++) {
            final int index = i;
            addSource(sources[index], emittedValue -> setValue(emittedValue, index));
            Optional.of(sources[i]).map(LiveData::getValue).ifPresent(value -> setValue(value, index));
        }
    }

    private void setValue(T value, int index) {
        final var nextValue = Optional
                .ofNullable(getValue())
                .map(val -> Arrays.copyOf(val, sources.length))
                .orElseGet(() -> generator.apply(sources.length));

        nextValue[index] = value;
        setValue(nextValue);
    }
}
