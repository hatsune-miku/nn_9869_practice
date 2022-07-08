package nn;

import nn.functions.FunctionFactory;
import nn.functions.IFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class NeuralNetwork {
    /**
     * numLayerNeurons[X] = Y means that layer X has Y neurons.
     */
    protected final int[] numLayerNeurons;

    /**
     * A network is consist of many layers.
     */
    protected final List<NeuralNetworkLayer> layers;

    /**
     * The network also store the globally-shared weight data.
     */
    protected final WeightTable globalWeights;
    protected final ExternalInputTable externalInputs;


    /**
     * Constructor.
     *
     * @param numNeuronsInLayer    numNeuronsInLayer[X] = Y means that layer X has Y neurons.
     * @param globalWeights      -
     * @param externalInputs     -
     * @param activationFunction -
     */
    public NeuralNetwork(
        int[] numNeuronsInLayer,
        WeightTable globalWeights,
        ExternalInputTable externalInputs,
        IFunction activationFunction
    ) {
        this.numLayerNeurons = numNeuronsInLayer;
        this.layers = new ArrayList<>();
        this.globalWeights = globalWeights;
        this.externalInputs = externalInputs;

        // Generate layers.
        for (int i = 0; i < numNeuronsInLayer.length; ++i) {
            NeuralNetworkLayer.LayerType layerType;
            if (i == 0) {
                // Input layer.
                layerType = NeuralNetworkLayer.LayerType.INPUT;

            } else if (i == numNeuronsInLayer.length - 1) {
                // Output layer.
                layerType = NeuralNetworkLayer.LayerType.OUTPUT;

            } else {
                // Internal layer.
                layerType = NeuralNetworkLayer.LayerType.INTERNAL;
            }
            layers.add(new NeuralNetworkLayer(
                this, layerType,
                i, numNeuronsInLayer[i], activationFunction
            ));
        }
    }

    /**
     * In the same layer all neurons can run simultaneously
     * because they do not interfere with each other,
     * but in layer level not.
     * <p>
     * The network must be run in a layer-by-layer mode.
     */
    public void start() {
        for (NeuralNetworkLayer layer : layers) {
            // Create the latch and set the capacity to
            // be equal to the number of neurons.
            CountDownLatch latch = new CountDownLatch(layer.getNeurons().size());
            layer.setLatch(latch);
            layer.start();

            try {
                // Wait until all neurons in this layer
                // are finished.
                latch.await();
            } catch (Exception e) {
                System.err.println("Error when executing the network!");
                System.exit(1);
            }

            // Next layer, another latch will be created
            // and old one will be collected.
        }
    }

    public NeuralNetworkLayer getOutputLayer() {
        return layers.get(layers.size() - 1);
    }

    public List<NeuralNetworkLayer> getLayers() {
        return layers;
    }

    public WeightTable getGlobalWeights() {
        return globalWeights;
    }

    public ExternalInputTable getExternalInputs() {
        return externalInputs;
    }
}
