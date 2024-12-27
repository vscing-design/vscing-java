DROP TABLE IF EXISTS vscing_movie_producer;
CREATE TABLE vscing_movie_producer
(
    id BIGINT UNSIGNED PRIMARY KEY COMMENT '主键',
    tp_movie_id BIGINT UNSIGNED NULL COMMENT '影片ID',
    avatar VARCHAR(255) DEFAULT '' NOT NULL COMMENT '导演/演员图片',
    en_name VARCHAR(20) DEFAULT '' NOT NULL COMMENT '导演/演员英文名称',
    sc_name VARCHAR(20) DEFAULT '' NOT NULL COMMENT '导演/演员中文名称',
    act_name VARCHAR(20) DEFAULT '' NOT NULL COMMENT '演员角色名称',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建日期',
    created_by BIGINT COMMENT '创建人',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  NOT NULL COMMENT '修改日期',
    updated_by BIGINT COMMENT '修改人',
    deleted_at TIMESTAMP NULL COMMENT '删除日期',
    deleted_by BIGINT COMMENT '删除人'
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci comment = '影片导演/演员表';

DROP TABLE IF EXISTS vscing_movie_show;
CREATE TABLE vscing_movie_show
(
    id BIGINT UNSIGNED PRIMARY KEY COMMENT '主键',
    show_id BIGINT UNSIGNED NULL COMMENT '三方ID',
    movie_id BIGINT UNSIGNED NULL COMMENT '影片ID',
    hall_name VARCHAR(150) DEFAULT '' NOT NULL COMMENT '影厅名称',
    duration INT UNSIGNED DEFAULT 1 NOT NULL COMMENT '放映时长（分钟）',
    show_time TIMESTAMP NULL COMMENT '放映开始时间',
    stop_sell_time TIMESTAMP NULL COMMENT '电影售卖结束时间',
    show_version_type VARCHAR(150) DEFAULT '' NOT NULL COMMENT '场次类型',
    show_price DECIMAL(10,2) DEFAULT 0 NOT NULL COMMENT '场次价格（元）',
    user_price DECIMAL(10,2) DEFAULT 0 NOT NULL COMMENT '影片结算价（元）',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建日期',
    created_by BIGINT COMMENT '创建人',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  NOT NULL COMMENT '修改日期',
    updated_by BIGINT COMMENT '修改人',
    deleted_at TIMESTAMP NULL COMMENT '删除日期',
    deleted_by BIGINT COMMENT '删除人'
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci comment = '影片场次表';

DROP TABLE IF EXISTS vscing_movie_show_area;
CREATE TABLE vscing_movie_show_area
(
    id BIGINT UNSIGNED PRIMARY KEY COMMENT '主键',
    show_id BIGINT UNSIGNED NULL COMMENT '场次ID',
    area VARCHAR(150) DEFAULT '' NOT NULL COMMENT '区域ID,可能为字母',
    show_price DECIMAL(10,2) DEFAULT 0 NOT NULL COMMENT '区域内场次价格（元）',
    user_price DECIMAL(10,2) DEFAULT 0 NOT NULL COMMENT '区域内影片结算价（元）',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建日期',
    created_by BIGINT COMMENT '创建人',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  NOT NULL COMMENT '修改日期',
    updated_by BIGINT COMMENT '修改人',
    deleted_at TIMESTAMP NULL COMMENT '删除日期',
    deleted_by BIGINT COMMENT '删除人'
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci comment = '影片场次区域表';

DROP TABLE IF EXISTS vscing_movie;
CREATE TABLE vscing_movie (
    id BIGINT UNSIGNED PRIMARY KEY COMMENT '主键',
    movie_id BIGINT UNSIGNED NULL COMMENT '三方ID',
    cinema_id BIGINT UNSIGNED NULL COMMENT '影院ID',
    name VARCHAR(255) DEFAULT '' NOT NULL COMMENT '影片名称',
    duration INT UNSIGNED DEFAULT 1 NOT NULL COMMENT '影片时长（分钟）',
    publish_date TIMESTAMP NULL COMMENT '上映日期',
    director VARCHAR(255) DEFAULT '' NOT NULL COMMENT '影片导演',
    cast VARCHAR(255) DEFAULT '' NOT NULL COMMENT '影片主演',
    intro VARCHAR(255) DEFAULT '' NOT NULL COMMENT '影片简介',
    version_type VARCHAR(255) DEFAULT '' NOT NULL COMMENT '上映类型',
    language VARCHAR(255) DEFAULT '' NOT NULL COMMENT '影片语言',
    movie_type VARCHAR(255) DEFAULT '' NOT NULL COMMENT '影片类型',
    poster_url VARCHAR(255) DEFAULT '' NOT NULL COMMENT '海报图片',
    plot_url VARCHAR(255) DEFAULT '' NOT NULL COMMENT '剧情照，多个用英文逗号隔开',
    grade VARCHAR(255) DEFAULT '' NOT NULL COMMENT '评分',
    like INT UNSIGNED DEFAULT 0 NOT NULL COMMENT '想看人数',
    publish_status VARCHAR(255) DEFAULT '' NOT NULL COMMENT '上映类型，HOT为热映，WAIT为待上映',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建日期',
    created_by BIGINT COMMENT '创建人',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  NOT NULL COMMENT '修改日期',
    updated_by BIGINT COMMENT '修改人',
    deleted_at TIMESTAMP NULL COMMENT '删除日期',
    deleted_by BIGINT COMMENT '删除人'
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci comment = '影院表';


