
package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author dawid
 */

@Entity
@Table(name = "todo")
@NamedQueries({
        @NamedQuery(name = "Todo.findAll", query = "SELECT t FROM Todo t")
})
public class Todo implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Size(max = 255)
    @Column(name = "category")
    private String category;

    @Size(max = 2550)
    @Column(name = "description")
    private String description;

    @Column(name = "priority")
    private short priority;

    // Empty Constructor
    public Todo() {
    }

    /**
     * 
     * @param id
     * @param category
     * @param description
     * @param priority
     */
    public Todo(long id, String category, String description, String priority) {
        setTodoId(id);
        setCategory(category);
        setDescription(description);
        setPriority(priority);
    }

    // Getters and Setters

    public void setTodoId(long id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(String priority) {
        this.priority = (short) Integer.parseInt(priority);
    }

    public long getTodoId() {
        return this.id;
    }

    public String getCategory() {
        return this.category;
    }

    public String getDescription() {
        return this.description;
    }

    public short getPriority() {
        return this.priority;
    }

    @Override
    public String toString() {
        return "Todo: Category - " + getCategory() + ", Description: " + getDescription() + ", Priority: " + getPriority();
    }
}
