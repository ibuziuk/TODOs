package org.exadel.todos.controller;

import static org.exadel.todos.util.TaskUtil.StringToJson;
import static org.exadel.todos.util.TaskUtil.TOKEN;
import static org.exadel.todos.util.TaskUtil.getIndex;
import static org.exadel.todos.util.TaskUtil.getSubTasksByIndex;
import static org.exadel.todos.util.TaskUtil.jsonToTask;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.exadel.todos.model.Task;
import org.exadel.todos.model.TaskStorage;
import org.exadel.todos.util.ServletUtil;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

@WebServlet("/todos")
public class TaskServlet extends HttpServlet {
	private static final String APPLICATION_JSON = "application/json";
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		addStubData();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = request.getParameter(TOKEN);
		System.out.println("Token " + token);

		if (token != null && !"".equals(token)) {
			int index = getIndex(token);
			System.out.println("Index " + index);
			String tasks = getSubTasksByIndex(index);
			response.setContentType(APPLICATION_JSON);
			PrintWriter out = response.getWriter();
			out.print(tasks);
			out.flush();
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String data = ServletUtil.getData(request);
		System.out.println(data);
		try {
			JSONObject json = StringToJson(data);
			Task task = jsonToTask(json);
			TaskStorage.getStorage().add(task);
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (ParseException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String data = ServletUtil.getData(request);
		System.out.println(data);
		try {
			JSONObject json = StringToJson(data);
			Task task = jsonToTask(json);
			String id = task.getId();
			Task taskToUpdate = TaskStorage.getTaskById(id);
			if (taskToUpdate != null) {
				taskToUpdate.setDescription(task.getDescription());
				taskToUpdate.setDone(task.isDone());
				response.setStatus(HttpServletResponse.SC_OK);
			} else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}
		} catch (ParseException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	private void addStubData() {
		Task[] stubTasks = { new Task("1", "Create markup", true), new Task("2", "Learn JavaScript", true),
				new Task("3", "Write The Chat !", false) };
		TaskStorage.getStorage().addAll(Arrays.asList(stubTasks));
	}

}
