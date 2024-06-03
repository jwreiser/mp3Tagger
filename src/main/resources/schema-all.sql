CREATE TABLE IF NOT EXISTS BATCH_JOB_INSTANCE  (
                                     JOB_INSTANCE_ID VARCHAR(36) PRIMARY KEY,
                                     JOB_NAME VARCHAR(32),
                                     JOB_KEY VARCHAR(32),
                                     VERSION INTEGER,
                                     constraint JOB_INST_UN unique (JOB_NAME, JOB_KEY,VERSION)

);
