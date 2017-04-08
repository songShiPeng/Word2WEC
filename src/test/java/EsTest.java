import com.sw.common.ElasticsearchClient;
import com.sw.domain.EsEntry;
import com.sw.domain.PoemRequest;
import com.sw.domain.WordEntry;
import com.sw.fenCi.Word2VEC;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import java.util.*;

/**
 * Created by songshipeng on 2017/3/27.
 */
public class EsTest {
    public static void main(String[] args) {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("spring-config.xml");
        ElasticsearchClient elasticsearchClient = appContext.getBean(ElasticsearchClient.class);
        Word2VEC word2VEC = appContext.getBean(Word2VEC.class);
        Set<WordEntry> result = new TreeSet<WordEntry>();
        PoemRequest poemRequest = new PoemRequest();
        poemRequest.setHanZi("明月");
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
            List<EsEntry> resultPoem= elasticsearchClient.test(parmStr,poemRequest.getZiShu());
        }else {
            guShi = "</br></br></br></br></br></br><font color=\"red\" size=\"20px\">不知道你在说什么~~~~~</font>";
        }
    }
}
