package com.sw.common;


import com.sw.domain.EsEntry;
import org.apache.commons.beanutils.BeanUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by songshipeng on 2017/1/11.
 */
@Service
public class ElasticsearchClient {
    static TransportClient transportClient;
    static String indexName = "pz";
    static String typeName = "gushiwen";

    /**
     * 连接es
     */
    @PostConstruct
    public void initEs() {
        try {
            System.out.println("初始化client");
            /**
             * client.transport.sniff 设置为true可以自动嗅探整个集群的状态并动态的将新加入的ip加入到客户端
             */
            Settings settings = Settings.builder()
                    .put("cluster.name", "my-application").put("client.transport.sniff",true).build();
            transportClient = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        }catch (Throwable throwable){
            throwable.printStackTrace();
        }
    }

    public List<EsEntry> getRelventPoem(List<String> appreciations,String ziShu){
        List<EsEntry> esEntryList = new ArrayList<EsEntry>();
        try {

            if (null != appreciations && appreciations.size() > 0) {
                BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
                for (String param : appreciations) {
                    boolQueryBuilder = boolQueryBuilder.should(QueryBuilders.matchQuery("appreciation", param));
                }
//                boolQueryBuilder = boolQueryBuilder.must(QueryBuilders.regexpQuery("content1","^.{5}$"))
//                        .must(QueryBuilders.regexpQuery("content2","^.{5}$"))
//                        .must(QueryBuilders.regexpQuery("content3","^.{5}$"))
//                        .must(QueryBuilders.regexpQuery("content4","^.{5}$"));
                SearchResponse searchResponse = transportClient.prepareSearch(indexName).setTypes(typeName).setSearchType(SearchType.DFS_QUERY_AND_FETCH)
                        .setQuery(boolQueryBuilder).execute().actionGet();
                SearchHit[] hitArray = searchResponse.getHits().getHits();
                for (SearchHit searchHit : hitArray) {
                    EsEntry esEntry = new EsEntry();
                    BeanUtils.populate(esEntry, searchHit.getSource());
                    esEntryList.add(esEntry);
                }
            }
            return esEntryList;
        }catch (Throwable throwable){
            throwable.printStackTrace();
        }
        return esEntryList;
    }

    public List<EsEntry> test(List<String> appreciations,String ziShu){
        try {
            List<EsEntry> esEntryList = new ArrayList<EsEntry>();
            if (null != appreciations && appreciations.size() > 0) {
                BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
                for (String param : appreciations) {
                    //boolQueryBuilder = boolQueryBuilder.should(QueryBuilders.matchQuery("appreciation", param));
                }
                //boolQueryBuilder = boolQueryBuilder.should(QueryBuilders.regexpQuery("content1","(^.{5}$)"))
                boolQueryBuilder.should(QueryBuilders.scriptQuery(new Script("doc['content1'].length = 5")))    ;
                SearchResponse searchResponse = transportClient.prepareSearch(indexName).setTypes(typeName).setSearchType(SearchType.DFS_QUERY_AND_FETCH).setSize(1000)
                        .setQuery(boolQueryBuilder).execute().actionGet();
                SearchHit[] hitArray = searchResponse.getHits().getHits();
                for (SearchHit searchHit : hitArray) {
                    EsEntry esEntry = new EsEntry();
                    BeanUtils.populate(esEntry, searchHit.getSource());
                    esEntryList.add(esEntry);
                }
            }
            return esEntryList;
        }catch (Throwable throwable){
            return null;
        }
    }
}
