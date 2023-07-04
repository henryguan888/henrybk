package com.henrybk.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henrybk.config.PropertiesConfig;
import com.henrybk.constants.TypeCode;
import com.henrybk.enums.AvatarEnum;
import com.henrybk.enums.ResultEnum;
import com.henrybk.enums.StatusEnum;
import com.henrybk.exception.BusinessException;
import com.henrybk.model.sys.SysUser;
import com.henrybk.system.mapper.SysUserMapper;
import com.henrybk.system.service.SysConfigService;
import com.henrybk.system.service.SysDeptService;
import com.henrybk.system.service.SysMenuService;
import com.henrybk.system.service.SysUserService;
import com.henrybk.utils.*;
import com.henrybk.vo.easyexcel.SysUserEeVo;
import com.henrybk.vo.query.sys.SysUserQueryVo;
import com.henrybk.vo.query.sys.UpdatePwdQueryVo;
import com.henrybk.vo.vo.sys.DateCountVo;
import com.henrybk.vo.vo.sys.RouterVo;
import com.henrybk.vo.vo.sys.SysUserVo;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description 用户信息表 服务实现类
 * @author Henry
 * @since 2023-05-15
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Resource
    private SysMenuService sysMenuService;

    @Resource
    private SysDeptService sysDeptService;

    @Resource
    private SysConfigService sysConfigService;

    @Resource
    private PropertiesConfig propertiesConfig;

    private SysUserQueryVo sysUserQueryVo;


    @Override
    public SysUser getByUsername(String username) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername,username);
        return baseMapper.selectOne(queryWrapper);
    }


    @Override
    public IPage<SysUserVo> getUserInfoPage(Integer pageNum, Integer pageSize, SysUserQueryVo sysUserQueryVo) {
        if (sysUserQueryVo != null && sysUserQueryVo.getDeptId() != null) {
            sysUserQueryVo.setDeptIds(sysDeptService.getDeptIds(sysUserQueryVo.getDeptId()));
        }
        this.sysUserQueryVo = sysUserQueryVo;
        Page<SysUserVo> page = new Page<>(pageNum, pageSize);
        return baseMapper.selectPage(page, sysUserQueryVo);
    }


    @Override
    public void updateStatus(Long id, Integer status) {
        SysUser sysUser = baseMapper.selectById(id);
        sysUser.setStatus(EnumUtil.getEnumByCode(StatusEnum.class,status));
        baseMapper.updateById(sysUser);
    }

    @Override
    public void upload(MultipartFile file) {
        //ExcelUtil.readExcel(file,)
    }

    @Override
    public void download(HttpServletResponse response) {
        List<SysUserEeVo> selectUserVoList = baseMapper.selectUserVoList(this.sysUserQueryVo);
        ExcelUtil.export(response,"用户列表",null,selectUserVoList, SysUserEeVo.class);
    }

    @Override
    public void restPassword(Long userId) {
        SysUser sysUser = baseMapper.selectById(userId);
        String initPwd = sysConfigService.getSysConfig(TypeCode.INIT_PASSWORD);
        if (StringUtils.isNotEmpty(initPwd)) {
            sysUser.setPassword(MD5Util.md5Salt(initPwd,propertiesConfig.getPasswordSalt()));
            baseMapper.updateById(sysUser);
        } else {
           throw new BusinessException(ResultEnum.GET_CONFIG_ERROR);
        }
    }


    @Override
    public void saveUser(SysUser sysUser) {
        sysUser.setUsername(sysUser.getUsername().toUpperCase());
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, sysUser.getUsername());
        Integer count = baseMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ResultEnum.ACCOUNT_ALREADY_ERROR);
        }
        // 设置默认密码
        String initPwd = sysConfigService.getSysConfig(TypeCode.INIT_PASSWORD);
        if (StringUtils.isNotEmpty(initPwd)) {
            sysUser.setPassword(MD5Util.md5Salt(initPwd, propertiesConfig.getPasswordSalt()));
            baseMapper.insert(sysUser);
        } else {
            throw new BusinessException(ResultEnum.GET_CONFIG_ERROR);
        }
    }

    @Override
    public void updateUser(SysUser sysUser) {
        SysUser user = baseMapper.selectById(sysUser.getId());

        sysUser.setUsername(sysUser.getUsername().toUpperCase());
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, sysUser.getUsername());
        Integer count = baseMapper.selectCount(queryWrapper);

        // 判断用户名是否存在
        if (!sysUser.getUsername().equalsIgnoreCase(user.getUsername()) && count > 0) {
            throw new BusinessException(ResultEnum.ACCOUNT_ALREADY_ERROR);
        }
        sysUser.setUsername(sysUser.getUsername());
        baseMapper.updateById(sysUser);
    }

    @Override
    public SysUserVo getUserInfo(String token) {
        Long userId = JwtUtil.getUserId(token);
        SysUserVo sysUserVo = baseMapper.getUserInfo(userId);
        if (StringUtils.isBlank(sysUserVo.getAvatar())) {
            sysUserVo.setAvatar(AvatarEnum.getNameByCode(sysUserVo.getGender().getCode()));
        }
        return sysUserVo;
    }

    @Override
    public void updatePwd(UpdatePwdQueryVo updatePwdQueryVo, HttpServletRequest request) {
        if (!updatePwdQueryVo.getNewPwd().equals(updatePwdQueryVo.getConfirmPwd())) {
            throw new BusinessException(ResultEnum.UPD_PWD_TWO_ERROR);
        }
        if (updatePwdQueryVo.getOldPwd().equals(updatePwdQueryVo.getNewPwd())) {
            throw new BusinessException(ResultEnum.UPD_PWD_REPETITION_ERROR);
        }
        Long userId = JwtUtil.getUserId(request.getHeader("token"));
        SysUser sysUser = baseMapper.selectById(userId);

        // 获取盐值
        String salt = propertiesConfig.getPasswordSalt();
        if (!StringUtils.equals(sysUser.getPassword(), MD5Util.md5Salt(updatePwdQueryVo.getOldPwd(),salt))) {
            throw new BusinessException(ResultEnum.UPD_PWD_ERROR);
        }

        sysUser.setPassword(MD5Util.md5Salt(updatePwdQueryVo.getNewPwd(),salt));
        baseMapper.updateById(sysUser);
    }

    @Override
    public Map<String, Object> getSystemData() {
        return baseMapper.getSystemData();
    }

    @Override
    public List<Map<String, Object>> getUserByRole() {
        return baseMapper.getUserByRole();
    }

    @Override
    public Map<String, Object> getLoginAndOperData(String searchDate) {
        Map<String, Object> map = new HashMap<>();
        List<String> dateList = DateUtil.getDatesOneWeekBefore(DateTime.parse(searchDate));
        List<DateCountVo> list1 = baseMapper.getLoginStatistics(searchDate);
        List<DateCountVo> list2 = baseMapper.getOperStatistics(searchDate);

        Map<String, Integer> loginMap = list1.stream()
                .collect(Collectors.toMap(DateCountVo::getCountDate, DateCountVo::getCountNum));
        Map<String, Integer> operMap = list2.stream()
                .collect(Collectors.toMap(DateCountVo::getCountDate, DateCountVo::getCountNum));

        List<Integer> loginList = dateList.stream()
                .map(date -> Optional.ofNullable(loginMap.get(date)).orElse(0))
                .collect(Collectors.toList());
        List<Integer> operList = dateList.stream()
                .map(date -> Optional.ofNullable(operMap.get(date)).orElse(0))
                .collect(Collectors.toList());

        map.put("dateList", dateList);
        map.put("loginList", loginList);
        map.put("operList", operList);
        return map;
    }

    /**
     * 根据token获取用户信息(基本信息、菜单权限、按钮权限)
     */
    @Override
    public Map<String, Object> getInfo(String token) {
        Map<String, Object> map = new HashMap<>();
        Long userId = JwtUtil.getUserId(token);
        //查询基本信息
        SysUser sysUser = baseMapper.selectById(userId);
        if (StringUtils.isBlank(sysUser.getAvatar())) {
            sysUser.setAvatar(AvatarEnum.getNameByCode(sysUser.getGender().getCode()));
        }
        //查询菜单权限
        List<RouterVo> routerVoList = sysMenuService.getUserMenuList(userId);
        //查询按钮权限
        List<String> permsList = sysMenuService.getUserButtonList(userId);

        List<String> roles = hasMainConsole(userId,JwtUtil.getUsername(token));
        map.put("name",sysUser.getUsername());
        map.put("avatar",sysUser.getAvatar());
        map.put("roles",roles);
        map.put("routers",routerVoList);
        map.put("buttons",permsList);
        return map;
    }

    private List<String> hasMainConsole(Long userId,String username) {
        List<String> roles = new ArrayList<>();
        if (sysConfigService.isSuperAdmin(username)) {
            roles.add("mainConsole");
        } else {
            roles.add(baseMapper.selectMainConsoleCount(userId) > 0 ? "mainConsole" : "");
        }
        return roles;
    }


}
