package com.itmo.teachingeva.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmo.teachingeva.common.ErrorCode;
import com.itmo.teachingeva.domain.*;
import com.itmo.teachingeva.dto.EvaluateDto;
import com.itmo.teachingeva.dto.StudentDto;
import com.itmo.teachingeva.exceptions.BusinessException;
import com.itmo.teachingeva.mapper.*;
import com.itmo.teachingeva.service.EvaluateService;
import com.itmo.teachingeva.service.MarkHistoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.System;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author chenjiahan
* @description 针对表【e_evaluate】的数据库操作Service实现
* @createDate 2023-01-16 20:44:13
*/
@Service
public class EvaluateServiceImpl extends ServiceImpl<EvaluateMapper, Evaluate>
    implements EvaluateService{

    @Resource
    private EvaluateMapper evaluateMapper;

    @Resource
    private CourseGradeMapper courseGradeMapper;

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private StudentClassMapper studentClassMapper;

    @Resource
    private SystemMapper systemMapper;

    @Resource
    private MarkHistoryMapper markHistoryMapper;

    @Resource
    private ScoreHistoryMapper scoreHistoryMapper;


    @Resource
    private MarkHistoryService markHistoryService;

    /**
     * 展示所有评测信息
     * @return 所有评测信息
     */
    @Override
    public List<EvaluateDto> listAllEvaluations() {
        // 查询所有评测信息
        List<Evaluate> evaluateList = this.list();
        if (evaluateList == null) {
            throw new BusinessException(ErrorCode.EVALUATION_EMPTY, "暂无评测结果");
        }
        // 将do转成dto
        List<EvaluateDto> evaluateDtoList = new ArrayList<>();
        for (Evaluate evaluate : evaluateList) {
            EvaluateDto evaluateDto = new EvaluateDto();
            BeanUtils.copyProperties(evaluate, evaluateDto);
            evaluateDtoList.add(evaluateDto);
        }

        return evaluateDtoList;
    }

    /**
     * 发布新的评测
     * @param evaluateDto 评测信息
     * @return 添加成功
     */
    @Override
    public Boolean addEvaluation(EvaluateDto evaluateDto) {
        // 对评测信息进行校验
        // a.课程信息不为空
        if (evaluateDto == null) {
            throw new BusinessException(ErrorCode.EVALUATION_EMPTY, "请重新添加");
        }
        // b.判断评测信息是否已存在【根据名称查询】
        String name = evaluateDto.getName();
        Evaluate evaluateExit = evaluateMapper.queryEvaluationByName(name);
        if (evaluateExit != null) {
            throw new BusinessException(ErrorCode.EVALUATION_EXIT, "评测已存在");
        }

        // 将dto转为do
        Evaluate evaluate = new Evaluate();
        BeanUtils.copyProperties(evaluateDto, evaluate);

        // 日常插库
        boolean save = this.save(evaluate);

        // 发布评测后，将评测按照年级和专业分发到各个学生手上
        Integer eid = evaluateMapper.queryEvaluationIsGoing();
        boolean check = handOutEvaluations(eid);

        return save;
    }

    /**
     * 删除某次测评 =》 删除测评表  =》 删除测评记录 =》 删除最终结果 【权限问题非常关键，涉及到三张表的同时删除】
     * @param evaluateDto 评测的id
     * @return
     */
    @Override
    public Boolean deleteEvaluation(EvaluateDto evaluateDto) {
        Integer eid = evaluateDto.getId();

        // 删除所有学生的测评记录
        markHistoryMapper.deleteByEid(eid);

        // 删除所有的最终结果
        scoreHistoryMapper.deleteByEid(eid);

        boolean delete = this.removeById(eid);

        return delete;
    }

    /**
     * 更新评测信息
     * @param evaluateDto 评测信息
     * @return 更新成功
     */
    @Override
    public Boolean updateEvaluation(EvaluateDto evaluateDto) {
        Evaluate evaluate = new Evaluate();

        BeanUtils.copyProperties(evaluateDto, evaluate);
        boolean update = this.updateById(evaluate);

        return update;
    }

    /**
     * 获取某个评测的信息
     * @param id 评测的id
     * @return
     */
    @Override
    public EvaluateDto getEvaluation(Integer id) {
        Evaluate evaluate = this.getById(id);

        if (evaluate == null) {
            throw new BusinessException(ErrorCode.EVALUATION_NO_EXIT);
        }

        // 将do转为dto传到前台
        EvaluateDto evaluateInfo = new EvaluateDto();
        BeanUtils.copyProperties(evaluate, evaluateInfo);

        return evaluateInfo;
    }

    /**
     * 按照课程年级表来为每一个学生分配评价
     * @return 分配成功
     */
    public Boolean handOutEvaluations(Integer eid) {

        // 1.列出所有课程相关的年级信息 => course_to_grade
        List<CourseGrade> courseGradeList = courseGradeMapper.listAllCourseToGrade();

        // 取出所有老师信息
        List<Teacher> teacherList = teacherMapper.queryTeacherName();
        // tid => identity
        Map<Integer, Integer> teacherMap = teacherList.stream().collect(Collectors.toMap(Teacher::getId, Teacher::getIdentity));

        // 取出所有课程信息
        List<Course> courseList = courseMapper.queryAllCourse();

        // 将课程list转为map
        // 键值为主键id，值为课程名称
        Map<Integer, String> courseMap = courseList.stream().collect(Collectors.toMap(Course::getId, Course::getCName));

        // 列出所有的一级评价体系【俄方】
        List<Integer> RussiaFirstSystem = systemMapper.queryFirstSystemOfRussian();
        // 列出所有的一级评价体系 【中方】
        List<Integer> ChinaFirstSystem = systemMapper.queryFirstSystemOfChina();

        /**
         * 分发的顺序
         * 同一个课程
         * 1. 按照学生主键，为第一层循环
         * 2. 按照教师主键 =》 教师有不同的国籍，不同国籍的老师拥有不同的一级指标
         *      a. 按照国籍划分
         * 总计四层循环
         * 【不能在循环中进行查库】
         */
        for (CourseGrade courseGrade : courseGradeList) {
            // 获取固定课程信息
            Integer cid = courseGrade.getCid();     // 课程主键
            Integer gid = courseGrade.getGid();     // 年级
            Integer major = courseGrade.getMajor();     // 专业

            // 通过courseId来找到courseName（课程名称） => 用键值对进行查找
            String courseName = courseMap.get(cid);  // 课程名称

            // 根据课程名称找到所有老师(List)

            List<Integer> teacherId = courseMapper.queryTeacherByName(courseName);

            // 根据以上的条件分别来找到学生分发问卷 => 问卷记录在e_mark_history state默认值为0
            // 根据gid（年级）和major（专业）来固定学生
            // 这里得到的是相对应学生的主键
            List<Integer> studentId = studentMapper.getStudentByGradeAndMajor(gid, major);

            // 把学生课程评价记录在e_mark_history表上
            List<MarkHistory> markHistoryList = new ArrayList<>();
            // 第一层循环 学生主键
            for (Integer Aid : studentId) {
                // 第二层循环 老师主键
                for (Integer Tid : teacherId) {


                    // 判断教师国籍 0俄方 1中方
                    Integer identity = teacherMap.get(Tid);
                    if (identity == 0) {
                        // 俄方的一级指标
                        for (Integer sid : RussiaFirstSystem) {
                            MarkHistory markHistory = new MarkHistory();
                            markHistory.setTid(Tid);    // 教师主键
                            markHistory.setCid(cid);    // 课程主键
                            markHistory.setEid(eid);    // 评价主键
                            markHistory.setScore(0);    // 分数
                            markHistory.setSid(sid);       // 评价体系主键 =》 system(判断国籍)
                            markHistory.setAid(Aid);      // 学生主键

                            markHistoryList.add(markHistory);

                        }
                    }
                    if (identity == 1) {
                        // 中方的一级指标
                        for (Integer sid : ChinaFirstSystem) {
                            MarkHistory markHistory = new MarkHistory();
                            markHistory.setTid(Tid);    // 教师主键
                            markHistory.setCid(cid);    // 课程主键
                            markHistory.setEid(eid);    // 评价主键
                            markHistory.setScore(0);    // 分数
                            markHistory.setSid(sid);       // 评价体系主键 =》 system(判断国籍)
                            markHistory.setAid(Aid);      // 学生主键
                            markHistoryList.add(markHistory);
                        }
                    }
                }
            }

            System.out.println(markHistoryList);
            markHistoryService.saveBatch(markHistoryList);
        }

        return true;
    }

    /**
     * 统计测评未完成学生
     * @return
     */
    @Override
    public List<StudentDto> studentsDoneEvaluation(Integer eid) {
        // 获取所有学生信息
        List<Student> studentList = studentMapper.getAllStudents();

        // 完成学生集合
        List<StudentDto> studentDone = new ArrayList<>();

        List<StudentClass> classList = studentClassMapper.queryClassToList();
        Map<Integer, String> classMap = classList.stream().collect(Collectors.toMap(StudentClass::getId, StudentClass::getCid));

        // 查找学生是否全部完成一级评价
        for (Student student : studentList) {
            Integer id = student.getId();   // 学生id
            // 查找该学生是否有一级指标还没有完成
            // 如果全部完成 state的总数 == 记录总数
            List<Integer> stateByAid = markHistoryMapper.getStateByAid(id, eid);
            Integer sum = stateByAid.stream().reduce(Integer::sum).orElse(0);
            if (sum == stateByAid.size()) {
                // 该学生完成所有的评测
                StudentDto studentInfo = new StudentDto();
                BeanUtils.copyProperties(student, studentInfo, "cid");
                studentInfo.setCid(classMap.get(student.getCid()));
                studentDone.add(studentInfo);
            }
        }
        return studentDone;
    }

    /**
     * 返回所有未完成的学生名单
     * @return
     */
    @Override
    public List<StudentDto> studentsUndoneEvaluation(Integer eid) {
        // 获取所有学生信息
        List<Student> studentList = studentMapper.getAllStudents();

        // 完成学生集合
        List<StudentDto> studentUndone = new ArrayList<>();

        List<StudentClass> classList = studentClassMapper.queryClassToList();
        Map<Integer, String> classMap = classList.stream().collect(Collectors.toMap(StudentClass::getId, StudentClass::getCid));

        // 查找学生是否全部完成一级评价
        for (Student student : studentList) {
            Integer id = student.getId();   // 学生id
            // 查找该学生是否有一级指标还没有完成
            // 如果全部完成 state的总数 == 记录总数
            List<Integer> stateByAid = markHistoryMapper.getStateByAid(id, eid);
            Integer sum = stateByAid.stream().reduce(Integer::sum).orElse(0);
            if (sum != stateByAid.size()) {
                // 该学生完成所有的评测
                StudentDto studentInfo = new StudentDto();
                BeanUtils.copyProperties(student, studentInfo, "cid");
                studentInfo.setCid(classMap.get(student.getCid()));
                studentUndone.add(studentInfo);
            }
        }
        return studentUndone;

    }

}


