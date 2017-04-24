package com.excilys.cdb.servlets.tags;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class LinkTag extends SimpleTagSupport{
    public static final int NUMBER_OF_PAGES = 5;
    
    private int page;
    private Map<String, String[]> param;

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = this.getJspContext().getOut();
        String url ="?";
        if (param != null && !param.isEmpty()) {
            for(Map.Entry<String, String[]> p : param.entrySet()) {
                if (!p.getKey().equals("page")) {
                    url = url + p.getKey() + '=' + p.getValue()[0]+ '&';
                }
            }
        }
        
        out.println("<ul class='pagination'>");
        // Previous
        out.println(li(a("<", url + "page=" + (page - 1))));
        
        // Show pages
        for (int i = 0; i < NUMBER_OF_PAGES; i++) {
            out.println(li(a(String.valueOf(page+i), url + "page=" + String.valueOf(page+i))));
        }
        
        // Next
        out.println(li(a(">", url + "page=" + (page + 1))));
        out.println("</ul>");
    }
    
    public void setPage(String page) {
        this.page = Integer.parseInt(page);
    }

    public void setParam(Map<String, String[]> param) {
        this.param = param;
    }

    private String li(String s){
        return "<li>"+s+"</li>";
    }
    
    private String a(String s, String href){
        return "<a href='"+href+"'>"+s+"</a>";
    }
}
