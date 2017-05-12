----------------------------------------------
-- Export file for user EPEI@XE             --
-- Created by liubin on 2015/11/8, 15:01:06 --
----------------------------------------------

create table CMS_CONTENT_BODY
(
  id   NUMBER(19) not null,
  body VARCHAR2(255 CHAR)
)
;
alter table CMS_CONTENT_BODY
  add primary key (ID);

create table CMS_CONTENT_TYPE
(
  id       NUMBER(19) not null,
  code     VARCHAR2(255 CHAR),
  comments VARCHAR2(255 CHAR),
  name     VARCHAR2(255 CHAR),
  path     VARCHAR2(255 CHAR),
  parentid NUMBER(19)
)
;
alter table CMS_CONTENT_TYPE
  add primary key (ID);
alter table CMS_CONTENT_TYPE
  add constraint FK_1FLE5QG6PQ57V84IGWJPTTQ97 foreign key (PARENTID)
  references CMS_CONTENT_TYPE (ID);

create table CMS_CONTENT
(
  id          NUMBER(19) not null,
  author      VARCHAR2(255 CHAR),
  belong_code VARCHAR2(255 CHAR),
  comments    VARCHAR2(255 CHAR),
  cover       VARCHAR2(255 CHAR),
  hits        NUMBER(19),
  keycode     VARCHAR2(255 CHAR),
  keywords    VARCHAR2(255 CHAR),
  pub_date    TIMESTAMP(6),
  status      NUMBER(10) not null,
  subject     VARCHAR2(255 CHAR),
  title       VARCHAR2(255 CHAR),
  type        NUMBER(19)
)
;
alter table CMS_CONTENT
  add primary key (ID);
alter table CMS_CONTENT
  add constraint FK_5MXQN78S2E5VNDDF90A91TYPQ foreign key (TYPE)
  references CMS_CONTENT_TYPE (ID);
alter table CMS_CONTENT
  add constraint FK_NJPL6C748TB7DNYBVIPNWPBUP foreign key (ID)
  references CMS_CONTENT_BODY (ID);

create table CMS_ATTACHMENT
(
  id        NUMBER(19) not null,
  comments  VARCHAR2(255 CHAR),
  file_name VARCHAR2(255 CHAR),
  file_path VARCHAR2(255 CHAR),
  file_size NUMBER(19),
  contentid NUMBER(19)
)
;
alter table CMS_ATTACHMENT
  add primary key (ID);
alter table CMS_ATTACHMENT
  add constraint FK_EPGDLCI6R1AW24VSG7BRJ5PBF foreign key (CONTENTID)
  references CMS_CONTENT (ID);

create table EP_COUPON
(
  id                 NUMBER(20) not null,
  coupon_type        VARCHAR2(16) not null,
  use_target         VARCHAR2(16) not null,
  customer_id        NUMBER(20),
  used               NUMBER(1),
  use_time           DATE,
  denomination       NUMBER(4),
  expiry_date        DATE,
  create_time        DATE not null,
  update_time        DATE,
  customer_user_name VARCHAR2(32)
)
;
comment on column EP_COUPON.coupon_type
  is '优惠券类型';
comment on column EP_COUPON.use_target
  is '优惠券使用目标';
comment on column EP_COUPON.customer_id
  is '所属会员';
comment on column EP_COUPON.used
  is '是否已使用';
comment on column EP_COUPON.use_time
  is '使用时间';
comment on column EP_COUPON.denomination
  is '面额';
comment on column EP_COUPON.expiry_date
  is '到期日';
comment on column EP_COUPON.create_time
  is '生成时间';
comment on column EP_COUPON.update_time
  is '更新时间';
comment on column EP_COUPON.customer_user_name
  is '会员账号';
alter table EP_COUPON
  add constraint EP_COUPON_PRIMARY primary key (ID);

create table EP_CUSTOMER
(
  id          NUMBER(20) not null,
  cus_no      NUMBER(12) not null,
  name        VARCHAR2(32) not null,
  user_name   VARCHAR2(32) not null,
  password    VARCHAR2(64) not null,
  reg_origin  VARCHAR2(16) not null,
  deleted     NUMBER(1),
  create_time DATE,
  update_time DATE,
  mobile      VARCHAR2(16) not null,
  salt        VARCHAR2(32)
)
;
comment on table EP_CUSTOMER
  is '会员';
comment on column EP_CUSTOMER.id
  is '主键';
comment on column EP_CUSTOMER.cus_no
  is '会员编号';
comment on column EP_CUSTOMER.name
  is '姓名';
comment on column EP_CUSTOMER.user_name
  is '账户';
comment on column EP_CUSTOMER.password
  is '密码';
comment on column EP_CUSTOMER.reg_origin
  is '注册来源';
comment on column EP_CUSTOMER.deleted
  is '删除';
comment on column EP_CUSTOMER.create_time
  is '注册时间';
comment on column EP_CUSTOMER.update_time
  is '更新时间';
comment on column EP_CUSTOMER.mobile
  is '手机号';
alter table EP_CUSTOMER
  add constraint PRIMARYEP_CUSTOMER1 primary key (ID);

create table EP_CUSTOMER_NO
(
  id NUMBER(20) not null
)
;
alter table EP_CUSTOMER_NO
  add constraint CUSTOMER_NO_PRIMARY primary key (ID);

