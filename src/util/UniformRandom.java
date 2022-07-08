/*
 * zguan@mun.ca
 * Student Number: 202191382
 */

package util;

import java.util.Random;

/**
 * Wrapper class of an implementation of uniform random.
 */
public class UniformRandom {
    protected Random random;

    public UniformRandom() {
        this.random = new Random();
    }

    /**
     * Returns a pseudorandom, uniformly distributed int value
     * between given range, drawn from random number generator's sequence.
     * @param minInclusive Lower bound, inclusive.
     * @param maxInclusive Upper bound, also inclusive.
     * @return The random value.
     */
    public int nextInt(int minInclusive, int maxInclusive) {
        return minInclusive + random.nextInt(maxInclusive + 1);
    }
}
