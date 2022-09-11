create database compress;

create table `t_user` (
                          `id` bigint unsigned auto_increment comment 'primary key',
                          `account` varchar(32)  comment 'account',
                          `name` varchar(126) default '' comment 'name',
                          `password` varchar(128) default '' comment 'password',
                          `email` varchar(128) default '' comment 'email',
                          `role` tinyint unsigned default 1 comment 'role 1-normal 2-vip',
                          `status` tinyint unsigned default 0 comment 'status 0-lock 1-normal',
                          `create_time` datetime default current_timestamp comment 'create time',
                          `update_time` datetime default current_timestamp on update current_timestamp comment 'update time',
                          primary key pk_id (`id`),
                          unique key uk_email(`email`),
                          unique key uk_name(`name`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='user table';

create table `t_picture` (
    `id` bigint unsigned auto_increment comment 'primary key',
    `user_id` varchar(32) not null comment 'user id',
    `compress_setting_id` varchar(32) not null comment 'compress setting id',
    `name` varchar(32) not null comment 'name',
    `type` varchar(32) not null  comment 'type',
    `size` bigint not null comment 'size',
    `uid` varchar(32) not null  comment 'uid',
    `pic_data` mediumblob not null comment 'data',
    `create_time` datetime default current_timestamp comment 'create time',
    `update_time` datetime default current_timestamp on update current_timestamp comment 'update time',
    primary key pk_id (`id`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='picture table';

create table `t_weblog` (
                             `id` bigint unsigned auto_increment comment 'primary key',
                             `url` varchar(32) comment 'url',
                             `ip` varchar(32) comment 'ip',
                             `class_info` varchar(32) comment 'class info',
                             `method` varchar(32) comment 'method',
                             `method_name` varchar(32) comment 'methodName',
                             `browser` varchar(32) comment 'browser',
                             `browser_version` varchar(32) comment 'browserVersion',
                             `os` varchar(32)  comment 'os',
                             `take_time` INTEGER comment 'take time',
                             `create_time` datetime default current_timestamp comment 'create time',
                             primary key pk_id (`id`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='weblog table';

create table `t_coupon` (
                            `id` bigint unsigned auto_increment comment '主键',
                            `title` varchar(64) not null comment '优惠券标题',
                            `with_amount` decimal(10, 2) not null default 0 comment '满减金额',
                            `used_amount` decimal(10, 2) not null default 0 comment '优惠金额',
                            `quota` bigint unsigned not null default 0 comment '发券数量',
                            `take_count` bigint unsigned not null default 0 comment '已领取数量',
                            `used_count` bigint unsigned not null default 0 comment '已使用数量',
                            `status` tinyint unsigned not null default 1 comment '状态 1-生效 2-失效',
                            `create_time` datetime default current_timestamp comment '创建时间',
                            `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
                            primary key pk_id (`id`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='优惠券表';


create table `t_coupon_record` (
                                   `id` bigint unsigned auto_increment comment '主键',
                                   `user_id` bigint unsigned not null comment '用户ID',
                                   `coupon_id` bigint unsigned not null comment '优惠券ID',
                                   `status` tinyint default 0 comment '状态 0-已领取未使用 1-已使用 -1为已过期',
                                   `create_time` datetime default current_timestamp comment '创建时间',
                                   `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
                                   primary key pk_id (`id`),
                                   index idx_user_id(`user_id`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='优惠券记录表';