create table EP_DEPARTMENT
(
  id          NUMBER(20) not null,
  name        VARCHAR2(64) not null,
  deleted     NUMBER(1),
  create_time DATE,
  update_time DATE
)
;
comment on table EP_DEPARTMENT
  is '医院科室';
comment on column EP_DEPARTMENT.id
  is '主键';
comment on column EP_DEPARTMENT.name
  is '科室';
comment on column EP_DEPARTMENT.deleted
  is '删除';
comment on column EP_DEPARTMENT.create_time
  is '创建时间';
comment on column EP_DEPARTMENT.update_time
  is '更新时间';
create index IDX_UNIQUE_NAMEEP_DEPARTMENT on EP_DEPARTMENT (NAME);
alter table EP_DEPARTMENT
  add constraint PRIMARYEP_DEPARTMENT1 primary key (ID);

create table EP_HOSPITAL
(
  id                 NUMBER(20) not null,
  name               VARCHAR2(64) not null,
  reception_position VARCHAR2(128),
  pz_orig_price      NUMBER(6,2),
  pz_current_price   NUMBER(6,2),
  ph_orig_price      NUMBER(6,2),
  ph_current_price   NUMBER(6,2),
  deleted            NUMBER(1),
  create_time        DATE,
  update_time        DATE,
  logo_image_id      NUMBER(20)
)
;
comment on table EP_HOSPITAL
  is '医院';
comment on column EP_HOSPITAL.id
  is '主键';
comment on column EP_HOSPITAL.name
  is '医院名';
comment on column EP_HOSPITAL.reception_position
  is '接待地点';
comment on column EP_HOSPITAL.pz_orig_price
  is '陪诊原价';
comment on column EP_HOSPITAL.pz_current_price
  is '陪诊现价';
comment on column EP_HOSPITAL.ph_orig_price
  is '陪护原价';
comment on column EP_HOSPITAL.ph_current_price
  is '陪护现价';
comment on column EP_HOSPITAL.deleted
  is '删除';
comment on column EP_HOSPITAL.create_time
  is '创建时间';
comment on column EP_HOSPITAL.update_time
  is '更新时间';
comment on column EP_HOSPITAL.logo_image_id
  is '医院LOGO';
create unique index IDX_UNIQUE_NAMEEP_HOSPITAL on EP_HOSPITAL (NAME);
alter table EP_HOSPITAL
  add constraint PRIMARYEP_HOSPITAL1 primary key (ID);

create table EP_IMAGE
(
  id        NUMBER(20) not null,
  ext       VARCHAR2(6),
  file_name VARCHAR2(64),
  file_size NUMBER(8),
  content   BLOB
)
;
comment on column EP_IMAGE.ext
  is '扩展名';
comment on column EP_IMAGE.file_name
  is '文件名';
comment on column EP_IMAGE.file_size
  is '大小';
comment on column EP_IMAGE.content
  is '内容';
alter table EP_IMAGE
  add constraint PRIMARYEP_EP_IMAGE1 primary key (ID);

create table EP_ORDER_BASE
(
  id              NUMBER(20) not null,
  order_no        VARCHAR2(20) not null,
  order_type      VARCHAR2(8) not null,
  patient_name    VARCHAR2(32) not null,
  patient_id_card VARCHAR2(20),
  patient_id      NUMBER(20) not null,
  cus_no          NUMBER(10),
  hospital_name   VARCHAR2(64) not null,
  hospital_id     NUMBER(20) not null,
  order_time      DATE not null,
  orign           VARCHAR2(16),
  create_time     DATE,
  update_time     DATE,
  state           VARCHAR2(16),
  patient_mobile  VARCHAR2(20)
)
;
comment on table EP_ORDER_BASE
  is '订单基础信息';
comment on column EP_ORDER_BASE.id
  is '主键';
comment on column EP_ORDER_BASE.order_no
  is '订单编号';
comment on column EP_ORDER_BASE.order_type
  is '订单类型';
comment on column EP_ORDER_BASE.patient_name
  is '病患姓名';
comment on column EP_ORDER_BASE.patient_id_card
  is '病患身份证号';
comment on column EP_ORDER_BASE.patient_id
  is '病患id';
comment on column EP_ORDER_BASE.cus_no
  is '会员编号';
comment on column EP_ORDER_BASE.hospital_name
  is '就诊医院';
comment on column EP_ORDER_BASE.hospital_id
  is '医院id';
comment on column EP_ORDER_BASE.order_time
  is '预约时间';
comment on column EP_ORDER_BASE.orign
  is '预约来源';
comment on column EP_ORDER_BASE.create_time
  is '创建时间';
comment on column EP_ORDER_BASE.update_time
  is '更新时间';
comment on column EP_ORDER_BASE.state
  is '订单状态';
comment on column EP_ORDER_BASE.patient_mobile
  is '病患电话';
create index IDX_FK_OEDER_HOSPITALEP_ORDER_ on EP_ORDER_BASE (HOSPITAL_ID);
create index IDX_FK_ORDER_PATIENTEP_ORDER_B on EP_ORDER_BASE (PATIENT_ID);
alter table EP_ORDER_BASE
  add constraint PRIMARYEP_ORDER_BASE1 primary key (ID);

create table EP_ORDER_PH
(
  id                NUMBER(20) not null,
  service_person    VARCHAR2(32),
  service_person_id NUMBER(20),
  start_time        DATE,
  end_time          DATE,
  duration          NUMBER
)
;
comment on table EP_ORDER_PH
  is '陪护订单';
