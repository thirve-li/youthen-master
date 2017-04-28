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
public interface MstKbnService {

    void addKbn(KbnDto dto);

    void editKbn(KbnDto dto);

    KbnDto getKbn(String type, String code);

    KbnDto getKbnById(Long id);

    List<KbnDto> getKbnListByType(String type);

    List<KbnDto> list(KbnDto dto);

    boolean isCmsNeedPlan();

    // 证书是否系统用户
    boolean isOurQuali();

}
