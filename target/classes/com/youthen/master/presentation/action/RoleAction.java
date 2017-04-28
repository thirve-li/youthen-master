// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.presentation.action;

import java.util.List;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.common.exception.OptimisticLockStolenException;
import com.youthen.master.persistence.entity.SubSystem;
import com.youthen.master.service.KbnService;
import com.youthen.master.service.MasterDataMantanceService;
import com.youthen.master.service.RoleService;
import com.youthen.master.service.dto.KbnDto;
import com.youthen.master.service.dto.RoleDto;

/**
 * 。
 * 
 * @author Dbl
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Namespace("/mst-role")
@Results({
        @Result(name = "entityInit", location = "/WEB-INF/jsp/master/role/entity.jsp"),
        @Result(name = "list", location = "/WEB-INF/jsp/master/role/list.jsp"),
        @Result(name = "tolist", location = "/mst-role/list.action", type = "redirect"),
        @Result(name = "view", location = "/WEB-INF/jsp/master/role/view.jsp")
})
@Controller
public class RoleAction {

    private RoleDto dto;

    private List<RoleDto> roleList;

    private List<KbnDto> bigColumList;

    private final int tabId = 10;

    private String namespace;

    private List<SubSystem> systemList;

    @Autowired
    private RoleService roleService;

    @Autowired
    KbnService kbnService;

    @Autowired
    MasterDataMantanceService masterDataMantanceService;

    @Action("list")
    public String list() {
        try {
            this.namespace = "mst-role";
            this.bigColumList = this.kbnService.getBigColum();
            this.roleList = this.roleService.selectAll();

        } catch (final ObjectNotFoundException e) {
            e.printStackTrace();
        }

        return "list";
    }

    @Action("entityInit")
    public String entityInit() {
        try {
            if (this.dto != null && this.dto.getId() != null) {
                this.dto = this.roleService.getById(this.dto.getId());
            }

            this.bigColumList = this.kbnService.getBigColum();
            this.systemList = this.masterDataMantanceService.selectAll();
        } catch (final ObjectNotFoundException e) {
            e.printStackTrace();
        }
        return "entityInit";
    }

    @Action("saveRole")
    public String saveRole() {
        try {
            if (this.dto != null && this.dto.getId() != null) {

                this.roleService.updateRole(this.dto);

            } else {
                this.roleService.insert(this.dto);
            }
        } catch (final ObjectNotFoundException e) {
            e.printStackTrace();
        } catch (final OptimisticLockStolenException e) {
            e.printStackTrace();
        } catch (final DuplicateKeyException e) {
            e.printStackTrace();
        }

        return "tolist";
    }

    @Action("deleteRole")
    public String deleteRole() {

        try {
            this.roleService.deleteRole(this.dto);
        } catch (final ObjectNotFoundException e) {
            e.printStackTrace();
        } catch (final OptimisticLockStolenException e) {
            e.printStackTrace();
        }

        return "tolist";
    }

    @Action("view")
    public String view() {
        try {
            this.bigColumList = this.kbnService.getBigColum();
            if (this.dto != null && this.dto.getId() != null) {

                this.dto = this.roleService.getById(this.dto);
            }
        } catch (final ObjectNotFoundException e) {
            e.printStackTrace();
        }
        return "view";
    }

    /**
     * getter for dto.
     * 
     * @return dto
     */
    public RoleDto getDto() {
        return this.dto;
    }

    /**
     * setter for dto.
     * 
     * @param aDto dto
     */
    public void setDto(final RoleDto aDto) {
        this.dto = aDto;
    }

    /**
     * getter for roleList.
     * 
     * @return roleList
     */
    public List<RoleDto> getRoleList() {
        return this.roleList;
    }

    /**
     * setter for roleList.
     * 
     * @param aRoleList roleList
     */
    public void setRoleList(final List<RoleDto> aRoleList) {
        this.roleList = aRoleList;
    }

    /**
     * getter for bigColumList.
     * 
     * @return bigColumList
     */
    public List<KbnDto> getBigColumList() {
        return this.bigColumList;
    }

    /**
     * setter for bigColumList.
     * 
     * @param aBigColumList bigColumList
     */
    public void setBigColumList(final List<KbnDto> aBigColumList) {
        this.bigColumList = aBigColumList;
    }

    /**
     * getter for namespace.
     * 
     * @return namespace
     */
    public String getNamespace() {
        return this.namespace;
    }

    /**
     * setter for namespace.
     * 
     * @param aNamespace namespace
     */
    public void setNamespace(final String aNamespace) {
        this.namespace = aNamespace;
    }

    /**
     * getter for tabId.
     * 
     * @return tabId
     */
    public int getTabId() {
        return this.tabId;
    }

    /**
     * getter for systemList.
     * 
     * @return systemList
     */
    public List<SubSystem> getSystemList() {
        return this.systemList;
    }

    /**
     * setter for systemList.
     * 
     * @param aSystemList systemList
     */
    public void setSystemList(final List<SubSystem> aSystemList) {
        this.systemList = aSystemList;
    }

}
