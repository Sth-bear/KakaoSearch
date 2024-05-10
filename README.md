![image](https://github.com/Sth-bear/KakaoSearch/assets/72172581/88bd81da-543b-42e3-8988-fb56ea650579)

위의 그림을 구상하고 진행하였습니다. 


![main](https://github.com/Sth-bear/KakaoSearch/assets/72172581/a7a5738c-e1ba-4567-8dad-ccb86b6a343d) ![scroll](https://github.com/Sth-bear/KakaoSearch/assets/72172581/aa47b573-9039-43c6-bd9b-7ea14fc36db4)

검색 시 로딩바와 함께 완료시 아이템이 표시됩니다. 



최대 4225개의 아이템까지 스크롤이 가능합니다. 한번에 25개의 아이템이 등장합니다. 


![bookmark](https://github.com/Sth-bear/KakaoSearch/assets/72172581/c6a7e8ae-606e-469f-a35a-1f90ce46bce9)

저장기능이 있습니다. 추가한 순서대로 표시됩니다. 삭제시 검색리스트에도 반영됩니다.


24.05.10 최신 fix

 	apiKey값에 관한 보안성을 위해 env방식을 채용했습니다. 
  	환경변수(key)는 도트 앤 이브 로 관리하며 해당 파일을 ignore 처리하여 등록되어 있지 않습니다. 
   	실제 파일을 테스트시 retrofit의 헤더값에 본인의 api key값을 넣어주시면 됩니다.(36번째 줄)
![image](https://github.com/Sth-bear/KakaoSearch/assets/72172581/973b5da8-99f7-4a4e-a5a9-dba3a102a45d)




DATA & RETROFIT 
  NetWorkClient 오브젝트에서 Retrofit을 Build 합니다. API Key를 헤더로 요구하기에 해당부분에서 추가합니다.
  Video & Image 가 요구되는 DTO가 다릅니다. 이에맞춰 따로 요청하는 Repository 와 DTO를 구현하였습니다. 각각의 Interface로 비동기 요청하게 됩니다. 

SearchViewModel
	
  	SearchFrag의 data만 담당합니다. Video 와 Image 의 DTO가 달라 이곳에서 AdaapterItem으로 mapping 합니다. 
   	무한스크롤을 구현하기 위해 현재 한번에 모든 검색을 하고, 이후 스크롤 시 마다 특정 갯수의 아이템을 리턴합니다. 
    	이를 위해 3개의 라이브데이터를 사용합니다. 
     	1. currentPage -> 스크롤이 끝에 닿으면 증가합니다. 해당값이 변경되면 리스트 추가를 요청하도록 옵저빙 중 입니다.
      	2. searchResult -> 해당 리스트가 변경되면 adapter를 새로고침하도록 옵저빙 중 입니다.
        3. totalSearchResult -> 최초 검색시, 검색이 완료되었음을 파악하기 위해 옵저빙 중 입니다.

BookMarkViewModel
  	요구사항 : 보관함에 저장한 순서대로 표시할 것. 

 	SharedPreferences 를 사용하고 있습니다. .all을 사용하여 모든 DATA를 받아올 시 저장한 순서대로 꺼내오지 않습니다. 
	이에 BookMark 저장시 순서를 기억하는 Long 타입의 sharedpreference 를 기반으로 순서를 같이 저장하는 dataClass : BookMarkItem 을 사용합니다. 
 
  	해당 뷰모델에서 Search에서 들어오는 AdapterItem 타입을 BookMarkItem 으로 변환하고 저장합니다.

  	Q. 왜 Long타입인가? 
	  	-> 리스트의 길이로 판단한다면, 저장된 값을 삭제하는 기믹을 사용시 계속해서 최대값이 바뀝니다. 이에 중복되는 번호 및 순서가 유지되지 않는다고 판단했습니다. 
	 	->Long 타입을 선언, 0 부터 아이템이 추가될때마다 1씩 증가시키도록 디자인했습니다. 이론상 9223372036854775807 번의 저장을 지원합니다. 현재 상태에서는 문제될것이 없다고 판단했습니다.

	Q. 최대치에 도달한다면?
		-> 현시점 해결책으로 어플이 실행될 때 마다 초기 세팅시 넘버링을 새로 하는 방법을 시도중입니다. 가능하다면 한번에 9223372036854775807번의 저장이 이루어지지 않는한 문제가 발생하지 않을것입니다. 
		-> 혹, 일정 횟수에 도달한다면 넘버링을 다시 하는 방식을 고민중입니다.
