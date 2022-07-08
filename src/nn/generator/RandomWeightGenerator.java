package nn.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

// This class
public class RandomWeightGenerator {
    int numLayers;
    double lowerBound;
    double upperBound;

    // number of neurons in input layer
    static final int numNeuronsInputLayer = 4;

    // number of neurons in any internal layer
    static final int numNeuronsInternalLayer = 5;

    // number of neurons in output layer
    static final int numNeuronsOutputLayer = 3;

    // Probability that weight between two neurons is zero
    static double noLinkProbability;

    static String outputFileName;


    // Class constructor
    public RandomWeightGenerator(String outputfilename, int L, double lowerBound, double upperBound, double noLinkProbability) {
        RandomWeightGenerator.outputFileName = outputfilename;
        RandomWeightGenerator.noLinkProbability = noLinkProbability;

        numLayers = L;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    /**
     * Generates random weights equal to zero with a probability of `noLinkProbability`, and otherwise
     * uniformly distributed between `lowerBound` and `upperBound`.
     */
    public void GenerateRandomWeightFile() {
        String separator = System.getProperty("line.separator");

        Writer weightWriter;
        File WeightFile = new File(outputFileName); // "weights.txt"
        try {
            weightWriter = new BufferedWriter(new FileWriter(WeightFile));
            weightWriter.write(numLayers + separator);
            double randWeight;

            // WEIGHTS BETWEEN INPUT LAYER AND FIRST INTERNAL LAYER
            for (int i = 0; i < numNeuronsInputLayer; i++) {
                for (int j = 0; j < numNeuronsInternalLayer; j++) {
                    if (Math.random() < noLinkProbability) {
                        randWeight = 0;
                        weightWriter.write("0");
                    } else {
                        randWeight = lowerBound + (upperBound - lowerBound) * Math.random();
                        weightWriter.write(Double.toString(randWeight));
                    }
                    if (j < numNeuronsInternalLayer - 1) {
                        weightWriter.write(", ");
                    } else {
                        weightWriter.write(separator);
                    }
                }
            }

            // WEIGHTS BETWEEN INTERNAL LAYERS
            for (int l = 0; l < numLayers - 1; l++) {
                for (int i = 0; i < numNeuronsInternalLayer; i++) {
                    for (int j = 0; j < numNeuronsInternalLayer; j++) {
                        if (Math.random() < noLinkProbability) {
                            randWeight = (double) 0;
                            weightWriter.write("0");
                        } else {
                            randWeight = lowerBound + (upperBound - lowerBound) * Math.random();
                            weightWriter.write(Double.toString(randWeight));
                        }
                        if (j < numNeuronsInternalLayer - 1) {
                            weightWriter.write(", ");
                        } else {
                            weightWriter.write(separator);
                        }
                    }
                }
            }

            // WEIGHTS BETWEEN LAST INTERNAL LAYER AND OUTPUT LAYER
            for (int i = 0; i < numNeuronsInternalLayer; i++) {
                for (int j = 0; j < numNeuronsOutputLayer; j++) {

                    if (Math.random() < noLinkProbability) {
                        randWeight = (double) 0;
                        weightWriter.write("0");
                    } else {
                        randWeight = lowerBound + (upperBound - lowerBound) * Math.random();
                        weightWriter.write(Double.toString(randWeight));
                    }
                    if (j < numNeuronsOutputLayer - 1) {
                        weightWriter.write(", ");
                    } else {
                        weightWriter.write(separator);
                    }
                }
            }
            weightWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
