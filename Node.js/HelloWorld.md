# Hello Node
- 노드의 헬로월드 코드 링크 https://nodejs.org/dist/latest-v6.x/docs/api/synopsis.html
~~~C
const http = require('http');

const hostname = '127.0.0.1';
const port = 3000;

const server = http.createServer((req, res) => {
  res.statusCode = 200;
  res.setHeader('Content-Type', 'text/plain');
  res.end('Hello World!\n');
});

server.listen(port, hostname, () => {
  console.log(`Server running at http://${hostname}:${port}/`);
});
~~~
- 실행 명령
  - `$ node index.js`
- 실행 확인
  - `curl -X GET 'localhost:3000'`

## Hello Node 코드 분석
1. `const http = require('http')`
    - 노드의 기본모듈 중 http라는 모듈을 가져와서 http 변수에 할당
2. 호스트 네임(내컴퓨터 주소)과 포트 번호(클라이언트와 통신할 포트) 할당
3. `const server = http.createServer(req, res) =>{}`
    - (req, res)=> 는 es6문법으로 function(req, res)와 같다.
    - http모듈의 메소드 중 createServer메소드를 실행하고 콜백함수를 넣어준다.
    - 콜백함수는 클라이언트가 접속했을 때 동작하는 코드
4. `res.statusCode = 200;`
    - 200은 성공을 의미
5. `res.end('Hello Node\n');`
    - end함수를 통해서 헬로노드라는 문자열을 클라이언트에 보낸다.
5. `server.listen(port, hostname, () => {}`
    - listen은 서버를 요청 대기상태로 만들어주는 함수.
    - 요청대기 상태는 서버가 클라이언트의 요청을 받기 위해서 종료하지 않고 대기하는 상태
    - listen함수는 파라미터를 3개 받는다. `listen(port, hostname, (콜백함수) =>{} );`

6. `{console.log(`Server running at http://${hostname}:${port}/`);`
    - 콜백함수는 listen이라는 메서드가 완료되면 호출되는 함수이다.
    - String을 정의할때 템플릿 문자열 ( ` )를 썼다.
    - ${hostname}은 값으로 인식되어 박힌다.

## 라우터 추가
- 현재상태의 서버는 루트 경로로만 접속할 수 있는 서버이다.
  - 경로를 추가해도 계속 Hello Node만 응답해주는 상태이다.
  - 그러므로 다양한 요청에 응답하는 로직을 만들어야한다.
- 서버가 접속했을 때 응답하는 코드가 CreateServer의 콜백함수이다.
  - 요청한 사용자의 경로를 확인해보면 : `console.log(req.url);` - request객체의 url로 요청이 들어온다.
  - `if (req.rul === '/')` 등으로 분기문을 만들어서 다양한 요청에 응답하는 api를 만들 수 있다.
- 예시
~~~javascript
const server = http.createServer((req,res) => {
  if (req.url ==='/'){
    res.statusCode = 200;
    res.setHeader('Content-Type', 'text/plain');
    res.end('Hello Node\n');
  } else if (req.url === '/users') {
    res.statusCode = 200;
    res.setHeader('Content-Type', 'text/plain');
    res.end('User list');
  } else {
    res.statusCode = 404;
    res.end('Not Found');
  }
});
~~~

- 이렇게 api를 추가해 나갈 수 있지만 분기문이 계속 길어지고 코드도 반복된다.
- 그래서 http 모듈만 쓰지않고 Express.js라는 프레임워크를 사용한다.
