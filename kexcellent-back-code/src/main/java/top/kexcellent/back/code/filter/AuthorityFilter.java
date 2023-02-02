package top.kexcellent.back.code.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 登录拦截器
 * 
 * @author soft01
 * 
 */
public class AuthorityFilter implements Filter {

    @Override
    public void destroy() {

    }

    /**
     * 过滤器
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        // ServletRequest为父类，而需要的是子类HttpServletRequest，所以先强转，方便调用需要的方法
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        // URL为绝对路径 URI为相对于服务器地址的相对路径(web名+"/")
        String uri = request.getRequestURI();

        // web名
        String contextPath = request.getContextPath();

        /*if (!uri.contains("demo.jsp") && !uri.contains("regist.jsp") && !uri.contains("index.jsp") && !uri.contains("query4Index.action") && !uri.contains("control.jsp")
            && !uri.contains("getCode.action") && !uri.contains("sessionInvalidate4JoyBeansUser.action") && !uri.contains("queryGameLike.action")
            && !uri.contains("queryGame4Buy.action") && !uri.contains("queryGame4Down.action") && !uri.contains("gameBuy.action") && !uri.contains("gameBuy.jsp")
            && !uri.contains("gameDetails.jsp") && !uri.contains("game.jsp") && !("/Joy/").equalsIgnoreCase(uri)) {
            JoyBeansUser joyBeansUser = (JoyBeansUser) request.getSession().getAttribute("joyBeansUser");
            if (null == joyBeansUser) {
                // 重定向
                response.sendRedirect(contextPath + "/" + "njwb/prompt/control.jsp");
            }
        }*/
        // 不满足上面条件的都放行
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

}
