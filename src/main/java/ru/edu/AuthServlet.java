package ru.edu;

import java.io.IOException;
import java.io.Writer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession();
		UserInfo info = (UserInfo) session.getAttribute("userInfo");

		// Если сессии нет, то предлагаем авторизоваться
		if (info == null) {
			Writer writer = resp.getWriter();
			writer.write("<html>");
			writer.write("<body>");
			writer.write("<h1>Create new user</h1>");
			writer.write("<form  method=\"POST\" action=\"\">");
			writer.write("<input name=\"shouldDelete\" value=\"false\" hidden><br>");
			writer.write("LOGIN: <input name=\"login\"><br>");
			writer.write("NAME: <input name=\"name\"><br>");
			writer.write("<input type=\"submit\" value=\"create\"><br>");
			writer.write("</form>");
			writer.write("</body>");
			writer.write("</html>");
		} else {
			// если сессия есть, то выводим данные пользователя
			Writer writer = resp.getWriter();
			writer.write("<html>");
			writer.write("<body>");
			writer.write("<h1>Hello, " + info.getName() + "</h1>");
			writer.write("LOGIN: " + info.getLogin() + " <br>");
			writer.write("NAME: " + info.getName() + "<br>");
			writer.write("<hr>");
			writer.write("<form  method=\"POST\" action=\"\">");
			writer.write("<input name=\"shouldDelete\" value=\"true\" hidden><br>");
			writer.write("<input type=\"submit\" value=\"DELETE\"><br>");
			writer.write("</form>");
			writer.write("</body>");
			writer.write("</html>");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		boolean shouldDelete = Boolean.parseBoolean(req.getParameter("shouldDelete"));

		if (!shouldDelete) {
			String login = req.getParameter("login");
			String name = req.getParameter("name");

			UserInfo info = new UserInfo(login, name);

			// создаем пользователя в сессии
			HttpSession session = req.getSession();
			session.setAttribute("userInfo", info);

			Writer writer = resp.getWriter();
			writer.write("<html><body>OK</html></body>");
		} else {
			// удаляем пользователя в сессии
			HttpSession session = req.getSession();
			session.removeAttribute("userInfo");

			Writer writer = resp.getWriter();
			writer.write("<html><body>Deleted  OK</html></body>");
		}
	}

}
