package com.itmo.teachingeva.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmo.teachingeva.common.ErrorCode;
import com.itmo.teachingeva.domain.PTitle;
import com.itmo.teachingeva.domain.Position;
import com.itmo.teachingeva.domain.Teacher;
import com.itmo.teachingeva.dto.TeacherDto;
import com.itmo.teachingeva.exceptions.BusinessException;
import com.itmo.teachingeva.mapper.PTitleMapper;
import com.itmo.teachingeva.mapper.PositionMapper;
import com.itmo.teachingeva.service.TeacherService;
import com.itmo.teachingeva.mapper.TeacherMapper;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chenjiahan
 * @description 针对表【e_teacher】的数据库操作Service实现
 * @createDate 2023-01-15 20:17:42
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
        implements TeacherService {

    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    private PositionMapper positionMapper;

    @Resource
    private PTitleMapper pTitleMapper;


    /**
     * 查询所有教师信息
     *
     * @return 所有教师信息
     */
    @Override
    public List<TeacherDto> listAllTeachers() {
        // 查询所有教师信息
        List<Teacher> teacherList = this.list();
        if (teacherList == null) {
            throw new BusinessException(ErrorCode.TEACHER_EMPTY);
        }

        // 将Do转换为Dto 【职位，职称需要对应】
        // 先取出所有的职位和职称列表
        List<Position> positionList = positionMapper.queryPositionToList();
        List<PTitle> pTitleList = pTitleMapper.queryPTitleToList();

        // 将列表转化为map
        Map<Integer, String> positionMap = positionList.stream().collect(Collectors.toMap(Position::getId, Position::getName));
        Map<Integer, String> pTitleMap = pTitleList.stream().collect(Collectors.toMap(PTitle::getId, PTitle::getName));

        // 转换数据
        List<TeacherDto> teacherDtoList = new ArrayList<>();
        for (Teacher teacher : teacherList) {
            TeacherDto teacherDto = new TeacherDto();
            BeanUtils.copyProperties(teacher, teacherDto, "position", "pTitle");
            teacherDto.setPosition(positionMap.get(teacher.getPosition()));
            teacherDto.setPTitle(pTitleMap.get(teacher.getPTitle()));

            teacherDtoList.add(teacherDto);
        }

        return teacherDtoList;
    }

    /**
     * 添加教师信息【单个】
     *
     * @param teacherDto 教师添加信息
     * @return 添加成功
     */
    @Override
    public Boolean addTeacher(TeacherDto teacherDto) {
        // 对教师信息进行校验
        // 1.教师信息不为空
        if (teacherDto == null) {
            throw new BusinessException(ErrorCode.TEACHER_EMPTY, "插入信息为空！");
        }
        // 2.教师信息是否已经存在【根据姓名和邮箱两个字段进行查询】
        Teacher teacherExit = teacherMapper.queryTeacherByNameAndEmail(teacherDto.getName(), teacherDto.getEmail());
        if (teacherExit != null) {
            throw new BusinessException(ErrorCode.TEACHER_EXIT, "该教师已存在");
        }

        // 将dto转成do插入数据表中
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacherDto, teacher, "position", "pTitle");
        // 将职称和职位按照主键存在数据库中
        teacher.setPosition(positionMapper.queryPositionId(teacherDto.getPosition()));
        teacher.setPTitle(pTitleMapper.queryPTileId(teacherDto.getPTitle()));

        boolean save = this.save(teacher);

        return save;
    }

    /**
     * 删除单条教师信息
     *
     * @param teacherDto 教师的id
     * @return 删除成功
     */
    @Override
    public Boolean deleteTeacher(TeacherDto teacherDto) {
        Integer id = teacherDto.getId();

        boolean isDelete = this.removeById(id);

        return isDelete;
    }

    /**
     * 更新某条教师信息
     *
     * @param teacherDto 教师信息
     * @return 更新成功
     */
    @Override
    public Boolean updateTeacher(TeacherDto teacherDto) {
        // 判断教师是否存在
        Teacher teacherExit = this.getById(teacherDto.getId());

        if (teacherExit == null) {
            throw new BusinessException(ErrorCode.TEACHER_NO_EXIT, "查无此人");
        }

        Teacher teacher = new Teacher();


        BeanUtils.copyProperties(teacherDto, teacher, "position", "pTitle");
        teacher.setPosition(positionMapper.queryPositionId(teacherDto.getPosition()));
        teacher.setPTitle(pTitleMapper.queryPTileId(teacherDto.getPosition()));

        boolean update = this.updateById(teacher);

        return update;
    }

    /**
     * 获取单条教师信息
     *
     * @param id 教师的id
     * @return 教师信息
     */
    @Override
    public TeacherDto getTeacher(Integer id) {

        Teacher teacher = this.getById(id);

        if (teacher == null) {
            throw new BusinessException(ErrorCode.TEACHER_EMPTY, "不存在该教师");
        }

        TeacherDto teacherInfo = new TeacherDto();
        BeanUtils.copyProperties(teacher, teacherInfo, "position", "pTitle");
        teacherInfo.setPTitle(pTitleMapper.queryPTileName(teacher.getPTitle()));
        teacherInfo.setPosition(positionMapper.queryPositionName(teacher.getPosition()));

        return teacherInfo;
    }

    /**
     * Excel批量上传保存
     *
     * @param file excel文件
     * @return
     */
    @Override
    @Transactional
    public Boolean excelImport(MultipartFile file) {

        // 1.判断文件是否为空
        if (file.isEmpty()) {
            throw new BusinessException(ErrorCode.FILE_EMPTY, "请重新上传文件");
        }
        XSSFWorkbook wb = null;

        try {
            // 2.POI获取Excel文件信息
            wb = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = wb.getSheetAt(0);

            // 3.定义程序集合来接收文件内容
            List<Teacher> teacherList = new ArrayList<>();
            XSSFRow row = null;

            // 定义职称和职位的列表
            List<Position> positionList = positionMapper.queryPositionToList();
            List<PTitle> pTitleList = pTitleMapper.queryPTitleToList();

            // 将列表转化为map
            Map<String, Integer> positionMap = positionList.stream().collect(Collectors.toMap(Position::getName, Position::getId));
            Map<String, Integer> pTitleMap = pTitleList.stream().collect(Collectors.toMap(PTitle::getName, PTitle::getId));

            //4.接收数据 装入集合中
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                row = sheet.getRow(i);
                Teacher teacher = new Teacher();
                teacher.setName(row.getCell(0).getStringCellValue());   //姓名
                teacher.setSex(row.getCell(1).getStringCellValue().equals("男") ? 1 : 0);   //性别  男为1 女为0
                teacher.setAge(Integer.valueOf(new DataFormatter().formatCellValue(row.getCell(2))));       //年龄
                teacher.setPosition(positionMap.get(row.getCell(3).getStringCellValue()));      //职位
                teacher.setPTitle(pTitleMap.get(row.getCell(4).getStringCellValue()));      //职称
                teacher.setMajor(Integer.valueOf(new DataFormatter().formatCellValue(row.getCell(5))));     //专业
                teacher.setEmail(row.getCell(6).getStringCellValue());      //邮箱
                teacher.setIdentity(Integer.valueOf(new DataFormatter().formatCellValue(row.getCell(7))));  //国籍

                teacherList.add(teacher);
            }

            // 将列表保存至数据库中
            boolean save = this.saveBatch(teacherList);

            return save;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<TeacherDto> getChinaTeacher() {

        List<Teacher> teacherList = teacherMapper.queryAllChinaTeacher();

        if (teacherList == null) {
            throw new BusinessException(ErrorCode.TEACHER_EMPTY, "没有查询到中方教师信息");
        }

        List<TeacherDto> teacherDtoList = teacherList.stream().map(teacher -> {
            TeacherDto teacherDto = new TeacherDto();
            BeanUtils.copyProperties(teacher, teacherDto);
            return teacherDto;
        }).collect(Collectors.toList());


        return teacherDtoList;
    }

    @Override
    public List<TeacherDto> getRussianTeacher() {

        List<Teacher> teacherList = teacherMapper.queryAllRussianTeacher();

        if (teacherList == null) {
            throw new BusinessException(ErrorCode.TEACHER_EMPTY, "没有查询到俄方教师信息");
        }

        List<TeacherDto> teacherDtoList = teacherList.stream().map(teacher -> {
            TeacherDto teacherDto = new TeacherDto();
            BeanUtils.copyProperties(teacher, teacherDto);
            return teacherDto;
        }).collect(Collectors.toList());

        return teacherDtoList;
    }
}




