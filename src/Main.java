import nn.functions.FunctionFactory;

public class Main {
    public static void main(String[] args) {
        FunctionFactory ff = new FunctionFactory();
        System.out.println(ff.relu().apply(-50));
        System.out.println(ff.relu().apply(1450));
    }
}
