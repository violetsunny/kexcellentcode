package top.kexcellent.back.code.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 字符编码拦截器
 * @author soft01
 *
 */
public class CharacterEncodingFilter implements Filter{

	private String encoding;

	@Override
	public void destroy() {
		
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.encoding = config.getInitParameter("encoding");
	}
	/**
	 * 拦截所有请求的文件，进行编码设置
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		//ServletRequest为父类，而需要的是子类HttpServletRequest，所以先强转，方便调用需要的方法
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		String method = request.getMethod();
		if("post".equals(method)){//post
			request.setCharacterEncoding(encoding);
		}else{//get
			request = new GetRequest(request);
		}
		//放行
		chain.doFilter(request, response);
	}


	/**
	 * HttpServletRequest的子类    使用内部类
	 * @author soft01
	 *
	 */
	private class GetRequest extends HttpServletRequestWrapper{

		private HttpServletRequest reuqest;
		
		//GetRequest构造方法
		public GetRequest(HttpServletRequest request) {
			super(request);
			this.reuqest = request;
		}
		
		/**
		 * 字符
		 */
		@Override
		public String getParameter(String name) {
			String value = this.reuqest.getParameter(name);
			if(null == value){
				return null;
			}else{
				//get
				try {
					return  new String(value.getBytes("iso-8859-1"),CharacterEncodingFilter.this.encoding);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		
		/**
		 * 字符数组
		 */
		@Override
		public String[] getParameterValues(String name) {
			String[] values = this.reuqest.getParameterValues(name);
			if(null == values || values.length == 0){
				return null;
			}else{
				try {
					for (int i = 0; i < values.length; i++) {
						 String value = new String(values[i].getBytes("iso-8859-1"),CharacterEncodingFilter.this.encoding);
						 values[i] = value;
					}
					return values;
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
	}

}
