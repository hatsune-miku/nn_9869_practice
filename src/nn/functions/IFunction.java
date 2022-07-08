package nn.functions;

/**
 * Protocol for an activation function g(z).
 */
public interface IFunction {
    double apply(double input);
}
