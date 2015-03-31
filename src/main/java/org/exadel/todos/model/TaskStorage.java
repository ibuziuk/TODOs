package org.exadel.todos.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskStorage {
	private static final List<Task> INSTANSE = Collections.synchronizedList(new ArrayList<Task>());

	private TaskStorage() {
	}

	public static List<Task> getStorage() {
		return INSTANSE;
	}

	public static Task getTaskById(String id) {
		for (Task task : INSTANSE) {
			if (task.getId().equals(id)) {
				return task;
			}
		}
		return null;
	}

}
