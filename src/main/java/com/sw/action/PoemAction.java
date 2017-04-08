package com.sw.action;

import com.sw.common.ElasticsearchClient;
import com.sw.domain.EsEntry;
import com.sw.domain.PoemRequest;
import com.sw.domain.WordEntry;
import com.sw.fenCi.Word2VEC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * Created by songshipeng on 2017/3/20.
 */
@Controller
public class PoemAction {

    @Autowired
    private Word2VEC word2VEC;
    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @RequestMapping(value = "/search.mvc")
    public String search(Model model){
        model.addAttribute(new PoemRequest());
        return "search.jsp";
    }

    /**
     * 根据前台输入的汉字获取古诗
     */
    @RequestMapping(value = "/getPoem.mvc")
    public String getPoem(PoemRequest poemRequest,Model model){
        int ziShu = Integer.valueOf(poemRequest.getZiShu());
        Set<WordEntry> result = new TreeSet<WordEntry>();
        result = word2VEC.distance(poemRequest.getHanZi());//手动输入要计算的相关词
        String guShi = "";//要返回的古诗
        List<String> parmStr = new ArrayList<String>();
        parmStr.add(poemRequest.getHanZi());
        if(null != result && result.size() > 0) {
            Iterator iter = result.iterator();
            while (iter.hasNext()) {
                WordEntry word = (WordEntry) iter.next();
                parmStr.add(word.name);
            }
            List<EsEntry> resultPoem= elasticsearchClient.getRelventPoem(parmStr,poemRequest.getZiShu());
            int gushiJu = 0;
            List<String> gushiList = new ArrayList<String>();
            //处理得到古诗
            char xs[] = poemRequest.getXs().toCharArray();
            for(char ch : xs) {
                if(gushiJu == 4){
                    break;
                }
                for (EsEntry esEntry : resultPoem) {
                    String temp = esEntry.getContent1();
                    String pzTemp = esEntry.getPz1();
                     if(isNull(temp,pzTemp) && temp.length() == ziShu && pzTemp.endsWith(String.valueOf(ch))){
                            if(!gushiList.contains(temp)){
                                gushiList.add(temp);
                                gushiJu ++;
                                break;
                            }
                     }
                    temp = esEntry.getContent2();
                    pzTemp = esEntry.getPz2();
                    if(isNull(temp,pzTemp) && temp.length() == ziShu && pzTemp.endsWith(String.valueOf(ch))){
                        if(!gushiList.contains(temp)){
                            gushiList.add(temp);
                            gushiJu ++;
                            break;
                        }
                    }
                    temp = esEntry.getContent3();
                    pzTemp = esEntry.getPz3();
                    if(isNull(temp,pzTemp) && temp.length() == ziShu && pzTemp.endsWith(String.valueOf(ch))){
                        if(!gushiList.contains(temp)){
                            gushiList.add(temp);
                            gushiJu ++;
                            break;
                        }
                    }
                    temp = esEntry.getContent4();
                    pzTemp = esEntry.getPz4();
                    if(isNull(temp,pzTemp) && temp.length() == ziShu && pzTemp.endsWith(String.valueOf(ch))){
                        if(!gushiList.contains(temp)){
                            gushiList.add(temp);
                            gushiJu ++;
                            break;
                        }
                    }
                }
            }
            for(String out : gushiList){
                guShi = guShi + out +"</br>";
            }
        }else {
            guShi = "</br></br></br></br></br></br><font color=\"red\" size=\"20px\">不知道你在说什么~~~~~</font>";
        }
        poemRequest.setTargetPoem(guShi);
        return "search.jsp";
    }


    /**
     * 备份，暂不使用
     * 根据前台输入的汉字获取古诗
     */
    @RequestMapping(value = "/getPoem2.mvc")
    public String getPoem2(PoemRequest poemRequest,Model model){
        Set<WordEntry> result = new TreeSet<WordEntry>();
        result = word2VEC.distance(poemRequest.getHanZi());//手动输入要计算的相关词
        String guShi = "";
        List<String> parmStr = new ArrayList<String>();
        if(null != result && result.size() > 0) {
            Iterator iter = result.iterator();
            while (iter.hasNext()) {
                WordEntry word = (WordEntry) iter.next();
                guShi = guShi + word.name + "</br>";
                parmStr.add(word.name);
            }
        }else {
            guShi = "</br></br></br></br></br></br><font color=\"red\" size=\"20px\">不知道你在说什么~~~~~</font>";
        }
        poemRequest.setTargetPoem(guShi);
        return "search.jsp";
    }

    public boolean isNull(String... args){
        for(String s : args){
            if(null == s || s.length() < 1){
                return false;
            }
        }
        return true;
    }
}
