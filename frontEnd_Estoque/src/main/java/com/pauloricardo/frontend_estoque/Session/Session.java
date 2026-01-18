package com.pauloricardo.frontend_estoque.Session;

public class Session {
    private static  String token;
    private Session(){}

    public static void setToken(String jwt){
        token = jwt;
    }

    public static String getToken(){
        return token;
    }

    public static boolean isAuthenticated() {
        return token != null && !token.isBlank();
    }

    public static void clear() {
        token = null;
    }


}
