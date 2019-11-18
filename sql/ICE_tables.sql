/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/11/18 14:25:41                          */
/*==============================================================*/


drop table if exists BELONG;

drop table if exists CATEGORIES;

drop table if exists CHART;

drop table if exists CONSOLES;

drop table if exists GAMES;

drop table if exists HAS_REVIEW;

drop table if exists HAS_TAG;

drop table if exists ORDERS;

drop table if exists PLAYED_ON;

drop table if exists PUBLISHERS;

drop table if exists RATE_GAME;

drop table if exists REVIEWS;

drop table if exists SALE_GAME;

drop table if exists TAGS;

drop table if exists USERS;

drop table if exists WISHLIST;

drop table if exists WRITE_REVIEW;


/*==============================================================*/
/* Table: CATEGORIES                                            */
/*==============================================================*/
create table CATEGORIES
(
   CATE_ID              int not null,
   CATE_NAME            varchar(30),
   primary key (CATE_ID)
);

/*==============================================================*/
/* Table: USERS                                                 */
/*==============================================================*/
create table USERS
(
   USER_ID              int not null,
   USER_NAME            varchar(20),
   GENDER               bool,
   BIRTHDAY             date,
   TEL                  char(11),
   AVATAR_PATH          varchar(100),
   ADDRESS              text,
   PWD                  varchar(20),
   primary key (USER_ID)
);

/*==============================================================*/
/* Table: CONSOLES                                              */
/*==============================================================*/
create table CONSOLES
(
   CONSOLE_ID           int not null,
   CONSOLE_NAME         varchar(25),
   primary key (CONSOLE_ID)
);

/*==============================================================*/
/* Table: GAMES                                                 */
/*==============================================================*/
create table GAMES
(
   GAME_ID              int not null,
   TITLE                varchar(50),
   PRICE                float,
   COVER_PATH           varchar(100),
   DISCOUNT             bool,
   AVERAGE_RATE         float,
   RELEASE_DATE         date,
   PRE_ORDER            bool,
   RATE_COUNT           int,
   DISCRIPTION          text,
   ON_SALE              bool,
   primary key (GAME_ID)
);


/*==============================================================*/
/* Table: ORDERS                                                */
/*==============================================================*/
create table ORDERS
(
   ORDER_ID             int not null,
   DATE                 date,
   ADDRESS              varchar(255),
   CONSOLE_ID           int not null,
   CONTACT_TEL          char(11),
   STATUS               int,
   USER_ID              int not null,
   GAME_ID              int not null,
   primary key (ORDER_ID),
   index (GAME_ID),
   index (USER_ID),
   index (CONSOLE_ID)
);
alter table ORDERS add constraint ORDERS_FK1 foreign key (GAME_ID)
      references GAMES (GAME_ID) on delete CASCADE on update CASCADE;
alter table ORDERS add constraint ORDERS_FK2 foreign key (USER_ID)
      references USERS (USER_ID) on delete CASCADE on update CASCADE;
alter table ORDERS add constraint ORDERS_FK3 foreign key (CONSOLE_ID)
      references CONSOLES (CONSOLE_ID) on delete CASCADE on update CASCADE;

/*==============================================================*/
/* Table: PUBLISHERS                                            */
/*==============================================================*/
create table PUBLISHERS
(
   PUBLISHER_ID         int not null,
   PUBLISHER_NAME       varchar(50),
   LOGO_PATH            varchar(100),
   PWD                  varchar(20),
   DISCRIPTION          text,
   primary key (PUBLISHER_ID)
);


/*==============================================================*/
/* Table: REVIEWS                                               */
/*==============================================================*/
create table REVIEWS
(
   REVIEW_ID            int not null,
   CONTENT              text,
   primary key (REVIEW_ID)
);

/*==============================================================*/
/* Table: TAGS                                                  */
/*==============================================================*/
create table TAGS
(
   TAG_ID               int not null,
   TAG_NAME             varchar(20),
   primary key (TAG_ID)
);

/*==============================================================*/
/* Table: WISHLIST                                              */
/*==============================================================*/
create table WISHLIST
(
   GAME_ID              int not null,
   USER_ID              int not null,
   NOTIFICATION         bool,
   primary key (GAME_ID, USER_ID),
   index (GAME_ID),
   index (USER_ID)
);
alter table WISHLIST add constraint WISHLIST_FK1 foreign key (GAME_ID)
      references GAMES (GAME_ID) on delete CASCADE on update CASCADE;
alter table WISHLIST add constraint WISHLIST_FK2 foreign key (USER_ID)
      references USERS (USER_ID) on delete CASCADE on update CASCADE;


/*==============================================================*/
/* Table: WRITE_REVIEW                                          */
/*==============================================================*/
create table WRITE_REVIEW
(
   REVIEW_ID            int not null,
   USER_ID              int not null,
   primary key (REVIEW_ID),
   index (REVIEW_ID),
   index (USER_ID)
);
alter table WRITE_REVIEW add constraint WRITE_REVIEW_FK1 foreign key (REVIEW_ID)
      references REVIEWS (REVIEW_ID) on delete CASCADE on update CASCADE;
