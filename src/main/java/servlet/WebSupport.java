package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Hỗ trợ các Servlet trong cùng gói. */
abstract class WebSupport extends HttpServlet {
    @FunctionalInterface
    interface Action {
        void run() throws IOException, ServletException, SQLException;
    }

    final void handle(HttpServletRequest request, HttpServletResponse response,
                      String role, Action action) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        var session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            String demo = System.getenv("GARAMAN_DEMO_" + role);
            if (demo == null || demo.isBlank()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            session = request.getSession(true);
            session.setAttribute("username", demo);
            session.setAttribute("role", role);
        }
        if (!role.equals(session.getAttribute("role"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        try {
            action.run();
        } catch (SQLException error) {
            throw new ServletException("Không thể xử lý dữ liệu GaraMan", error);
        }
    }

    static void view(HttpServletRequest request, HttpServletResponse response, String jsp)
            throws IOException, ServletException {
        request.getRequestDispatcher("/view/" + jsp + ".jsp").forward(request, response);
    }

    static void forward(HttpServletRequest request, HttpServletResponse response,
                        String path, String action) throws IOException, ServletException {
        request.setAttribute("garamanInternalAction", action);
        request.getRequestDispatcher(path).forward(request, response);
    }

    static String text(HttpServletRequest request, String name, int maxLength) {
        String value = request.getParameter(name);
        value = value == null ? "" : value.strip();
        if (value.length() > maxLength) throw new IllegalArgumentException(name);
        return value;
    }

    static LocalDate date(HttpServletRequest request, String name) {
        return LocalDate.parse(text(request, name, 10));
    }

    static int id(HttpServletRequest request, String name) {
        int value = Integer.parseInt(text(request, name, 10));
        if (value < 1) throw new IllegalArgumentException(name);
        return value;
    }
}
