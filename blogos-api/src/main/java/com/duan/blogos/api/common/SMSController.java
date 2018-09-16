package com.duan.blogos.api.common;

import com.duan.base.util.common.StringUtils;
import com.duan.blogos.api.BaseCheckController;
import com.duan.blogos.service.exception.CodeMessage;
import com.duan.blogos.service.exception.ResultUtil;
import com.duan.blogos.service.restful.ResultModel;
import com.duan.blogos.service.service.common.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2018/2/18.
 *
 * @author DuanJiaNing
 */
@RestController
@RequestMapping("/sms")
public class SMSController extends BaseCheckController {

    @Autowired
    private SmsService smsService;

    /**
     * 向指定号码发送短信
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResultModel send(HttpServletRequest request,
                            @RequestParam("phone") String phone,
                            @RequestParam("content") String content) {

        if (StringUtils.isBlank(phone) || StringUtils.isBlank(content)) {
            throw ResultUtil.failException(CodeMessage.COMMON_PARAMETER_ILLEGAL);
        }

        return smsService.sendSmsTo(content, phone);
    }
}