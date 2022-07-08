package nn;

public record Config(
    int inputLayerSize,
    int internalLayerSize,
    int outputLayerSize
) {
    /**
     * Configuration:
     * How many neurons in the input/internal/output layer?
     */
    public final static int DEFAULT_INPUT_LAYER_SIZE = 4;
    public final static int DEFAULT_INTERNAL_LAYER_SIZE = 5;
    public final static int DEFAULT_OUTPUT_LAYER_SIZE = 3;

    public static Config defaultConfig() {
        return new Config(
            DEFAULT_INPUT_LAYER_SIZE,
            DEFAULT_INTERNAL_LAYER_SIZE,
            DEFAULT_OUTPUT_LAYER_SIZE
        );
    }
}
