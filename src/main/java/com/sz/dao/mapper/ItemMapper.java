package com.sz.dao.mapper;

import java.util.List;
import java.util.Map;

import com.sz.entity.Item;

import tk.mybatis.mapper.common.Mapper;

public interface ItemMapper extends Mapper<Item> {
    public List<Item> queryItem(Map<String,Object> map);
}