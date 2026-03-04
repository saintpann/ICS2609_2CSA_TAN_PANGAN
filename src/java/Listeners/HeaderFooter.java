package Listeners;

import java.util.Date;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class HeaderFooter implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        context.setAttribute("date", new Date());
        context.setAttribute("header", "/WEB-INF/jsps/misc/header.jsp");
        context.setAttribute("footer", "/WEB-INF/jsps/misc/footer.jsp");
    }
    
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Context Destroyed");
    }
}