comment on column EP_ORDER_PH.id
  is '主键';
comment on column EP_ORDER_PH.service_person
  is '陪护人';
comment on column EP_ORDER_PH.service_person_id
  is '陪护人id';
comment on column EP_ORDER_PH.start_time
  is '开始时间';
comment on column EP_ORDER_PH.end_time
  is '结束时间';
comment on column EP_ORDER_PH.duration
  is '持续时长';
alter table EP_ORDER_PH
  add constraint PRIMARYEP_ORDER_PH1 primary key (ID);

create table EP_ORDER_PZ
(
  id                 NUMBER(20) not null,
  pz_mode            VARCHAR2(16),
  department         VARCHAR2(64),
  reception_position VARCHAR2(64)
)
;
comment on table EP_ORDER_PZ
  is '陪诊订单';
comment on column EP_ORDER_PZ.id
  is '主键';
comment on column EP_ORDER_PZ.pz_mode
  is '陪诊模式';
comment on column EP_ORDER_PZ.department
  is '就诊科室';
comment on column EP_ORDER_PZ.reception_position
  is '接待地点';
alter table EP_ORDER_PZ
  add constraint PRIMARYEP_ORDER_PZ1 primary key (ID);

create table EP_ORDER_PZ_DETAIL
(
  id                NUMBER(20) not null,
  service_name      VARCHAR2(32),
  service_id        NUMBER(20),
  service_person    VARCHAR2(32),
  service_person_id NUMBER(20),
  order_id          NUMBER(20),
  deleted           NUMBER(1),
  create_time       DATE,
  update_time       DATE,
  remark            VARCHAR2(128)
)
;
comment on table EP_ORDER_PZ_DETAIL
  is '陪诊详情';
comment on column EP_ORDER_PZ_DETAIL.id
  is '主键';
comment on column EP_ORDER_PZ_DETAIL.service_name
  is '陪诊服务';
comment on column EP_ORDER_PZ_DETAIL.service_id
  is '服务id';
comment on column EP_ORDER_PZ_DETAIL.service_person
  is '陪诊人员';
comment on column EP_ORDER_PZ_DETAIL.service_person_id
  is '陪诊人员id';
comment on column EP_ORDER_PZ_DETAIL.order_id
  is '陪诊订单id';
comment on column EP_ORDER_PZ_DETAIL.deleted
  is '删除';
comment on column EP_ORDER_PZ_DETAIL.create_time
  is '创建时间';
comment on column EP_ORDER_PZ_DETAIL.update_time
  is '更新时间';
comment on column EP_ORDER_PZ_DETAIL.remark
  is '备注';
create index IDX_FK_ORDER_DETAIL_SERVICEEP_ on EP_ORDER_PZ_DETAIL (SERVICE_ID);
create index IDX_FK_ORDER_DETAIL_SERVICE_PE on EP_ORDER_PZ_DETAIL (SERVICE_PERSON_ID);
create index IDX_FK_ORDER_PZEP_ORDER_PZ_DET on EP_ORDER_PZ_DETAIL (ORDER_ID);
alter table EP_ORDER_PZ_DETAIL
  add constraint PRIMARYEP_ORDER_PZ_DETAIL1 primary key (ID);

create table EP_PATIENT
(
  id            NUMBER(20) not null,
  cus_name      VARCHAR2(32),
  cus_no        NUMBER(12),
  cus_id        NUMBER(20),
  name          VARCHAR2(32) not null,
  mobile        VARCHAR2(16) not null,
  id_card       VARCHAR2(32) not null,
  birthday      VARCHAR2(32) not null,
  gender        NUMBER(1) not null,
  medicare_card VARCHAR2(32),
  deleted       NUMBER(1),
  create_time   DATE,
  update_time   DATE
)
;
comment on table EP_PATIENT
  is '病患';
comment on column EP_PATIENT.id
  is '主键';
comment on column EP_PATIENT.cus_name
  is '所属会员姓名';
comment on column EP_PATIENT.cus_no
  is '所属会员编号';
comment on column EP_PATIENT.cus_id
  is '所属会员id';
comment on column EP_PATIENT.name
  is '姓名';
comment on column EP_PATIENT.mobile
  is '手机号';
comment on column EP_PATIENT.id_card
  is '身份证号';
comment on column EP_PATIENT.birthday
  is '生日';
comment on column EP_PATIENT.gender
  is '性别 男 1 女 0';
comment on column EP_PATIENT.medicare_card
  is '医保卡号';
comment on column EP_PATIENT.deleted
  is '删除';
comment on column EP_PATIENT.create_time
  is '创建时间';
comment on column EP_PATIENT.update_time
  is '更新时间';
create index IDX_FK_PETIENT_CUSTOMEREP_PATI on EP_PATIENT (CUS_ID);
alter table EP_PATIENT
  add constraint PRIMARYEP_PATIENT1 primary key (ID);

create table EP_PZ_SERVICE
(
  id          NUMBER(20) not null,
  service     VARCHAR2(64) not null,
  deleted     NUMBER(1),
  create_time DATE,
  update_time DATE
)
;
comment on table EP_PZ_SERVICE
  is '陪诊服务';
comment on column EP_PZ_SERVICE.id
  is '主键';
comment on column EP_PZ_SERVICE.service
  is '服务项';
