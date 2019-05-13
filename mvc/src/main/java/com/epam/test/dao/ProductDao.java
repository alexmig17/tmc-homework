package com.epam.test.dao;

import static com.epam.test.entity.Entity.COLUMN_ID;
import static com.epam.test.entity.Product.COLUMN_DESCRIPTION;
import static com.epam.test.entity.Product.COLUMN_IMAGE;
import static com.epam.test.entity.Product.COLUMN_NAME;
import static com.epam.test.entity.Product.COLUMN_PRICE;
import static java.lang.String.format;
import static java.util.Collections.singletonMap;
import static java.util.Optional.ofNullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.epam.test.entity.Product;
import com.epam.test.enums.Sort;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class ProductDao implements Dao<Product, Integer> {

    private static final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<Map<String, Object>>() {
    };
    private static final String SQL_DELETE = format("delete from PRODUCT where %s=:%s", COLUMN_ID, COLUMN_ID);
    private static final String SQL_GET_ALL = "select * from PRODUCT order by name %s limit :offset, :elementCount";
    private static final String SQL_SEARCH = "select %s from PRODUCT where UPPER(name) LIKE UPPER(:search)";
    private static final String SQL_PAGINATION = " order by name %s limit :offset, :elementCount";
    private static final String SQL_SEARCH_AND_PAGINATION = format(SQL_SEARCH, "*") + SQL_PAGINATION;
    private static final String PARAM_OFFSET = "offset";
    private static final String PARAM_ELEMENT_COUNT = "elementCount";
    private static final String PARAM_SEARCH = "search";
    private static final String FIND_ALL_CHARACTER = "%";

    private final ObjectMapper objectMapper;
    private ProductSelectAll selectAll;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private ProductGetById getById;
    private UpdateProduct update;
    private CreateProduct create;

    @Autowired
    public ProductDao(@Qualifier("upperCamelCase") ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Optional<Product> get(Integer id) {
        return ofNullable(getById.findObjectByNamedParam(singletonMap(COLUMN_ID, id)));
    }

    @Override
    public List<Product> getAll() {
        return selectAll.execute();
    }

    private static Product mapRow(ResultSet set, int i) throws SQLException {
        Product product = new Product();
        product.setId(set.getInt(COLUMN_ID));
        product.setDescription(set.getString(COLUMN_DESCRIPTION));
        product.setImage(set.getBytes(COLUMN_IMAGE));
        product.setName(set.getString(COLUMN_NAME));
        product.setPrice(set.getBigDecimal(COLUMN_PRICE));
        return product;
    }

    public Integer getTotalCount(String search) {
        String sql;
        Map<String, Object> parameters;
        if (StringUtils.isBlank(search)) {
            sql = "Select count(*) from PRODUCT";
            parameters = Collections.emptyMap();
        } else {
            sql = format(SQL_SEARCH, "count(*)");
            parameters = Collections.singletonMap(PARAM_SEARCH, FIND_ALL_CHARACTER + search + FIND_ALL_CHARACTER);
        }
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);
    }

    public List<Product> search(Integer offset, Integer elementCount, Sort orderBy, String search) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(PARAM_OFFSET, offset);
        paramMap.put(PARAM_ELEMENT_COUNT, elementCount);
        paramMap.put(PARAM_SEARCH, FIND_ALL_CHARACTER + search + FIND_ALL_CHARACTER);
        return namedParameterJdbcTemplate.query(format(SQL_SEARCH_AND_PAGINATION, orderBy.name().toLowerCase()),
                paramMap,
                ProductDao::mapRow);
    }

    @Override
    public Integer update(Product product) {
        if (product.getId() != null) {
            update.updateByNamedParam(objectMapper.convertValue(product, MAP_TYPE));
        } else {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            Map<String, Object> properties = objectMapper.convertValue(product, MAP_TYPE);
            properties.remove(COLUMN_ID);
            create.updateByNamedParam(properties, keyHolder);
            product.setId(keyHolder.getKey().intValue());
        }
        return product.getId();
    }

    @Override
    public boolean delete(Product product) {
        return namedParameterJdbcTemplate.update(SQL_DELETE, singletonMap(COLUMN_ID, product.getId())) == 1;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        selectAll = new ProductSelectAll(dataSource);
        getById = new ProductGetById(dataSource);
        update = new UpdateProduct(dataSource);
        create = new CreateProduct(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    // add merge logic during update
//    private Map<String, Object> merge(Product product) {
//        Product repository = get(product.getId()).orElseThrow(RuntimeException::new);
//
//
//    }

    private static class ProductSelectAll extends ProductMapping {

        private static final String SQL = "Select * from PRODUCT";

        ProductSelectAll(DataSource ds) {
            super(ds, SQL);
        }
    }

    public List<Product> getAll(Integer offset, Integer elementCount, Sort orderBy) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(PARAM_OFFSET, offset);
        paramMap.put(PARAM_ELEMENT_COUNT, elementCount);
        return namedParameterJdbcTemplate.query(format(SQL_GET_ALL, orderBy.name().toLowerCase()),
                paramMap,
                ProductDao::mapRow);
    }

    private static class ProductSelectAllPagination extends ProductMapping {

        private static final String SQL = "Select * from PRODUCT limit %d, %d";

        ProductSelectAllPagination(DataSource ds) {
            super(ds, SQL);
        }
    }

    private static abstract class ProductMapping extends MappingSqlQuery<Product> {

        ProductMapping(DataSource ds, String sql) {
            super(ds, sql);
        }

        @Override
        protected Product mapRow(ResultSet set, int i) throws SQLException {
            return ProductDao.mapRow(set, i);
        }
    }

    private static final class ProductGetById extends ProductMapping {

        private static final String SQL = format("Select * from PRODUCT where %s = :%s", COLUMN_ID, COLUMN_ID);

        ProductGetById(DataSource ds) {
            super(ds, SQL);
            super.declareParameter(new SqlParameter(COLUMN_ID, Types.INTEGER));
        }
    }

    private static abstract class UpdateProductConfiguration extends SqlUpdate {

        UpdateProductConfiguration(DataSource ds, String sql) {
            super(ds, sql);
            super.declareParameter(new SqlParameter(COLUMN_DESCRIPTION, Types.CLOB));
            super.declareParameter(new SqlParameter(COLUMN_IMAGE, Types.BLOB));
            super.declareParameter(new SqlParameter(COLUMN_NAME, Types.VARCHAR));
            super.declareParameter(new SqlParameter(COLUMN_PRICE, Types.DECIMAL));
        }
    }

    private static final class CreateProduct extends UpdateProductConfiguration {

        private static final String SQL = format("insert into Product (%s, %s, %s, %s) values (:%s, :%s, :%s, :%s)",
                COLUMN_DESCRIPTION, COLUMN_IMAGE, COLUMN_NAME, COLUMN_PRICE,
                COLUMN_DESCRIPTION, COLUMN_IMAGE, COLUMN_NAME, COLUMN_PRICE);

        CreateProduct(DataSource ds) {
            super(ds, SQL);
            super.setGeneratedKeysColumnNames(COLUMN_ID);
        }
    }

    private static final class UpdateProduct extends UpdateProductConfiguration {

        private static final String SQL = format("update Product set %s = :%s, %s = :%s, %s = :%s, %s = :%s where %s = :%s",
                COLUMN_DESCRIPTION, COLUMN_DESCRIPTION,
                COLUMN_IMAGE, COLUMN_IMAGE,
                COLUMN_NAME, COLUMN_NAME,
                COLUMN_PRICE, COLUMN_PRICE,
                COLUMN_ID, COLUMN_ID);

        UpdateProduct(DataSource ds) {
            super(ds, SQL);
            super.declareParameter(new SqlParameter(COLUMN_ID, Types.INTEGER));
        }
    }
}

