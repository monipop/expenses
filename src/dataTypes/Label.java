package dataTypes;

/**
 * Created with IntelliJ IDEA.
 * User: moni
 * Date: 7/10/14
 * Time: 6:32 PM
 */
public class Label {
    private Integer id;
    private String label;

    public Label(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getLabelId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

}
