
package bean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import entity.Todo;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import session.TodoFacade;
/**
 *
 * @author dawid
 */

@Named("TodoBean")
@RequestScoped
public class TodoBean {
    
    @EJB
    private TodoFacade todoFacade;
    

    
    private String category;
    private String description;
    private String priority;
    private long id;
    private Todo todo;
    
    @PostConstruct
    public void postConstruct(){
        String todoIdParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("todoId");
        if(todoIdParam != null){
            id = Integer.parseInt(todoIdParam);
            todo = todoFacade.find(id);
            category = todo.getCategory();
            description = todo.getDescription();
            priority = Short.toString(todo.getPriority());
            
        }
    }
    
    
    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getPriority(){
        return priority;
    }
    public void setPriority(String priority){
        this.priority = priority;
    }
   
    
     public String add() {
        Todo newTodo = new Todo();
        newTodo.setCategory(category);
        newTodo.setDescription(description);
        newTodo.setPriority(priority);
        todoFacade.create(newTodo);
        return "Success";
    }
    
    public String update()
    {
        todo.setCategory(category);
        todo.setDescription(description);
        todo.setPriority(priority);
        todoFacade.edit(todo);
        return "Success";
    }
    
    public String delete(){
        todoFacade.remove(todo);
        return "Success";
    }
    
    
}
