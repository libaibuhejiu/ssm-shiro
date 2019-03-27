package com.huaruan.qhg.util;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.tags.PermissionTag;

/**
 * 自定义标签，是否拥有权限之一
 * @author Administrator
 *
 */
public class HasAnyPermissionTag extends PermissionTag{
	
	private static final long serialVersionUID = 1L;

    public HasAnyPermissionTag() {

    }

    protected boolean showTagBody(String permissions) {
        boolean hasAnyPermissions = false;

        Subject subject = getSubject();

        if (subject != null) {
            for (String role : permissions.split(",")) {

                if (subject.isPermitted(role.trim())) {
                    hasAnyPermissions = true;
                    break;
                }
            }
        }
        return hasAnyPermissions;
    }

}
