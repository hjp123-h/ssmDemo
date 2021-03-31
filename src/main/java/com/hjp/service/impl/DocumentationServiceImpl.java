package com.hjp.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjp.bean.Documentation;
import com.hjp.bean.Type;
import com.hjp.mapper.DocumentationMapper;
import com.hjp.mapper.TypeMapper;
import com.hjp.service.DocumentationService;
import com.hjp.service.TypeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author demo
 * @since 2021-03-28
 */
@Service
public class DocumentationServiceImpl extends ServiceImpl<DocumentationMapper, Documentation> implements DocumentationService {

}
