package model;

/**
 * Instance of class {@code Part}
 *
 * @version 1.0
 */
public class Part {
    /**
     * Topology type
     */
    private Topology type;
    /**
     * Starting index pointing to index buffer
     */
    private int index;
    /**
     * Number of entities
     */
    private int count;

    /**
     * Create part
     *
     * @param type  of type
     * @param index starting index
     * @param count number of elements
     */
    public Part(Topology type, int index, int count) {
        this.type = type;
        this.index = index;
        this.count = count;
    }

    public Topology getType() {
        return type;
    }

    public void setType(Topology type) {
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Part{" +
                "type=" + type +
                ", index=" + index +
                ", count=" + count +
                '}';
    }
}
