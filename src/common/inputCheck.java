package common;

public class inputCheck {

	private final String halfSize = "^[A-Za-z0-9]+$";
	private final String countCheck = "\\d{1,3}?";
	public inputCheck() {
	}
	public boolean numCheck(String word) {
		return word.matches(getCountCheck());
	}
	public boolean halfCheck(String word) {
		return word.matches(getHalfSize());
	}
	private String getCountCheck() {
		return this.countCheck;
	}
	private String getHalfSize() {
		return this.halfSize;
	}
}