comment on column EP_PZ_SERVICE.deleted
  is '删除';
comment on column EP_PZ_SERVICE.create_time
  is '创建时间';
comment on column EP_PZ_SERVICE.update_time
  is '更新时间';
alter table EP_PZ_SERVICE
  add constraint PRIMARYEP_PZ_SERVICE1 primary key (ID);

create table EP_SERVICE_PERSON
(
  id           NUMBER(20) not null,
  name         VARCHAR2(32),
  mobile       NUMBER(16),
  id_card      VARCHAR2(20),
  hospital     VARCHAR2(64),
  hospital_id  NUMBER(20),
  service_type VARCHAR2(4),
  deleted      NUMBER(1),
  create_time  DATE,
  update_time  DATE
)
;
comment on table EP_SERVICE_PERSON
  is '陪护陪诊人员';
comment on column EP_SERVICE_PERSON.id
  is '主键';
comment on column EP_SERVICE_PERSON.name
  is '姓名';
comment on column EP_SERVICE_PERSON.mobile
  is '手机号';
comment on column EP_SERVICE_PERSON.id_card
  is '身份证号';
comment on column EP_SERVICE_PERSON.hospital
  is '所属医院';
comment on column EP_SERVICE_PERSON.hospital_id
  is '医院id';
comment on column EP_SERVICE_PERSON.service_type
  is '服务类型';
comment on column EP_SERVICE_PERSON.deleted
  is '删除';
comment on column EP_SERVICE_PERSON.create_time
  is '创建时间';
comment on column EP_SERVICE_PERSON.update_time
  is '更新时间';
create index IDX_FK_SERVICE_PERSON_HOSPITAL on EP_SERVICE_PERSON (HOSPITAL_ID);
alter table EP_SERVICE_PERSON
  add constraint PRIMARYEP_SERVICE_PERSON1 primary key (ID);

create table SYS_CONFIG
(
  id       NUMBER not null,
  title    VARCHAR2(64),
  name     VARCHAR2(64),
  value    VARCHAR2(512),
  comments VARCHAR2(512)
)
;
alter table SYS_CONFIG
  add constraint PRIMARYSYS_CONFIG1 primary key (ID);

create table SYS_OFILE
(
  id            NUMBER not null,
  comments      VARCHAR2(255),
  create_time   DATE,
  file_ext      VARCHAR2(8),
  file_name     VARCHAR2(64),
  file_path     VARCHAR2(128),
  file_size     NUMBER,
  file_type     VARCHAR2(16),
  metadatas     VARCHAR2(255),
  module        VARCHAR2(16),
  object_id     VARCHAR2(64),
  original_name VARCHAR2(64),
  status        VARCHAR2(16),
  thumbnail     VARCHAR2(128),
  update_time   DATE,
  user_name     VARCHAR2(32),
  input_name    VARCHAR2(64)
)
;
comment on column SYS_OFILE.input_name
  is '表单名称';
alter table SYS_OFILE
  add constraint PRIMARYONLINE_FILE1 primary key (ID);

create table SYS_OLOG
(
  id                   NUMBER not null,
  module               VARCHAR2(256),
  module_name          VARCHAR2(256),
  action               VARCHAR2(32),
  action_name          VARCHAR2(32),
  execute_milliseconds NUMBER,
  operate_time         DATE,
  operate_user         VARCHAR2(64),
  operate_user_id      NUMBER,
  request_parameters   VARCHAR2(512),
  operate_result       NUMBER,
  operate_message      VARCHAR2(512),
  client_informations  VARCHAR2(256),
  descn                VARCHAR2(256)
)
;
alter table SYS_OLOG
  add constraint PRIMARYSYS_OLOG1 primary key (ID);

create table SYS_PORTALLET
(
  id          NUMBER not null,
  title       VARCHAR2(64),
  user_name   VARCHAR2(32),
  width       NUMBER,
  height      NUMBER,
  collapsible NUMBER,
  load_mode   NUMBER,
  show_mode   NUMBER,
  href        VARCHAR2(128),
  content     VARCHAR2(4000)
)
;
alter table SYS_PORTALLET
  add constraint PRIMARYSYS_PORTALLET1 primary key (ID);

create table SYS_RESOURCE
(
  id         NUMBER not null,
  parentid   NUMBER,
  name       VARCHAR2(64),
  type       VARCHAR2(64),
  show_state NUMBER,
  order_time DATE,
  value      VARCHAR2(256),
  show_mode  NUMBER,
  icon       VARCHAR2(64),
  descn      VARCHAR2(256)
)
;
alter table SYS_RESOURCE
  add constraint PRIMARYSYS_RESOURCE1 primary key (ID);
alter table SYS_RESOURCE
  add constraint FK_OERA7PKMUHK3NL9PK7CUMEY5L foreign key (PARENTID)
  references SYS_RESOURCE (ID);

create table SYS_ROLE
(
  id     NUMBER not null,
  name   VARCHAR2(64),
  org_id NUMBER,
  descn  VARCHAR2(256)
)
;
alter table SYS_ROLE
  add constraint PRIMARYSYS_ROLE1 primary key (ID);

create table SYS_ROLE_RESC
(
  role_id NUMBER not null,
  resc_id NUMBER not null
)
;
create index IDX_FK_SYS_ROLE_RESC_SYS_RESOU on SYS_ROLE_RESC (RESC_ID);
alter table SYS_ROLE_RESC
  add constraint PRIMARYSYS_ROLE_RESC1 primary key (ROLE_ID, RESC_ID);
