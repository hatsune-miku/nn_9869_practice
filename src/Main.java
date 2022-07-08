import nn.ExternalInputTable;
import nn.NeuralNetwork;
import nn.Neuron;
import nn.WeightTable;
import nn.functions.FunctionFactory;
import test.parser.AbstractFileParser;
import test.parser.ExternalInputParser;
import test.parser.WeightsParser;

import java.io.*;
import java.util.List;

/**
 * Tested to be successfully generate 17567.6798
 * with Dr. Shahidi's sample input.
 */
public class Main {
    public static void main(String[] args) {
        int[] numLayerNeurons = {4, 5, 5, 5, 5, 3};
        WeightsParser weightsParser;
        ExternalInputParser inputParser;
        FunctionFactory functionFactory = new FunctionFactory();

        // Parse weights and inputs.
        try {
            weightsParser = new WeightsParser(
                "weights.txt", numLayerNeurons);
            inputParser = new ExternalInputParser(
                "inputs.txt"
            );
        } catch (AbstractFileParser.FileUnreadableException e) {
            System.out.println("Weight or input files not found.");
            return;
        }
        WeightTable weights = weightsParser.parse();
        ExternalInputTable inputs = inputParser.parse();

        // Build the network.
        NeuralNetwork network = new NeuralNetwork(
            numLayerNeurons, weights, inputs,
            functionFactory.relu()
        );

        // Start the network.
        network.start();

        // Obtain result.
        List<Double> calculatedResults = network
            .getOutputLayer()
            .getNeurons()
            .stream()
            .map(Neuron::getCalculatedOutput)
            .toList();

        // Get max.
        int maxIndex = 0;
        for (int i = 1; i < calculatedResults.size(); ++i) {
            if (calculatedResults.get(i) > calculatedResults.get(maxIndex)) {
                maxIndex = i;
            }
        }

        // Write output.txt
        String outputPath = "output.txt";
        File outputFile = new File(outputPath);
        try {
            PrintWriter out = new PrintWriter(outputFile);
            out.printf(
                "Index: %d, actual value: %f\n",
                maxIndex, calculatedResults.get(maxIndex)
            );
            out.close();
        }
        catch (FileNotFoundException e) {
            System.err.printf(
                "Failed to write output file (%s).\n", outputFile);
        }
    }
}
