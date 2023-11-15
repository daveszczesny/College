
package bean;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.util.List;
import session.TodoFacade;
import entity.Todo;
import jakarta.annotation.PostConstruct;
import java.io.Serializable;

/**
 *
 * @author dawid
 */

@Named("TodoListBean")
@RequestScoped
public class TodoListBean implements Serializable{
    
    @EJB
    private TodoFacade todoFacade;
    
    private List<Todo> todoList;
    
    @PostConstruct
    public void postContruct(){
        todoList = todoFacade.findAll();
    }
    
    public List<Todo> getTodoList(){
        return todoList;
    }
    
}
