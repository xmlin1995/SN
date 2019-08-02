package com.SN.friend.controller;

import com.SN.friend.client.UserClient;
import com.SN.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendController {
    /**
     * 添加好友或者非好友
     * @param friendid
     * @param type
     * @return
     */
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private FriendService friendService;

    @Autowired
    private UserClient userClient;

    @RequestMapping(value = "/like/{friendid}/{type}",method = RequestMethod.PUT)
    public Result addFriend(@PathVariable String friendid,@PathVariable  String type){
        //验证是否登录，并且拿到当前登录的用户id
        Claims claims=(Claims) request.getAttribute("claims_user");
        if(claims==null){
            //说明当前没有user角色
            return new Result(false, StatusCode.LOGINERROR,"权限不足");
        }
        //得到用户的id
        String userid=claims.getId();
        //判断是添加好友还是非好友
        if(type!=null){
            if(type.equals("1")){
                //添加好友
                int flag=friendService.addFriend(userid,friendid);
                if(flag==0){
                    return new Result(false,StatusCode.ERROR,"不能重复添加好友");
                }
                if(flag==1){
                    userClient.updatefanscountandfollowcount(userid,friendid,1);
                    return new Result(true,StatusCode.OK,"添加好友成功");
                }
            }else if(type.equals("2")){
                //添加非好友
                int flag=friendService.addNoFriend(userid,friendid);
                if(flag==0){
                    return new Result(false,StatusCode.ERROR,"不能重复添加非好友");
                }
                if(flag==1){
                    return new Result(true,StatusCode.OK,"添加好友成功");
                }
            }
            return new Result(false,StatusCode.ERROR,"参数异常");
        }else{
            return new Result(false,StatusCode.ERROR,"参数异常");
        }
    }


    @RequestMapping(value = "/{friendid}",method = RequestMethod.DELETE)
    public Result deleteFriend(@PathVariable String friendid){
        //验证是否登录，并且拿到当前登录的用户id
        Claims claims=(Claims) request.getAttribute("claims_user");
        if(claims==null){
            //说明当前没有user角色
            return new Result(false, StatusCode.LOGINERROR,"权限不足");
        }
        //得到用户的id
        String userid=claims.getId();
        friendService.deleteFriend(userid,friendid);
        userClient.updatefanscountandfollowcount(userid,friendid,-1);
        return new Result(true,StatusCode.OK,"删除成功");
    }









}
