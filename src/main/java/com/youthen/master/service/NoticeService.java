// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service;

import java.util.List;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.master.persistence.entity.LoginUser;
import com.youthen.master.service.dto.NoticeDto;

/**
 * 。
 * 
 * @author PRO
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public interface NoticeService {

    List<NoticeDto> getNoticeList(NoticeDto aDto, final boolean choose);

    int getNoticeCount(NoticeDto aDto, final boolean choose);

    public Long insert(NoticeDto NoticeDto)
            throws DuplicateKeyException;

    NoticeDto getById(Long id);

    NoticeDto update(NoticeDto aDto) throws DuplicateKeyException;

    LoginUser getUserById(final String userId);
}
