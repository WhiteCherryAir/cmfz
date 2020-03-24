package com.ljy.entity;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.ljy.dao.BannerDao;

import java.util.ArrayList;
import java.util.List;

public class BannerListener extends AnalysisEventListener<Banner> {

    private BannerDao bannerDao;

    public BannerListener(BannerDao bannerDao) {
        this.bannerDao = bannerDao;
    }


    List<Banner> list = new ArrayList<Banner>();
    @Override
    public void invoke(Banner banner, AnalysisContext analysisContext) {
        list.add(banner);
        if(list!=null){
            bannerDao.insert(banner);
        }
        System.out.println(banner);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println(list);
    }
}
