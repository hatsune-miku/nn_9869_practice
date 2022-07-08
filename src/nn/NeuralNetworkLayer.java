package nn;

import nn.functions.IFunction;
import util.UniformRandom;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

// Class representing a layer in an artificial neural network
public class NeuralNetworkLayer extends Thread {
    // List of neurons in this layer
    ArrayList<Neuron> neurons;

    // Any layer is either an input layer to the neural network, an internal layer, or an output layer
    // to the network
    public enum LayerType { INPUT, INTERNAL, OUTPUT }

    protected LayerType layerType;
    protected final NeuralNetwork network;
    protected int layerIndex;

    protected CountDownLatch latch;
    protected UniformRandom random;


    // Constructor for neural network layer
    public NeuralNetworkLayer(
        NeuralNetwork network,
        LayerType layerType,
        int layerIndex,
        int numNeurons,
        IFunction activationFunction
    ) {
        this.network = network;
        this.layerIndex = layerIndex;
        this.layerType = layerType;
        this.neurons = new ArrayList<>();
        this.latch = null;
        this.random = new UniformRandom();

        for (int i = 0; i < numNeurons; ++i) {
            neurons.add(
                new Neuron(network, activationFunction, layerIndex, i)
            );
        }
    }

    @Override
    public void run() {
        for (Neuron neuron : neurons) {
            // Neurons should know whether they expect their inputs from
            // the inputs to entire network or from outputs of previous layer
            neuron.setInputLayer(layerType == LayerType.INPUT);

            // Start threads for neurons in this layer.
            // Neurons at the same layer can run simultaneously.
            neuron.setLatch(latch);
            neuron.start();

            try {
                // A random delay between 5 and 20 ms.
                Thread.sleep(random.nextInt(5, 20));
            } catch (InterruptedException e) {
                // Ignored.
            }
        } // end for
    }

    public List<Neuron> getNeurons() {
        return neurons;
    }

    public NeuralNetwork getNetwork() {
        return network;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }
}
