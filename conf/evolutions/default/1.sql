# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table account (
  id                        bigint auto_increment not null,
  name                      varchar(255) not null,
  username                  varchar(255) not null,
  password                  varchar(255) not null,
  created_timestamp         datetime not null,
  constraint uq_account_1 unique (username),
  constraint pk_account primary key (id))
;

create table cohort (
  cohort_id                 bigint auto_increment not null,
  cohort_name               varchar(255) not null,
  created_by_account_id     bigint not null,
  number_of_vials           integer not null,
  number_of_groups          integer not null,
  status                    varchar(255) not null,
  experiment_experiment_id  bigint not null,
  created_timestamp         datetime not null,
  constraint uq_cohort_1 unique (cohort_name),
  constraint pk_cohort primary key (cohort_id))
;

create table experiment (
  experiment_id             bigint auto_increment not null,
  experiment_name           varchar(255) not null,
  constraint uq_experiment_1 unique (experiment_name),
  constraint pk_experiment primary key (experiment_id))
;

create table video (
  video_id                  bigint auto_increment not null,
  video_name                varchar(255) not null,
  cohort_cohort_id          bigint not null,
  video_blob                varchar(255),
  uploaded_timestap         datetime not null,
  constraint pk_video primary key (video_id))
;

alter table cohort add constraint fk_cohort_created_by_account_1 foreign key (created_by_account_id) references account (id) on delete restrict on update restrict;
create index ix_cohort_created_by_account_1 on cohort (created_by_account_id);
alter table cohort add constraint fk_cohort_experiment_2 foreign key (experiment_experiment_id) references experiment (experiment_id) on delete restrict on update restrict;
create index ix_cohort_experiment_2 on cohort (experiment_experiment_id);
alter table video add constraint fk_video_cohort_3 foreign key (cohort_cohort_id) references cohort (cohort_id) on delete restrict on update restrict;
create index ix_video_cohort_3 on video (cohort_cohort_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table account;

drop table cohort;

drop table experiment;

drop table video;

SET FOREIGN_KEY_CHECKS=1;

