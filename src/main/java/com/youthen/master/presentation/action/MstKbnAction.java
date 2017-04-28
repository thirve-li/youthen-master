package com.youthen.master.presentation.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.youthen.framework.common.PageBean;
import com.youthen.framework.common.StringUtils;
import com.youthen.framework.common.annotation.ExecAuthority;
import com.youthen.framework.presentation.action.BaseAction;
import com.youthen.framework.util.BeanUtils;
import com.youthen.master.persistence.entity.Company;
import com.youthen.master.persistence.entity.Kbn;
import com.youthen.master.persistence.entity.KbnType;
import com.youthen.master.service.KbnService;
import com.youthen.master.service.MasterDataMantanceService;
import com.youthen.master.service.MstKbnService;
import com.youthen.master.service.dto.CompanyDto;
import com.youthen.master.service.dto.KbnDto;

/**
 * @author Administrator
 */
@Namespace("/oa-kbn")
@Results({
        @Result(name = "addInit", location = "/WEB-INF/jsp/master/mstData/kbn/entity.jsp"),
        @Result(name = "list", location = "/WEB-INF/jsp/master/mstData/kbn/list.jsp"),
        @Result(name = "view", location = "/WEB-INF/jsp/master/mstData/kbn/view.jsp"),
        @Result(name = "editInit", location = "/WEB-INF/jsp/master/mstData/kbn/entity.jsp"),

        @Result(name = "toKbnList", location = "/oa-kbn/list.action?dto.type=${dto.type}&typeId=${typeId}", type = "redirect"),
        @Result(name = "toNameList", location = "/oa-kbn/listName.action?dto.type=INST_NAME", type = "redirect"),

        @Result(name = "addNameInit", location = "/WEB-INF/jsp/master/mstData/kbn/instName/add.jsp"),
        @Result(name = "listName", location = "/WEB-INF/jsp/master/mstData/kbn/instName/list.jsp"),
        @Result(name = "viewName", location = "/WEB-INF/jsp/master/mstData/kbn/instName/view.jsp"),
        @Result(name = "editNameInit", location = "/WEB-INF/jsp/master/mstData/kbn/instName/edit.jsp")})
@Controller
@ExecAuthority(functioncd = "1")
public class MstKbnAction extends BaseAction {

    Long typeId;

    KbnDto dto;

    KbnType typeDto;

    List<KbnDto> kbnDtoList;

    List<KbnDto> bigTypeList;
    List<KbnDto> smallTypeList;
    PageBean<Kbn> pageBean;

    List<CompanyDto> companyList;

    // 首页大栏位
    private List<KbnDto> bigColumList;

    private String namespace;

    private int tabId = 3;

    @Autowired
    MstKbnService mstKbnService;

    @Autowired
    private KbnService kbnService;

    @Autowired
    MasterDataMantanceService masterDataMantanceService;

    @Action("list")
    public String list() {
        // this.kbnDtoList = this.mstKbnService.list(this.dto);
        final Kbn kbn = new Kbn();
        BeanUtils.copyAllNullableProperties(this.dto, kbn);

        if (this.pageBean == null) {
            this.pageBean = new PageBean<Kbn>(1, this.dto.getPageSize());
        }
        String condition = "";
        if (StringUtils.isNotEmpty(this.dto.getType())) {
            condition += " and (tmpObj.type='" + this.dto.getType() + "' or tmpObj.type='OA_SMALL_MENU' ) ";
        }
        if (this.dto.getParentTypeId() != null) {
            condition += " and tmpObj.parentTypeId='" + this.dto.getParentTypeId() + "'";
        }
        this.namespace = "oa-kbn";
        this.bigColumList = this.kbnService.getBigColum();
        this.pageBean.setWhereHql(condition);
        this.pageBean.setSortColumnName("type,parentTypeId");
        this.masterDataMantanceService.setType(Kbn.class);
        this.pageBean = this.masterDataMantanceService.getByPageBean(this.pageBean);

        this.masterDataMantanceService.setType(KbnType.class);
        this.typeDto = (KbnType) this.masterDataMantanceService.getById(this.typeId);

        return "list";
    }

