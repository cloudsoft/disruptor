package com.lmax.disruptor;

import java.util.concurrent.TimeUnit;

/**
 * Coordination barrier for tracking the cursor for producers and sequence of
 * dependent {@link EntryConsumer}s for a {@link RingBuffer}
 *
 * @param <T> {@link Entry} implementation stored in the {@link RingBuffer}
 */
public interface ConsumerBarrier<T extends Entry>
{
    /**
     * Get the {@link Entry} for a given sequence from the underlying {@link RingBuffer}.
     *
     * @param sequence of the {@link Entry} to get.
     * @return the {@link Entry} for the sequence.
     */
    T getEntry(long sequence);

    /**
     * Get the sequence number that the {@link RingBuffer} and dependent {@link EntryConsumer}s have progressed to.
     *
     * This is the RingBuffer cursor and minimum sequence number of the dependent {@link EntryConsumer}s.
     *
     * @return the sequence that is now valid for consuming.
     */
    long getAvailableSequence();

    /**
     * Wait for the given sequence to be available for consumption.
     *
     * @param sequence to wait for
     * @return the sequence up to which is available
     * @throws AlertException if a status change has occurred for the Disruptor
     * @throws InterruptedException if the thread needs awaking on a condition variable.
     */
    long waitFor(long sequence) throws AlertException, InterruptedException;

    /**
     * Wait for the given sequence to be available for consumption with a time out.
     *
     * @param sequence to wait for
     * @param timeout value
     * @param units for the timeout value
     * @return the sequence up to which is available
     * @throws AlertException if a status change has occurred for the Disruptor
     * @throws InterruptedException if the thread needs awaking on a condition variable.
     */
    long waitFor(long sequence, long timeout, TimeUnit units) throws AlertException, InterruptedException;

    /**
     * Alert the consumers of a status change.
     */
    void alert();
}