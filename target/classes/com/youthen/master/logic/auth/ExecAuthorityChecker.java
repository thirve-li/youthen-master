package com.youthen.master.logic.auth;

import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.youthen.framework.common.SimpleAppMessage;
import com.youthen.framework.common.annotation.BusinessLogic;
import com.youthen.framework.common.exception.ApplicationException;
import com.youthen.framework.common.exception.InterceptorAccessDeniedException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.master.persistence.entity.Function;
import com.youthen.master.persistence.entity.Role;
import com.youthen.master.persistence.entity.RoleFunction;
import com.youthen.master.service.FunctionService;
import com.youthen.master.service.LoginUserService;
import com.youthen.master.service.RoleFunctionService;
import com.youthen.master.service.dto.LoginUserDto;

@BusinessLogic(value = "ExecAuthorityChecker")
public class ExecAuthorityChecker {

    @Resource
    LoginUserService logInUserService;

    @Resource
    FunctionService functionService;

    @Resource
    RoleFunctionService roleFunctionService;

    /** logger */
    private static final Log LOG = LogFactory.getLog(ExecAuthorityChecker.class);

    private static final String DEBUG_LOG_TEMPLATE = "authorities not exist. userid:%s, functionid:%s";

    public void check(final String aFunctioncd, final String aUserid)
            throws InterceptorAccessDeniedException {

        try {

            final LoginUserDto user = this.logInUserService.getById(aUserid);
            final Set<Role> roleList = user.getRoles();
            if (roleList != null) {
                boolean hasAuthority = false;

                final Function function = this.functionService.getFunctionByCode(aFunctioncd);

                for (final Role role : roleList) {
                    if (function != null) {

                        // 获取此角色下的权限
                        final List<RoleFunction> roleFunctionList =
                                this.roleFunctionService.getFunctionByRoleId(role.getId());

                        for (final RoleFunction roleFunction : roleFunctionList) {

                            // 判断此角色是否拥有当前权限
                            if (roleFunction.getFunctionId() == function.getId()) {
                                hasAuthority = true;
                            }
                        }
                    }
                }
                if (!hasAuthority) {

                    if (LOG.isDebugEnabled()) {
                        LOG.debug(String.format(DEBUG_LOG_TEMPLATE, aUserid, aFunctioncd));
                    }
                    throw new InterceptorAccessDeniedException(new SimpleAppMessage("ECO13013"));
                }
            }
        } catch (final ObjectNotFoundException e) {
            throw new ApplicationException(new SimpleAppMessage("XCO10023"));
        }

    }
}
