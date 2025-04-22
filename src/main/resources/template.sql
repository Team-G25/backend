SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM templates;
DELETE FROM keywords;

ALTER TABLE templates AUTO_INCREMENT = 1;
ALTER TABLE keywords AUTO_INCREMENT = 1;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO keywords (keyword)
VALUES
    ('출결처리'), ('성적문의'), ('과제문의'), ('수강문의'),
    ('동료'), ('상사'), ('지각'), ('결석'), ('출석확인'),
    ('제출확인'), ('과제질문'), ('추가'), ('정정'),
    ('업무확인'), ('회의요청'), ('비용청구'),
    ('서류확인'), ('휴가');

-- Template 삽입
INSERT INTO templates (title, content, target_id, keyword1_id, keyword2_id) VALUES
    (
        '[출결 관련 문의] 지각 사유서 제출 및 출석 인정 요청',
        '안녕하세요 교수님,

        저는 [이름]입니다. 금일 진행된 [수업명] 수업에 부득이한 개인 사정으로 인해 예정된 시간보다 늦게 도착하게 되었습니다.

        출석을 위해 최선을 다해 서둘렀으나, [지각 사유 - 예: 교통 체증, 건강 문제] 등의 이유로 불가피하게 지각하게 되었습니다.
        이에 따라 지각 사유서를 제출하고자 하며, 혹시 출석으로 인정받을 수 있는 방법이 있는지 여쭙고자 합니다.

        추가적으로 보완할 서류나 절차가 있다면 안내해 주시면 감사하겠습니다.
        확인 부탁드리며, 바쁘신 와중에 메일 확인해 주셔서 감사합니다.

        좋은 하루 보내시길 바랍니다.
        감사합니다.

        [이름] 드림.',
        (SELECT id FROM targets WHERE target_name = '학생' LIMIT 1),
        (SELECT id FROM keywords WHERE keyword = '출결처리' LIMIT 1),
        (SELECT id FROM keywords WHERE keyword = '지각' LIMIT 1)
    ),
    (
        '[출석 확인 요청] 출석 여부 확인 및 정정 요청',
        '안녕하세요 교수님,

        저는 [이름]입니다. 오늘 [수업명] 수업에 정상적으로 참석하였으나, 출석 기록을 확인해 보니 누락된 것으로 보입니다.

        출석이 정상적으로 반영되지 않은 이유가 있을지 궁금하며, 혹시 기록 정정이 가능할지 여쭙고자 합니다.
        출석 증빙이 필요한 경우, [출석을 증명할 수 있는 방법 - 예: 수업 중 제출한 과제, 강의 녹화 캡처]을 제공할 수 있습니다.

        번거로우시겠지만 확인해 주시면 감사하겠습니다.
        좋은 하루 보내시길 바랍니다.

        감사합니다.

        [이름] 드림.',
        (SELECT id FROM targets WHERE target_name = '학생' LIMIT 1),
        (SELECT id FROM keywords WHERE keyword = '출결처리' LIMIT 1),
        (SELECT id FROM keywords WHERE keyword = '출석확인' LIMIT 1)
    ),
    (
        '[성적 관련 문의] 기말고사 성적 확인 및 채점 정정 요청',
        '안녕하세요 교수님,

        저는 [이름]입니다. 최근 발표된 기말고사 성적을 확인하던 중 몇 가지 궁금한 점이 있어 문의드립니다.

        성적 세부 내역을 확인하고 싶은데, 혹시 채점 기준을 명확히 알려주실 수 있을까요?
        또한, [질문할 항목 - 예: 특정 문제의 채점 방식, 감점 사유]에 대해 추가적인 설명을 부탁드립니다.

        만약 채점 오류가 있는 경우, 성적 정정이 가능한지 여부도 함께 문의드립니다.
        확인 후 회신해 주시면 감사하겠습니다.

        바쁘신 와중에도 귀한 시간 내어 주셔서 감사합니다.

        [이름] 드림.',
        (SELECT id FROM targets WHERE target_name = '학생' LIMIT 1),
        (SELECT id FROM keywords WHERE keyword = '성적문의' LIMIT 1),
        NULL
    ),
    (
        '[과제 관련 문의] 과제 제출 확인 및 평가 일정 문의',
        '안녕하세요 교수님,

        저는 [이름]입니다. 지난 [과제명] 과제를 제출하였는데, 정상적으로 제출이 완료되었는지 확인하고 싶습니다.

        혹시 과제 평가 일정이 언제쯤 진행될 예정인지 안내해 주실 수 있을까요?
        또한, 평가 기준에 대한 추가 설명이 가능하다면 알고 싶습니다.

        추가로 보완해야 할 사항이 있다면 미리 알려주시면 감사하겠습니다.
        바쁘신 와중에도 확인해 주셔서 감사합니다.

        좋은 하루 보내세요.

        [이름] 드림.',
        (SELECT id FROM targets WHERE target_name = '학생' LIMIT 1),
        (SELECT id FROM keywords WHERE keyword = '과제문의' LIMIT 1),
        (SELECT id FROM keywords WHERE keyword = '제출확인' LIMIT 1)
    ),
    (
        '[업무 진행 현황 공유 요청] 현재 프로젝트 진행 상황 확인',
        '안녕하세요 [동료 이름]님,

        진행 중인 [프로젝트명] 업무와 관련하여 현재 진행 상황을 공유해 주시면 감사하겠습니다.
        혹시 진행 중 어려움이 있거나 추가적인 지원이 필요한 부분이 있다면 말씀해 주세요.

        이번 프로젝트가 원활하게 마무리될 수 있도록 협력하고 싶습니다.
        가능하시다면 금일 중으로 진행 사항을 업데이트해 주시면 좋겠습니다.

        바쁘시겠지만 확인 부탁드립니다.
        감사합니다.

        [보내는 사람 이름] 드림.',
        (SELECT id FROM targets WHERE target_name = '직장인' LIMIT 1),
        (SELECT id FROM keywords WHERE keyword = '동료' LIMIT 1),
        (SELECT id FROM keywords WHERE keyword = '업무확인' LIMIT 1)
    ),
    (
        '[출장 경비 정산 요청] 비용 청구 및 처리 요청',
        '안녕하세요 [상사 이름]님,

        지난 [출장 일정] 동안 발생한 경비 청구를 위해 관련 서류를 첨부하여 전달드립니다.
        해당 경비 항목에 대한 검토 후 처리해 주시면 감사하겠습니다.

        혹시 추가적으로 필요한 자료나 절차가 있다면 안내 부탁드립니다.
        빠른 확인과 승인 부탁드립니다.

        감사합니다.

        [보내는 사람 이름] 드림.',
        (SELECT id FROM targets WHERE target_name = '직장인' LIMIT 1),
        (SELECT id FROM keywords WHERE keyword = '상사' LIMIT 1),
        (SELECT id FROM keywords WHERE keyword = '비용청구' LIMIT 1)
    ),
    (
        '[회의 요청] 팀 미팅 일정 조율 및 아젠다 공유',
        '안녕하세요 [동료 이름]님,

        현재 진행 중인 [프로젝트명]과 관련하여 팀 미팅을 진행하고자 합니다.
        주요 논의 아젠다는 다음과 같습니다.

        - [회의 주제 1]
        - [회의 주제 2]
        - [논의할 사항]

        편하신 시간대를 공유해 주시면, 가능한 일정에 맞춰 조율하도록 하겠습니다.
        회의 참석 가능 여부와 추가 논의하고 싶은 사항이 있다면 알려주세요.

        감사합니다.

        [보내는 사람 이름] 드림.',
        (SELECT id FROM targets WHERE target_name = '직장인' LIMIT 1),
        (SELECT id FROM keywords WHERE keyword = '동료' LIMIT 1),
        (SELECT id FROM keywords WHERE keyword = '회의요청' LIMIT 1)
    ),
    (
        '[휴가 신청 요청] 연차 휴가 일정 승인 요청',
        '안녕하세요 [상사 이름]님,

        개인적인 사정으로 인해 [휴가 날짜]에 연차 휴가를 신청하고자 합니다.
        업무 인수인계는 사전에 마무리하도록 하겠습니다.

        혹시 보완해야 할 사항이 있거나 대체 업무 조정이 필요하면 미리 안내해 주시면 감사하겠습니다.
        승인 가능 여부 확인 후 회신 부탁드립니다.

        바쁘시겠지만 검토해 주시면 감사하겠습니다.

        감사합니다.

        [보내는 사람 이름] 드림.',
        (SELECT id FROM targets WHERE target_name = '직장인' LIMIT 1),
        (SELECT id FROM keywords WHERE keyword = '상사' LIMIT 1),
        (SELECT id FROM keywords WHERE keyword = '휴가' LIMIT 1)
    );
