package MyException;

public class MyException extends Exception {
	public String msg;
	
	public MyException(String msg) {
		this.msg = msg;
	}
	
	public String getMesage() {
		return this.msg;
	}
}
