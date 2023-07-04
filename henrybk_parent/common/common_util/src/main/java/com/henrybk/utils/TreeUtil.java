package com.henrybk.utils;

import com.henrybk.constants.TypeCode;
import com.henrybk.enums.MenuTypeEnum;
import com.henrybk.model.sys.SysDept;
import com.henrybk.model.sys.SysMenu;
import com.henrybk.vo.vo.sys.RouterVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @description 生成菜单树工具类
 * @author Henry
 * @since 2023-05-19
 */

public class TreeUtil {
    /**
     * 生成菜单树
     * @param menuList 菜单列表
     * @param pid 父编号
     * @return List<SysMenu>
     */
    public static List<SysMenu> makeMenuTree(List<SysMenu> menuList, Long pid) {
        ArrayList<SysMenu> sysMenuList = new ArrayList<>();

        Optional.ofNullable(menuList).orElse(new ArrayList<SysMenu>())
                .stream().filter(item -> item != null && item.getParentId().equals(pid))
                .forEach(item -> {
                    //创建权限菜单对象
                    SysMenu sysMenu = new SysMenu();
                    //将原有的属性复制给菜单对象
                    BeanUtils.copyProperties(item,sysMenu);
                    //获取每一个item对象子菜单，递归生成菜单树
                    List<SysMenu> children = makeMenuTree(menuList, item.getId());
                    //设置子菜单
                    sysMenu.setChildren(children);
                    //将菜单对象添加到集合中
                    sysMenuList.add(sysMenu);
                });
        //返回菜单信息
        return sysMenuList;
    }

    /**
     * 生成路由树
     * @param meuList 菜单列表
     * @param pid 父级菜单
     * @return List<RouterVo>
     */
    public static List<RouterVo> makeRouter(List<SysMenu> meuList, Long pid) {
        //创建集合保存路由信息
        List<RouterVo> routerVoList = new ArrayList<>();
        //判断菜单列表是否为空，如果不为空则使用菜单列表，否则创建集合对象
        Optional.ofNullable(meuList).orElse(new ArrayList<SysMenu>())
                //筛选不为空的菜单与菜单父编号相同的数据
                .stream().filter(item -> item != null && item.getParentId().equals(pid))
                .forEach(item -> {
                    //创建路由信息对象
                    RouterVo routerVo = new RouterVo();
                    routerVo.setPath(item.getParentId() == 0L ? "/" + item.getPath() : item.getPath());
                    routerVo.setComponent(item.getComponent());
                    //判断当前菜单是一级菜单,展示菜单，否则折叠菜单
                    routerVo.setAlwaysShow(Objects.equals(item.getAlwaysShow().getCode(), TypeCode.FLAG_ONE));
                    routerVo.setHidden(Objects.equals(item.getHidden().getCode(), TypeCode.FLAG_ONE));
                    routerVo.setMeta(routerVo.new Meta(item.getMenuName(), item.getIcon()));
                    List<SysMenu> children = item.getChildren();

                    // 如果是菜单，则其children则是按钮，需要将有component的按钮放到目录children下
                    if (!CollectionUtils.isEmpty(children) && item.getMenuType().equals(MenuTypeEnum.MENU)) {
                       children.stream().filter(obj -> !StringUtils.isEmpty(obj.getComponent()))
                                .forEach(obj -> {
                                    RouterVo hiddenRouter = new RouterVo();
                                    hiddenRouter.setHidden(true);
                                    hiddenRouter.setAlwaysShow(false);
                                    hiddenRouter.setPath(obj.getPath());
                                    hiddenRouter.setComponent(obj.getComponent());
                                    hiddenRouter.setMeta(hiddenRouter.new Meta(obj.getMenuName(), obj.getIcon()));
                                    routerVoList.add(hiddenRouter);
                                });
                    } else {
                        if (!CollectionUtils.isEmpty(children)) {
                            routerVo.setChildren(makeRouter(children,item.getId()));
                        }
                    }
                    //将路由信息提交到集合中
                    routerVoList.add(routerVo);
                });
        return routerVoList;
    }

