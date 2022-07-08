/*
 * zguan@mun.ca
 * Student Number: 202191382
 */

package nn;

import nn.functions.IFunction;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * The basic unit of the neural network.
 */
public class Neuron extends Thread {
    /**
     * Each neuron keep a reference to the network
     * to obtain some information including `globalWeights`.
     */
    protected final NeuralNetwork network;
    protected final IFunction activationFunction;

    /**
     * Neurons also remember corresponding layer's index
     * and themselves.
     */
    protected final int layerIndex;
    protected final int neuronIndex;

    protected boolean isInputLayer;

    /**
     * Store the output of the neuron.
     * Initially 0.
     */
    protected double calculatedOutput;

    /**
     * Concurrent control for layers.
     */
    protected CountDownLatch latch;

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
        this.latch = null;
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

        // Don't need a mutex, because all
        // these operations are thread-local or read-only.
        if (isInputLayer) {
            // Get output from external input.
            ExternalInputTable inputs = network.getExternalInputs();
            output = inputs.getInput(neuronIndex);
        } else {
            // Get output from previous layer.
            WeightTable weights = network.getGlobalWeights();
            List<Neuron> prevNeurons = network
                .getLayers().get(layerIndex - 1)
                .getNeurons();
            output = 0;
            for (Neuron prevNeuron : prevNeurons) {
                // Get weight and previous output.
                int prevNeuronIndex = prevNeuron.getNeuronIndex();
                double prevOutput = prevNeuron.getCalculatedOutput();
                double weight = weights.getWeight(layerIndex, prevNeuronIndex, neuronIndex);

                // Sum up to self output.
                output += prevOutput * weight;
            }
        }
        // Apply activation function.
        setCalculatedOutput(
            activationFunction.apply(output)
        );

        // I am ready!
        if (latch != null) {
            latch.countDown();
            latch = null;
        }
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

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }
}
