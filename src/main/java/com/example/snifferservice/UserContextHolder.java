package com.example.snifferservice;

public class UserContextHolder {
    private static final ThreadLocal<UserContext> userContext = new ThreadLocal<>();
    public static final UserContext getContext(){
        UserContext context = userContext.get();
        if(context == null){
            context = createEmptyContext();
        }
    }
}
