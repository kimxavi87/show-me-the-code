graph 로 node는 어떠한 작업을 표시하고, link 는 그 작업에 대해 다른 선행되어야할 노드로부터의 연결이라고 할 때
indegree 가 0 인 node 부터 작업을 시작하도록 node 간 graph 연결을 만들고
indegree 가 0 인 노드 부터
queue 에 넣어서 작업을 시작
작업이 끝나면 link 들을 제거해주고
또 indegree 가 0 인 노드를 찾아서 queue 에 넣어주도록 반복


indegree : 노드가 받는 link 개수