alter table SYS_ROLE_RESC
  add constraint FK_84OH89HN30LF49605XUNFT0W7 foreign key (RESC_ID)
  references SYS_RESOURCE (ID);
alter table SYS_ROLE_RESC
  add constraint FK_KNJKVPD446U0QLD2T6EW9LQOK foreign key (ROLE_ID)
  references SYS_ROLE (ID);

create table SYS_USER
(
  id               NUMBER not null,
  username         VARCHAR2(16),
  real_name        VARCHAR2(32),
  password         VARCHAR2(128),
  salt             VARCHAR2(32),
  user_type        NUMBER,
  email            VARCHAR2(64),
  mobile_no        VARCHAR2(32),
  org_id           NUMBER,
  org_name         VARCHAR2(16),
  create_time      DATE,
  last_modify_time DATE,
  expiration_time  DATE,
  unlock_time      DATE,
  login_status     NUMBER,
  login_fail_times NUMBER,
  login_time       DATE,
  status           NUMBER,
  descn            VARCHAR2(256)
)
;
alter table SYS_USER
  add constraint PRIMARYSYS_USER1 primary key (ID);

create table SYS_USER_ROLE
(
  role_id NUMBER not null,
  user_id NUMBER not null
)
;
create index IDX_FK_SYS_USER_ROLE_SYS_USERS on SYS_USER_ROLE (USER_ID);
alter table SYS_USER_ROLE
  add constraint PRIMARYSYS_USER_ROLE1 primary key (ROLE_ID, USER_ID);
alter table SYS_USER_ROLE
  add constraint FK_FETHVR269T6STIVLDDBO5PXRY foreign key (USER_ID)
  references SYS_USER (ID);
alter table SYS_USER_ROLE
  add constraint FK_FXU3TD9M5O7QOV1KBDVMN0G0X foreign key (ROLE_ID)
  references SYS_ROLE (ID);

  CREATE SEQUENCE "SEQ_EP_CUSTOMER"
INCREMENT BY 1
START WITH 1
 MAXVALUE 9999999999
 MINVALUE 1
CYCLE
 CACHE 20;

CREATE SEQUENCE "SEQ_EP_HOSPITAL"
INCREMENT BY 1
START WITH 1
 MAXVALUE 9999999999
 MINVALUE 1
CYCLE
 CACHE 20;


 CREATE SEQUENCE "SEQ_EP_PZ_SERVICE"
INCREMENT BY 1
START WITH 1
 MAXVALUE 9999999999
 MINVALUE 1
CYCLE
 CACHE 20;

 CREATE SEQUENCE "SEQ_EP_IMAGE"
INCREMENT BY 1
START WITH 1
 MAXVALUE 9999999999
 MINVALUE 1
CYCLE
 CACHE 20;

CREATE SEQUENCE "SEQ_EP_SERVICE_PERSON"
INCREMENT BY 1
START WITH 1
 MAXVALUE 9999999999
 MINVALUE 1
CYCLE
 CACHE 20;

CREATE SEQUENCE "SEQ_EP_PATIENT"
INCREMENT BY 1
START WITH 1
 MAXVALUE 9999999999
 MINVALUE 1
CYCLE
 CACHE 20;

 CREATE SEQUENCE "SEQ_CUS_NO"
INCREMENT BY 1
START WITH 26323
 MAXVALUE 9999999999
 MINVALUE 1
CYCLE
 CACHE 20;

CREATE SEQUENCE "SEQ_EP_COUPON"
INCREMENT BY 1
START WITH 1
 MAXVALUE 9999999999
 MINVALUE 1
CYCLE
 CACHE 20;

 CREATE SEQUENCE "HIBERNATE_SEQUENCE"
INCREMENT BY 1
START WITH 1
 MAXVALUE 9999999999
 MINVALUE 1
CYCLE
 CACHE 20;


