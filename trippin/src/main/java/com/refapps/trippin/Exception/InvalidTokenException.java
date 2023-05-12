package com.refapps.trippin.Exception;

public class InvalidTokenException extends RuntimeException{
  public InvalidTokenException(String message){
    super(message);
  }
}
