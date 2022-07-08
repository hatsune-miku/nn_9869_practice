package test;

// import static org.junit.jupiter.api.Assertions.*;

import nn.WeightTable;
import test.generator.RandomWeightGenerator;
import test.parser.WeightsParser;

class RandomWeightGeneratorTest {
    public static void main(String[] args) {
        RandomWeightGenerator rwg = new RandomWeightGenerator(
            "weights.txt",
            4, 0.0, 1.0, 0.2
        );
        rwg.generateRandomWeightFile();

        try {
            WeightsParser parser = new WeightsParser("weights.txt");
            WeightTable table = parser.parse();
            table.toString();
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