    @Action("addInit")
    public String addInit() {

        this.masterDataMantanceService.setType(KbnType.class);
        this.typeDto = (KbnType) this.masterDataMantanceService.getById(this.typeId);

        this.masterDataMantanceService.setType(KbnType.class);
        final List<KbnType> list =
                this.masterDataMantanceService.queryByCondition("typeCode ='" + this.dto.getType() + "'");
        if (CollectionUtils.isNotEmpty(list)) {
            final KbnType kbnType = list.get(0);
            final KbnDto condition = new KbnDto();
            if (kbnType.getParentType() != null) {
                condition.setType(kbnType.getParentType().getTypeCode());
                condition.setStatus(1L);
                this.kbnDtoList = this.mstKbnService.list(condition);
            }

        }
        this.namespace = "oa-kbn";

        this.bigColumList = this.kbnService.getBigColum();

        this.masterDataMantanceService.setType(Company.class);
        this.companyList = this.masterDataMantanceService.queryByCondition(" status=1 ");

        return "addInit";
    }

    @Action("editInit")
    public String editInit() {

        final KbnDto aDto = this.mstKbnService.getKbnById(this.dto.getId());
        BeanUtils.copyAllNullableProperties(aDto, this.dto);
        this.namespace = "mst-kbn";
        this.bigColumList = this.kbnService.getBigColum();
        this.masterDataMantanceService.setType(Company.class);
        this.companyList = this.masterDataMantanceService.queryByCondition(" status=1 ");

        init();// 初始化下拉框

        return "editInit";
    }

    @Action("view")
    public String view() {
        this.namespace = "oa-kbn";
        this.bigColumList = this.kbnService.getBigColum();
        final KbnDto aDto = this.mstKbnService.getKbnById(this.dto.getId());
        BeanUtils.copyAllNullableProperties(aDto, this.dto);
        return "view";
    }

    @Action("listName")
    public String listName() {// 计量器具名称列表
        if (this.dto == null) {
            this.dto = new KbnDto();
            this.dto.setType("INST_NAME");
        }

        KbnDto condition = new KbnDto();
        condition.setType("OA_BIG_MENU");
        condition.setStatus(1L);
        this.bigTypeList = this.mstKbnService.list(condition);

        if (this.dto != null && this.dto.getBigTypeId() != null) {
            condition = new KbnDto();
            if (!StringUtils.isEmpty(this.dto.getBigTypeId())) {
                condition.setParentTypeId(Long.valueOf(this.dto.getBigTypeId()));
            }
            this.smallTypeList = this.mstKbnService.list(condition);
            final HttpServletRequest request = ServletActionContext.getRequest();
            request.getSession().setAttribute("smallTypeList", this.smallTypeList);
        }

        final Kbn kbn = new Kbn();
        BeanUtils.copyAllNullableProperties(this.dto, kbn);

        if (this.pageBean == null) {
            this.pageBean = new PageBean<Kbn>(1, this.dto.getPageSize());
        }
        String conditionStr = "";
        if (this.dto != null && StringUtils.isNotEmpty(this.dto.getType())) {
            conditionStr += " and tmpObj.type='" + this.dto.getType() + "'";
        }
        if (this.dto != null && this.dto.getParentTypeId() != null) {
            conditionStr += " and tmpObj.parentTypeId='" + this.dto.getParentTypeId() + "'";
        }
        if (this.dto != null && StringUtils.isNotEmpty(this.dto.getBigTypeId())) {
            conditionStr += " and tmpObj.parentType.parentTypeId='" + this.dto.getBigTypeId() + "'";
        }
        this.pageBean.setWhereHql(conditionStr);
        this.pageBean.setSortColumnName("type,parentTypeId");
        this.masterDataMantanceService.setType(Kbn.class);
        this.pageBean = this.masterDataMantanceService.getByPageBean(this.pageBean);

        // this.kbnDtoList = this.mstKbnService.list(this.dto);
        return "listName";
    }

