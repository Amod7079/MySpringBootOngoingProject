package com.coderXAmod.ElectronicStore.Exception;

import lombok.Builder;

@Builder
public class ResourseNotFoundException extends RuntimeException{
    public ResourseNotFoundException()
    {
        super("Resourse Not Found !!");
    }
    public ResourseNotFoundException(String message)
    {
        super(message);
    }
}