insert into SYS_RESOURCE (id, parentid, name, type, show_state, order_time, value, show_mode, icon, descn)
values (20151025, null, '内容管理', 'MENU', 0, to_date('23-10-2015 18:32:05', 'dd-mm-yyyy hh24:mi:ss'), null, 1, 'icons-resource-caozuorizhichaxun', null);
insert into SYS_RESOURCE (id, parentid, name, type, show_state, order_time, value, show_mode, icon, descn)
values (201510251, 20151025, '类型管理', 'URL', 0, to_date('25-10-2015 01:14:31', 'dd-mm-yyyy hh24:mi:ss'), '/manage/module/feature/cms/contentType/index.html', 1, 'icons-resource-iconfontcolor28', null);
insert into SYS_RESOURCE (id, parentid, name, type, show_state, order_time, value, show_mode, icon, descn)
values (201510252, 20151025, '服务协议', 'URL', 0, to_date('25-10-2015 05:14:39', 'dd-mm-yyyy hh24:mi:ss'), '/manage/module/feature/cms/content/index.html?code=agreement', 1, 'icons-resource-hezuo', null);
insert into SYS_RESOURCE (id, parentid, name, type, show_state, order_time, value, show_mode, icon, descn)
values (201510253, 20151025, '关于我们', 'URL', 0, to_date('25-10-2015 07:14:41', 'dd-mm-yyyy hh24:mi:ss'), '/manage/module/feature/cms/content/index.html?code=aboutus', 1, 'icons-resource-guanyuwomen1', null);
insert into SYS_RESOURCE (id, parentid, name, type, show_state, order_time, value, show_mode, icon, descn)
values (201510254, 20151025, '联系我们', 'URL', 0, to_date('25-10-2015 06:14:41', 'dd-mm-yyyy hh24:mi:ss'), '/manage/module/feature/cms/content/index.html?code=contactus', 1, 'icons-resource-6', null);
insert into SYS_RESOURCE (id, parentid, name, type, show_state, order_time, value, show_mode, icon, descn)
values (201510255, 20151025, '常见问题', 'URL', 0, to_date('25-10-2015 04:14:41', 'dd-mm-yyyy hh24:mi:ss'), '/manage/module/feature/cms/content/index.html?code=faq', 1, 'icons-resource-changjianwenti', null);
insert into SYS_RESOURCE (id, parentid, name, type, show_state, order_time, value, show_mode, icon, descn)
values (201510256, 20151025, '新闻动态', 'URL', 0, to_date('25-10-2015 03:14:40', 'dd-mm-yyyy hh24:mi:ss'), '/manage/module/feature/cms/content/index.html?code=news', 1, 'icons-resource-anli1', null);
insert into SYS_RESOURCE (id, parentid, name, type, show_state, order_time, value, show_mode, icon, descn)
values (201510257, 20151025, '最新公共', 'URL', 0, to_date('25-10-2015 02:14:40', 'dd-mm-yyyy hh24:mi:ss'), '/manage/module/feature/cms/content/index.html?code=notice', 1, 'icons-resource-tiyanbiao', null);
insert into SYS_RESOURCE (id, parentid, name, type, show_state, order_time, value, show_mode, icon, descn)
values (441, null, '业务管理', 'MENU', 0, to_date('23-10-2015 18:32:06', 'dd-mm-yyyy hh24:mi:ss'), null, 1, 'icons-resource-meiyuehuankuan1', null);
insert into SYS_RESOURCE (id, parentid, name, type, show_state, order_time, value, show_mode, icon, descn)
values (23, 441, '会员管理', 'URL', 0, to_date('21-10-2015 20:44:35', 'dd-mm-yyyy hh24:mi:ss'), '/manage/epei/customer/index.html', 1, 'icons-resource-admin', null);
insert into SYS_RESOURCE (id, parentid, name, type, show_state, order_time, value, show_mode, icon, descn)
values (41, 441, '医院管理', 'URL', 0, to_date('23-10-2015 18:32:06', 'dd-mm-yyyy hh24:mi:ss'), '/manage/epei/hospital/index.html', 1, 'icons-resource-heart', null);
insert into SYS_RESOURCE (id, parentid, name, type, show_state, order_time, value, show_mode, icon, descn)
values (92, 441, '科室管理', 'URL', 0, to_date('23-10-2015 18:32:05', 'dd-mm-yyyy hh24:mi:ss'), '/manage/epei/department/index.html', 1, 'icons-resource-computer', null);
insert into SYS_RESOURCE (id, parentid, name, type, show_state, order_time, value, show_mode, icon, descn)
values (442, 441, '陪护订单管理', 'URL', 0, to_date('26-10-2015 21:45:48', 'dd-mm-yyyy hh24:mi:ss'), '/manage/epei/orderPh/index.html', 1, 'icons-resource-bianmin', null);
insert into SYS_RESOURCE (id, parentid, name, type, show_state, order_time, value, show_mode, icon, descn)
values (443, 441, '陪诊订单管理', 'URL', 0, to_date('26-10-2015 21:46:35', 'dd-mm-yyyy hh24:mi:ss'), '/manage/epei/orderPz/index.html', 1, 'icons-resource-bianmin', null);
insert into SYS_RESOURCE (id, parentid, name, type, show_state, order_time, value, show_mode, icon, descn)
values (1, null, '系统管理', 'MENU', 0, to_date('07-01-2014', 'dd-mm-yyyy'), null, 1, 'icons-resource-shezhi', null);
insert into SYS_RESOURCE (id, parentid, name, type, show_state, order_time, value, show_mode, icon, descn)
values (2, 1, '账户管理', 'URL', 0, to_date('10-01-2014 00:00:02', 'dd-mm-yyyy hh24:mi:ss'), '/manage/system/user/index.html', 1, 'icons-resource-kehuguanli', null);
insert into SYS_RESOURCE (id, parentid, name, type, show_state, order_time, value, show_mode, icon, descn)
values (3, 1, '角色管理', 'URL', 0, to_date('10-01-2014 00:00:01', 'dd-mm-yyyy hh24:mi:ss'), '/manage/system/role/index.html', 1, 'icons-resource-guanyuwomen', null);
insert into SYS_RESOURCE (id, parentid, name, type, show_state, order_time, value, show_mode, icon, descn)
values (4, 1, '资源菜单', 'URL', 0, to_date('10-01-2014', 'dd-mm-yyyy'), '/manage/system/resource/index.html', 1, 'icons-resource-anli', null);
insert into SYS_RESOURCE (id, parentid, name, type, show_state, order_time, value, show_mode, icon, descn)
values (5, 1, '操作日志', 'URL', 0, to_date('08-01-2014 02:36:49', 'dd-mm-yyyy hh24:mi:ss'), '/manage/module/olog/olog/index.html', 1, 'icons-resource-jiekuan1', null);
insert into SYS_RESOURCE (id, parentid, name, type, show_state, order_time, value, show_mode, icon, descn)
values (6, 1, '系统监控', 'URL', 0, to_date('07-01-2014 00:00:01', 'dd-mm-yyyy hh24:mi:ss'), '/druid/index.html', 2, 'icons-resource-mac', null);
insert into SYS_RESOURCE (id, parentid, name, type, show_state, order_time, value, show_mode, icon, descn)
values (7, 1, '桌面管理', 'URL', 0, to_date('08-01-2014 02:39:40', 'dd-mm-yyyy hh24:mi:ss'), '/manage/system/portallet/index.html', 1, 'icons-resource-wangzhan1', null);
insert into SYS_RESOURCE (id, parentid, name, type, show_state, order_time, value, show_mode, icon, descn)
values (207, 441, '陪诊服务管理', 'URL', 0, to_date('23-10-2015 18:32:04', 'dd-mm-yyyy hh24:mi:ss'), '/manage/epei/pzService/index.html', 1, 'icons-resource-hudongchoujiang', null);
insert into SYS_RESOURCE (id, parentid, name, type, show_state, order_time, value, show_mode, icon, descn)
values (226, 441, '病患管理', 'URL', 0, to_date('21-10-2015 20:44:34', 'dd-mm-yyyy hh24:mi:ss'), '/manage/epei/patient/index.html', 1, 'icons-resource-group', null);
insert into SYS_RESOURCE (id, parentid, name, type, show_state, order_time, value, show_mode, icon, descn)
values (229, 441, '陪诊人员管理', 'URL', 0, to_date('21-10-2015 20:44:35', 'dd-mm-yyyy hh24:mi:ss'), '/manage/epei/servicePerson/pzPerson.html', 1, 'icons-resource-admin', null);
insert into SYS_RESOURCE (id, parentid, name, type, show_state, order_time, value, show_mode, icon, descn)
values (231, 441, '陪护人员管理', 'URL', 0, to_date('21-10-2015 20:44:58', 'dd-mm-yyyy hh24:mi:ss'), '/manage/epei/servicePerson/phPerson.html', 1, 'icons-resource-admin', null);
insert into SYS_RESOURCE (id, parentid, name, type, show_state, order_time, value, show_mode, icon, descn)
values (481, 441, '优惠券管理', 'URL', 0, to_date('31-10-2015 23:54:07', 'dd-mm-yyyy hh24:mi:ss'), '/manage/epei/coupon/index.html', 1, 'icons-resource-iconfontliushuizijin', null);


