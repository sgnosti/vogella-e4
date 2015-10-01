
package com.example.e4.rcp.todo.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class SaveAllHandler {

	@Execute
	public void execute(EPartService partService) {
		System.out.println(this.getClass().getSimpleName() + " called.");
		partService.saveAll(false);
	}

	@CanExecute
	public boolean canExecute(@Optional MDirtyable dirty) {
		return dirty.isDirty();
	}

}