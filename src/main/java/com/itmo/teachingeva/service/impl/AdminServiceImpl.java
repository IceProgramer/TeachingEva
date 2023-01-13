package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.entity.Admin;
import generator.service.AdminService;
import generator.mapper.AdminMapper;
import org.springframework.stereotype.Service;

/**
* @author chenjiahan
* @description 针对表【e_admin】的数据库操作Service实现
* @createDate 2023-01-13 15:34:56
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

}




