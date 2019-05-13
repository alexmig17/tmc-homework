package com.epam.test.dao;

import static com.epam.test.entity.Entity.COLUMN_ID;
import static com.epam.test.entity.User.COLUMN_EMAIL;
import static com.epam.test.entity.User.COLUMN_FIRST_NAME;
import static com.epam.test.entity.User.COLUMN_LAST_MAME;
import static com.epam.test.entity.User.COLUMN_PASSWORD;
import static com.epam.test.entity.User.COLUMN_ROLE;
import static java.lang.String.format;
import static java.util.Collections.singletonMap;
import static java.util.Optional.ofNullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.epam.test.entity.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class UserDao implements Dao<User, Integer> {

    private static final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<Map<String, Object>>() {
    };
    private static final String SQL_DELETE = format("delete from User where %s=:%s", COLUMN_ID, COLUMN_ID);
    private final ObjectMapper objectMapper;
    private UserSelectAll selectAll;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private UserGetById getById;
    private UserGetByEmail getByEmail;
    private UpdateUser update;
    private CreateUser create;

    @Autowired
    public UserDao(@Qualifier("upperCamelCase") ObjectMapper objectMapper, DataSource dataSource) {
        this.objectMapper = objectMapper;
        selectAll = new UserSelectAll(dataSource);
        getById = new UserGetById(dataSource);
        getByEmail = new UserGetByEmail(dataSource);
        update = new UpdateUser(dataSource);
        create = new CreateUser(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> get(Integer id) {
        return ofNullable(getById.findObjectByNamedParam(singletonMap(COLUMN_ID, id)));
    }

    @Override
    public List<User> getAll() {
        return selectAll.execute();
    }

    @Override
    public Integer update(User user) {
        if (user.getId() != null) {
            update.updateByNamedParam(objectMapper.convertValue(user, MAP_TYPE));
        } else {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            Map<String, Object> properties = objectMapper.convertValue(user, MAP_TYPE);
            properties.remove(COLUMN_ID);
            create.updateByNamedParam(properties, keyHolder);
            user.setId(keyHolder.getKey().intValue());
        }
        return user.getId();
    }

    @Override
    public boolean delete(User user) {
        return namedParameterJdbcTemplate.update(SQL_DELETE, singletonMap(COLUMN_ID, user.getId())) == 1;
    }

    public Optional<User> getByEmail(String email) {
        return ofNullable(getByEmail.findObjectByNamedParam(singletonMap(COLUMN_EMAIL, email)));
    }

//    private Map<String, Object> merge(Product product) {
//        Product repository = get(product.getId()).orElseThrow(RuntimeException::new);
//
//
//    }

    private static class UserSelectAll extends UserMapping {

        private static final String SQL = "Select * from USER";

        UserSelectAll(DataSource ds) {
            super(ds, SQL);
        }
    }

    private static abstract class UserMapping extends MappingSqlQuery<User> {

        UserMapping(DataSource ds, String sql) {
            super(ds, sql);
        }

        @Override
        protected User mapRow(ResultSet set, int i) throws SQLException {
            User user = new User();
            user.setId(set.getInt(COLUMN_ID));
            user.setFirstName(set.getString(COLUMN_FIRST_NAME));
            user.setLastName(set.getString(COLUMN_LAST_MAME));
            user.setEmail(set.getString(COLUMN_EMAIL));
            user.setPassword(set.getString(COLUMN_PASSWORD));
            user.setRole(set.getString(COLUMN_ROLE));
            return user;
        }
    }

    private static final class UserGetByEmail extends UserMapping {

        private static final String SQL = format("Select * from USER where %s = :%s", COLUMN_EMAIL, COLUMN_EMAIL);

        UserGetByEmail(DataSource ds) {
            super(ds, SQL);
            super.declareParameter(new SqlParameter(COLUMN_EMAIL, Types.VARCHAR));
        }
    }

    private static final class UserGetById extends UserMapping {

        private static final String SQL = format("Select * from USER where %s = :%s", COLUMN_ID, COLUMN_ID);

        UserGetById(DataSource ds) {
            super(ds, SQL);
            super.declareParameter(new SqlParameter(COLUMN_ID, Types.INTEGER));
        }
    }

    private static abstract class UpdateUserConfiguration extends SqlUpdate {

        UpdateUserConfiguration(DataSource ds, String sql) {
            super(ds, sql);
            super.declareParameter(new SqlParameter(COLUMN_FIRST_NAME, Types.VARCHAR));
            super.declareParameter(new SqlParameter(COLUMN_LAST_MAME, Types.VARCHAR));
            super.declareParameter(new SqlParameter(COLUMN_EMAIL, Types.VARCHAR));
            super.declareParameter(new SqlParameter(COLUMN_PASSWORD, Types.VARCHAR));
            super.declareParameter(new SqlParameter(COLUMN_ROLE, Types.VARCHAR));
        }
    }

    private static final class CreateUser extends UpdateUserConfiguration {

        private static final String SQL = format("insert into USER (%s, %s, %s, %s, %s) values (:%s, :%s, :%s, :%s, :%s)",
                COLUMN_FIRST_NAME, COLUMN_LAST_MAME, COLUMN_EMAIL, COLUMN_PASSWORD, COLUMN_ROLE,
                COLUMN_FIRST_NAME, COLUMN_LAST_MAME, COLUMN_EMAIL, COLUMN_PASSWORD, COLUMN_ROLE);

        CreateUser(DataSource ds) {
            super(ds, SQL);
            super.setGeneratedKeysColumnNames(COLUMN_ID);
        }
    }

    private static final class UpdateUser extends UpdateUserConfiguration {

        private static final String SQL = format("update User set %s = :%s, %s = :%s, %s = :%s, %s = :%s, %s = :%s where %s = :%s",
                COLUMN_FIRST_NAME, COLUMN_FIRST_NAME,
                COLUMN_LAST_MAME, COLUMN_LAST_MAME,
                COLUMN_EMAIL, COLUMN_EMAIL,
                COLUMN_PASSWORD, COLUMN_PASSWORD,
                COLUMN_ROLE, COLUMN_ROLE,
                COLUMN_ID, COLUMN_ID);

        UpdateUser(DataSource ds) {
            super(ds, SQL);
            super.declareParameter(new SqlParameter(COLUMN_ID, Types.INTEGER));
        }
    }
}
