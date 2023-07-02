package com.xuecheng.content.service.impl;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.service.CourseCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ragnarok
 * @version 1.0
 * @description
 * @create 2023-07-01 21:40
 * @github https://github.com/Ragnarokoo
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CourseCategoryServiceImpl implements CourseCategoryService
{
    private final CourseCategoryMapper courseCategoryMapper;

    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id)
    {
        // 1.调用mapper递归查询分类信息
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryMapper.selectTreeNodes(id);
        // 2. 找到每个节点的子节点,最终封装成List<CourseCategoryTreeDto>
        // 2.1先将list转成map,key就是节点的id,value就是CourseCategoryTreeDto对象,目的就是放方便从map获取节点, filter(item -> !id.equals(item.getId()))排除根节点
        Map<String, CourseCategoryTreeDto> categoryTreeDtoMap =
                courseCategoryTreeDtos.stream().filter(item -> !id.equals(item.getId())).collect(Collectors.toMap(key -> key.getId(), value -> value, (key1, key2) -> key2));

        // 定义一个list作为最终返回的list
        ArrayList<CourseCategoryTreeDto> courseCategoryList = new ArrayList<>();

        // 2.2 从头遍历 List<CourseCategoryTreeDto>, 一遍遍历一遍找子节点放在父节点的childrenTreeNodes
        courseCategoryTreeDtos.stream().filter(item->!id.equals(item.getId())).forEach(item-> {
            if (item.getParentid().equals(id)) {
                courseCategoryList.add(item);
            }
            // 找到节点的父节点
            CourseCategoryTreeDto courseCategoryParent = categoryTreeDtoMap.get(item.getParentid());
            if (courseCategoryParent!=null) {
                if (courseCategoryParent.getChildrenTreeNodes()==null) {
                    // 如果该付姐带你的ChildrenTreeNodes属性为空要new 一个集合,因为要向该集合中放他的子节点
                    courseCategoryParent.setChildrenTreeNodes(new ArrayList<CourseCategoryTreeDto>());
                }
                // 每个节点的子节点放在父节点的childrenTreeNodes属性中
                courseCategoryParent.getChildrenTreeNodes().add(item);
            }
        });

        return courseCategoryList;
    }
}