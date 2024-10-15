package com.dartech.azalymanagementserver.exceptioHandler;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException  {

    private CustomError customError;

    public ApplicationException(CustomError customError){
        super();
        this.customError = customError;
    }
}
