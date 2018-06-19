# CDATA 섹션
- CDATA 섹션에 의해 포함된 문자에 태그(>,<)가 없음을 파서에 알릴 수 있다.
~~~xml
<select id="listPage" resultType="BoardVO">
<![CDATA[
	select
		bno, title, content, writer, regdate, viewcnt
	from
		tbl_board
	where bno > 0
	order by bno desc, regdate desc
	limit #{page}, 10
]]>
</select>
~~~

# Reference
- https://msdn.microsoft.com/ko-kr/library/ms256076(v=vs.120).aspx
