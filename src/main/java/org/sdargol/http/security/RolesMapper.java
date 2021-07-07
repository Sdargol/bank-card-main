package org.sdargol.http.security;

public class RolesMapper {
    public static Roles stringToRole(String role){
        Roles r = Roles.USER;

        switch (role){
            case "ADMIN": r = Roles.ADMIN;
                    break;
            case "MODERATOR": r = Roles.MODERATOR;
                break;
        }

        return r;
    }
}
