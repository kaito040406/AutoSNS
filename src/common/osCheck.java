package common;

public class osCheck {
	private static String OS = System.getProperty("os.name").toLowerCase();
	private String osName = "";

	public osCheck() {
		if(OS.indexOf("win") >= 0) {
			this.osName = "winsows";
		}else if(OS.indexOf("mac") >= 0){
			this.osName = "mac";
		}
	}
	public String getOsName() {
		return this.osName;
	}
}
