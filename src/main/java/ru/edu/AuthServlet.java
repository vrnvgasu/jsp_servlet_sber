package ru.edu;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Writer;
import java.util.Base64;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Cookie infoCookie = getUserInfoCookie(req);

		// Если куки нет, то предлагаем авторизоваться
		if (infoCookie == null) {
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
			// если кука есть, то выводим данные пользователя
			String infoJson = new String(Base64.getDecoder().decode(infoCookie.getValue()));
			UserInfo info = new ObjectMapper().readValue(infoJson, UserInfo.class);

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
			String infoJson = new ObjectMapper().writeValueAsString(info);

			// json кодируем в base64, чтобы исключить служебные символы
			String encodedInfo = Base64.getEncoder().encodeToString(infoJson.getBytes());
			resp.addCookie(new Cookie("userInfo", encodedInfo));

			Writer writer = resp.getWriter();
			writer.write("<html><body>OK</html></body>");
		} else {
			// удаляем пользователя в куке
			Cookie cookie = new Cookie("userInfo", "");
			// куку удалять нельзя, т.к. она на фронте, но можем ее обновить
			// и поставить время жизни - 0. Браузер сам ее удалит
			cookie.setMaxAge(0);
			resp.addCookie(cookie);

			Writer writer = resp.getWriter();
			writer.write("<html><body>Deleted  OK</html></body>");
		}
	}

	private Cookie getUserInfoCookie(HttpServletRequest request) {
		if (request.getCookies() == null) {
			return null;
		}

		for (Cookie cookie: request.getCookies()) {
			if (cookie.getName().equals("userInfo")) {
				return cookie;
			}
		}

		return null;
	}

}
