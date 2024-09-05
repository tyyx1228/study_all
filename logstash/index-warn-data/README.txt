项目名称：索引报警数据
实现功能：1.离线批量一次性向elasticsearch导入2018年7月1号之前所有已经报警结束的事件,建立索引
         2.对2018年7月1号之后产生的或结束的事件，进行准实时增量同步到elasticsearch建立索引


实施说明：
为报警事件建立索引分为两个阶段：
第一阶段：etl一次性离线导入7月1号前的已结束报警的事件（包括通讯检测和超量程上下限的事件）
第二阶段：对7月1号至今的事件进行准实时地同步建立或更新索引


第一阶段中做的前期准备
    1.使用sqoop将oracle数据表全量导入到hdfs文件系统
    2.综合四报警数据查询中四张表的字段，重命名字段，并做索引和oracle表字段的字段映射，详见映射表: "ES-ORACLE字段对照表.xlsx"


第二阶段中做的前期准备：
    1. oracle原先没有维护tes_ba_t_p_w_list表中的last_update_date字段，现已增加触发器自动维
    2. oracle中baojing_info_sc表中缺失last_update_date字段，现已增加该字段，并增加触发器自动维
    3. 以上增加字段后，只对新增的数据有效。所以在还需要将7月份所有已经结束的事件手动更新一下last_update_date为大于7月1号


脚本文件说明：
    1. sql/query_tes_ba_t_p_w_list.sql  编写的sql用于增量查询tes_ba_t_p_w_list表中的数据
    2. sql/query_baojing_info_sc.sql    编写的sql用于增量查询baojing_info_sc表中的数据
    4. meta/tes_ba_t_p_w_list.meta      用于从7月1号开始增量同步tes_ba_t_p_w_list表数据时，记录上一次同步的位置（时间），这里只提供配置模板，具体配置视当时的部署而定
    5. meta/baojing_info_sc.meta        用于从7月1号开始增量同步baojing_info_sc表数据时，记录上一次同步的位置（时间），这里只提供配置模板，具体配置视当时的部署而定
    6. sync-warn-data.conf              采用logstash标准编写的配置文件，用于索引oracle中的表tes_ba_t_p_w_list、baojing_info_sc
    7. etl/*                            处理tes_ba_t_p_w_list表中7有份之前的所有报警数据，通过hive做join操作导入elasticsearch
    8. import/*                         从oracle中将导出tes_ba_t_p_w_list, baojing_info_sc, XIP_PUB_ORGS, TES_BA_POINTS_INDEX，这四张表的数据导入到hdfs文件系统


logstash启动脚本说明：
    1. 请确保服务器上已经安装logstash，并且能正常运行在jvm上
    2. 启动脚本，先将index-warn-data目录放到/home/logstash_app目录下，再执行以下命令启动：
        cd /home/logstash_app/index-warn-data/
        logstash -f sync-warn-data.conf --path.data=/home/logstash_app/index-warn-data/meta >>logstash.log 2>&1 &

        启动参数说明：
        -f                          以指定配置文件的方式起动logstash
        --path.data                 指定logstash运行时所需维护元数据目录
        --config.reload.automatic   lostash启动参数用于动态加载配置文件sync-warn-data.conf
        --config.test_and_exit      测试配置文件语法是否正确

    另外：若后续维护中需要修改logstash配置重新部署，请务必事先备份/home/logstash_app/index-warn-data目录，再以新的配置文件启动。
         此过程中若没有更改meta目录中的文件，请继续使用备份的meta目录文件