alter table WRITE_REVIEW add constraint WRITE_REVIEW_FK2 foreign key (USER_ID)
      references USERS (USER_ID) on delete CASCADE on update CASCADE;

/*==============================================================*/
/* Table: BELONG                                                */
/*==============================================================*/
create table BELONG
(
   GAME_ID         int not null,
   CATE_ID         int not null,
   primary key (GAME_ID),
   index (GAME_ID),
   index (CATE_ID)
);
alter table BELONG add constraint BELONG_FK1 foreign key (GAME_ID)
      references GAMES (GAME_ID) on delete CASCADE on update CASCADE;
alter table BELONG add constraint BELONG_FK2 foreign key (CATE_ID)
      references CATEGORIES (CATE_ID) on delete CASCADE on update CASCADE;

/*==============================================================*/
/* Table: CHART                                                 */
/*==============================================================*/
create table CHART
(
   GAME_ID           int not null,
   USER_ID           int not null,
   primary key (GAME_ID, USER_ID),
   index (GAME_ID),
   index (USER_ID)
);
alter table CHART add constraint CHART_FK1 foreign key (GAME_ID)
      references GAMES (GAME_ID) on delete CASCADE on update CASCADE;
alter table CHART add constraint CHART_FK2 foreign key (USER_ID)
      references USERS (USER_ID) on delete CASCADE on update CASCADE;

/*==============================================================*/
/* Table: HAS_REVIEW                                            */
/*==============================================================*/
create table HAS_REVIEW
(
   REVIEW_ID            int not null,
   GAME_ID              int not null,
   primary key (REVIEW_ID, GAME_ID),
   index (GAME_ID),
   index (REVIEW_ID)
);
alter table HAS_REVIEW add constraint HAS_REVIEW_FK1 foreign key (GAME_ID)
      references GAMES (GAME_ID) on delete CASCADE on update CASCADE;
alter table HAS_REVIEW add constraint HAS_REVIEW_FK2 foreign key (REVIEW_ID)
      references REVIEWS (REVIEW_ID) on delete CASCADE on update CASCADE;

/*==============================================================*/
/* Table: HAS_TAG                                               */
/*==============================================================*/
create table HAS_TAG
(
   GAME_ID                 int not null,
   TAG_ID                  int not null, 
   primary key (GAME_ID, TAG_ID),
   index (GAME_ID),
   index (TAG_ID)
);
alter table HAS_TAG add constraint HAS_TAG_FK1 foreign key (GAME_ID)
      references GAMES (GAME_ID) on delete CASCADE on update CASCADE;
alter table HAS_TAG add constraint HAS_TAG_FK2 foreign key (TAG_ID)
      references TAGS (TAG_ID) on delete CASCADE on update CASCADE;

/*==============================================================*/
/* Table: PLAYED_ON                                             */
/*==============================================================*/
create table PLAYED_ON
(
   GAME_ID                 int not null,
   CONSOLE_ID              int not null,
   primary key (GAME_ID, CONSOLE_ID),
   index (GAME_ID),
   index (CONSOLE_ID)
);
alter table PLAYED_ON add constraint PLAYED_ON_FK1 foreign key (GAME_ID)
      references GAMES (GAME_ID) on delete CASCADE on update CASCADE;
alter table PLAYED_ON add constraint PLAYED_ON_FK2 foreign key (CONSOLE_ID)
      references CONSOLES (CONSOLE_ID) on delete CASCADE on update CASCADE;

/*==============================================================*/
/* Table: RATE_GAME                                             */
/*==============================================================*/
create table RATE_GAME
(
   GAME_ID             int not null,
   USER_ID             int not null,
   RATE                int,
   primary key (GAME_ID, USER_ID),
   index (GAME_ID),
   index (USER_ID)
);
alter table RATE_GAME add constraint RATE_GAME_FK1 foreign key (GAME_ID)
      references GAMES (GAME_ID) on delete CASCADE on update CASCADE;
alter table RATE_GAME add constraint RATE_GAME_FK2 foreign key (USER_ID)
      references USERS (USER_ID) on delete CASCADE on update CASCADE;

/*==============================================================*/
/* Table: SALE_GAME                                             */
/*==============================================================*/
create table SALE_GAME
(
   PUBLISHER_ID                 int not null,
   GAME_ID                      int not null,
   primary key (PUBLISHER_ID, GAME_ID),
   index (GAME_ID),
   index (PUBLISHER_ID)
);
alter table SALE_GAME add constraint SALE_GAME_FK1 foreign key (GAME_ID)
      references GAMES (GAME_ID) on delete CASCADE on update CASCADE;
alter table SALE_GAME add constraint SALE_GAME_FK2 foreign key (PUBLISHER_ID)
      references PUBLISHERS (PUBLISHER_ID) on delete CASCADE on update CASCADE;


