package com.zzvc.mmps.server;

public class ServerServiceUtilException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ServerServiceUtilException() {
		super();
	}

	public ServerServiceUtilException(String message) {
		super(message);
	}
	
	public ServerServiceUtilException(Throwable cause) {
		super(cause);
	}
    
    public ServerServiceUtilException(String message, Throwable cause) {
    	super(message, cause);
    }
	
}
