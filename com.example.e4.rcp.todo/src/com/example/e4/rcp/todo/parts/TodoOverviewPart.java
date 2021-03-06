package com.example.e4.rcp.todo.parts;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import com.example.e4.rcp.todo.events.MyEventConstants;
import com.example.e4.rcp.todo.model.ITodoService;
import com.example.e4.rcp.todo.model.Todo;

public class TodoOverviewPart {

	private Button button;
	private TableViewer viewer;

	@Inject
	Shell shell;

	@Inject
	ESelectionService selectionService;

	@Inject
	EPartService partService;

	@Inject
	ITodoService model;

	@PostConstruct
	public void createUserInterface(Composite parent, final ITodoService model, EMenuService menuService) {
		GridLayout layout = new GridLayout(2, false);
		parent.setLayout(layout);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.widthHint = SWT.DEFAULT;
		gridData.heightHint = SWT.DEFAULT;

		viewer = new TableViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION);
		List<Todo> list = model.getTodos();
		viewer.setContentProvider(ArrayContentProvider.getInstance());

		Table table = viewer.getTable();
		table.setLayoutData(gridData);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
		column.getColumn().setText("Summary");
		column.getColumn().setWidth(200);
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Todo todo = (Todo) element;
				return todo.getSummary();
			}

		});

		viewer.setInput(list);

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {

				if (!partService.getDirtyParts().isEmpty()) {
					boolean save = MessageDialog.openConfirm(shell, "Confirm selection",
							"You have changes in your current selection. Do you want to save them?");
					if (save)
					partService.saveAll(false);
				}
				IStructuredSelection selection = viewer.getStructuredSelection();
				selectionService.setSelection(selection.getFirstElement());
			}
		});

		button = new Button(parent, SWT.PUSH);
		button.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		button.setText("Update table");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				viewer.setInput(model.getTodos());
			}
		});

		menuService.registerContextMenu(viewer.getControl(), "com.example.e4.rcp.todo.popupmenu.table");

	}

	@Focus
	public void setFocus() {
		button.setFocus();
	}

	@Inject
	@Optional
	private void subscribeTopicTodoAllTopics(
			@UIEventTopic(MyEventConstants.TOPIC_TODO_ALLTOPICS) Map<String, String> event) {
		if (viewer != null) {
			viewer.setInput(model.getTodos());
		}
	}

}