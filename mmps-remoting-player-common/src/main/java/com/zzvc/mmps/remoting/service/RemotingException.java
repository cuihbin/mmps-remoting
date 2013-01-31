package com.zzvc.mmps.remoting.service;

public class RemotingException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public RemotingException() {
		super();
	}

	public RemotingException(String message) {
		super(message);
	}
	
	public RemotingException(Throwable cause) {
		super(cause);
	}
    
    public RemotingException(String message, Throwable cause) {
    	super(message, cause);
    }
	
}
