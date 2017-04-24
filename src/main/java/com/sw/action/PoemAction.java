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

import java.io.File;
import java.util.*;

/**
 * Created by songshipeng on 2017/3/20.
 */
@Controller
@RequestMapping(value = "/wll")
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
    public String getPoem(String from,PoemRequest poemRequest,Model model){
        //检测图片作诗
        if("1".equals(from)){

            File file = new File(System.getProperty("Word2WEC.folder")+"/poemImages");
            System.out.println(System.getProperty("Word2WEC.folder"));
            System.out.println(file.getAbsoluteFile());
            if(file.exists()){
                File[] files = file.listFiles();
                Random random = new Random();
                File targetFile = files[random.nextInt(files.length)];
                if(null != targetFile) {
                    poemRequest.setImages(targetFile.getName());
                    poemRequest.setHanZi(targetFile.getName().substring(0,targetFile.getName().indexOf(".")));
                }

            }
        }
        int ziShu = Integer.valueOf(poemRequest.getZiShu());
       TreeSet<WordEntry> firstResult = new TreeSet<WordEntry>();
        firstResult = word2VEC.distance(poemRequest.getHanZi());//手动输入要计算的相关词
        TreeSet<WordEntry> result = new TreeSet<WordEntry>();
        int ri = 0;
        for(WordEntry wordEntry : firstResult){
            result.add(wordEntry);
            ri++;
            if(ri > 5){
                break;
            }
        }
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
            if(resultPoem.size() > 1){
                //处理得到古诗
                char xs[] = poemRequest.getXs().toCharArray();
                Random random = new Random();
                for(char ch : xs) {
                    if (gushiJu == 4) {
                        break;
                    }
                    for (int i = 0; i < 10000; i++) {
                        EsEntry esEntry = resultPoem.get(random.nextInt(resultPoem.size()));
                        if (null == esEntry) {
                            continue;
                        }
                        String temp = getTrim(esEntry.getContent1());
                        String pzTemp = getTrim(esEntry.getPz1());
                        if (!isNull(temp, pzTemp) && temp.length() == ziShu && pzTemp.endsWith(String.valueOf(ch))) {
                            if (!gushiList.contains(temp)) {
                                gushiList.add(temp);
                                gushiJu++;
                                break;
                            }
                        }
                        temp = getTrim(esEntry.getContent2());
                        pzTemp = getTrim(esEntry.getPz2());
                        if (!isNull(temp, pzTemp) && temp.length() == ziShu && pzTemp.endsWith(String.valueOf(ch))) {
                            if (!gushiList.contains(temp)) {
                                gushiList.add(temp);
                                gushiJu++;
                                break;
                            }
                        }

                        temp = getTrim(esEntry.getContent3());
                        pzTemp = getTrim(esEntry.getPz3());
                        if (!isNull(temp, pzTemp) && temp.length() == ziShu && pzTemp.endsWith(String.valueOf(ch))) {
                            if (!gushiList.contains(temp)) {
                                gushiList.add(temp);
                                gushiJu++;
                                break;
                            }
                        }
                        temp = getTrim(esEntry.getContent4());
                        pzTemp = getTrim(esEntry.getPz4());
                        if (!isNull(temp, pzTemp) && temp.length() == ziShu && pzTemp.endsWith(String.valueOf(ch))) {
                            if (!gushiList.contains(temp)) {
                                gushiList.add(temp);
                                gushiJu++;
                                break;
                            }
                        }

                    }
                }
            }
            for(String out : gushiList){
                guShi = guShi + out +"</br></br>";
            }
        }else {
            guShi = "</br></br></br></br></br></br><font color=\"red\" size=\"10px\">不知道你在说什么~~~~~</font>";
        }
        if(null == guShi || guShi.length() < 10){
            guShi = "</br></br></br></br></br></br><font color=\"red\" size=\"10px\">不知道你在说什么~~~~~</font>";
        }
        if("1".equals(from)){
            poemRequest.setHanZi("");
        }
        poemRequest.setTargetPoem("<font  size=\"5px\">"+guShi + "</font>");
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
            if(null == s || s.trim().length() < 1){
                return true;
            }
        }
        return false;
    }

    public String getTrim(String param){
        if(null == param)
            return  null;
        return param.replace((char) 12288, ' ').trim().replace("\t","");
    }
}