insert into SYS_ROLE (id, name, org_id, descn)
values (1, 'ROLE_SYSTEM', null, '系统管理角色');


insert into SYS_ROLE_RESC (role_id, resc_id)
values (1, 1);
insert into SYS_ROLE_RESC (role_id, resc_id)
values (1, 2);
insert into SYS_ROLE_RESC (role_id, resc_id)
values (1, 3);
insert into SYS_ROLE_RESC (role_id, resc_id)
values (1, 4);
insert into SYS_ROLE_RESC (role_id, resc_id)
values (1, 5);
/*insert into SYS_ROLE_RESC (role_id, resc_id)
values (1, 6);
insert into SYS_ROLE_RESC (role_id, resc_id)
values (1, 7);*/
insert into SYS_ROLE_RESC (role_id, resc_id)
values (1, 23);
insert into SYS_ROLE_RESC (role_id, resc_id)
values (1, 41);
insert into SYS_ROLE_RESC (role_id, resc_id)
values (1, 92);
insert into SYS_ROLE_RESC (role_id, resc_id)
values (1, 207);
insert into SYS_ROLE_RESC (role_id, resc_id)
values (1, 226);
insert into SYS_ROLE_RESC (role_id, resc_id)
values (1, 229);
insert into SYS_ROLE_RESC (role_id, resc_id)
values (1, 231);
insert into SYS_ROLE_RESC (role_id, resc_id)
values (1, 441);
insert into SYS_ROLE_RESC (role_id, resc_id)
values (1, 442);
insert into SYS_ROLE_RESC (role_id, resc_id)
values (1, 443);
insert into SYS_ROLE_RESC (role_id, resc_id)
values (1, 481);
/*insert into SYS_ROLE_RESC (role_id, resc_id)
values (1, 20151025);
insert into SYS_ROLE_RESC (role_id, resc_id)
values (1, 201510251);
insert into SYS_ROLE_RESC (role_id, resc_id)
values (1, 201510252);
insert into SYS_ROLE_RESC (role_id, resc_id)
values (1, 201510253);
insert into SYS_ROLE_RESC (role_id, resc_id)
values (1, 201510254);
insert into SYS_ROLE_RESC (role_id, resc_id)
values (1, 201510255);
insert into SYS_ROLE_RESC (role_id, resc_id)
values (1, 201510256);
insert into SYS_ROLE_RESC (role_id, resc_id)
values (1, 201510257);*/


