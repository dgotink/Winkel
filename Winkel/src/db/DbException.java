package db;

public class DbException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public DbException(){
		super();
	}
	
	public DbException(String message, Throwable exception){
		super(message, exception);
	}
	
	public DbException(String message){
		super(message);
	}
	
}
