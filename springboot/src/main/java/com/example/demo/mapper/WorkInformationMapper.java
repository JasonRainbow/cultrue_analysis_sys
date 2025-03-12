package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.WorkInformation;
import com.example.demo.entity.vo.DoubanData;
import com.example.demo.entity.vo.IMDbData;
import com.example.demo.entity.vo.YoutubeData;
import org.apache.ibatis.annotations.Param;

public interface WorkInformationMapper extends BaseMapper<WorkInformation> {

    public DoubanData getDoubanData(@Param(value = "platform") String platform);

    public IMDbData getIMDbData(@Param(value="platform") String platform);

    public YoutubeData getYoutubeData(@Param(value="platform") String platform);
}
