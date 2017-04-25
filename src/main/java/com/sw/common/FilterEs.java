package com.sw.common;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 过滤es
 */
@Service
public class FilterEs {
    static List<String> esIds = new ArrayList<String>();
    static {
        String s = "AVlKnXVEnVxFiym6eogb,\n" +
                "AVlKnkbdnVxFiym6eog7,\n" +
                "AVlKoATKnVxFiym6eohd,\n" +
                "AVlKn8MinVxFiym6eohS,\n" +
                "AVlKoT5pnVxFiym6eoiO,\n" +
                "AVlKormXnVxFiym6eojR,\n" +
                "AVlKpdAZnVxFiym6eolI,\n" +
                "AVlKpUocnVxFiym6eok0,\n" +
                "AVlKnnQtnVxFiym6eohA,\n" +
                "AVlKn4MMnVxFiym6eohH,\n" +
                "AVlKnblJnVxFiym6eogm,\n" +
                "AVlKnYt0nVxFiym6eogf,\n" +
                "AVlKna61nVxFiym6eogk,\n" +
                "AVlKn5SJnVxFiym6eohK,\n" +
                "AVlKoK8snVxFiym6eoh0,\n" +
                "AVlKoJ1unVxFiym6eohy,\n" +
                "AVlKn8h1nVxFiym6eohT,\n" +
                "AVlKoYoLnVxFiym6eoib,\n" +
                "AVlKoPr3nVxFiym6eoh_,\n" +
                "AVlKorTFnVxFiym6eojQ,\n" +
                "AVlKo9hLnVxFiym6eokB,\n" +
                "AVlKnX-4nVxFiym6eogd,\n" +
                "AVlKnnx3nVxFiym6eohB,\n" +
                "AVlKnkE-nVxFiym6eog6,\n" +
                "AVlKnjDanVxFiym6eog3,\n" +
                "AVlKndIlnVxFiym6eogq,\n" +
                "AVlKoNB6nVxFiym6eoh4,\n" +
                "AVlKoPS5nVxFiym6eoh-,\n" +
                "AVlKoo62nVxFiym6eojK,\n" +
                "AVlKov11nVxFiym6eojc,\n" +
                "AVlKownpnVxFiym6eoje,\n" +
                "AVlKpWBRnVxFiym6eok4,\n" +
                "AVlKpa2bnVxFiym6eolD,\n" +
                "AVlKneJFnVxFiym6eogt,\n" +
                "AVlKndcnnVxFiym6eogr,\n" +
                "AVlKngBrnVxFiym6eogx,\n" +
                "AVlKoFJznVxFiym6eohq,\n" +
                "AVlKoaJonVxFiym6eoif,\n" +
                "AVlKoVftnVxFiym6eoiT,\n" +
                "AVlKpUSFnVxFiym6eokz,\n" +
                "AVlKpIu_nVxFiym6eokc,\n" +
                "AVlKpSLdnVxFiym6eoku,\n" +
                "AVlKoC2_nVxFiym6eohk,\n" +
                "AVlKn7YdnVxFiym6eohQ,\n" +
                "AVlKo8BRnVxFiym6eoj9,\n" +
                "AVlKpjTwnVxFiym6eolU\n" +
                "\n";
        String [] strings = s.split(",");
        for(String s1 : strings){
            FilterEs.esIds.add(s1);
        }
    }
}
