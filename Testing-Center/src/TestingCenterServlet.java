import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TestingCenterServlet")
public class TestingCenterServlet extends HttpServlet
{
	private TestingCenter tc;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Preprocess request: load list of products for display in JSP.
		request.getRequestDispatcher("/WEB-INF/index.html").forward(request, response);
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Postprocess request: gather and validate submitted data and display the result in the same JSP.

		tc = TestingCenter.getTestingCenter();
        request.getSession().setAttribute("Tc", tc);
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        
        
        
        
//        // Prepare messages.
//        Map<String, String> messages = new HashMap<String, String>();
//        request.setAttribute("messages", messages);
//
//        // Get and validate name.
//        String name = request.getParameter("name");
//        if (name == null || name.trim().isEmpty()) {
//            messages.put("name", "Please enter name");
//        } else if (!name.matches("\\p{Alnum}+")) {
//            messages.put("name", "Please enter alphanumeric characters only");
//        }
//
//        // Get and validate age.
//        String age = request.getParameter("age");
//        if (age == null || age.trim().isEmpty()) {
//            messages.put("age", "Please enter age");
//        } else if (!age.matches("\\d+")) {
//            messages.put("age", "Please enter digits only");
//        }
//
//        // No validation errors? Do the business job!
//        if (messages.isEmpty()) {
//            messages.put("success", String.format("Hello, your name is %s and your age is %s!", name, age));
//        }

        request.getRequestDispatcher("/WEB-INF/index.html").forward(request, response);
    }
	
}
