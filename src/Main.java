import nn.*;
import nn.functions.FunctionFactory;
import test.parser.AbstractFileParser;
import test.parser.ExternalInputParser;
import test.parser.WeightsParser;

import java.io.*;
import java.util.List;

/**
 * Tested to be successfully generate 17567.679840
 * with Dr. Shahidi's sample weights and inputs.
 */
public class Main {
    public static void main(String[] args) {
        WeightsParser weightsParser;
        ExternalInputParser inputParser;
        FunctionFactory functionFactory = new FunctionFactory();

        // Default config:
        // 4 neurons in input layer;
        // 5 neurons in each internal layer;
        // 3 neurons in output layer.
        Config networkConfig = Config.defaultConfig();

        // Parse weights and inputs.
        try {
            weightsParser = new WeightsParser(
                "weights.txt", networkConfig
            );
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
            weightsParser.getNumNeuronsInLayer(), weights, inputs,
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

        // According to the question, nothing should be
        // printed to stdout.
    }
}
