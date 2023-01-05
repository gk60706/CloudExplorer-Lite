package com.fit2cloud.provider;

import com.fit2cloud.es.entity.ResourceInstance;
import com.fit2cloud.provider.constants.ProviderConstants;
import com.fit2cloud.provider.entity.InstanceSearchField;

import java.util.Arrays;
import java.util.List;

/**
 * {@code @Author:张少虎}
 * {@code @Date: 2022/12/5  15:41}
 * {@code @Version 1.0}
 * {@code @注释: }
 */
public interface ICloudProvider {
    /**
     * 获取云服务器列表
     *
     * @param req 请求参数 包括认证对象
     * @return 云服务器列表
     */
    List<ResourceInstance> listEcsInstance(String req);

    /**
     * 获取实例可查询字段
     *
     * @return 实例可查询字段
     */
    List<InstanceSearchField> listEcsInstanceSearchField();

    /**
     * 获取云数据库redis实例
     *
     * @param req 请求对象
     * @return 云数据库redis相关数据
     */
    List<ResourceInstance> listRedisInstance(String req);

    /**
     * 获取云数据库redis可查询字段
     *
     * @return 实例可查询字段
     */
    List<InstanceSearchField> listRedisInstanceSearchField();

    /**
     * 获取云数据库 mongodb实例
     *
     * @param req 请求对象
     * @return 云数据库Mongodb相关数据
     */
    List<ResourceInstance> listMongodbInstance(String req);

    /**
     * 获取云数据库Mongodb可查询字段
     *
     * @return mongodb实例可查询字段
     */
    List<InstanceSearchField> listMongodbInstanceSearchField();

    /**
     * 获取云数据库 mysql实例
     *
     * @param req 请求对象
     * @return 云数据库mysql相关数据
     */
    List<ResourceInstance> listMysqlInstance(String req);

    /**
     * 获取云数据库mysql可查询字段
     *
     * @return mysql实例可查询字段
     */
    List<InstanceSearchField> listMysqlInstanceSearchField();

    /**
     * 获取云数据库 sqlServer实例列表
     *
     * @param req 请求对象
     * @return 云数据库sqlserver 实力列表
     */
    List<ResourceInstance> listSqlServerInstance(String req);

    /**
     * 获取云数据库 SqlServer可查询字段
     *
     * @return sqlserver 可查询字段
     */
    List<InstanceSearchField> listSqlServerInstanceSearchField();

    /**
     * 获取 PostGreSql 实例列表
     *
     * @param req 请求对象
     * @return 云数据库 PostGreSql 实例列表
     */
    List<ResourceInstance> listPostGreSqlInstance(String req);

    /**
     * 获取云数据库 PostGreSql可查询字段
     *
     * @return PostGreSql 可查询字段
     */
    List<InstanceSearchField> listPostGreSqlInstanceSearchField();

    /**
     * 获取 MariaDB 实例列表
     *
     * @param req 请求对象
     * @return 云数据库 MariaDB 实例列表
     */
    List<ResourceInstance> listMariaDBInstance(String req);

    /**
     * 获取云数据库 MariaDB 可查询字段
     *
     * @return MariaDB 可查询字段
     */
    List<InstanceSearchField> listMariaDBInstanceSearchField();

    /**
     * 获取 Elasticsearch 实例列表
     *
     * @param req 请求对象
     * @return 云数据库 elasticsearch 实例列表
     */
    List<ResourceInstance> listElasticSearchInstance(String req);

    /**
     * 获取 云数据库 ElasticSearch 可查询字段
     *
     * @return ElasticSearch可查询字段
     */
    List<InstanceSearchField> listElasticSearchInstanceSearchField();

    /**
     * 根据供应商获取对应云平台处理器
     *
     * @param platform 供应商
     * @return 处理器
     */
    static Class<? extends ICloudProvider> of(String platform) {
        return (Class<? extends ICloudProvider>) Arrays.stream(ProviderConstants.values()).filter(providerConstants -> providerConstants.name().equals(platform)).findFirst().orElseThrow(() -> new RuntimeException("不支持的云平台")).getCloudProvider();
    }
}
