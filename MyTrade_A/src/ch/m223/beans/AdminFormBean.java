package ch.m223.beans;

public class AdminFormBean {

	public String portfolio() {
		return "portfolio?faces-redirect=true";
	}

	public String auftraege() {
		return "auftraege?faces-redirect=true";
	}

	public String admin() {
		return "admin?faces-redirect=true";
	}

	public String aktienerfassen() {
		return "aktienerfassen?faces-redirect=true";
	}
}
