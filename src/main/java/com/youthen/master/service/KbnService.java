// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service;

import java.util.List;
import com.youthen.master.service.dto.KbnDto;

/**
 * 。
 * 
 * @author Lixin
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public interface KbnService {

    KbnDto getById(Long id);

    KbnDto getKbn(String type, String code);

    List<KbnDto> getKbnListByType(String type);

    List<KbnDto> getKbnListBy(KbnDto dto);

    List<KbnDto> getBigColum();

    List<KbnDto> getBigXiaoQuColum();

    List<KbnDto> getByParentTypeId(Long id);

    void update(KbnDto dto);

    void insert(KbnDto dto);

}
