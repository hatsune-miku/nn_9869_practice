/*
 * zguan@mun.ca
 * Student Number: 202191382
 */

package nn;

import java.util.function.Function;

public class WeightTable {
    /**
     * The maximum neurons allowed in a single layer.
     */
    public final static int NEURONS_COUNT_MAX = 2022;

    /**
     * The maximum layers allowed in a network.
     */
    public final static int LAYERS_COUNT_MAX = 708;

    /**
     * `table[X][Y][Z] = W` means that
     * the weight from neuron Y at layer X-1
     * to neuron Z at layer X, is W
     */
    protected double[][][] table;

    /**
     * Constructor.
     *
     * @param numLayers                 The number of layers.
     * @param numNeuronsInLayerFunction f(X) = Y, means that at layer X there are Y neurons.
     * @throws IllegalArgumentException if the function gives an invalid number
     *                                  that can not represent the number of layers.
     */
    public WeightTable(
        int numLayers,
        Function<Integer, Integer> numNeuronsInLayerFunction
    ) {
        // Confirm the max value.
        int maxNumNeurons = 0;
        for (int i = 0; i < numLayers; ++i) {
            int numNeurons = numNeuronsInLayerFunction.apply(i);
            if (numNeurons <= 0 || numNeurons > NEURONS_COUNT_MAX) {
                throw new IllegalArgumentException(
                    "The number of neurons (%d) is invalid."
                        .formatted(numNeurons)
                );
            }
            maxNumNeurons = Math.max(maxNumNeurons, numNeurons);
        }

        // Allocate space.
        this.table = new double[numLayers][maxNumNeurons][maxNumNeurons];

        // Assign initial values.
        // By default, the weight is 0.
        for (int i = 0; i < numLayers; ++i) {
            for (int j = 0; j < maxNumNeurons; ++j) {
                for (int k = 0; k < maxNumNeurons; ++k) {
                    this.table[i][j][k] = 0;
                }
            }
        } // end for
    }

    /**
     * Constructor.
     *
     * @param table The given table.
     */
    public WeightTable(double[][][] table) {
        this.table = table;
    }

    /**
     * Query the weight on a directed edge between
     * neuron with index `sourceNeuronIndex` at layer `destLayerIndex-1` and
     * neuron with index `destNeuronIndex` at layer `destLayerIndex`.
     *
     * @param destLayerIndex    From 0.
     * @param sourceNeuronIndex From 0.
     * @param destNeuronIndex   From 0.
     * @return The weight.
     * @throws IndexOutOfBoundsException if any index is out of bound.
     */
    public double getWeight(
        int destLayerIndex,
        int sourceNeuronIndex,
        int destNeuronIndex
    ) throws IndexOutOfBoundsException {
        return table[destLayerIndex][sourceNeuronIndex][destNeuronIndex];
    }

    /**
     * Set the weight on a directed edge between
     * neuron with index `sourceNeuronIndex` at layer `destLayerIndex-1` and
     * neuron with index `destNeuronIndex` at layer `destLayerIndex`.
     *
     * @param destLayerIndex    From 0.
     * @param sourceNeuronIndex From 0.
     * @param destNeuronIndex   From 0.
     * @param weight            The weight.
     * @throws IndexOutOfBoundsException if any index is out of bound.
     */
    public void setWeight(
        int destLayerIndex,
        int sourceNeuronIndex,
        int destNeuronIndex,
        double weight
    ) throws IndexOutOfBoundsException {
        table[destLayerIndex][sourceNeuronIndex][destNeuronIndex] = weight;
    }
}
