package org.in.com.exception;


@SuppressWarnings("serial")
public class InvalidException extends RuntimeException{

	public InvalidException()
	{
		super();
	}
	public InvalidException(String message)
	{
		super(message);
	}

}