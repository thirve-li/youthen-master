// ============================================================
// Copyright(c) Soltoris Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.presentation.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.youthen.framework.common.annotation.ExecAuthority;
import com.youthen.framework.presentation.action.BaseAction;
import com.youthen.master.service.DemoTestService;
import com.youthen.master.service.dto.DemoTestDto;

/**
 * 。
 * 
 * @author PRO
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */

@Namespace("/mst-demo")
@Results({@Result(name = "main", location = "/WEB-INF/jsp/master/main.jsp"),
        @Result(name = "addInit", location = "/WEB-INF/jsp/master/demo/add.jsp"),
        @Result(name = "list", location = "/WEB-INF/jsp/master/demo/list.jsp"),
        @Result(name = "editInit", location = "/WEB-INF/jsp/master/demo/edit.jsp")})
@Controller
@ExecAuthority(functioncd = "1")
public class DemoTestAction extends BaseAction {

    private List<DemoTestDto> dtoList;
    DemoTestDto dto;

    @Autowired
    private DemoTestService demoTestService;

    @Action("abc")
    public String list() {
        this.dtoList = this.demoTestService.list();
        return "list";
    }

    @Action("addInit")
    public String addInit() {
        return "addInit";
    }

    @Action("add")
    public String add() {

        final HttpServletRequest request = ServletActionContext.getRequest();
        final String name = request.getParameter("dto.name");
        this.dto.setName(name);

        this.demoTestService.add(this.dto);
        return list();
    }

    @Action("editInit")
    public String editInit() {
        this.dto = this.demoTestService.getById(this.dto.getId());
        return "editInit";
    }

    @Action("edit")
    public String edit() {
        this.demoTestService.edit(this.dto);
        return list();
    }

    /**
     * getter for dtoList.
     * 
     * @return dtoList
     */
    public List<DemoTestDto> getDtoList() {
        return this.dtoList;
    }

    /**
     * setter for dtoList.
     * 
     * @param aDtoList dtoList
     */
    public void setDtoList(final List<DemoTestDto> aDtoList) {
        this.dtoList = aDtoList;
    }

    /**
     * getter for dto.
     * 
     * @return dto
     */
    public DemoTestDto getDto() {
        return this.dto;
    }

    /**
     * setter for dto.
     * 
     * @param aDto dto
     */
    public void setDto(final DemoTestDto aDto) {
        this.dto = aDto;
    }

}
