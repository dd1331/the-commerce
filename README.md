# 실행방법

* h2 db 메모리 실행방식을 사용하여 별도의 db 연결설정 없이 테스트와 앱 모두 실행이 가능합니다
* 포트등 기타 설정은 별도의 지정은 하지 않았습니다

# OPEN API

* http://localhost:8080/swagger-ui/index.html 에서 명세 확인이 가능합니다

# 회원가입

* 회원가입시 유저정보의 중복에 관한 요건을 이메일/핸드폰번호/아이디에 모두 적용해보았습니다. 구체적 요구사항이 있을 시를 대비하여 UserValidationService 인터페이스를 적용하였고 현재는 앞선 요건을
  구현한 DefaultUserValidationService를 사용하고 있으며 추후 변경시 UserService의 영향없이 알맞는 구현체로 변경이 가능하도록 했습니다

# Password

* embedded 형식으로 엔티티에 적용되었고 비밀번호의 역할에 집중하여 비밀번호 일치여부, 비밀번호 암호화의 기능을 합니다
