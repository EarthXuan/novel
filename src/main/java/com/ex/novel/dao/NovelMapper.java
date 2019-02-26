package com.ex.novel.dao;

import com.ex.novel.domain.Novel;
import com.ex.novel.domain.NovelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NovelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table novel
     *
     * @mbggenerated
     */
    int countByExample(NovelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table novel
     *
     * @mbggenerated
     */
    int deleteByExample(NovelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table novel
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table novel
     *
     * @mbggenerated
     */
    int insert(Novel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table novel
     *
     * @mbggenerated
     */
    int insertSelective(Novel record);

    int insertBatch(List<Novel> novels);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table novel
     *
     * @mbggenerated
     */
    List<Novel> selectByExample(NovelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table novel
     *
     * @mbggenerated
     */
    Novel selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table novel
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") Novel record, @Param("example") NovelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table novel
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") Novel record, @Param("example") NovelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table novel
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Novel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table novel
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Novel record);

}