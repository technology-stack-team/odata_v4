package com.sap.olingo.jpa.processor.core.database;

import com.sap.olingo.jpa.metadata.core.edm.mapper.api.JPADataBaseFunction;
import com.sap.olingo.jpa.metadata.core.edm.mapper.api.JPAEntityType;
import com.sap.olingo.jpa.processor.core.exception.ODataJPADBAdaptorException;
import com.sap.olingo.jpa.processor.core.exception.ODataJPAProcessorException;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceKind;
import org.apache.olingo.server.api.uri.queryoption.SearchOption;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import java.util.ArrayList;
import java.util.List;

import static com.sap.olingo.jpa.processor.core.exception.ODataJPAProcessorException.MessageKeys.NOT_SUPPORTED_FUNC_WITH_NAVI;

public class JPA_MYSQL_DatabaseProcessor  extends JPAAbstractDatabaseProcessor { // NOSONAR
    private static final String SELECT_BASE_PATTERN = "CALL $FUNCTIONNAME$($PARAMETER$)";

    @Override
    public Expression<Boolean> createSearchWhereClause(final CriteriaBuilder cb, final CriteriaQuery<?> cq,
                                                       final From<?, ?> root, final JPAEntityType entityType, final SearchOption searchOption)
            throws ODataApplicationException {

        /*
         * Even so PostgesSQL has text search, as of know no generic implementation made for search
         */
        throw new ODataJPADBAdaptorException(ODataJPADBAdaptorException.MessageKeys.NOT_SUPPORTED_SEARCH,
                HttpStatusCode.NOT_IMPLEMENTED);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> executeFunctionQuery(final List<UriResource> uriResourceParts,
                                            final JPADataBaseFunction jpaFunction, final EntityManager em) throws ODataApplicationException {

        final UriResource last = uriResourceParts.get(uriResourceParts.size() - 1);

        if (last.getKind() == UriResourceKind.function)
            return  executeQuery(uriResourceParts, jpaFunction, em, SELECT_BASE_PATTERN);
        throw new ODataJPAProcessorException(NOT_SUPPORTED_FUNC_WITH_NAVI, HttpStatusCode.NOT_IMPLEMENTED);
    }
}