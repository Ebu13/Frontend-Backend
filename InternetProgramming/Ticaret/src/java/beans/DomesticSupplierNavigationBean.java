package beans;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Named(value = "nbdomestic")
@ViewScoped
public class DomesticSupplierNavigationBean implements Serializable {

	private Map<String, String> pages;
	
	public DomesticSupplierNavigationBean() {
		pages = new HashMap<>();
	}
	
	public String module(String page) {
		this.pages.put(page, "active");
		return "/domesticsupplier/pages/"+page+"/index?faces-redirect=true";
	}

	public Map<String, String> getPages() {
		return pages;
	}
	
	
}
