
package com.example.e4.rcp.todo.handlers;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.services.IServiceConstants;

import com.example.e4.rcp.todo.events.MyEventConstants;
import com.example.e4.rcp.todo.model.ITodoService;
import com.example.e4.rcp.todo.model.Todo;

public class RemoveTodoHandler {

	@Inject
	ITodoService model;


	@Execute
	public void execute(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Todo todo, IEventBroker broker) {
		System.out.println(this.getClass().getSimpleName() + " called.");
		// todo is optional in case the selection is not a Todo object
		if (todo != null) {
			model.deleteTodo(todo.getId());
			// broker.post(MyEventConstants.TOPIC_TODO_DELETE,
			// createEventData(MyEventConstants.TOPIC_TODO_DELETE, todo));
		}

	}

	private Map<String, String> createEventData(String topicTodoDelete, Todo todo) {
		Map<String, String> result = new HashMap<>();
		result.put(MyEventConstants.TOPIC_TODO, topicTodoDelete);
		result.put(Todo.FIELD_ID, String.valueOf(todo.getId()));
		return result;
	}

}