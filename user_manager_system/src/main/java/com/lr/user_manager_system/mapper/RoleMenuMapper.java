package com.lr.user_manager_system.mapper;

import com.lr.user_manager_system.bean.Menu;
import com.lr.user_manager_system.bean.RoleMenu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RoleMenuMapper {

    //通过id查找
    @Select("select * from rolt_menu where id=#{id}")
    RoleMenu findById(Integer id);

    //根据角色ID删除某个角色下的所有菜单
    @Delete("delete from role_menu where role_id=#{roleId}")
    void deleteByRoleId(Integer roleId);

    @Select("selec * from role_menu where role_id=#{roleId} and menu_id=#{menuId}")
    RoleMenu findByRoleIdAndMenuID(Integer roleId,Integer menuId);

    //通过角色ID查询PID=0的菜单
    @Select("select m.id,m.PERMISSION,m.pid from ROLE_MENU rm,role r,menu m " +
            "where rm.ROLE_ID=r.id and rm.MENU_ID=m.id and r.id=#{RoleId} and m.pid=0 ")
    List<Menu> findMenuByRoleId(Integer RoleId);

    //通过角色ID查询PID不等于0的菜单
    @Select("select m.id,m.PERMISSION,m.pid from ROLE_MENU rm,role r,menu m " +
            "where rm.ROLE_ID=r.id and rm.MENU_ID=m.id and r.id=#{RoleId} and m.pid!=0")
    List<Menu> findNotBaseMenuByRoleId(Integer RoleId);

    @Select("select m.id,m.PERMISSION,m.pid from ROLE_MENU rm,role r,menu m " +
            "where rm.ROLE_ID=r.id and rm.MENU_ID=m.id and r.id=#{RoleId}")
    List<Menu> findAllMenuByRoleId(Integer RoleId);

    //删除某个权限下的某个Menu
    @Delete("delete from role_menu where role_id=#{roleId} and menu_id=#{menuId}")
    void deleteByRIdAndMId(Integer roleId,Integer menuId);

    //查询菜单的状态根据菜单id
    @Select("select m.state from role_menu rm,menu m where rm.menu_id=m.id and m.id=#{menuId}")
    String findMenuState(Integer menuId);

    //根据RID查询查询所有数据
   @Select("select * from role_menu where role_id=#{rid}")
   List<RoleMenu> selectRoleMenuByRID(Integer rid);

   @Options(useGeneratedKeys = true,keyColumn ="id")//获取数据库自增ID
   @Insert("insert into role_menu(role_id,menu_id) values (#{RId},#{MId}) ")
    void insertIntoMenu(Integer RId,Integer MId);

   //删除某个权限下的所有菜单
    @Delete("delete from role_menu where role_id=#{roleId} ")
    void deleteByRId(Integer roleId);

    //删除某个菜单下对应的所有权限
    @Delete("delete from role_menu where menu_id=#{menuID} ")
    void deleteByMenu(Integer menuID);

    //通过角色ID查询菜单ID
    @Select("select menu_id from role_menu where role_id=#{rid}")
    List<Integer> selectMenuIdByRID(Integer rid);
}
