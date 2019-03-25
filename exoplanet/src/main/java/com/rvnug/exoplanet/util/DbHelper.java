package com.rvnug.exoplanet.util;

import com.rvnug.exoplanet.EntityBase;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.asyncsql.AsyncSQLClient;
import io.vertx.ext.sql.UpdateResult;

import java.util.ArrayList;

public class DbHelper<T extends EntityBase> {

    private Logger log;

    public DbHelper(Logger logger) {
        this.log = logger;
    }

    public Future<T> retrieve(AsyncSQLClient sqlClient, T entity) {
        log.trace("DbHelper::retrieve::" + Json.encode(entity));
        return this.retrieve(sqlClient, entity, false, false);
    }

    private Future<T> retrieve(AsyncSQLClient sqlClient, T entity, boolean includeDeleted, boolean includeSuspended) {
        log.trace("DbHelper::retrieve::" + Json.encode(entity));
        String entityTypeName = entity.getClass().getSimpleName().toLowerCase();
        String query = String.format("SELECT id, data FROM %s WHERE id = %s" , entityTypeName, entity.getId());
        if (!includeDeleted) {
            query += " AND deleteStamp = null";
        }
        if (!includeSuspended) {
            query += " AND suspended = FALSE";
        }
        return runQuery(sqlClient, entity, query);
    }

    public Future<T> runQuery(AsyncSQLClient sqlClient, T entity, String query) {
        log.trace("DbHelper::runQuery::" + query);
        Future<T> future = Future.future();
        sqlClient.getConnection(res -> {
            if (res.succeeded()) {
                SQLConnection connection = res.result();
                connection.query(query, (queryResult -> {
                    if (queryResult.succeeded()) {
                        log.debug("DbHelper::runQuery::Query Succeeded");
                        JsonArray resultRecord = queryResult.result().getResults().get(0);
                        log.debug("DbHelper::runQuery::" + resultRecord.toString());
                        T resultEntity = Json.decodeValue(resultRecord.getString(0), (Class<T>) entity.getClass());
                        log.debug("DbHelper::runQuery::" + Json.encode(resultEntity));
                        future.complete(resultEntity);
                    } else {
                        future.fail(queryResult.cause().getMessage());
                    }
                }));
            } else {
                future.fail(res.cause().getMessage());
            }
        });
        return future;
    }

    public Future<Integer> runQueryCount(AsyncSQLClient sqlClient, String query) {
        log.trace("DbHelper::runQueryCount::" + query);
        Future<Integer> future = Future.future();
        sqlClient.getConnection(res -> {
            if (res.succeeded()) {
                SQLConnection connection = res.result();
                connection.query(query, (queryResult -> {
                    if (queryResult.succeeded()) {
                        log.debug("DbHelper::runQueryCount::Query Succeeded");
                        JsonArray resultRecord = queryResult.result().getResults().get(0);
                        log.debug("DbHelper::runQueryCount::" + resultRecord.toString());
                        Integer resultEntity = resultRecord.getInteger(0);
                        log.debug("DbHelper::runQueryCount::" + Json.encode(resultEntity));
                        future.complete(resultEntity);
                    } else {
                        future.fail(queryResult.cause().getMessage());
                    }
                }));
            } else {
                future.fail(res.cause().getMessage());
            }
        });
        return future;
    }

    public Future<T> create(AsyncSQLClient sqlClient, T entity) {
        log.trace("DbHelper::create::" + Json.encode(entity));
        String entityTypeName = entity.getClass().getSimpleName().toLowerCase();
//        String query = String.format("INSERT INTO %s (tenantid, orgid, accountid, deletestamp, suspended, data) VALUES (%s, %s, %s, %s, %s, '%s');" , entityTypeName, entity.getTenantId(), entity.getOrgId(), entity.getAccountId(), entity.getDeleteStamp(), entity.getSuspended(), Json.encode(entity));
        String query = "INSERT INTO " + entityTypeName + " (tenantid, orgid, accountid, deletestamp, suspended, data) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
//        String query = "INSERT INTO " + entityTypeName + " VALUES (?, ?, ?, ?, ?, ?::JSON) RETURNING ID;";
        return this.upsert(sqlClient, entity, query);
    }

    public Future<T> update(AsyncSQLClient sqlClient, T entity) {
        String entityTypeName = entity.getClass().getSimpleName().toLowerCase();
        String query = String.format("UPDATE %s SET data = '%s' WHERE id = %s" , entityTypeName, Json.encode(entity), entity.getId());
        return this.upsert(sqlClient, entity, query);
    }

    public Future<T> delete(AsyncSQLClient sqlClient, T entity) {
        String entityTypeName = entity.getClass().getSimpleName().toLowerCase();
        String query = String.format("UPDATE %s SET deleteStamp = current_timestamp WHERE id = %s" , entityTypeName, Json.encode(entity), entity.getId());
        return this.upsert(sqlClient, entity, query);
    }

