package com.miaoshaproject.controller;

import com.alibaba.druid.util.StringUtils;
import com.miaoshaproject.controller.viewobject.UserVO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.UserModel;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@Controller("user")
@RequestMapping("/user")
@CrossOrigin
public class UserController extends BaseController {
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/register", method = {RequestMethod.POST}, consumes = CONTENT_TYPE_FORMED)
    @ResponseBody
    public CommonReturnType register(@RequestParam(name = "telphone") String telphone,
                                     @RequestParam(name = "otpCode") String otpCode,
                                     @RequestParam(name = "name") String name,
                                     @RequestParam(name = "gender") Integer gender,
                                     @RequestParam(name = "age") Integer age,
                                     @RequestParam(name = "password") String password) throws BusinessException {
        {
            String inSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(telphone);
            if (!StringUtils.equals(otpCode, inSessionOtpCode)) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERREOR, "短信验证码不符合");
            }
            UserModel userModel = new UserModel();
            userModel.setName(name);
            userModel.setGender(new Byte(String.valueOf(gender.intValue())));
            userModel.setAge(age);
            userModel.setTelphone(telphone);
            userModel.setRegisterMode("byphone");
            userModel.setEncrptPassword(MD5Encoder.encode(password.getBytes()));
userService.register(userModel);
return CommonReturnType.create(null);

        }
    }

    @RequestMapping(value = "/getopt", method = {RequestMethod.POST}, consumes = CONTENT_TYPE_FORMED)
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name = "telphone") String telphone) {
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String optCode = String.valueOf(randomInt);
        httpServletRequest.getSession().setAttribute(telphone, optCode);
        System.out.println("telphone=" + telphone + "&optCode=" + optCode);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException {
        UserModel userModel = userService.getUserById(id);
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        UserVO userVO = convertFromModel(userModel);
        return CommonReturnType.create(userVO);
    }

    private UserVO convertFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }

}
