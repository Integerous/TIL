# 원격저장소에 Commit한 파일 삭제하기
>$ git rm --cached
>>.gitignore 파일에 내용을 제대로 갖추지 않아서 bin 폴더가 함께 원격 저장소에 Push 되었다.  
>>저장소 자체를 삭제하고 다시 만들까 생각했지만, git rm -r --cached bin 명령으로  
>>간단하게 bin 폴더만 원격저장소에서 제거할 수 있었다.

### 옵션
- `$ git rm [파일명]` : 원격저장소와 로컬저장소에 있는 파일 삭제
- `$ git rm --cached [파일명]` : 원격저장소의 파일만 삭제
- `$ git rm -r --cached [폴더명]` : 원격저장소의 폴더 내의 모든 파일 삭제

## Reference
- [GitHub Help - Removing files from a repository's history](https://help.github.com/articles/removing-files-from-a-repository-s-history/)
- http://mygumi.tistory.com/103
