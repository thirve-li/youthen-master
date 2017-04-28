package com.youthen.master.presentation.action;

import java.util.List;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.youthen.framework.common.annotation.ExecAuthority;
import com.youthen.framework.presentation.action.BaseAction;
import com.youthen.master.persistence.entity.KbnType;
import com.youthen.master.service.MasterDataMantanceService;

/**
 * @author LiXin
 */
@Namespace("/mst-data")
@Results({

        @Result(name = "index", location = "/WEB-INF/jsp/master/mstData/index.jsp")})
@Controller
@ExecAuthority(functioncd = "1")
public class MstIndexAction extends BaseAction {

    List<KbnType> dtoList;

    @Autowired
    MasterDataMantanceService masterDataMantanceService;

    private final Integer tabId = 10;

    @Autowired
    @Action("index")
    public String index() {

        this.masterDataMantanceService.setType(KbnType.class);
        this.dtoList = this.masterDataMantanceService.selectAll();
        return "index";
    }

    /**
     * getter for dtoList.
     * 
     * @return dtoList
     */
    public List<KbnType> getDtoList() {
        return this.dtoList;
    }

    /**
     * setter for dtoList.
     * 
     * @param aDtoList dtoList
     */
    public void setDtoList(final List<KbnType> aDtoList) {
        this.dtoList = aDtoList;
    }

    public Integer getTabId() {
        return this.tabId;
    }

}
