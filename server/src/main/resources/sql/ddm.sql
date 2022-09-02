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

