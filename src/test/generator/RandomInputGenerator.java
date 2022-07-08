package test.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

// Class to generate random inputs to neural network
// and output them to CSV file 
public class RandomInputGenerator {

    // Name of file to output inputs of neural network to
    private static String outputFileName;

    // number of random inputs to generate
    int numInputs;

    // assume random inputs are uniformly distributed between lowerBound and upperbound
    double lowerBound;
    double upperBound;

    // Constructor
    public RandomInputGenerator(String outputFileName, int numInputs, double lowerBound, double upperBound) {
        RandomInputGenerator.outputFileName = outputFileName;

        this.numInputs = numInputs;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    // Generate text file (CSV) with random inputs
    public void generateRandomInputsFile() {
        String separator = System.getProperty("line.separator");

        Writer inputsWriter;
        File InputsFile = new File(outputFileName);
        try {
            inputsWriter = new BufferedWriter(new FileWriter(InputsFile));
            double randInput;

            // input layer
            for (int i = 0; i < numInputs; i++) {
                randInput = lowerBound + (upperBound - lowerBound) * Math.random();
                inputsWriter.write(Double.toString(randInput));
                if (i < numInputs - 1) {
                    inputsWriter.write(", ");
                } else {
                    inputsWriter.write(separator);
                }
            }
            inputsWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
