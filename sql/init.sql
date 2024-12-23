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
