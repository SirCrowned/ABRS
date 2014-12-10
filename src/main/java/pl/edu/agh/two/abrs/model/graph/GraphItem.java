package pl.edu.agh.two.abrs.model.graph;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "graphitem")
@Table(name = "graphitem")
public class GraphItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "html_id")
    private String html_id;

    @Column(name = "left")
    private int left;

    @Column(name = "top")
    private int top;

    @Column(name = "label", unique = true)
    private String label;

    public GraphItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHtml_id() {
        return html_id;
    }

    public void setHtml_id(String html_id) {
        this.html_id = html_id;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GraphItem graphItem = (GraphItem) o;

        if (!label.equals(graphItem.label)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return label.hashCode();
    }
}
