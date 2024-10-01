package com.spring_jwt.utility;

public class GetStatusRoleUtils {
    
    public static String getRoleValue(String role) {
        String value = null;
        for (ERole eRole : ERole.values()) {
            if(eRole.getValue().equals(role)) {
                value = eRole.getValue();
                break;                
            }
        }
        return value;
    }

}
