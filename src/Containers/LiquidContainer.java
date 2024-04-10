package Containers;

public class LiquidContainer extends HeavyContainer {

    public LiquidContainer(int ID, int weight) {
        super(ID, weight);
    }

    @Override
    public double consumption() {
        return getWeight() * 0.4;
    }
}
