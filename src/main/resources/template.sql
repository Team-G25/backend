SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM templates;
DELETE FROM keywords;

ALTER TABLE templates AUTO_INCREMENT = 1;
ALTER TABLE keywords AUTO_INCREMENT = 1;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO keywords (keyword)
VALUES
    -- 학생 키워드 관련
    ('성적문의'), -- keyword1
    ('성적 정정'), -- keyword2
    ('성적 확인'), -- keyword2

    ('출결문의'), -- keyword1
    ('출석확인'), -- keyword2
    ('지각'), -- keyword2
    ('결석'), -- keyword2

    ('과제문의'), -- keyword1
    ('제출 확인'), -- keyword2
    ('과제'), -- keyword2

    ('수강문의'),  -- keyword1
    ('수강'),  -- keyword2
    ('추가 수강'),  -- keyword1

    -- 직장인 키워드 관련
    ('상사'), -- keyword1
    ('휴가 요청'), -- keyword2
    ('비용 청구'), -- keyword2
    ('업무 보고'), -- keyword2
    ('상담 요청'), -- keyword2

    ('동료'), -- keyword1
    ('업무 확인'),  -- keyword2
    ('회의 조율'),  -- keyword2
    ('피드백 요청');  -- keyword2

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
        (SELECT id FROM keywords WHERE keyword = '출결문의' LIMIT 1),
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
        (SELECT id FROM keywords WHERE keyword = '출결문의' LIMIT 1),
        (SELECT id FROM keywords WHERE keyword = '결석' LIMIT 1)
    ),
    (
        '[성적 관련 문의] [과목명] 성적 정정 요청',
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
        (SELECT id FROM keywords WHERE keyword = '성적 정정' LIMIT 1)
    ),
    (
        '[성적 관련 문의] [과목명] 점수 확인',
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
        (SELECT id FROM keywords WHERE keyword = '성적 확인' LIMIT 1)
    ),
    (
        '[과제 관련 문의] 과제 제출 확인',
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
        (SELECT id FROM keywords WHERE keyword = '제출 확인' LIMIT 1)
    ),
    (
        '[과제 관련 문의] 과제 관련 문의',
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
        (SELECT id FROM keywords WHERE keyword = '과제' LIMIT 1)
    ),
    (
        '[수강 관련 문의] 수강 관련 문의',
        '안녕하세요 교수님,

        저는 [이름]입니다. 이번학기에 [강의명]을 수강하고 싶습니다.

        혹시 수업 커리큘럼이 언제쯤 진행될 공개 예정인지 궁금하여 문의드립니다.
        또한, 평가 기준도 궁금합니다.

        바쁘신 와중에도 확인해 주셔서 감사합니다.

        좋은 하루 보내세요.

        [이름] 드림.',
        (SELECT id FROM targets WHERE target_name = '학생' LIMIT 1),
        (SELECT id FROM keywords WHERE keyword = '수강문의' LIMIT 1),
        (SELECT id FROM keywords WHERE keyword = '수강' LIMIT 1)
    ),
    (
        '[수강 관련 문의] 추가 수강 가능 여부 문의',
        '안녕하세요 교수님,

        저는 [이름]입니다. 이번 수강신청에 [강의명]을 실패하여 하였습니다.[수업명] 수업을 추가로 수강하고 싶어 이렇게 연락드립니다.
        제가 이번학기에 꼭 들어야 졸업이 가능한 상황이기에 수강을 해야만 합니다.

        학습 의지가 높아 수업 참여에 문제가 없을 것이며, 혹시 추가 수강이 가능할지 여쭙고자 합니다

        바쁘신 와중에도 확인해 주셔서 감사합니다. 회신부탁드립니다.

        좋은 하루 보내세요.

        [이름] 드림.',
        (SELECT id FROM targets WHERE target_name = '학생' LIMIT 1),
        (SELECT id FROM keywords WHERE keyword = '수강문의' LIMIT 1),
        (SELECT id FROM keywords WHERE keyword = '추가 수강' LIMIT 1)
    ), -- 학생 끝
    (
        '[업무 진행 현황 공유] 현재 프로젝트 진행 상황 보고',
        '안녕하세요 [상사 이름]님,

        현재 진행 중인 [프로젝트명] 업무에 대한 상황을 공유드립니다.

        - [업무 항목 1]은 현재 완료되어 검토 중에 있으며,
        - [업무 항목 2]는 내부 검토를 거쳐 이번 주 중으로 마무리할 예정입니다.
        - 또한, [업무 항목 3]에 대해서는 현재 진행률이 약 70%이며, 추가 논의가 필요한 부분은 별도로 정리해 두었습니다.

        전체 일정에는 큰 차질은 없으나, 혹시 협업 과정에서 조율이 필요한 사항이 있다면 말씀해 주세요. 프로젝트가 원활히 마무리될 수 있도록 지속적으로 공유드리겠습니다.

        확인 부탁드리며, 추가 의견이나 요청사항이 있으시면 언제든지 알려주시기 바랍니다.
        감사합니다.

        [이름] 드림.',
        (SELECT id FROM targets WHERE target_name = '직장인' LIMIT 1),
        (SELECT id FROM keywords WHERE keyword = '상사' LIMIT 1),
        (SELECT id FROM keywords WHERE keyword = '업무 보고' LIMIT 1)
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
        (SELECT id FROM keywords WHERE keyword = '비용 청구' LIMIT 1)
    ),
    (
        '[상담 요청 문의] 업무 관련 진행 이슈 및 방향성 논의 요청',
        '안녕하세요 [상사 이름]님

        현재 담당 중인 [프로젝트명] 업무를 진행하며 몇 가지 고려해야 할 사안이 있어 간단히 상담을 요청드리고자 합니다.

        - [이슈 항목 1]: 현재 상황 및 우려되는 부분 요약
        - [이슈 항목 2]: 선택 가능한 대응 방향 및 예상 영향
        - [협의 필요사항]: 최종 결정에 대한 방향성 논의 필요


        업무에 지장을 드리지 않는 범위 내에서, 짧게라도 방향성에 대해 논의할 수 있는 시간이 있을지 여쭙습니다. 편하신 시간대를 알려주시면 일정을 맞추도록 하겠습니다.

        항상 지원해주셔서 감사드리며, 긍정적인 검토 부탁드립니다.

        감사합니다.
        [이름] 드림.',
        (SELECT id FROM targets WHERE target_name = '직장인' LIMIT 1),
        (SELECT id FROM keywords WHERE keyword = '상사' LIMIT 1),
        (SELECT id FROM keywords WHERE keyword = '상담 요청' LIMIT 1)
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
        (SELECT id FROM keywords WHERE keyword = '휴가 요청' LIMIT 1)
    ),
    (
        '[업무 확인 요청] 현재 업무 진행 상황 공유 요청',
        '안녕하세요 [동료 이름]님,

        [프로젝트명] 관련하여 현재 맡고 계신 업무의 진행 상황을 간단히 공유해 주실 수 있을지 문의드립니다.

        프로젝트 일정이 차질 없이 진행될 수 있도록 각자 진행 중인 항목을 상호 공유하고자 하며, 현재 상태 및 남은 작업에 대한 간략한 요약을 부탁드립니다.
        필요 시 지원이 필요한 부분이 있다면 말씀해 주세요. 함께 원활히 마무리할 수 있도록 하겠습니다.

        감사합니다.
        [이름] 드림.',
        (SELECT id FROM targets WHERE target_name = '직장인' LIMIT 1),
        (SELECT id FROM keywords WHERE keyword = '동료' LIMIT 1),
        (SELECT id FROM keywords WHERE keyword = '업무 확인' LIMIT 1)
    ),
    (
        '[회의 일정 조율 요청] 미팅 스케줄 협의 및 참석 가능 시간 문의',
        '안녕하세요 [동료 이름]님,

        [주제/프로젝트명] 관련하여 팀 내 미팅을 조율하고자 합니다. 가능한 일정 중 참석이 편하신 시간을 공유해 주시면 감사하겠습니다.

        미팅의 주요 논의 내용은 아래와 같습니다:
        - [안건 1]
        - [안건 2]
        가능하시다면 이번 주 중 미팅을 진행하고 싶습니다. 일정 확인 부탁드리며, 참석이 어려운 시간대가 있다면 함께 알려주세요.

        감사합니다.
        [이름] 드림.',
        (SELECT id FROM targets WHERE target_name = '직장인' LIMIT 1),
        (SELECT id FROM keywords WHERE keyword = '동료' LIMIT 1),
        (SELECT id FROM keywords WHERE keyword = '회의 조율' LIMIT 1)
    ),
    ('[피드백 요청] 작업물 검토 및 의견 요청',
     '안녕하세요 [동료 이름]님,

    최근 작업한 [작업물/문서/기획안 이름]을 공유드립니다.
    완성 전에 사전에 피드백을 받아 더 나은 방향으로 개선하고자 하오니, 시간 괜찮으실 때 검토 부탁드립니다.

    특히 [피드백 받고 싶은 항목 - 예: 표현 방식, 기능 설계, 흐름 등]에 대해 의견 주시면 큰 도움이 될 것 같습니다.
    n바쁘시겠지만 검토해 주시면 감사하겠습니다. 관련 자료는 첨부파일/링크로 함께 전달드리겠습니다.

    감사합니다.
    [이름] 드림.',
     (SELECT id FROM targets WHERE target_name = '직장인' LIMIT 1),
     (SELECT id FROM keywords WHERE keyword = '동료' LIMIT 1),
     (SELECT id FROM keywords WHERE keyword = '피드백 요청' LIMIT 1))
    ;




