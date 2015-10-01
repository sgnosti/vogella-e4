 
package com.example.e4.rcp.todo.handlers;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class ExitHandler {

	@Inject
	Shell shell;

	@Inject
	EPartService partService;

	@Execute
	public void execute(IWorkbench workbench) {
		System.out.println(this.getClass().getSimpleName() + " called.");

		if (!partService.getDirtyParts().isEmpty()) {
			boolean save = MessageDialog.openConfirm(shell, "Confirm selection",
					"You have changes in your current selection. Do you want to save them?");
			if (save)
			partService.saveAll(false);
		}

		workbench.close();
	}
		
}