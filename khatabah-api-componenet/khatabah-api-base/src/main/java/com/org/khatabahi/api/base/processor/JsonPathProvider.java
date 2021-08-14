/*
 * System Name         : GEBNexGen
 * Program Id          : uob-gebng
 * Author              : tmpil9
 * Date                : 14/07/2019
 * Copyright (c) United Overseas Bank Limited Co.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * United Overseas Bank Limited Co. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with
 * United Overseas Bank Limited Co.
 */

package com.org.khatabahi.api.base.processor;

//import com.uob.geb.ng.common.constant.StatusCode;

/**
 * @author tmpil9
 * @version 1.0
 * @date 14/07/2019
 */
public interface JsonPathProvider {
    String RESPONSE_CODE_RULE = "$..responseCode";
    String RESPONSE_DESC_RULE = "$..responseDescription";

    String jsonPathChecker(String jsonStr, String rule);

   // String jsonPathChecker(String jsonStr, String rule, StatusCode statusCodeEnum);

    String jsonPathFinder(String jsonStr, String rule);

    String jsonPathPutter(String jsonStr, String rule, String key, String value);
}
