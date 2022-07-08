package nn;

import java.util.ArrayList;

// Class representing a layer in an artificial neural network
public class NeuralNetLayer extends Thread {
    // List of neurons in this layer
    ArrayList<Neuron> neurons;

    // Any layer is either an input layer to the neural network, an internal layer, or an output layer
    // to the network
    public enum LayerType { INPUT, INTERNAL, OUTPUT }

    LayerType layerType;

    // Constructor for neural network layer
    public NeuralNetLayer(ArrayList<Neuron> neurons, LayerType layerType) {
        this.neurons = neurons;
        this.layerType = layerType;
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
}