insert into SYS_USER (id, username, real_name, password, salt, user_type, email, mobile_no, org_id, org_name, create_time, last_modify_time, expiration_time, unlock_time, login_status, login_fail_times, login_time, status, descn)
values (1, 'admin', '张浦', 'ab7cc1898fe24ef738595e56759b17893f2dbcc6', 'f10a5bda42097431', 1, 'zp820705@163.com', '13896177630', null, null, to_date('07-01-2014', 'dd-mm-yyyy'), to_date('01-11-2015 00:49:20', 'dd-mm-yyyy hh24:mi:ss'), null, null, 1, 0, null, 1, null);



insert into SYS_USER_ROLE (role_id, user_id)
values (1, 1);



-- Add/modify columns
alter table EP_CUSTOMER add ID_CARD varchar2(64);
-- Add comments to the columns
comment on column EP_CUSTOMER.ID_CARD
  is '身份证号';


  -- 添加微信账号关联字段
alter table EP_CUSTOMER add OPENID varchar2(32);
-- Add comments to the columns
comment on column EP_CUSTOMER.OPENID
  is '微信用户在该公众号的唯一id';

-- 会员编号长度变更
alter table EP_ORDER_BASE modify CUS_NO NUMBER(12);

-- Add/modify columns
alter table EP_SERVICE_PERSON add EMP_NO varchar2(32);
-- Add comments to the columns
comment on column EP_SERVICE_PERSON.EMP_NO
  is '员工编号';

  -- Add/modify columns
alter table EP_COUPON add ORDER_NO varchar2(20);
-- Add comments to the columns
comment on column EP_COUPON.ORDER_NO
  is '使用该优惠券的订单号';

  -- Add/modify columns
alter table EP_ORDER_BASE add PAY_MODE varchar2(32);
-- Add comments to the columns
comment on column EP_ORDER_BASE.PAY_MODE
  is '支付方式';

-- Add/modify columns
alter table EP_ORDER_PZ add YBBX number(1) default 0 not null;
-- Add comments to the columns
comment on column EP_ORDER_PZ.YBBX
  is '是否医保报销';

  -- Add/modify columns
alter table EP_HOSPITAL add SERVICE_TYPE varchar2(32);
-- Add comments to the columns
comment on column EP_HOSPITAL.SERVICE_TYPE
  is '支持的服务类型';


-- Create table
create table EP_SERVICE_PRICE
(
  ID            number(20),
  HOSPITAL_ID   number(20),
  SERVICE_GRADE varchar2(32),
  PRICE         number(8,2),
  CREATE_TIME   date,
  UPDATE_TIME   date
)
;
-- Add comments to the columns
comment on column EP_SERVICE_PRICE.HOSPITAL_ID
  is '医院id';
comment on column EP_SERVICE_PRICE.SERVICE_GRADE
  is '服务等级';
comment on column EP_SERVICE_PRICE.PRICE
  is '价格';
comment on column EP_SERVICE_PRICE.CREATE_TIME
  is '创建时间';
comment on column EP_SERVICE_PRICE.UPDATE_TIME
  is '更新时间';
-- Create/Recreate primary, unique and foreign key constraints
alter table EP_SERVICE_PRICE
  add constraint primary_id primary key (ID);

  -- Add/modify columns
alter table EP_SERVICE_PRICE add SERVICE_TYPE varchar2(32);
-- Add comments to the columns
comment on column EP_SERVICE_PRICE.SERVICE_TYPE
  is '服务类型';

 CREATE SEQUENCE "SEQ_EP_SERVICE_PRICE"
INCREMENT BY 1
START WITH 1
 MAXVALUE 9999999999
 MINVALUE 1
CYCLE
 CACHE 20;

 -- Add/modify columns
alter table EP_ORDER_BASE add SERVICE_PRICE_ID number(20);
-- Add comments to the columns
comment on column EP_ORDER_BASE.SERVICE_PRICE_ID
  is '服务价格id';

  alter table EP_ORDER_BASE add PRICE number(8,2);
-- Add comments to the columns
comment on column EP_ORDER_BASE.PRICE
  is '费用';

  -- Add/modify columns
alter table EP_ORDER_BASE add SERVICE_GRADE varchar2(32);
-- Add comments to the columns
comment on column EP_ORDER_BASE.SERVICE_GRADE
  is '服务方式';


-- Drop indexes
drop index IDX_UNIQUE_NAMEEP_HOSPITAL;

create table SYS_SMS_LOG
(
  ID              NUMBER(19) not null,
  BATCH_NO        VARCHAR2(255 CHAR),
  CLIENT_IP       VARCHAR2(255 CHAR),
  COMMENTS        VARCHAR2(255 CHAR),
  CONTENT         VARCHAR2(255 CHAR),
  CREATE_TIME     TIMESTAMP(6),
  MOBILE_NO       VARCHAR2(255 CHAR),
  PROVIDER        VARCHAR2(255 CHAR),
  PROVIDER_MEMO   VARCHAR2(255 CHAR),
  PROVIDER_STATUS VARCHAR2(255 CHAR),
  SEND_TIME       TIMESTAMP(6),
  STATUS          NUMBER(10) not null,
  TIMER_TIME      TIMESTAMP(6)
)
;
alter table SYS_SMS_LOG
  add primary key (ID);


create sequence SEQ_EP_DEPARTMENT
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
cache 20
cycle;

create sequence SEQ_EP_ORDER
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
cache 20
cycle;

create sequence SEQ_EP_PZ_DETAIL
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
cache 20
cycle;
-- 用户名唯一约束
create unique index UNIQUE_USERNAME on EP_CUSTOMER (user_name);


