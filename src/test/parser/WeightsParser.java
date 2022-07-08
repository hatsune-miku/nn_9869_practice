/*
 * zguan@mun.ca
 * Student Number: 202191382
 */

package test.parser;

import nn.Config;
import nn.WeightTable;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Weight parser. Convert `weight.txt` into `WeightTable`.
 */
public class WeightsParser extends AbstractFileParser<WeightTable> {
    /**
     * numLayerNeurons[X] = Y means that layer X has Y neurons.
     */
    protected int[] numNeuronsInLayer;

    protected final Config config;

    /**
     * Constructor.
     *
     * @param filename -
     * @param config   The network config.
     * @throws FileUnreadableException if the output file does not exist
     *                                 or have no write permission.
     */
    public WeightsParser(String filename, Config config) throws FileUnreadableException {
        super(filename);
        this.config = config;
    }

    @Override
    public WeightTable parse() {
        Scanner scanner = getScanner();
        int numInternalLayers = scanner.nextInt();

        // Confirm numLayerNeurons.
        // One 1+ is input layer, one 1+ is output layer.
        this.numNeuronsInLayer = new int[1 + 1 + numInternalLayers];
        for (int i = 0; i < numNeuronsInLayer.length; ++i) {
            if (i == 0) {
                // Input layer.
                numNeuronsInLayer[i] = config.inputLayerSize();

            } else if (i == numNeuronsInLayer.length - 1) {
                // Output layer.
                numNeuronsInLayer[i] = config.outputLayerSize();
            } else {
                // Internal layer.
                numNeuronsInLayer[i] = config.internalLayerSize();
            }
        }

        // Skip current empty line.
        scanner.nextLine();

        // One +1 for input layer, one +1 for output layer.
        int numLayers = numInternalLayers + 1 + 1;
        int currentLayerIndex = 0;
        int currentNeuronIndex = 0;

        // +1 because the layer index starts from 1 -- layer 0 has no previous layer.
        // -1 because the last layer is not anyone's previous layer.
        double[][][] table = new double[numLayers + 1 - 1][][];

        // Skip first layer as it is input layer.
        ++currentLayerIndex;
        table[currentLayerIndex] = new double[numNeuronsInLayer[currentLayerIndex - 1]][];

        // Repeatedly readline -- each line represent a single neuron.
        while (true) {
            String line = scanner.nextLine().strip();

            // Parse weight data.
            double[] weights = Arrays.stream(line.split(", "))
                .mapToDouble(Double::parseDouble)
                .toArray();

            // Fill in table.
            table[currentLayerIndex][currentNeuronIndex++] = weights;

            // Next layer?
            if (currentNeuronIndex >= numNeuronsInLayer[currentLayerIndex - 1]) {
                ++currentLayerIndex;

                // Exit condition: reached the last layer?
                if (currentLayerIndex >= numLayers) {
                    break;
                }

                // Allocate space for next layer.
                // Reset neuron counter.
                table[currentLayerIndex] = new double[numNeuronsInLayer[currentLayerIndex - 1]][];
                currentNeuronIndex = 0;
            }
        }

        return new WeightTable(table);
    }

    public int[] getNumNeuronsInLayer() {
        return numNeuronsInLayer;
    }
}
