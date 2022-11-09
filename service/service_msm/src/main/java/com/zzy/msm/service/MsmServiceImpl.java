package com.zzy.msm.service;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.swing.text.html.FormSubmitEvent;
import java.util.Map;

/**
 * @Description
 * @Author Zzy
 * @Date 2021/2/9
 */
@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(Map<String, Object> params, String phone) {

        if(!StringUtils.isNotBlank(phone)){
            return false;
        }
        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAI4G4WqUU8YZrNTDcT2zLm", "GWOuP8zCEDKTSv2SJFl2ih3P3lYv8M");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "	 zzy的后端学习网站");
        request.putQueryParameter("TemplateCode", "SMS_211497205");
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(params));
        try {
            CommonResponse response = client.getCommonResponse(request);
            return response.getHttpResponse().isSuccess();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;

    }
}
