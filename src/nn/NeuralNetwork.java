package nn;

import nn.functions.FunctionFactory;
import nn.functions.IFunction;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {
    protected final int[] numLayerNeurons;
    protected final List<NeuralNetworkLayer> layers;
    protected final WeightTable globalWeights;
    protected final ExternalInputTable externalInputs;


    public NeuralNetwork(
        int[] numLayerNeurons,
        WeightTable globalWeights,
        ExternalInputTable externalInputs,
        IFunction activationFunction
    ) {
        this.numLayerNeurons = numLayerNeurons;
        this.layers = new ArrayList<>();
        this.globalWeights = globalWeights;
        this.externalInputs = externalInputs;

        // Generate layers.
        for (int i = 0; i < numLayerNeurons.length; ++i) {
            NeuralNetworkLayer.LayerType layerType;
            if (i == 0) {
                // Input layer.
                layerType = NeuralNetworkLayer.LayerType.INPUT;
            } else if (i == numLayerNeurons.length - 1) {
                // Output layer.
                layerType = NeuralNetworkLayer.LayerType.OUTPUT;
            } else {
                // Internal layer.
                layerType = NeuralNetworkLayer.LayerType.INTERNAL;
            }
            layers.add(new NeuralNetworkLayer(
                this, layerType,
                i, numLayerNeurons[i], activationFunction
            ));
        }
    }

    public void start() {
        for (NeuralNetworkLayer layer : layers) {
            layer.start();
        }
    }

    public int[] getNumLayerNeurons() {
        return numLayerNeurons;
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
