package org.exadel.todos.model;

public class Task {
	private String id;
	private String description;
	private boolean done;

	public Task(String id, String description, boolean done) {
		this.setId(id);
		this.setDescription(description);
		this.setDone(done);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public String toString() {
		return "{\"id\":\"" + this.id + "\",\"description\":\"" + this.description + "\",\"done\":" + this.done + "}";
	}
}
