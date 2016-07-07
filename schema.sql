--------------------------------------------------------
--  File created - Wednesday-July-06-2016
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table ARCHIVE
--------------------------------------------------------
CREATE TABLE "ARCHIVE"
    (   "ID" NUMBER,
        "YEAR" NUMBER,
        "STU_SRC" VARCHAR2(999),
        "STU_TYPE" VARCHAR2(999),
        "BATCH" NUMBER
    ) ;

--------------------------------------------------------
--  DDL for Table UNIVERSITY
--------------------------------------------------------
CREATE TABLE "UNIVERSITY"
    (   "ID" NUMBER,
        "NAME" VARCHAR2(999),
        "PROVINCE" VARCHAR2(999),
        "IS_985" NUMBER,
        "IS_211" NUMBER
    ) ;

--------------------------------------------------------
--  DDL for Table RANKING
--------------------------------------------------------
CREATE TABLE "RANKING"
    (   "ID" NUMBER,
        "A_ID" NUMBER,
        "SCORE" NUMBER,
        "STU_COUNT" NUMBER,
        "RANK" NUMBER
    ) ;

--------------------------------------------------------
--  DDL for Table BATCH_SCORE
--------------------------------------------------------
CREATE TABLE "BATCH_SCORE"
    (   "ID" NUMBER,
        "A_ID" NUMBER,
        "MIN_SCORE" NUMBER
    ) ;

--------------------------------------------------------
--  DDL for Table ENROLLMENT_UNIVERSITY
--------------------------------------------------------
CREATE TABLE "ENROLLMENT_UNIVERSITY"
    (   "ID" NUMBER,
        "A_ID" NUMBER,
        "U_ID" NUMBER,
        "SCORE_AVG" NUMBER,
        "STU_COUNT" NUMBER
    ) ;

--------------------------------------------------------
--  DDL for Table ENROLLMENT_MAJOR
--------------------------------------------------------
CREATE TABLE "ENROLLMENT_MAJOR"
    (   "ID" NUMBER,
        "A_ID" NUMBER,
        "U_ID" NUMBER,
        "MAJOR" VARCHAR2(999),
        "SCORE_AVG" NUMBER
    ) ;
--------------------------------------------------------
--  DDL for Table TRANSLATION
--------------------------------------------------------
CREATE TABLE "TRANSLATION"
    (   "ID" NUMBER,
        "ENGLISH" VARCHAR2(999),
        "CHINESE" VARCHAR2(999)
    ) ;

--------------------------------------------------------
--  DDL for Index
--------------------------------------------------------
CREATE UNIQUE INDEX "ARCHIVE_PK" ON "ARCHIVE" ("ID")
;
CREATE UNIQUE INDEX "UNIVERSITY_PK" ON "UNIVERSITY" ("ID")
;
CREATE UNIQUE INDEX "RANKING_PK" ON "RANKING" ("ID")
;
CREATE UNIQUE INDEX "BATCH_SCORE_PK" ON "BATCH_SCORE" ("ID")
;
CREATE UNIQUE INDEX "ENROLLMENT_UNIVERSITY_PK" ON "ENROLLMENT_UNIVERSITY" ("ID")
;
CREATE UNIQUE INDEX "ENROLLMENT_MAJOR_PK" ON "ENROLLMENT_MAJOR" ("ID")
;
CREATE UNIQUE INDEX "TRANSLATION_PK" ON "TRANSLATION" ("ID")
;

--------------------------------------------------------
--  Constraints for Tables
--------------------------------------------------------
ALTER TABLE "ARCHIVE" ADD CONSTRAINT "ARCHIVE_PK" PRIMARY KEY ("ID")
USING INDEX  ENABLE;
ALTER TABLE "ARCHIVE" MODIFY ("ID" NOT NULL ENABLE);

ALTER TABLE "UNIVERSITY" ADD CONSTRAINT "UNIVERSITY_PK" PRIMARY KEY ("ID")
USING INDEX  ENABLE;
ALTER TABLE "UNIVERSITY" MODIFY ("ID" NOT NULL ENABLE);

ALTER TABLE "RANKING" ADD CONSTRAINT "RANKING_PK" PRIMARY KEY ("ID")
USING INDEX  ENABLE;
ALTER TABLE "RANKING" MODIFY ("ID" NOT NULL ENABLE);

ALTER TABLE "BATCH_SCORE" ADD CONSTRAINT "BATCH_SCORE_PK" PRIMARY KEY ("ID")
USING INDEX  ENABLE;
ALTER TABLE "BATCH_SCORE" MODIFY ("ID" NOT NULL ENABLE);

ALTER TABLE "ENROLLMENT_UNIVERSITY" ADD CONSTRAINT "ENROLLMENT_UNIVERSITY_PK" PRIMARY KEY ("ID")
USING INDEX  ENABLE;
ALTER TABLE "ENROLLMENT_UNIVERSITY" MODIFY ("ID" NOT NULL ENABLE);

ALTER TABLE "ENROLLMENT_MAJOR" ADD CONSTRAINT "ENROLLMENT_MAJOR_PK" PRIMARY KEY ("ID")
USING INDEX  ENABLE;
ALTER TABLE "ENROLLMENT_MAJOR" MODIFY ("ID" NOT NULL ENABLE);

ALTER TABLE "TRANSLATION" ADD CONSTRAINT "TRANSLATION_PK" PRIMARY KEY ("ID")
USING INDEX  ENABLE;
ALTER TABLE "TRANSLATION" MODIFY ("ID" NOT NULL ENABLE);
