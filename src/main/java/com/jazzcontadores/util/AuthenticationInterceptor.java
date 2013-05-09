/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.util;

import com.jazzcontadores.extra.JCConstants;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import java.util.Map;

/**
 *
 * @author Venegas
 */
public class AuthenticationInterceptor implements Interceptor {

    @Override
    public void destroy() {
    }

    @Override
    public void init() {
    }

    @Override
    public String intercept(ActionInvocation ai) throws Exception {

        Map session = ai.getInvocationContext().getSession();
        Object user = session.get(JCConstants.USER);

        if (user == null) {
            return Action.LOGIN;
        } else {
            Action action = (Action) ai.getAction();

            //si la action que procesará necesita al usuario inyectado entonces 
            //se le inyecta con la interfaz UserAware
            if (action instanceof UserAware) {
                ((UserAware) action).setUser(user);
            }
            return ai.invoke();
        }
    }
}
