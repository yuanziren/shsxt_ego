
import com.shsxt.ego.search.entity.GoodsVo;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext-elasticsearch.xml"})
public class TestSpringDataEs02 {

    @Resource
    private TransportClient client;

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;



    @Test
    public void test02(){
        HighlightBuilder.Field field = new HighlightBuilder.Field("goodsName")
                .preTags("<font style='color:red;'>")
                .postTags("</font>");
        QueryBuilder queryBuilder= QueryBuilders.multiMatchQuery("手机","goodsName");
        SearchQuery query =new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withHighlightFields(field)
                .withPageable(new PageRequest(0,10)).build();
        Page<GoodsVo> page= elasticsearchTemplate.queryForPage(query, GoodsVo.class, new SearchResultMapper() {
            @Override
            public <T> Page<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                List<GoodsVo> goodsVos =new ArrayList<GoodsVo>();
                SearchHits hits=response.getHits();
                for(SearchHit hit:hits){
                    GoodsVo goodsVo=new GoodsVo();
                    Map<String,Object> map=  hit.getSource();
                    goodsVo.setGoodsId(Long.parseLong(map.get("goodsId").toString()));
                    goodsVo.setGoodsName(hit.getHighlightFields().get("goodsName").fragments()[0].toString());// 高亮商品名称
                    goodsVo.setPrice(Long.parseLong(map.get("price").toString()));
                    goodsVo.setImage(map.get("image").toString());
                    goodsVos.add(goodsVo);
                }
                return (Page<T>) new PageImpl<GoodsVo>(goodsVos,pageable,hits.getTotalHits());
            }
        });

        System.out.println("总记录-->"+page.getTotalElements());
        System.out.println("总页数-->"+page.getTotalPages());
        page.getContent().forEach(g->{
            System.out.println(g);
        });
    }













}
