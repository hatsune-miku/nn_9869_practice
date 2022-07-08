package test;

import test.generator.RandomInputGenerator;
import test.generator.RandomWeightGenerator;

public class GeneratorMain {
    /**
     * Run this to generate random weights and inputs.
     * @param args -
     */
    public static void main(String[] args) {
        RandomWeightGenerator weightGenerator = new RandomWeightGenerator(
            "weights_custom.txt", 4,
            0.0, 1.0, 0.2
        );

        RandomInputGenerator inputGenerator = new RandomInputGenerator(
            "inputs_custom.txt", 4, 0.0, 1.0
        );

        weightGenerator.generateRandomWeightFile();
        inputGenerator.generateRandomInputsFile();
    }
}
