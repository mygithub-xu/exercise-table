package com.dhlg.module.web.sysTest.controller;

import com.alibaba.fastjson.JSON;
import com.dhlg.module.web.sysTest.entity.SysTest;
import com.dhlg.module.web.sysTest.service.ISysTestService;
import com.dhlg.utils.Parameter.QueryEntity;
import com.dhlg.utils.Result;
import com.dhlg.utils.common.ExcelUtils;
import com.dhlg.utils.common.StringUtils;
import com.dhlg.module.common.ParamIsNullException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  ???????????????
 * </p>
 *
 * @author xu
 * @since
 */

@RestController
@Api(tags = "????????????")
@RequestMapping("/api/test/sysTest")
@CrossOrigin
public class SysTestController {

        @Autowired
        ISysTestService doService;

        @Autowired
        public RestHighLevelClient restHighLevelClient;

        @ApiOperation("????????????")
        @GetMapping("/testCreateIndex")
        public void testCreateIndex() throws IOException {
                CreateIndexRequest request = new CreateIndexRequest("liuyou_index");
                CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
                System.out.println(response.isAcknowledged());// ????????????????????????
                System.out.println(response);// ??????????????????
//                restHighLevelClient.close();
        }

        @ApiOperation("?????????????????????????????????????????????")
        @GetMapping("/testIndexIsExists")
        public void testIndexIsExists() throws IOException {
                GetIndexRequest request = new GetIndexRequest("liuyou_index");
                boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
                System.out.println(exists);// ??????????????????
//                restHighLevelClient.close();
        }

        @ApiOperation("??????????????????")
        @GetMapping("/testDeleteIndex")
        public void testDeleteIndex() throws IOException {
                DeleteIndexRequest request = new DeleteIndexRequest("liuyou_index");
                AcknowledgedResponse response = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
                System.out.println(response.isAcknowledged());// ??????????????????
//                restHighLevelClient.close();
        }


        @ApiOperation("??????????????????(???????????????User?????????)")
        @GetMapping("/testAddDocument")
        public void testAddDocument() throws IOException {
                // ????????????User??????
                User liuyou = new User("liuyou", 18);
                // ????????????
                IndexRequest request = new IndexRequest("liuyou_index");
                // ???????????? PUT /liuyou_index/_doc/1
                request.type("_doc");
                request.id("1");// ????????????ID
                request.timeout(TimeValue.timeValueMillis(1000));// request.timeout("1s")
                // ?????????????????????????????????
                request.source(JSON.toJSONString(liuyou), XContentType.JSON);
                // ?????????????????????????????????????????????
                IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
                System.out.println(response.status());// ????????????????????????????????? CREATED
                System.out.println(response);// ?????????????????? IndexResponse[index=liuyou_index,type=_doc,id=1,version=1,result=created,seqNo=0,primaryTerm=1,shards={"total":2,"successful":1,"failed":0}]
        }


        @ApiOperation("????????????????????????")
        @GetMapping("/testGetDocument")
        public void testGetDocument() throws IOException {
                GetRequest request = new GetRequest("liuyou_index","_doc","1");
                GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
                System.out.println(response.getSourceAsString());// ??????????????????
                System.out.println(request);// ??????????????????????????????????????????
//                restHighLevelClient.close();
        }


        @ApiOperation("????????????????????????")
        @GetMapping("/testUpdateDocument")
        public void testUpdateDocument() throws IOException {
                UpdateRequest request = new UpdateRequest("liuyou_index", "_doc","1");
                User user = new User("lmk",11);
                request.doc(JSON.toJSONString(user),XContentType.JSON);
                UpdateResponse response = restHighLevelClient.update(request, RequestOptions.DEFAULT);
                System.out.println(response.status()); // OK
//                restHighLevelClient.close();
        }

        @ApiOperation("??????????????????")
        @GetMapping("/testDeleteDocument")
        public void testDeleteDocument() throws IOException {
                DeleteRequest request = new DeleteRequest("liuyou_index", "_doc","1");
                request.timeout("1s");
                DeleteResponse response = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
                System.out.println(response.status());// OK
        }

