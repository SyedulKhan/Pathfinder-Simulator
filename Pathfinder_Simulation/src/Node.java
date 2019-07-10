//Syedul Khan   15330190

public class Node {
    private int i, j;
    private int weight;
    private boolean notBlocked;
    private Node parent;
    private double g;
    private double h;
    private double cost;
    private double totalCost;

    public Node(int i, int j, boolean notBlocked, int weight) {
        this.i = i;
        this.j = j;
        this.notBlocked = notBlocked;
        this.weight = weight;
    }

    public void setICoordinate(int i) {
        this.i = i;
    }

    public int getICoordinate() {
        return i;
    }

    public void setJCoordinate(int j) {
        this.j = j;
    }

    public int getJCoordinate() {
        return j;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setIsNotBlocked(boolean notBlocked) {
        this.notBlocked = notBlocked;
    }

    public boolean getIsNotBlocked() {
        return notBlocked;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getParent() {
        return parent;
    }

    public void setG(Node parent, double g) {
        this.g = parent.getG() + g;
        setCost(g);

    }

    public double getG() {
        return g;
    }

    public double calculateG(Node parent) {
        return (parent.getG() + getCost());
    }

    public void setH(Node destination, String metricType) {
        switch (metricType) {
            case "Manhattan": {
                h = (Math.abs(getICoordinate() - destination.getICoordinate())
                        + Math.abs(getJCoordinate() - destination.getJCoordinate()));
                break;
            }
            case "Euclidean": {
                double hSqrd = Math.pow((getICoordinate() - destination.getICoordinate()), 2)
                        + Math.pow((getJCoordinate() - destination.getJCoordinate()), 2);
                h = Math.sqrt(hSqrd);

                break;
            }
            case "Chebyshev": {
                h = Math.max(Math.abs(getICoordinate() - destination.getICoordinate()),
                        Math.abs(getJCoordinate() - destination.getJCoordinate()));
                break;
            }
        }
    }

    public double getH() {
        return h;
    }

    public double getF() {
        return g + h;
    }

}
