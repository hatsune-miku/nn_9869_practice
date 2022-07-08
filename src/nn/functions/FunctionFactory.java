package nn.functions;

public class FunctionFactory {
    /**
     * ReLU: g(z) = max(0, z)
     */
    public IFunction relu() {
        return x -> Math.max(0, x);
    }
}