        // ??????
        // SearchRequest ????????????
        // SearchSourceBuilder ????????????
        // HighlightBuilder ??????
        // TermQueryBuilder ????????????
        // MatchAllQueryBuilder
        // xxxQueryBuilder ...
        @ApiOperation("????????????")
        @GetMapping("/testSearch")
        public  void testSearch() throws IOException {
                // 1.????????????????????????
                SearchRequest searchRequest = new SearchRequest();
                // 2.??????????????????
                SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
                // (1)???????????? ??????QueryBuilders???????????????
                // ????????????
                TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "liuyou");
                //        // ????????????
                //        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
                // (2)??????<????????????>?????????????????? SearchSourceBuilder ??????????????????
                // ????????????
                searchSourceBuilder.highlighter(new HighlightBuilder());
                //        // ??????
                //        searchSourceBuilder.from();
                //        searchSourceBuilder.size();
                searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
                // (3)????????????
                searchSourceBuilder.query(termQueryBuilder);
                // 3.?????????????????????
                searchRequest.source(searchSourceBuilder);
                // 4.?????????????????????
                SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
                // 5.??????????????????
                SearchHits hits = search.getHits();
                System.out.println(JSON.toJSONString(hits));
                System.out.println("=======================");
                for (SearchHit documentFields : hits.getHits()) {
                        System.out.println(documentFields.getSourceAsMap());
                }
        }

        @ApiOperation("??????????????????")
        @GetMapping("/test1")
        public void test1() throws IOException {
                IndexRequest request = new IndexRequest("bulk");// ??????id???????????????????????????ID
                request.source(JSON.toJSONString(new User("liu",1)),XContentType.JSON);
                request.source(JSON.toJSONString(new User("min",2)),XContentType.JSON);
                request.source(JSON.toJSONString(new User("kai",3)),XContentType.JSON);
                IndexResponse index = restHighLevelClient.index(request, RequestOptions.DEFAULT);
                System.out.println(index.status());// created
        }

        // ????????????????????????????????? ??????????????????
        @ApiOperation("??????????????????")
        @GetMapping("/testBulk")
        public void testBulk() throws IOException {
                BulkRequest bulkRequest = new BulkRequest();
                bulkRequest.timeout("10s");
                ArrayList<User> users = new ArrayList<>();
                users.add(new User("liuyou-1",1));
                users.add(new User("liuyou-2",2));
                users.add(new User("liuyou-3",3));
                users.add(new User("liuyou-4",4));
                users.add(new User("liuyou-5",5));
                users.add(new User("liuyou-6",6));
                // ??????????????????
                for (int i = 0; i < users.size(); i++) {
                        bulkRequest.add(
                                // ?????????????????????
                                new IndexRequest("bulk")
//                                        .id(""+(i + 1)) // ????????????id ???????????????????????????id/
                                        .type("_doc")
                                        .source(JSON.toJSONString(users.get(i)),XContentType.JSON)
                        );
                }
                BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
                System.out.println(bulk.status());// ok
        }

        @ApiOperation("??????????????????")
        @PostMapping("/saveOrUpdate")
        public Result saveOrUpdate(@RequestBody SysTest sysTest) {
                if(StringUtils.isBlank(sysTest)){
                        throw new ParamIsNullException();
                }
                return  doService.saveOrUpdateCommon(sysTest);
        }

        @ApiOperation("????????????????????????")
        @DeleteMapping("/deleteById/{id}")
        public Result delete(@PathVariable String id) {
                new Hashtable<>();
                new HashMap<>();
                return doService.delete(id);
        }

        @ApiOperation("??????")
        @PostMapping("/query")
        public Result query(@RequestBody QueryEntity<SysTest> parameter) {
                if(StringUtils.isBlank(parameter)){
                        throw new ParamIsNullException();
                }
                return  doService.query(parameter);
        }

        @ApiOperation("??????")
        @GetMapping("/queryById/{id}")
        public Result query(@PathVariable String id) {
                System.out.println("aaaa");
                return  doService.query(id);
        }

        @ApiOperation("??????????????????????????????")
        @PostMapping("/batchDelete")
        public Result batchDelete(@RequestBody List<String> ids) {

                return doService.deleteBatch(ids);
        }

        @ApiOperation("??????")
        @PostMapping("/down")
        public void down(@RequestBody List<SysTest> data , HttpServletResponse response) {
                for(SysTest test : data){
                        String status =  "1".equals(test.getTestStatus()) ?"??????" : "??????";
                        test.setTestStatus(status);
                }

                ExcelUtils.downSimple(response,data);
        }

        @ApiOperation("??????????????????")
        @PostMapping("/down2")
        public void down2(@RequestBody List<SysTest> data , HttpServletResponse response){
                for(SysTest test : data){
                        String status =  "1".equals(test.getTestStatus()) ?"??????" : "??????";
                        test.setTestStatus(status);
                }
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("total", 1000);
                ExcelUtils.down("demo.xlsx",response,data,map);
        }

        @ApiOperation("??????")
        @PostMapping("/down3")
        public void down3(HttpServletResponse response) {
                doService.down3(response);
        }

}

