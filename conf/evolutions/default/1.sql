# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table account (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  username                  varchar(255),
  password                  varchar(255),
  created_timestamp         datetime,
  constraint uq_account_1 unique (username),
  constraint pk_account primary key (id))
;

create table cohort (
  cohort_id                 bigint auto_increment not null,
  cohort_name               varchar(255),
  created_by_user           varchar(255),
  number_of_vials           integer,
  number_of_groups          integer,
  status                    varchar(255),
  experiment_id             bigint,
  created_timestamp         datetime,
  constraint uq_cohort_1 unique (cohort_name),
  constraint pk_cohort primary key (cohort_id))
;

create table experiment (
  experiment_id             bigint auto_increment not null,
  experiment_name           varchar(255),
  constraint uq_experiment_1 unique (experiment_name),
  constraint pk_experiment primary key (experiment_id))
;

create table video (
  video_id                  bigint auto_increment not null,
  video_name                varchar(255),
  cohort_id                 bigint,
  uploaded_timestap         datetime,
  video_blob                varchar(255),
  constraint pk_video primary key (video_id))
;

alter table cohort add constraint fk_cohort_created_by_account_1 foreign key (created_by_user) references account (username) on delete restrict on update restrict;
create index ix_cohort_created_by_account_1 on cohort (created_by_user);
alter table cohort add constraint fk_cohort_experiment_2 foreign key (experiment_id) references experiment (experiment_id) on delete restrict on update restrict;
create index ix_cohort_experiment_2 on cohort (experiment_id);
alter table video add constraint fk_video_cohort_3 foreign key (cohort_id) references cohort (cohort_id) on delete restrict on update restrict;
create index ix_video_cohort_3 on video (cohort_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table account;

drop table cohort;

drop table experiment;

drop table video;

SET FOREIGN_KEY_CHECKS=1;