    private Future<T> upsert(AsyncSQLClient sqlClient, T entity, String query) {
        log.trace("DbHelper::upsert::" + query);
        Future<T> future = Future.future();
        try {
            sqlClient.getConnection(res -> {
                log.debug("DbHelper::upsert::getting connection...");
                if (res.succeeded()) {
                    log.debug("DbHelper::upsert::connection succeeded");
                    SQLConnection connection = res.result();
                    if (connection != null) {
                        log.debug("DbHelper::upsert::connection is not null");
                        JsonArray params = new JsonArray();
                        params.add(Json.encode(entity));
                        log.debug("DbHelper::upsert::" + params.toString());
                        connection.updateWithParams(query, params, (AsyncResult<UpdateResult> upsertResult) -> {
                            if (upsertResult.succeeded()) {
                                log.debug("DbHelper::upsert::query succeeded");
                                UpdateResult result = upsertResult.result();
                                if (result.getUpdated() == 1) {
                                    log.debug("DbHelper::upsert::one item::" + result.toJson().toString());
                                    // HACK: Last Inserted ID
                                    String entityTypeName = entity.getClass().getSimpleName().toLowerCase();
                                    String jsonPropertyName = entity.getLastInsertedIdFieldName();
                                    String jsonPropertyValue = entity.getLastInsertedIdFieldValue();
                                    String subQuery = String.format("%s.data->>'%s'::text='%s'", entityTypeName, jsonPropertyName, jsonPropertyValue);
                                    String lastInsertedIdQuery = String.format("SELECT id FROM %s WHERE %s", entityTypeName, subQuery);
                                    log.debug("DbHelper::upsert::" + lastInsertedIdQuery);
                                    connection.query(lastInsertedIdQuery, (queryResult -> {
                                        if (queryResult.succeeded()) {
                                            log.debug("DbHelper::upsert::Last Inserted ID Query Succeeded::" + queryResult.result().getResults().size());
                                            JsonArray resultRecord = queryResult.result().getResults().get(0);
                                            log.debug("DbHelper::upsert::" + resultRecord.toString());
                                            Integer resultId = resultRecord.getInteger(0);
                                            log.debug("DbHelper::upsert::" + resultId);
                                            entity.setId(resultId);
                                            log.debug("DbHelper::upsert::" + Json.encode(entity));
                                            future.complete(entity);
                                        } else {
                                            future.fail(queryResult.cause().getMessage());
                                        }
                                    }));
                                    // HACK: end of last inserted id hack
                                    future.complete(entity);
                                } else {
                                    log.error("DbHelper::upsert::query failed::" + upsertResult.cause().getMessage());
                                    future.fail("Records upserted is not 1");
                                }
                            } else {
                                log.error("DbHelper::upsert::query failed::" + upsertResult.cause().getMessage());
                                future.fail(upsertResult.cause().getMessage());
                            }
                        });
                    }
                } else {
                    log.error("Failed to obtain connection.");
                    future.fail(res.cause().getMessage());
                }
            });
        }
        catch (Exception ex) {
            log.error("DbHelper::upsert::" + ex.getMessage());
        }
        log.trace("DbHelper::upsert::returning...");
        return future;
    }

    private String generateQuerySqlForAccount(T entity) {
        String entityTypeName = entity.getClass().getSimpleName().toLowerCase();
        // JSON-friendly subquery.
        //        String subQuery = String.format("%s.data->>'accountId'::text='%s'", entityTypeName, accountId);
        StringBuilder subQuery = new StringBuilder();
        String result = String.format("SELECT data FROM %s WHERE %s" , entityTypeName, subQuery.toString());
        return result;
    }

    private String generateSubQuerySqlForDeleteStamp(T entity, boolean includeDeleted) {
        String entityTypeName = entity.getClass().getSimpleName().toLowerCase();
        StringBuilder subQuery = new StringBuilder();
        if (!includeDeleted) {
            subQuery.append(" AND deleteStamp = null");
        }
        String result = String.format("SELECT data FROM %s WHERE %s" , entityTypeName, subQuery.toString());
        return result;
    }

    public Future<ArrayList<T>> runQueryList(AsyncSQLClient sqlClient, T entity, int accountId) {
        String query = this.generateQuerySqlForAccount(entity);
        Future<ArrayList<T>> future = Future.future();
        sqlClient.getConnection(res -> {
            if (res.succeeded()) {
                SQLConnection connection = res.result();
                connection.query(query, (queryResult -> {
                    if (queryResult.succeeded()) {
                        ArrayList<T> futureResults = new ArrayList<>();
                        for(JsonArray record : queryResult.result().getResults()) {
                            T resultEntity = Json.decodeValue(record.getString(0), (Class<T>) entity.getClass());
                            futureResults.add(resultEntity);
                        }
                        future.complete(futureResults);
                    } else {
                        future.fail(queryResult.cause().getMessage());
                    }
                }));
            } else {
                future.fail(res.cause().getMessage());
            }
        });
        return future;
    }
}