    @Action("addNameInit")
    public String addNameInit() {// 计量器具名称新增页面
        if (this.dto == null) {
            this.dto = new KbnDto();
            this.dto.setStatus(1L);
            this.dto.setType("OA_BIG_MENU");
        }
        this.bigTypeList = this.mstKbnService.list(this.dto);
        return "addNameInit";
    }

    @Action("editNameInit")
    public String editNameInit() {// 计量器具名称修改页面
        final KbnDto aDto = this.mstKbnService.getKbnById(this.dto.getId());
        BeanUtils.copyAllNullableProperties(aDto, this.dto);
        this.dto.setType("OA_BIG_MENU");
        this.bigTypeList = this.mstKbnService.list(this.dto);
        return "editNameInit";
    }

    @Action("viewName")
    public String viewName() {// 计量器具名称查看页面
        final KbnDto aDto = this.mstKbnService.getKbnById(this.dto.getId());
        BeanUtils.copyAllNullableProperties(aDto, this.dto);
        return "viewName";
    }

    private void init() {

        // 初始化下拉框
        final KbnDto condition = new KbnDto();
        if (this.dto.getParentType() != null) {
            condition.setType(this.dto.getParentType().getType());
            condition.setStatus(1L);
            this.kbnDtoList = this.mstKbnService.list(condition);
        }

        this.masterDataMantanceService.setType(KbnType.class);
        this.typeDto = (KbnType) this.masterDataMantanceService.getById(this.typeId);

    }

    @Action("add")
    public String add() {

        this.dto.setObjectName(this.dto.getNameCn());

        if (this.dto.getNameCn() != null) {
            this.dto.setType("OA_SMALL_MENU");
            this.dto.setObjectName(this.dto.getNameCn());
            this.mstKbnService.editKbn(this.dto);
            final KbnDto aDto = new KbnDto();
            aDto.setType(this.dto.getType());
            this.kbnDtoList = this.mstKbnService.list(aDto);
            this.dto.setParentTypeId(null);
        } else {
            this.dto.setType("OA_BIG_MENU");
            this.mstKbnService.addKbn(this.dto);

        }
        final KbnDto aDto = new KbnDto();
        this.kbnDtoList = this.mstKbnService.list(aDto);

        // final HttpServletRequest request = ServletActionContext.getRequest();
        // request.getSession().setAttribute("dto", this.dto);
        this.dto.setNameCn(null);
        this.dto.setParentTypeId(null);
        return "toKbnList";
    }

    @Action("edit")
    public String edit() {
        this.dto.setObjectName(this.dto.getNameCn());
        this.mstKbnService.editKbn(this.dto);
        final KbnDto aDto = new KbnDto();
        aDto.setType(this.dto.getType());
        this.kbnDtoList = this.mstKbnService.list(aDto);
        this.dto.setParentTypeId(null);
        return "toKbnList";
    }

    @Action("del")
    public String del() {
        final KbnDto kbnDto = this.mstKbnService.getKbnById(this.dto.getId());
        kbnDto.setStatus(0L);
        kbnDto.setObjectName(this.dto.getNameCn());
        kbnDto.setReason(this.dto.getReason());
        kbnDto.setActionName("失效");
        this.mstKbnService.editKbn(kbnDto);

        final KbnDto aDto = new KbnDto();
        aDto.setType(this.dto.getType());

        this.kbnDtoList = this.mstKbnService.list(aDto);
        this.dto.setParentTypeId(null);
        return "toKbnList";
    }

    @Action("addName")
    public String addName() {

        final HttpServletRequest request = ServletActionContext.getRequest();
        final String[] names = request.getParameterValues("name");
        final String[] codes = request.getParameterValues("code");
        if (names != null) {
            int i = 0;
            for (final String name : names) {
                this.dto.setType("INST_NAME");
                this.dto.setCode(codes[i]);
                this.dto.setNameCn(name);
                this.dto.setObjectName(this.dto.getNameCn());
                this.mstKbnService.addKbn(this.dto);
                i++;
            }

        }

        /*
         * final KbnDto aDto = new KbnDto();
         * aDto.setType("INST_NAME");
         * this.kbnDtoList = this.mstKbnService.list(aDto);
         */

        return "toNameList";
    }

