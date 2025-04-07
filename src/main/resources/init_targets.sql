-- 외래 키 해제 (잠시)
SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM targets ; -- 테이블 초괴화
ALTER TABLE targets AUTO_INCREMENT = 1;
INSERT INTO targets (target_name) VALUES ('직장인'), ('학생'); -- 초기데이터

-- ALTER TABLE targets ADD CONSTRAINT unique_target_name UNIQUE (target_name); -- 대상 중복 방지
