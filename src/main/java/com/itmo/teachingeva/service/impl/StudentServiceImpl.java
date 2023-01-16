package com.itmo.teachingeva.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmo.teachingeva.common.ErrorCode;
import com.itmo.teachingeva.common.ResultUtils;
import com.itmo.teachingeva.domain.StudentClass;
import com.itmo.teachingeva.dto.StudentDto;
import com.itmo.teachingeva.domain.Student;
import com.itmo.teachingeva.exceptions.BusinessException;
import com.itmo.teachingeva.mapper.StudentClassMapper;
import com.itmo.teachingeva.service.StudentService;
import com.itmo.teachingeva.mapper.StudentMapper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chenjiahan
 * @description 针对表【e_student】的数据库操作Service实现
 * @createDate 2023-01-14 14:40:03
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
        implements StudentService {

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private StudentClassMapper studentClassMapper;

    /**
     * 返回所有学生信息
     *
     * @return 学生信息
     */
    @Override
    public List<StudentDto> listAllStudents() {
        // 只有学期数小于8的学生才能被查询
        List<Student> allStudents = studentMapper.getAllStudents();
        if (allStudents == null) {
            throw new BusinessException(ErrorCode.STUDENT_EMPTY, "请联系开发人员");
        }

        // 将do数据转到dto
        List<StudentDto> studentDtoList = new ArrayList<>();

        // 先取出所有的班级列表
        List<StudentClass> studentClasses = studentClassMapper.queryClassToList();

        // 将班级根据id和cid对应做成Map
        Map<Integer, String> classMap = studentClasses.stream().collect(Collectors.toMap(StudentClass::getId, StudentClass::getCid));

        // 转换数据
        for (Student student : allStudents) {
            StudentDto studentDto = new StudentDto();
            BeanUtils.copyProperties(student, studentDto, "Cid");
            studentDto.setCid(classMap.get(student.getCid()));
            studentDtoList.add(studentDto);
        }

        return studentDtoList;
    }

    /**
     * 添加学生信息（单个）
     *
     * @return 添加成功
     */
    @Override
    public Boolean addStudents(StudentDto studentDto) {
        // 1.对student进行校验
        // a.student不为空
        if (studentDto == null) {
            throw new BusinessException(ErrorCode.STUDENT_EMPTY, "插入信息有误");
        }
        // b.学生的年级不大于8
        if (studentDto.getGrade() > 8) {
            throw new BusinessException(ErrorCode.STUDENT_GRADUATE, "请检查输入信息");
        }
        // c.查找该学生是否已存在
        Student studentBySid = studentMapper.getStudentBySid(studentDto.getSid());
        if (studentBySid != null) {
            throw new BusinessException(ErrorCode.STUDENT_EXIT, "请重新输入");
        }

        // 2.将学生的密码设为账号
        String password = studentDto.getSid();
        String cid = studentDto.getCid();

        Student student = new Student();
        BeanUtils.copyProperties(studentDto, student);
        //密码加密
        student.setPassword(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)));
        student.setCid(studentClassMapper.queryClassId(cid));
        // 插入数据
        boolean save = this.save(student);

        return save;
    }

    /**
     * 添加学生信息 （单个）
     *
     * @return 是否添加成功
     */
    @Override
    public Boolean deleteStudent(StudentDto studentDto) {
        Integer id = studentDto.getId();

        boolean isDelete = this.removeById(id);

        return isDelete;
    }

    /**
     * 删除学生数据
     *
     * @param studentDto 学生的id
     * @return 删除成功
     */
    @Override
    public Boolean updateStudent(StudentDto studentDto) {
        Student student = new Student();

        BeanUtils.copyProperties(studentDto, student);

        this.updateById(student);

        return true;
    }

    /**
     * 获取单个学生信息
     *
     * @param id 学生的id
     * @return 学生信息
     */
    @Override
    public StudentDto getStudent(Integer id) {

        Student student = this.getById(id);

        if (student == null) {
            // 学生是否存在
            throw new BusinessException(ErrorCode.STUDENT_EMPTY, "请检查id");
        }

        StudentDto studentInfo = new StudentDto();
        BeanUtils.copyProperties(student, studentInfo);
        studentInfo.setCid(studentClassMapper.queryClass(student.getCid()));

        return studentInfo;
    }

    /**
     * Excel批量保存上传
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
            // 2.POI 获取Excel数据
            wb = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = wb.getSheetAt(0);

            // 3.定义程序集合来接收文件内容
            List<Student> studentList = new ArrayList<>();
            XSSFRow row = null;

            // 定义班级集合
            List<StudentClass> studentClasses = studentClassMapper.queryClassToList();

            // 将班级根据id和cid对应做成Map
            Map<String, Integer> classMap = studentClasses.stream().collect(Collectors.toMap(StudentClass::getCid, StudentClass::getId));


            //4.接收数据 装入集合中
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                row = sheet.getRow(i);
                Student student = new Student();
                student.setName(row.getCell(0).getStringCellValue());   //姓名
                student.setSid(new DataFormatter().formatCellValue(row.getCell(1)));
                student.setSex(row.getCell(2).getStringCellValue().equals("男") ? 1 : 0);   //性别  男为1 女为0
                student.setAge(Integer.valueOf(new DataFormatter().formatCellValue(row.getCell(3))));       //年龄
                student.setMajor(Integer.valueOf(new DataFormatter().formatCellValue(row.getCell(4))));     //专业
                student.setCid(classMap.get(new DataFormatter().formatCellValue(row.getCell(5))));   //班级
                student.setGrade(Integer.valueOf(new DataFormatter().formatCellValue(row.getCell(6))));  //年级

                studentList.add(student);
            }
            // 密码进行加密添加
            studentList = studentList.stream().map(student -> {
                student.setPassword(DigestUtils.md5DigestAsHex(student.getSid().getBytes(StandardCharsets.UTF_8)));
                return student;
            }).collect(Collectors.toList());

            // 将列表保存到数据库中
            boolean save = this.saveBatch(studentList);

            if (!save) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "保存文件失败");
            }
            // 保存成功
            return true;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}




