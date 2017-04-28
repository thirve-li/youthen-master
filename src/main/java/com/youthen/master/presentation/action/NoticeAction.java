package com.youthen.master.presentation.action;

import java.util.Date;
import java.util.List;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.youthen.framework.common.annotation.ExecAuthority;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.presentation.action.BaseAction;
import com.youthen.framework.util.BeanUtils;
import com.youthen.framework.util.CommonUtils;
import com.youthen.master.persistence.entity.LoginUser;
import com.youthen.master.persistence.entity.Role;
import com.youthen.master.service.KbnService;
import com.youthen.master.service.LoginUserService;
import com.youthen.master.service.NoticeService;
import com.youthen.master.service.OsAudittrailService;
import com.youthen.master.service.dto.KbnDto;
import com.youthen.master.service.dto.NoticeDto;
import com.youthen.master.service.dto.OsAudittrailDto;

@Namespace("/mst-notice")
@Results({
        @Result(name = "main", location = "/WEB-INF/jsp/master/main.jsp"),
        @Result(name = "addNoticeInit", location = "/WEB-INF/jsp/master/notices/add.jsp"),
        @Result(name = "editNoticeInit", location = "/WEB-INF/jsp/master/notices/edit.jsp"),
        @Result(name = "view", location = "/WEB-INF/jsp/master/notices/view.jsp"),
        @Result(name = "list", location = "/WEB-INF/jsp/master/notices/list.jsp"),
        @Result(name = "success", location = "/mst-user/index.action", type = "redirect")})
@Controller
@ExecAuthority(functioncd = "MST-ADMIN-001")
public class NoticeAction extends BaseAction {

    private static final long serialVersionUID = -4372884128344672292L;

    // 公告
    private NoticeDto noticedto = new NoticeDto();

    // 公告集合
    private List<NoticeDto> noticedtoList;

    // 首页大栏位
    private List<KbnDto> bigColumList;

    private String namespace;

    private int tabId = 9;

    @Autowired
    private LoginUserService loginUserService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private KbnService kbnService;

    @Autowired
    private OsAudittrailService audittrailService;

    /*
     * 公告信息区列表
     */
    @Action("list")
    public String list() {
        this.namespace = "mst-notice";

        this.bigColumList = this.kbnService.getBigColum();
        final LoginUser loginUser = this.loginUserService.getUserByUserId(this.getSessionUser().getUserId());
        boolean isAdmin = false;
        for (final Role role : loginUser.getRoles()) { // 此角色,用户已经拥有,不做校验
            if ("ADMIN".equals(role.getCode())) {
                isAdmin = true;
            }
        }

        this.noticedtoList = this.noticeService.getNoticeList(this.noticedto, isAdmin);

        final int listSize = this.noticeService.getNoticeCount(this.noticedto, isAdmin);
        final int pages = CommonUtils.countPages(listSize, this.noticedto.getPageSize());
        this.noticedto.setPages(pages);
        this.noticedto.setListSize(listSize);

        return "list";
    }

    /*
     * 公告信息区新增界面
     */

    @Action("addNoticeInit")
    public String addNoticeInit() {
        this.namespace = "mst-notice";
        this.bigColumList = this.kbnService.getBigColum();

        return "addNoticeInit";
    }

    /*
     * 公告信息区新增
     */
    @Action("addNotice")
    public String addNotice() {
        try {
            if (this.noticedto.getNoticeTitle() != null) {
                this.noticedto.setNoticeContent(this.noticedto.getNoticeContent().replace(" ", "@"));
                this.noticedto.setCreater(this.noticeService.getUserById(this.getSessionUser().getUserId()));
                this.noticedto.setCreateDate(new Date());
                this.noticedto.setStatus(1);
                this.noticeService.insert(this.noticedto);
            }

        } catch (final DuplicateKeyException e) {
            e.printStackTrace();
        }

        return "success";
    }

    @Action("main")
    public String main() {
        this.namespace = "mst-notice";
        this.bigColumList = this.kbnService.getBigColum();
        return "main";
    }

    /**
     * 公告信息区修改界面
     * 
     * @return String
     */
    @Action("editNoticeInit")
    public String editNoticeInit() {
        this.namespace = "mst-notice";
        this.bigColumList = this.kbnService.getBigColum();
        this.noticedto = this.noticeService.getById(this.noticedto.getId());
        this.noticedto.setNoticeContent(this.noticedto.getNoticeContent().replace("@", "&nbsp;"));
        return "editNoticeInit";
    }

    @Action("editNotice")
    public String editNotice() {

        try {
            this.noticeService.update(this.noticedto);

        } catch (final DuplicateKeyException e) {
            e.printStackTrace();

        }

        return list();
    }

    @Action("deleteNotice")
    public String deleteNotice() {

        try {
            this.noticedto.setStatus(0);
            this.noticeService.update(this.noticedto);

        } catch (final DuplicateKeyException e) {
            e.printStackTrace();

        }
        try {
            // 审查追踪
            final LoginUser loginUser = this.loginUserService.getUserByUserId(this.getSessionUser().getUserId());
            final OsAudittrailDto auditDto = new OsAudittrailDto();
            auditDto.setCreaterId(loginUser.getUserId());
            auditDto.setReason("失效公告栏");
            auditDto.setObjectName("公告栏");
            auditDto.setActionName("失效公告栏信息");
            auditDto.setChangedContent(loginUser.getName() + "失效了标题为" + this.noticedto.getNoticeTitle() + "的信息。");
            this.audittrailService.add(auditDto);
        } catch (final DuplicateKeyException e) {
            e.printStackTrace();
        } catch (final ObjectNotFoundException e) {
            e.printStackTrace();
        }

        return list();
    }

    @Action("view")
    public String view() {
        this.namespace = "mst-notice";
        this.bigColumList = this.kbnService.getBigColum();
        this.noticedto = this.noticeService.getById(this.noticedto.getId());
        BeanUtils.setNAProperty(this.noticedto);
        final String sour = this.noticedto.getNoticeContent();
        final String targ = sour.replace("\n", "<br/>").replace("\r\n", "<br/>").replace("@", "&nbsp;");
        this.noticedto.setNoticeContent(targ);
        return "view";
    }

    /**
     * getter for noticedto.
     * 
     * @return noticedto
     */
    public NoticeDto getNoticedto() {
        return this.noticedto;
    }

    /**
     * setter for noticedto.
     * 
     * @param aNoticedto noticedto
     */
    public void setNoticedto(final NoticeDto aNoticedto) {
        this.noticedto = aNoticedto;
    }

    /**
     * getter for noticedtoList.
     * 
     * @return noticedtoList
     */
    public List<NoticeDto> getNoticedtoList() {
        return this.noticedtoList;
    }

    /**
     * setter for noticedtoList.
     * 
     * @param aNoticedtoList noticedtoList
     */
    public void setNoticedtoList(final List<NoticeDto> aNoticedtoList) {
        this.noticedtoList = aNoticedtoList;
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
