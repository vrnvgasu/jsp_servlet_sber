package ru.edu;

import java.io.IOException;
import java.io.Writer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		HttpSession session = req.getSession();
		UserInfo info = (UserInfo) session.getAttribute("userInfo");

		// Если сессии нет, то предлагаем авторизоваться
		if (info == null) {
			getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
		} else {
			// если сессия есть, то выводим данные пользователя
			// будем считывать параметры из request в jsp объекте
			req.setAttribute("name", info.getName());
			req.setAttribute("login", info.getLogin());
			getServletContext().getRequestDispatcher("/info.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		boolean shouldDelete = Boolean.parseBoolean(req.getParameter("shouldDelete"));
		String status;

		if (!shouldDelete) {
			String login = req.getParameter("login");
			String name = req.getParameter("name");

			UserInfo info = new UserInfo(login, name);

			// создаем пользователя в сессии
			HttpSession session = req.getSession();
			session.setAttribute("userInfo", info);

			status = "OK";
		} else {
			// удаляем пользователя в сессии
			HttpSession session = req.getSession();
			session.removeAttribute("userInfo");

			status = "Deleted  OK";
		}

		req.setAttribute("message", status );
		getServletContext().getRequestDispatcher("/status.jsp").forward(req, resp);
	}

}