    /**
     * 生成部门树
     * @param deptList 部门列表
     * @param pid 父编号
     * @return List<SysDept>
     */
    public static List<SysDept> makeDeptTree(List<SysDept> deptList, Long pid) {
        ArrayList<SysDept> sysDeptList = new ArrayList<>();

        Optional.ofNullable(deptList).orElse(new ArrayList<SysDept>())
                .stream().filter(item -> item != null && item.getParentId().equals(pid))
                .forEach(item -> {
                    //创建权限菜单对象
                    SysDept sysDept = new SysDept();
                    //将原有的属性复制给菜单对象
                    BeanUtils.copyProperties(item,sysDept);
                    //获取每一个item对象子菜单，递归生成菜单树
                    List<SysDept> children = makeDeptTree(deptList, item.getId());
                    //设置子菜单
                    sysDept.setChildren(children);
                    //将菜单对象添加到集合中
                    sysDeptList.add(sysDept);
                });
        //返回菜单信息
        return sysDeptList;
    }

    /**
     * 生成树形结构数据
     * @param dataList 数据列表
     * @param parentId 父节点ID
     * @param <T> 数据类型
     * @return 树形结构数据
     */
    public static <T> List<T> makeTree(List<T> dataList, Long parentId) {
        ArrayList<T> treeList = new ArrayList<>();
        Optional.ofNullable(dataList).orElse(new ArrayList<>())
                .stream().filter(item -> item != null && getParentId(item).equals(parentId))
                .forEach(item -> {
                    // 创建新的树节点对象
                    T treeNode = newItemInstance(item);
                    // 设置id和parentId属性
                    setNodeIdAndParentId(treeNode, item);
                    // 获取每一个item对象的子节点，递归生成树形结构
                    List<T> children = makeTree(dataList, getId(item));
                    // 设置子节点列表
                    setChildren(treeNode, children);
                    // 将树节点对象添加到集合中
                    treeList.add(treeNode);
                });
        // 返回树形结构数据
        return treeList;
    }

    // 获取节点ID
    private static <T> Long getId(T item) {
        try {
            Field idField = item.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            return (Long) idField.get(item);
        } catch (Exception e) {
            throw new RuntimeException("获取id属性值失败", e);
        }
    }

    // 获取父节点ID
    private static <T> Long getParentId(T item) {
        try {
            Field parentIdField = item.getClass().getDeclaredField("parentId");
            parentIdField.setAccessible(true);
            return (Long) parentIdField.get(item);
        } catch (Exception e) {
            throw new RuntimeException("获取parentId属性值失败", e);
        }
    }

    // 设置节点ID和父节点ID
    private static <T> void setNodeIdAndParentId(T treeNode, T item) {
        try {
            Field idField = item.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            Long idValue = (Long) idField.get(item);
            Field parentIdField = item.getClass().getDeclaredField("parentId");
            parentIdField.setAccessible(true);
            Long parentIdValue = (Long) parentIdField.get(item);
            Field treeNodeIdField = treeNode.getClass().getDeclaredField("id");
            treeNodeIdField.setAccessible(true);
            treeNodeIdField.set(treeNode, idValue);
            Field treeNodeParentIdField = treeNode.getClass().getDeclaredField("parentId");
            treeNodeParentIdField.setAccessible(true);
            treeNodeParentIdField.set(treeNode, parentIdValue);
        } catch (Exception e) {
            throw new RuntimeException("设置nodeId和parentId属性值失败", e);
        }
    }

    // 获取子节点列表
    private static <T> List<T> getChildrenList(T item) {
        try {
            Field childrenField = item.getClass().getDeclaredField("children");
            childrenField.setAccessible(true);
            return (List<T>) childrenField.get(item);
        } catch (Exception e) {
            throw new RuntimeException("获取children属性值失败", e);
        }
    }

    // 设置子节点列表
    private static <T> void setChildren(T treeNode, List<T> children) {
        try {
            Field childrenField = treeNode.getClass().getDeclaredField("children");
            childrenField.setAccessible(true);
            childrenField.set(treeNode, children);
        } catch (Exception e) {
            throw new RuntimeException("设置children属性值失败", e);
        }
    }

    // 创建新的对象实例
    private static <T> T newItemInstance(T item) {
        try {
            return (T) item.getClass().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("创建" + item.getClass().getName() + "实例失败", e);
        }
    }

}
