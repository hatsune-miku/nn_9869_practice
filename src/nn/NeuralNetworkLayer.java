package nn;

import nn.functions.IFunction;

import java.util.ArrayList;
import java.util.List;

// Class representing a layer in an artificial neural network
public class NeuralNetworkLayer extends Thread {
    // List of neurons in this layer
    ArrayList<Neuron> neurons;

    // Any layer is either an input layer to the neural network, an internal layer, or an output layer
    // to the network
    public enum LayerType { INPUT, INTERNAL, OUTPUT }

    LayerType layerType;

    private NeuralNetwork network;
    private int layerIndex;

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

        for (int i = 0; i < numNeurons; ++i) {
            neurons.add(
                new Neuron(
                    network, activationFunction, layerIndex, i
                )
            );
        }
    }

    @Override
    public void run() {
		for (Neuron neuron : neurons) {
			// Neurons should know whether they expect their inputs from
			// the inputs to entire network or from outputs of previous layer
			neuron.setInputLayer(layerType == LayerType.INPUT);

			// Start threads for neurons in this layer
			neuron.start();

//            try {
//                // This may not be necesary - good to slow things down a bit
//                Thread.sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

		}

    }

    public List<Neuron> getNeurons() {
        return neurons;
    }

    public NeuralNetwork getNetwork() {
        return network;
    }
}