    @Action("editName")
    public String editName() {
        this.mstKbnService.editKbn(this.dto);

        // final KbnDto aDto = new KbnDto();
        // aDto.setType("INST_NAME");
        // this.kbnDtoList = this.mstKbnService.list(aDto);

        return "toNameList";
    }

    @Action("delName")
    public String delName() {
        final KbnDto kbnDto = this.mstKbnService.getKbnById(this.dto.getId());
        kbnDto.setStatus(0L);
        kbnDto.setReason(this.dto.getReason());
        kbnDto.setActionName("失效");
        this.mstKbnService.editKbn(kbnDto);

        final KbnDto aDto = new KbnDto();
        aDto.setType("INST_NAME");
        this.kbnDtoList = this.mstKbnService.list(aDto);
        return "toNameList";

    }

    /**
     * @see com.youthen.master.presentation.action.CommonMasterMaintenanceAction#setDto(com.youthen.master.service.dto.MasterEntryDto)
     */

    public void setDto(final KbnDto aDto) {
        this.dto = aDto;
    }

    /**
     * @see com.youthen.master.presentation.action.CommonMasterMaintenanceAction#getDto()
     */

    public KbnDto getDto() {
        return this.dto;
    }

    /**
     * getter for kbnDtoList.
     * 
     * @return kbnDtoList
     */
    public List<KbnDto> getKbnDtoList() {
        return this.kbnDtoList;
    }

    /**
     * setter for kbnDtoList.
     * 
     * @param aKbnDtoList kbnDtoList
     */
    public void setKbnDtoList(final List<KbnDto> aKbnDtoList) {
        this.kbnDtoList = aKbnDtoList;
    }

    /**
     * getter for bigTypeList.
     * 
     * @return bigTypeList
     */
    public List<KbnDto> getBigTypeList() {
        return this.bigTypeList;
    }

    /**
     * setter for bigTypeList.
     * 
     * @param aBigTypeList bigTypeList
     */
    public void setBigTypeList(final List<KbnDto> aBigTypeList) {
        this.bigTypeList = aBigTypeList;
    }

    /**
     * getter for smallTypeList.
     * 
     * @return smallTypeList
     */
    public List<KbnDto> getSmallTypeList() {
        return this.smallTypeList;
    }

    /**
     * setter for smallTypeList.
     * 
     * @param aSmallTypeList smallTypeList
     */
    public void setSmallTypeList(final List<KbnDto> aSmallTypeList) {
        this.smallTypeList = aSmallTypeList;
    }

    /**
     * getter for pageBean.
     * 
     * @return pageBean
     */
    public PageBean<Kbn> getPageBean() {
        return this.pageBean;
    }

    /**
     * setter for pageBean.
     * 
     * @param aPageBean pageBean
     */
    public void setPageBean(final PageBean<Kbn> aPageBean) {
        this.pageBean = aPageBean;
    }

    /**
     * getter for companyList.
     * 
     * @return companyList
     */
    public List<CompanyDto> getCompanyList() {
        return this.companyList;
    }

    /**
     * setter for companyList.
     * 
     * @param aCompanyList companyList
     */
    public void setCompanyList(final List<CompanyDto> aCompanyList) {
        this.companyList = aCompanyList;
    }

    /**
     * getter for typeId.
     * 
     * @return typeId
     */
    public Long getTypeId() {
        return this.typeId;
    }

    /**
     * setter for typeId.
     * 
     * @param aTypeId typeId
     */
    public void setTypeId(final Long aTypeId) {
        this.typeId = aTypeId;
    }

    /**
     * getter for typeDto.
     * 
     * @return typeDto
     */
    public KbnType getTypeDto() {
        return this.typeDto;
    }

    /**
     * setter for typeDto.
     * 
     * @param aTypeDto typeDto
     */
    public void setTypeDto(final KbnType aTypeDto) {
        this.typeDto = aTypeDto;
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
     * setter for tabId.
     * 
     * @param aTabId tabId
     */
    public void setTabId(final int aTabId) {
        this.tabId = aTabId;
    }

}
