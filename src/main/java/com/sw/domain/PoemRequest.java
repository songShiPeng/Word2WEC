package com.sw.domain;

/**
 * Created by songshipeng on 2017/3/20.
 */
public class PoemRequest {
    private String hanZi;//用户输入的汉字
    private String ziShu;//5或者7
    private String xs;//诗歌形式
    private String targetPoem;//返回给用户的古诗

    public String getHanZi() {
        return hanZi;
    }

    public void setHanZi(String hanZi) {
        this.hanZi = hanZi;
    }

    public String getZiShu() {
        return ziShu;
    }

    public void setZiShu(String ziShu) {
        this.ziShu = ziShu;
    }

    public String getXs() {
        return xs;
    }

    public void setXs(String xs) {
        this.xs = xs;
    }

    public String getTargetPoem() {
        return targetPoem;
    }

    public void setTargetPoem(String targetPoem) {
        this.targetPoem = targetPoem;
    }
}
