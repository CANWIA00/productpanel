package com.canwia.productapi.util;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.canwia.productapi.service.ProductSearchService;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.function.Function;
import java.util.function.Supplier;

@UtilityClass
public class EsUtil {
    private static final Logger logger = LoggerFactory.getLogger(EsUtil.class);
    public static Supplier<Query> buildFieldForFieldAndValue(String field, String value){

        return () -> Query.of(q->q.match(buildMatchQueryForFieldAndValue(field,value)));
    }

    private static MatchQuery buildMatchQueryForFieldAndValue(String field, String value) {
        return new MatchQuery.Builder()
                .field(field)
                .query(value)
                .build();
    }
}
