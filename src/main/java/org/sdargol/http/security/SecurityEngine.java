package org.sdargol.http.security;

import org.sdargol.controllers.core.IController;
import org.sdargol.dto.DTORoles;
import org.sdargol.http.security.annotation.Security;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

//c - class, u - user, a - annotation
public class SecurityEngine {
    public boolean checkRoles(final Class<? extends IController> cController, final DTORoles uRoles){
        Security aSecurity = cController.getDeclaredAnnotation(Security.class);

        Set<Roles> cRoles = new HashSet<>(Arrays.asList(aSecurity.hasRoles()));

        return cRoles.stream().anyMatch(r -> uRoles.getRoles().contains(r));
    }
}
