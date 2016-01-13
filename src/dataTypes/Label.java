package dataTypes;

/**
 * Created with IntelliJ IDEA.
 * User: moni
 * Date: 7/10/14
 * Time: 6:32 PM
 */
public class Label implements Comparable<Label> {
    private Integer id;
    private String label;

    public Label(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Label(String label) {
        this.label = label;
    }

    public Integer getLabelId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public int compareTo(Label l) {
        int comp = this.label.toLowerCase().compareTo(l.getLabel().toLowerCase());
        if (comp < 0) {
            return -1;
        } else if (comp > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Label label1 = (Label) o;

        if (id != null ? !id.equals(label1.id) : label1.id != null) return false;
        if (label != null ? !label.equals(label1.label) : label1.label != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (label != null ? label.hashCode() : 0);
        return result;
    }
}
