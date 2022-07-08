package nn;

import nn.functions.IFunction;

import java.util.List;

public class Neuron implements Runnable {
    protected final NeuralNetwork network;
    protected final IFunction activationFunction;
    protected final int layerIndex;
    protected final int neuronIndex;

    protected boolean isInputLayer;
    protected double calculatedOutput;

    public Neuron(
        NeuralNetwork network,
        IFunction activationFunction,
        int layerIndex,
        int neuronIndex
    ) {
        this.network = network;
        this.activationFunction = activationFunction;
        this.layerIndex = layerIndex;
        this.neuronIndex = neuronIndex;
        this.calculatedOutput = 0;
        this.isInputLayer = false;
    }

    public void start() {
        run();
    }

    /**
     * Run this neuron according to the formula of fully-connected layer:
     *
     * <ul>z = g(a1*w1 + a2*w2 + ... + an*wn)</ul> where
     *
     * <li>`z` is the neuron's output</li>
     * <li>`g` is activation function</li>
     * <li>`an` is the `n`th input</li>
     * <li>`wn` is the `n`th weight</li>
     * </ul>
     */
    @Override
    public void run() {
        double output;

        if (isInputLayer) {
            // Get output from external input.
            ExternalInputTable inputs = network.getExternalInputs();
            output = inputs.getInput(neuronIndex);
        } else {
            // Get output from previous layer.
            WeightTable weights = network.getGlobalWeights();
            List<Neuron> lastNeurons = network
                .getLayers().get(layerIndex - 1)
                .getNeurons();
            output = 0;
            for (Neuron lastNeuron : lastNeurons) {
                // Get weight and last output.
                int lastIndex = lastNeuron.getNeuronIndex();
                double lastOutput = lastNeuron.getCalculatedOutput();
                double weight = weights.getWeight(layerIndex, lastIndex, neuronIndex);

                // Sum up to self output.
                output += lastOutput * weight;
            }
        }
        // Apply activation function.
        setCalculatedOutput(
            activationFunction.apply(output)
        );
    }

    public boolean isInputLayer() {
        return isInputLayer;
    }

    public void setInputLayer(boolean isInputLayer) {
        this.isInputLayer = isInputLayer;
    }

    public double getCalculatedOutput() {
        return calculatedOutput;
    }

    private void setCalculatedOutput(double output) {
        this.calculatedOutput = output;
    }

    public int getNeuronIndex() {
        return neuronIndex;
    }
}
