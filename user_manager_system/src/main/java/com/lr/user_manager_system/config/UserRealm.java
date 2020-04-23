package com.lr.user_manager_system.config;

import com.lr.user_manager_system.bean.Menu;
import com.lr.user_manager_system.bean.Users;
import com.lr.user_manager_system.mapper.RoleUserMapper;
import com.lr.user_manager_system.mapper.UserMapper;
import com.lr.user_manager_system.service.MenuService;
import com.lr.user_manager_system.service.RoleMenuService;
import com.lr.user_manager_system.service.RoleUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义的Realm
 */
public class UserRealm extends AuthorizingRealm {
    @Resource
    @Lazy
    UserMapper userMapper;
    @Resource
    RoleUserMapper roleUserMapper;
    @Autowired
    RoleUserService roleUserService;
    @Autowired
    RoleMenuService roleMenuService;
    @Autowired
    MenuService menuService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("============================ 授权 =======================================");
        SimpleAuthorizationInfo info = null;
        //获取当前用户权限
       String name = (String)SecurityUtils.getSubject().getPrincipal();
       System.out.println(name);
       Users user = userMapper.selectUserByUsername(name);
       //拿到当前角色的状态
        String statu = roleUserService.seletRStatuByUId(user.getId());
        if(statu.equals("有效")){
            //拿到当前用户角色，查找的是ID
            Integer roleId = roleUserService.seletRIdByUId(user.getId());
            //拿到角色对应的菜单
            List<Menu> menus = new ArrayList<>();
            List<Integer> mIds = roleMenuService.selectMenuIdByRID(roleId);
            info = new SimpleAuthorizationInfo();
            for (Integer mId : mIds) {
                Menu menu = menuService.selectById(mId);
                info.addStringPermission(menu.getPermission()) ;
            }
            return info;
        }

        return null;

    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("============================ 用户认证 =======================================");
        /* 获取用户信息 */
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String login_name = (String) token.getPrincipal();

        //获取数据库信息
        Users user  = userMapper.selectUserByUsername(login_name);

        //数据空返回空，无任何权限
        if(user == null || user.getStatu().equals("无效")){
            return null;
        }

        //!!第一个参数是传登录的名字，不然获取不到
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(login_name, user.getPassworld(),getName()) ;

        return info;
    }
}
