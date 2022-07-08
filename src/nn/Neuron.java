package nn;

public class Neuron implements Runnable {
    protected boolean isInputLayer;
    protected double weight;

    public Neuron(double weight) {
        this.weight = weight;
    }

    public void start() {

    }

    @Override
    public void run() {

    }

    public boolean isInputLayer() {
        return isInputLayer;
    }

    public void setInputLayer(boolean isInputLayer) {
        this.isInputLayer = isInputLayer;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
