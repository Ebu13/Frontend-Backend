package beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


@Named(value = "nbadmin")
@SessionScoped
public class AdminNavigationBean implements Serializable{

    private Map<String, String> pages;
	
	public AdminNavigationBean() {
		pages = new HashMap<>();
	}
	
	public String module(String page) {
		this.pages.put(page, "active");
		return "/admin/pages/"+page+"/index?faces-redirect=true";
	}

	public Map<String, String> getPages() {
		return pages;
	}
}

