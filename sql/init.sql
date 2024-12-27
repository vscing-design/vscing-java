DROP TABLE IF EXISTS vscing_organization;
CREATE TABLE vscing_organization (
    id BIGINT PRIMARY KEY COMMENT '主键',
    parent_id BIGINT NULL COMMENT '父主键',
    name VARCHAR(255) DEFAULT '' NOT NULL COMMENT '机构名称',
    type TINYINT(1) DEFAULT 1 NOT NULL COMMENT '机构类型 1 企业',
    sort_order INT DEFAULT 0 COMMENT '排序',
    notes VARCHAR(255) DEFAULT '' NOT NULL COMMENT '备注',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建日期',
    created_by BIGINT COMMENT '创建人',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  NOT NULL COMMENT '修改日期',
    updated_by BIGINT COMMENT '修改人',
    deleted_at TIMESTAMP NULL COMMENT '删除日期',
    deleted_by BIGINT COMMENT '删除人'
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci comment = '机构表';

DROP TABLE IF EXISTS vscing_admin_user_organization;
CREATE TABLE vscing_admin_user_organization (
    admin_user_id BIGINT NOT NULL,
    organization_id BIGINT NOT NULL,
    PRIMARY KEY (admin_user_id, role_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci comment = '用户关联机构表';

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
    publishStatus VARCHAR(255) DEFAULT '' NOT NULL COMMENT '上映类型，HOT为热映，WAIT为待上映',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建日期',
    created_by BIGINT COMMENT '创建人',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  NOT NULL COMMENT '修改日期',
    updated_by BIGINT COMMENT '修改人',
    deleted_at TIMESTAMP NULL COMMENT '删除日期',
    deleted_by BIGINT COMMENT '删除人'
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci comment = '影院表';
