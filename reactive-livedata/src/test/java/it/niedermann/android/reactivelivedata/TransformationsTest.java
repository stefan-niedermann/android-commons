package it.niedermann.android.reactivelivedata;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(RobolectricTestRunner.class)
public class TransformationsTest {

    @Test
    public void distinctByReference() throws InterruptedException {
        final var state1 = new State(1, "Lorem", 30);
        final var state2 = new State(2, "Ipsum", 40);

        Assert.assertNotEquals(state1, state2);
        Assert.assertNotSame(state1, state2);

        final var liveDataSource = new MutableLiveData<>(state1);
        final var liveData = Transformations.distinctUntilChanged(liveDataSource);

        liveDataSource.setValue(state2);

        final var newValueOfLiveData = getOrAwaitValue(liveData);
        Assert.assertEquals(state2, newValueOfLiveData);
        Assert.assertEquals(2, newValueOfLiveData.id());
        Assert.assertEquals("Ipsum", newValueOfLiveData.name());
        Assert.assertEquals(40, newValueOfLiveData.age());
    }

    /**
     * <blockquote>The value is considered changed <strong>if equals() yields false</strong>.</blockquote>
     *
     * @see <a href="https://developer.android.com/reference/androidx/lifecycle/Transformations">Documentation</a>
     */
    @Test
    public void distinctByEquals() throws InterruptedException {
        final var state1 = new State(1, "Lorem", 30);
        final var state2 = new State(1, "Ipsum", 40);

        Assert.assertEquals(state1, state2);
        Assert.assertNotSame(state1, state2);

        final var liveDataSource = new MutableLiveData<>(state1);
        final var liveData = Transformations.distinctUntilChanged(liveDataSource);

        liveDataSource.setValue(state2);

        final var newValueOfLiveData = getOrAwaitValue(liveData);
        Assert.assertEquals(state2, newValueOfLiveData);
        Assert.assertEquals(1, newValueOfLiveData.id());
        Assert.assertEquals("Ipsum", newValueOfLiveData.name());
        Assert.assertEquals(40, newValueOfLiveData.age());
    }

    private static <T> T getOrAwaitValue(final LiveData<T> liveData) throws InterruptedException {
        final var data = new Object[1];
        final var latch = new CountDownLatch(1);
        final var observer = new Observer<T>() {
            @Override
            public void onChanged(@Nullable T o) {
                data[0] = o;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(2, TimeUnit.SECONDS)) {
            throw new RuntimeException("LiveData value was never set.");
        }
        //noinspection unchecked
        return (T) data[0];
    }

    record State(
            int id,
            String name,
            int age
    ) {
        @Override
        public boolean equals(@Nullable Object o) {
            if (o instanceof State otherState) {
                return id == otherState.id();
            }

            return false;
        }
    }
}
