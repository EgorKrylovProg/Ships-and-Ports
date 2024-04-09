package Containers;


public abstract class Container implements Comparable<Container> {
    private int ID;
    private int weight;

    public Container(int ID, int weight) {
        setID(ID);
        setWeight(weight);
    }

    public static Container createContainer(int ID, int weight) {
        if (weight <= 3000) {
            return new BasicContainer(ID, weight);
        }
        return new HeavyContainer(ID, weight);
    }
    public static Container createContainer(int ID, int weight, char type) {
        if (type != 'R' && type != 'L') {
            throw new IllegalArgumentException("The type container can be only R or L!");
        }

        if (type == 'R') {
            return new RefrigeratedContainer(ID, weight);
        }
        return new LiquidContainer(ID, weight);
    }

    public abstract double consumption();

    private void setID(int ID) {
        if (ID < 0) throw new IndexOutOfBoundsException("The id port can't be negative!");
        this.ID = ID;
    }
    private void setWeight(int weight) {
        if (weight < 0) throw new IndexOutOfBoundsException("The weight container can't be negative!");
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public int getID() {
        return ID;
    }

    @Override
    public int compareTo(Container o) {
        return this.getID() - o.getID();
    }
}
