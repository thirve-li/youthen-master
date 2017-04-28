// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service;

import java.util.List;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.master.persistence.entity.LoginUser;
import com.youthen.master.service.dto.ArticleDto;

/**
 * 。
 * 
 * @author XS
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public interface ArticleService {

    List<ArticleDto> getArticleList(ArticleDto aDto);

    int getArticleCount(ArticleDto aDto);

    public Long insert(ArticleDto articleDto)
            throws DuplicateKeyException;

    ArticleDto getById(Long id);

    ArticleDto update(ArticleDto aDto) throws DuplicateKeyException;

    List<ArticleDto> getArticleTitle(String title);

    LoginUser getUserById(final String userId);

}
