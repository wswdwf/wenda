package com.wswdwf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;


//@Controller
public class IndexController {

    //@RequestMapping("/") //路径映射
    @RequestMapping(path={"/","/index"}) //可设置多个路径映射
    @ResponseBody  //直接返回字符串而非模板
    public String index(){
        return "Hello wswdwf";
    }

    @RequestMapping(path={"/profile/{group}/{userId}"})  //{}中为路径中的变量
    @ResponseBody  //直接返回字符串而非模板
    public String profile(@PathVariable("group") String group,
                          @PathVariable("userId") int userId,       //获取路径中的变量
                          @RequestParam(value = "type",defaultValue = "1") int type,
                          @RequestParam(value = "key",required = false) String key){  //路径中的请求变量，若无默认值且路径中没写会报错
                                                                                        //required参数表示是否为必须，若true则必须有，否则会报错，如果是false且路径中没提供则会返回null
        return String.format("Profile page of %s / %d t: %d k: %s", group, userId, type, key);
    }

    @RequestMapping(path={"/hm"},method = {RequestMethod.GET})
    public String template(Model model){
        model.addAttribute("value1","vvvvv1");
        List<String> colors = Arrays.asList(new String[]{"RED","GREEN","BLUE"});
        model.addAttribute("colors",colors);
        return "home";
    }

    @RequestMapping(path={"/request"},method = {RequestMethod.GET})
    @ResponseBody
    public String request(Model model, HttpServletResponse response,
                          HttpServletRequest request,
                          HttpSession httpSession){
        StringBuilder sb = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            sb.append(name + ":" + request.getHeader(name) + "<br>");
        }
        sb.append(request.getMethod()+"<br>");
        sb.append(request.getQueryString()+"<br>");
        sb.append(request.getPathInfo()+"<br>");
        sb.append(request.getRequestURI()+"<br>");

        return sb.toString();
    }

    @RequestMapping("/welcome")
    public String welcome(Model model){
        model.addAttribute("welcome","<h1>welcomeThymeleaf</h1>");

        return "home";
    }
}
