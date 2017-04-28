// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.youthen.framework.common.DateFormatUtils;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.util.BeanUtils;
import com.youthen.master.persistence.dao.LoginUserDao;
import com.youthen.master.persistence.dao.NoticeDao;
import com.youthen.master.persistence.entity.LoginUser;
import com.youthen.master.persistence.entity.Notice;
import com.youthen.master.service.NoticeService;
import com.youthen.master.service.dto.NoticeDto;

/**
 * 。
 * 
 * @author PRO
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Service(value = "noticeService")
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    NoticeDao noticeDao;

    @Autowired
    LoginUserDao loginUserDao;

    /**
     * 
     */

    @Override
    public List<NoticeDto> getNoticeList(final NoticeDto aDto, final boolean choose) {
        String hql = "from Notice where status=1 order by status asc, createDate desc";
        if (choose == true) {
            hql = "from Notice order by status asc, createDate desc";
        }
        final List<Notice> list =
                this.noticeDao.getByPage(hql, aDto.getGotoPage(), aDto.getPageSize());
        final ArrayList<NoticeDto> result = new ArrayList<NoticeDto>();

        if (CollectionUtils.isNotEmpty(list)) {
            for (final Notice entity : list) {
                final NoticeDto dto = new NoticeDto();
                BeanUtils.copyAllNullableProperties(entity, dto);
                result.add(dto);
            }
        }
        return result;
    }

    /**
     * 
     */

    @Override
    public int getNoticeCount(final NoticeDto aDto, final boolean choose) {
        String hql = "from Notice where status=1 order by status asc, createDate desc";
        if (choose == true) {
            hql = "from Notice order by status asc, createDate desc";
        }
        return this.noticeDao.getCount(hql);
    }

    /**
     * 
     */

    @Override
    @Transactional
    public Long insert(final NoticeDto aNoticeDto) throws DuplicateKeyException {
        final Notice notice = new Notice();
        BeanUtils.copyAllNullableProperties(aNoticeDto, notice);
        return (Long) this.noticeDao.insert(notice);
    }

    /**
     * 
     */

    @Override
    public NoticeDto getById(final Long aId) {
        final NoticeDto dto = new NoticeDto();
        BeanUtils.copyAllProperties(this.noticeDao.getById(aId), dto);
        dto.setUpdTime(DateFormatUtils.format("yyyy-MM-dd HH:mm:ss", new Date()));
        BeanUtils.setNAProperty(dto);
        return dto;
    }

    /**
     * 
     */

    @Override
    @Transactional
    public NoticeDto update(final NoticeDto aDto) throws DuplicateKeyException {
        final Notice e = this.noticeDao.getById(aDto.getId());
        BeanUtils.copyNullableProperties(aDto, e);
        this.noticeDao.update(e);
        BeanUtils.copyProperties(e, aDto);
        return aDto;
    }

    /**
     * 
     */

    @Override
    public LoginUser getUserById(final String aUserId) {
        return this.loginUserDao.getById(aUserId);
    }

}
