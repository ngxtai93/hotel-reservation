package team6.helper;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

public class HtmlPrinter implements IHtmlPrinter {
	
	PrintWriter pw;
	HttpServletRequest request;

	public HtmlPrinter(HttpServletRequest request, PrintWriter pw) {
		this.pw = pw;
		this.request = request;
	}

	@Override
	public void PrintHtml(String file) {
		// TODO Auto-generated method stub

	}
	@Override
	public String ParseHtml(String html) {
		// TODO Auto-generated method stub
		return null;
	}

}
