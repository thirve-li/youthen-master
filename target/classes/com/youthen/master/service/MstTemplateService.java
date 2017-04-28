// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service;

import java.io.Serializable;
import java.util.List;
import com.youthen.master.service.dto.MstCutomizedColDto;

/**
 * 。
 * 
 * @author LiXin
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public interface MstTemplateService {

    /**
     * 查询列表。
     * 
     * @param mstCutomizedColDto
     * @return
     */
    List<MstCutomizedColDto> list(MstCutomizedColDto mstCutomizedColDto);

    /**
     * 根据ID取得模板。
     * 
     * @param id
     * @return
     */
    MstCutomizedColDto getEntityById(Serializable id);

    /**
     * 修改模板。
     * 
     * @param mstCutomizedColDto
     * @param custmoizedCol
     */
    public void edit(MstCutomizedColDto mstCutomizedColDto, String[] custmoizedCol);

    /**
     * 取自定义列。
     * 
     * @param id
     * @return
     */
    MstCutomizedColDto getCutomizedCol(String tableName, String type);

}
