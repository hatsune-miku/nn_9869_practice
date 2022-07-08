package nn;

public class ExternalInputTable {
    /**
     * The maximum neurons allowed in the input layer.
     */
    public final static int NEURONS_COUNT_MAX = 2022;

    /**
     * table[X] = Y means the input of neuron X is Y.
     */
    protected double[] table;

    /**
     * Constructor.
     *
     * @param numNeurons The number of neurons in the input layer.
     */
    public ExternalInputTable(int numNeurons) {
        this.table = new double[numNeurons];
    }

    /**
     * Constructor.
     *
     * @param neurons The given neurons array.
     */
    public ExternalInputTable(double[] neurons) {
        this.table = neurons;
    }

    public double getInput(int neuronIndex) {
        return table[neuronIndex];
    }

    public void setInput(int neuronIndex, double input) {
        table[neuronIndex] = input;
    }
}
