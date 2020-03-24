package com.github.hexindai.s3harding.mybatis

import com.github.hexindai.s3harding.core.annotation.S3harding
import org.apache.ibatis.annotations.Select

interface Mapper {
    @S3harding(tableName = "New_V_FundIO", shardingKey = "148407")
    @Select("select * from New_V_FundIO limit 1")
    fun getOneNewVFundIO(): NewVFundIO
}