package Containers;

public class BasicContainer extends Container {

    public BasicContainer(int ID, int weight) {
        super(ID, weight);
    }

    @Override
    public double consumption() {
        return getWeight() * 2.5;
    }
}
