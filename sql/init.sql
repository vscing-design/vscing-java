DROP TABLE IF EXISTS vscing_user;
DROP TABLE IF EXISTS vscing_order;

CREATE TABLE vscing_user (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(20) NOT NULL,
  password VARCHAR(50) NOT NULL,
  phone VARCHAR(20) UNIQUE NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  created_by INT,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_by INT,
  deleted_at TIMESTAMP NULL,
  deleted_by INT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci comment = '用户信息表';

CREATE TABLE vscing_order (
  order_id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  amount DECIMAL(10, 2) NOT NULL,
  status VARCHAR(20) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  created_by INT,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_by INT,
  deleted_at TIMESTAMP NULL,
  deleted_by INT,
  CONSTRAINT fk_user
    FOREIGN KEY (user_id)
    REFERENCES vscing_user(id)
    ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci comment = '订单信息表';

CREATE TABLE vscing_menu (
    id BIGINT PRIMARY KEY COMMENT '主键',
    parent_id BIGINT NULL COMMENT '父主键',
    name VARCHAR(255) DEFAULT '' NOT NULL COMMENT '菜单名称',
    system VARCHAR(50) DEFAULT '' NOT NULL COMMENT '所属系统',
    icon VARCHAR(255) DEFAULT '' NOT NULL COMMENT 'icon',
    component VARCHAR(255) DEFAULT '' NOT NULL COMMENT '前端组件',
    status TINYINT(1) DEFAULT 1 NOT NULL COMMENT '状态 1 启用 2 禁用',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    created_by BIGINT COMMENT '创建人',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改日期',
    updated_by BIGINT COMMENT '修改人',
    deleted_at TIMESTAMP NULL COMMENT '删除日期',
    deleted_by BIGINT COMMENT '删除人',
    CONSTRAINT fk_parent_menu FOREIGN KEY (parent_id) REFERENCES vscing_menu(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci comment = '菜单表';

CREATE TABLE vscing_role (
    id BIGINT PRIMARY KEY COMMENT '主键',
    name VARCHAR(255) DEFAULT '' NOT NULL COMMENT '角色名称',
    sort_order INT DEFAULT 0 COMMENT '排序',
    notes VARCHAR(255) DEFAULT '' NOT NULL COMMENT '备注',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    created_by BIGINT COMMENT '创建人',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改日期',
    updated_by BIGINT COMMENT '修改人',
    deleted_at TIMESTAMP NULL COMMENT '删除日期',
    deleted_by BIGINT COMMENT '删除人',
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci comment = '角色表';

CREATE TABLE vscing_admin_user_role (
    admin_user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (admin_user_id, role_id),
    CONSTRAINT fk_admin_user FOREIGN KEY (admin_user_id) REFERENCES vscing_admin_user(id),
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES vscing_role(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci comment = '用户关联角色表';

CREATE TABLE vscing_roles_menu (
    role_id BIGINT NOT NULL,
    menu_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, menu_id),
    CONSTRAINT fk_role_menu FOREIGN KEY (role_id) REFERENCES vscing_role(id),
    CONSTRAINT fk_menu FOREIGN KEY (menu_id) REFERENCES vscing_menu(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci comment = '角色关联菜单表';
