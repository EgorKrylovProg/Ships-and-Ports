package Containers;

public class HeavyContainer extends Container {

    public HeavyContainer(int ID, int weight) {
        super(ID, weight);
    }

    @Override
    public double consumption() {
        return getWeight() * 3.0;
    }

}
