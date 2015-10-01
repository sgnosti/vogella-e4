
package com.example.e4.rcp.todo.handlers;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;

import com.example.e4.rcp.todo.model.ITodoService;
import com.example.e4.rcp.todo.model.Todo;

public class RemoveTodoHandler {

	@Inject
	ITodoService model;

	@Execute
	public void execute(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Todo todo) {
		System.out.println(this.getClass().getSimpleName() + " called.");
		// todo is optional in case the selection is not a Todo object
		if (todo != null)
			model.deleteTodo(todo.getId());
	}

}