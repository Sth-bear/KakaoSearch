![image](https://github.com/Sth-bear/SearchCollector/assets/72172581/5fa5830d-779e-4d2f-b625-8a518f3070fc)
위의 그림을 구상하고 진행하였습니다. 

DATA & RETROFIT 
  NetWorkClient 오브젝트에서 Retrofit을 Build 합니다. API Key를 헤더로 요구하기에 해당부분에서 추가합니다.
  Video & Image 가 요구되는 DTO가 다릅니다. 이에맞춰 따로 요청하는 Repository 와 DTO를 구현하였습니다. 각각의 Interface로 비동기 요청하게 됩니다. 

SearchViewModel

  	SearchFrag의 data만 담당합니다. Video 와 Image 의 DTO가 달라 이곳에서 AdaapterItem으로 mapping 합니다. 

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
