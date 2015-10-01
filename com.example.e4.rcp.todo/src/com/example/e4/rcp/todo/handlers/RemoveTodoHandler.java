 
package com.example.e4.rcp.todo.handlers;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;

import com.example.e4.rcp.todo.model.ITodoService;
import com.example.e4.rcp.todo.model.Todo;

public class RemoveTodoHandler {
	
	@Inject
	ITodoService model;
	
	@Inject
	ESelectionService selectionService;
	
	@Execute
	public void execute() {
		System.out.println(this.getClass().getSimpleName() + " called.");
		Todo todo = (Todo) selectionService.getSelection();
		model.deleteTodo(todo.getId());
	}
		
}