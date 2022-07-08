package test.parser;

import nn.NeuralNetwork;
import nn.WeightTable;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class WeightsParser extends AbstractFileParser<WeightTable> {
    private final int[] numLayerNeurons;

    public WeightsParser(String filename, int[] numLayerNeurons) throws FileUnreadableException {
        super(filename);
        this.numLayerNeurons = numLayerNeurons;
    }

    @Override
    public WeightTable parse() {
        Scanner scanner = getScanner();
        int numInternalLayers = scanner.nextInt();

        // Skip current empty line.
        scanner.nextLine();

        // One for input layer, one for output layer.
        int numLayers = numInternalLayers + 1 + 1;
        int currentLayerIndex = 0;
        int currentNeuronIndex = 0;

        // +1 because the layer index starts from 1 -- layer 0 has no previous layer.
        // -1 because the last layer is not anyone's previous layer.
        double[][][] table = new double[numLayers + 1 - 1][][];

        // Skip first layer as it is input layer.
        ++currentLayerIndex;
        table[currentLayerIndex] = new double[numLayerNeurons[currentLayerIndex - 1]][];

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
            if (currentNeuronIndex >= numLayerNeurons[currentLayerIndex - 1]) {
                ++currentLayerIndex;

                // Exit condition: reached the last layer?
                if (currentLayerIndex >= numLayers) {
                    break;
                }

                // Allocate space for next layer.
                // Reset neuron counter.
                table[currentLayerIndex] = new double[numLayerNeurons[currentLayerIndex - 1]][];
                currentNeuronIndex = 0;
            }
        }

        return new WeightTable(table);
    }
}
